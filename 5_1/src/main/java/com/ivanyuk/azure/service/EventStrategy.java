package com.ivanyuk.azure.service;

public class EventStrategy extends Template{
        public EventStrategy() {
            super(new EventHub());
        }
    }