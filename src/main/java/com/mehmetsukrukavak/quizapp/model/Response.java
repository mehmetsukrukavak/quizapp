package com.mehmetsukrukavak.quizapp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@RequiredArgsConstructor
public class Response {
    private Integer id;
    private String response;
}
