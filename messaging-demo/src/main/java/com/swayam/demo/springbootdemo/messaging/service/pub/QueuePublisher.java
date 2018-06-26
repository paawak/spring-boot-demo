package com.swayam.demo.springbootdemo.messaging.service.pub;

import com.swayam.demo.springbootdemo.messaging.model.BankDetail;

public interface QueuePublisher {

    void publish(BankDetail bankDetail);

}