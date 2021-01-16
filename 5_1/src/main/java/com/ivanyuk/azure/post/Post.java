package com.ivanyuk.azure.post;

import com.ivanyuk.azure.dto.DTO;
import com.ivanyuk.azure.service.InterfaceForSending;
import com.ivanyuk.azure.Strategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class Post {

    private Strategy strategy;

    public Post(Strategy strategy) {
        this.strategy = strategy;
    }

    @PostMapping("/url")
    public void addNewUrl(@RequestBody DTO dto) {
        InterfaceForSending logService = this.strategy.getService(dto.getStrategy());
        try {
            logService.sendAndLog(dto.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}