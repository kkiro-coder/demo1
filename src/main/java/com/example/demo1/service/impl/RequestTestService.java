package com.example.demo1.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static org.springframework.web.context.request.RequestAttributes.*;

@Service
public class RequestTestService {

    public void showRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        Arrays.stream(
//                requestAttributes.getAttributeNames(SCOPE_REQUEST)).map(name -> requestAttributes.getAttribute(name, SCOPE_REQUEST))
//                .forEach(System.out::println);
        System.out.println(Arrays.asList(requestAttributes.getAttributeNames(SCOPE_REQUEST)));

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        request.getParameterMap().entrySet().forEach(x -> System.out.println(x.getKey() + ":" + Arrays.toString(x.getValue())));
    }
}
