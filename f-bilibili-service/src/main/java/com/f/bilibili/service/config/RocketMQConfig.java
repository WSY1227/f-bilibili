package com.f.bilibili.service.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.f.bilibili.domain.UserFollowing;
import com.f.bilibili.domain.UserMoments;
import com.f.bilibili.domain.constant.UserMomentsConstant;
import com.f.bilibili.service.UserFollowingService;
import com.mysql.cj.util.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RocketMQConfig
 * @Description: RocketMQ配置文件
 * @author: XU
 * @date: 2022年05月06日 1:37
 **/
@Configuration
public class RocketMQConfig {
    /**
     * name服务器注入
     */
    @Value("${rocketmq.name.server.address}")
    private String nameServerAddr;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserFollowingService userFollowingService;

    /**
     * 创建消息生产着
     */
    @Bean("momentsProducer")
    public DefaultMQProducer momentsProducer() throws MQClientException {
        /**创建MQ生产者并传入命名*/
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
        /**引入name服务器地址*/
        producer.setNamesrvAddr(nameServerAddr);
        /**启动*/
        producer.start();
        return producer;
    }

    /**
     * 创建消息消费者
     */
    @Bean("momentsConsumer")
    public DefaultMQPushConsumer momentsConsumer() throws MQClientException {
        /**创建MQ消费者并传入命名*/
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
        /**引入name服务器地址*/
        consumer.setNamesrvAddr(nameServerAddr);
        /**设置订阅内容*/
        consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS, "*");
        /**设置监听器*/
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt msg = msgs.get(0);
                if (msg == null) {
                    /**msg为空直接返回成功*/
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                /**moments取出并直接转化为字符串*/
                String bodyStr = new String(msg.getBody());
                /**转化为JSONObject，类似于Map*/
                UserMoments userMoments = JSONObject.toJavaObject(JSONObject.parseObject(bodyStr), UserMoments.class);
                /**获取发布动态的用户id*/
                Long userId = userMoments.getUserId();
                /**获取用户粉丝列表*/
                List<UserFollowing> userFans = userFollowingService.getUserFans(userId);
                for (UserFollowing userFan : userFans) {
                    /**生成f粉丝在redis中订阅的key*/
                    String key = "subscribed-" + userFan.getUserId();
                    /**拿到粉丝存在redis中的订阅列表（暂时拿取到的是String型，后面在进行转化）*/
                    String subscribedListStr = redisTemplate.opsForValue().get(key);
                    /**定义用户订阅的列表*/
                    List<UserMoments> subscribedList;
                    if (StringUtils.isNullOrEmpty(subscribedListStr)) {
                        /**如果为空则生成一个空列表*/
                        subscribedList = new ArrayList<>();
                    } else {
                        /**将subscribedListStr转化为列表*/
                        subscribedList = JSONArray.parseArray(subscribedListStr, UserMoments.class);
                    }
                    /**将订阅信息进行添加*/
                    subscribedList.add(userMoments);
                    /**再转化为JSON字符串发送到redis*/
                    redisTemplate.opsForValue().set(key, JSONObject.toJSONString(subscribedList));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        return consumer;

    }
}
