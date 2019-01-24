package com.swayam.demo.springbootdemo.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ConfigurableWebEnvironment;

import com.swayam.demo.springbootdemo.service.BankDetailService;

@RestController
@RequestMapping("/")
public class BankDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDetailController.class);

    private final ConfigurableWebEnvironment configurableWebEnvironment;

    private final BankDetailService bankDetailService;

    public BankDetailController(ConfigurableWebEnvironment configurableWebEnvironment, BankDetailService bankDetailService) {
        this.configurableWebEnvironment = configurableWebEnvironment;
        this.bankDetailService = bankDetailService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> getBankDetails() {
        LOGGER.info("getting bank details");
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("active-profiles", Arrays.asList(configurableWebEnvironment.getActiveProfiles()));
        map.put("data", bankDetailService.getBankDetails());
        return map;
    }

    @RequestMapping(value = "profile/{profileId}", method = RequestMethod.GET)
    public boolean setProfile(@PathVariable String profileId) {
        LOGGER.info("the current active profile is: {}", Arrays.asList(configurableWebEnvironment.getActiveProfiles()));
        LOGGER.info("setting the profile to: {}", profileId);
        configurableWebEnvironment.setActiveProfiles(profileId);
        return true;
    }

}
