package com.f.bilibili.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.f.bilibili.dao.UserMomentsDao;
import com.f.bilibili.domain.UserMoments;
import com.f.bilibili.domain.constant.UserMomentsConstant;
import com.f.bilibili.service.util.RocketMQUtil;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserMomentsService
 * @Description:
 * @author: XU
 * @date: 2022年05月06日 13:31
 **/

@Service
public class UserMomentsService {
    @Autowired
    private UserMomentsDao userMomentsDao;
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 新增动态服务
     */
    public void addUserMoments(UserMoments userMoments) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        /**新增创建时间*/
        userMoments.setCreateTime(new Date());
        /**新增动态*/
        userMomentsDao.addUserMoments(userMoments);
        /**通过applicationContext获取到生产者消费者*/
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("momentsProducer");
        Message msg = new Message(UserMomentsConstant.TOPIC_MOMENTS, JSONObject.toJSONString(userMoments).getBytes(StandardCharsets.UTF_8));
        RocketMQUtil.syncSend(producer, msg);
    }

    /**
     * 通过用户id获取订阅的动态
     *
     * @param userId 需要查询的用户id
     * @return 返回获取的动态
     */
    public List<UserMoments> getUserSubscribedMoments(Long userId) {
        /**生成用户在redis中订阅的key*/
        String key = "subscribed-" + userId;
        /**从redis获取用户所有的订阅信息*/
        String listStr = redisTemplate.opsForValue().get(key);
        /**将从redis获取到的数据转化为列表并返回*/
        return JSONArray.parseArray(listStr, UserMoments.class);
    }
}
