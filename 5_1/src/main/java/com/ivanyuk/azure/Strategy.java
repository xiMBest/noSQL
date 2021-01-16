package com.ivanyuk.azure;

import com.ivanyuk.azure.service.EventHub;
import com.ivanyuk.azure.service.InterfaceForSending;
import com.ivanyuk.azure.service.Redis;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class Strategy {

    private Map<String, InterfaceForSending> logServiceMap;

    public Strategy() {
        logServiceMap = createStrategies();
    }

    public InterfaceForSending getService(String repositoryName) {
        return logServiceMap.get(repositoryName);
    }

    public Set<String> getAllNames() {
        return logServiceMap.keySet();
    }

    public Collection<InterfaceForSending> getAllServices(){
        return logServiceMap.values();
    }

    private Map<String, InterfaceForSending> createStrategies(){
        Map<String, InterfaceForSending> strategies = new HashMap<>();
        strategies.put("eventHub", new EventHub());
        strategies.put("redis", new Redis());
        return strategies;
    }
}