package com.f.bilibili.service.util;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RocketMQUtil
 * @Description: RocketMQ工具类
 * @author: XU
 * @date: 2022年05月06日 12:05
 **/

public class RocketMQUtil {
    /**
     * 同步发送信息的方法
     */
    public static void syncSend(DefaultMQProducer producer, Message msg) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        SendResult result = producer.send(msg);
        System.out.println(result);
    }

    /**
     * 异步同步消息
     */
    public static void asyncSend(DefaultMQProducer producer, Message msg) throws RemotingException, InterruptedException, MQClientException {
        /**计数*/
        int messageCount = 2;
        /**设置计数器*/
        CountDownLatch2 countDownLatch2 = new CountDownLatch2(messageCount);
        for (int i = 0; i < messageCount; i++) {
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    /**成功发生消息*/
                    countDownLatch2.countDown();
                    /**输出当前消息id*/
                    System.out.println(sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    /**发生失败*/
                    System.out.println("发生消息的时候发生了异常");
                    /**打印异常*/
                    throwable.printStackTrace();
                }
            });
        }
        /**设置计时器停留5秒钟*/
        countDownLatch2.await(5, TimeUnit.SECONDS);

    }
}
