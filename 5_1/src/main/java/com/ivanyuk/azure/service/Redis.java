package com.ivanyuk.azure.service;

import com.microsoft.azure.eventhubs.EventHubException;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Service
public class Redis implements InterfaceForSending {

    private static final boolean USE_SSL = true;
    private static final int MAX_NUMBER = 1000;
    private static String fileName;

    private final static String CACHE_HOSTNAME = "naidaredis.redis.cache.windows.net";
    private final static String CACHE_KEY = "8pnqNS641FJFNJLRwLpr4J2pKzs7YGSALnpZst9TfVA=";
    private final static String MAP_NAME = "ConsoleLog";
    private final static String FILE_NAME = "service.impl.Console";
    private final static int PORT = 6380;
    private Object String;

    private final Jedis jedis = new Jedis();


    @Override
    public void sendAndLog(java.lang.String url) throws IOException, EventHubException {

    }

    public void sendAndLog(JSONArray jsonArray, int startRaw, int endRaw) {
        jedis.hset(MAP_NAME, "File", "None");
        Map<String, String> redisData = jedis.hgetAll(MAP_NAME);

        assignTempFileName();
        if (checkIfFileExist(jedis)) {
            jedis.set("Raws", startRaw + ":" + endRaw);
            showData(jsonArray.length(), jsonArray, jedis, redisData);
        }
    }

    public void showData(int count, JSONArray jsonArray, Jedis jedis, Map<String, String> map) {
        jedis.hset(MAP_NAME, "Raws", "" + count);
        if (jsonArray.length() != MAX_NUMBER) {
            jedis.hset(MAP_NAME, fileName, jsonArray.toString());
            jedis.hset(MAP_NAME, "File", fileName);

            jedis.hset(MAP_NAME, "Status", "NotFinished");
        } else {
            jedis.hset(MAP_NAME, "Raws", "" + count);
            jedis.hset(MAP_NAME, "Status", "Completed");
            jedis.hset(MAP_NAME, "Info", "First attempt to input this file");
            jedis.close();
        }
    }

    public boolean checkIfFileExist(Jedis jedis) {
        Map<String, String> map = jedis.hgetAll(MAP_NAME);
        String name = map.get("File");
        String status = map.get("Status");

        if (!name.equals(fileName)) {
            jedis.hset(MAP_NAME, "File", fileName);
        } else {
            if (status.equals("Completed")) {
                jedis.hset(MAP_NAME, "Info", "Retry to input this file");
                return false;
            }
        }
        return true;
    }

    private static void assignTempFileName() {
        fileName = "FILE_" + Instant.now();
    }
}