package com.swayam.demo.springbootdemo.messaging.dao;

import com.swayam.demo.springbootdemo.messaging.model.BankDetailSortOrder;
import com.swayam.demo.springbootdemo.messaging.service.pub.QueuePublisher;

public interface BankDetailDao {

    void publishRawBankDetailsAsync(BankDetailSortOrder bankDetailGroups, QueuePublisher dataPublisher);

}