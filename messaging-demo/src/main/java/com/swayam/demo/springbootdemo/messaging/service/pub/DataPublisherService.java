package com.swayam.demo.springbootdemo.messaging.service.pub;

import com.swayam.demo.springbootdemo.messaging.model.BankDetailSortOrder;

public interface DataPublisherService {

    void publishData(BankDetailSortOrder bankDetailGroups);

}
