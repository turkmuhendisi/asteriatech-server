package com.teknofest.asteriatech_server.controller;

import com.teknofest.asteriatech_server.model.Greeting;
import com.teknofest.asteriatech_server.model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting("Hello " + HtmlUtils.htmlEscape(message.getName()));
    }
}
