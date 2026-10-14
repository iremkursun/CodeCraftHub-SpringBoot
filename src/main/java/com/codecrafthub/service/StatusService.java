package com.codecrafthub.service;

import com.codecrafthub.ApiStatus;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    public ApiStatus getStatus() {

        return new ApiStatus(
                "UP",
                "CodeCraft Hub API is running!"
        );
    }
}