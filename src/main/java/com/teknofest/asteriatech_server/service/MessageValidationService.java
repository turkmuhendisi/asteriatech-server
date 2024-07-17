package com.teknofest.asteriatech_server.service;

import com.teknofest.asteriatech_server.enums.CentralVisionAction;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MessageValidationService {

    public static boolean isEnumValue(String message){
        return Arrays.stream(CentralVisionAction.values())
                .anyMatch(x -> x.name().equalsIgnoreCase(message));
    }
}
