package com.ivanyuk.azure.dto;

public class DTO {

    private String url;

    private String strategy;

    public DTO(String url, String strategy) {
        this.url = url;
        this.strategy = strategy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
}