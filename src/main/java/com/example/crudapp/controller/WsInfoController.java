package com.example.crudapp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WsInfoController {
    @GetMapping(value = "/ws", produces = MediaType.TEXT_PLAIN_VALUE)
    public String info() {
        return "SOAP endpoint is available. Use POST with Content-Type: text/xml to /ws and include a SOAP 1.1 envelope (see Postman collection).";
    }
}
