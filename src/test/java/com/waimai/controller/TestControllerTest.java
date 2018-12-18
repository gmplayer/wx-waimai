package com.waimai.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestControllerTest {

    @Autowired
    RestTemplate rest;


    @Test
    public void restApi() {
        String url = "http://127.0.0.1:8080/buyer/product/list";
        String result = rest.getForObject(url,String.class);
        log.info("result:{}",result);
    }
}