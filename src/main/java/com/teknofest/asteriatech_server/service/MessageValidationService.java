package com.teknofest.asteriatech_server.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MessageValidationService {

    public static <E extends Enum<E>> boolean isValidAction(Class<E> enumClass, String message){
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(x -> x.name().equalsIgnoreCase(message));
    }
}
