package com.example.demo1.controller.domain;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ParamsOne {
    private String title;
//    private LocalDateTime localDateTime;
    private String content;
    private String username;
}
