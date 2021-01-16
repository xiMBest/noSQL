package com.ivanyuk.azure.service;

import com.microsoft.azure.eventhubs.EventHubException;
import org.json.JSONArray;

import java.io.IOException;

public interface InterfaceForSending {
    void sendAndLog(String url) throws IOException, EventHubException;

    void sendAndLog(JSONArray jsonArray, int startRaw, int endRaw);
}
