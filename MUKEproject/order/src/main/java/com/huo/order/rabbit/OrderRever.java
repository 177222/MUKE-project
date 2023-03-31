package com.huo.order.rabbit;

import com.huo.order.entity.SmsVo;
import com.huo.order.sms.SmsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderRever {

    @Autowired
    private SmsService smsService;

    @RabbitListener( queues = "${spring.rabbitmq.queue}")
    public void process(SmsVo smsVo){
        //System.out.println("得到通知，开始发送 = " + smsVo.getCourseName());

        // 调用发送短信
        smsService.sendSms(smsVo.getPhone(), smsVo.getCourseName());
    }
}
