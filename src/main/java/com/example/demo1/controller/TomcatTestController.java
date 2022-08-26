package com.example.demo1.controller;

import com.example.demo1.controller.domain.ParamsOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(path = "/api/tomcat/test")
public class TomcatTestController {

    @GetMapping(path = "/testPost")
    public Object testPost() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", "kkiro");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        ParamsOne paramsOne = ParamsOne.builder().username("xudi").title("this is tomcat request")
                .content("if a cat called tomcat. its a contain").build();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramsOne, httpHeaders);
        String url = "http://127.0.0.1:8080/test-servlet/customTest";
        ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
        return exchange.getBody();
    }
}
