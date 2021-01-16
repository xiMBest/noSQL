package com.ivanyuk.azure.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class EventHub implements InterfaceForSending {

    private EventHubClient client;

    public void SendEvent(){
    ConnectionStringBuilder connectionStringBuilder = new ConnectionStringBuilder()
            .setNamespaceName("naidaspace")
            .setEventHubName("naidapolicy")
            .setSasKeyName("Endpoint=sb://naidaspace.servicebus.windows.net/;SharedAccessKeyName=naidapolicy;SharedAccessKey=g5KmnS9i688ZCmn0YKsKnzg4dVEYvDavKQ/QorzEJro=;EntityPath=naidavitalii\"")
            .setSasKey("g5KmnS9i688ZCmn0YKsKnzg4dVEYvDavKQ/QorzEJro=");

    final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        try {
            this.client = EventHubClient.createSync(connectionStringBuilder.toString(), executorService);
        } catch (EventHubException | IOException exception) {
            exception.printStackTrace();
        }
    }


    public void sendAndLog(JSONArray jsonArray) {
        final Gson gson = new GsonBuilder().create();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            byte[] payloadBytes = gson.toJson(jsonObject).getBytes(Charset.defaultCharset());
            EventData sendEvent = EventData.create(payloadBytes);

            try {
                client.sendSync(sendEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    public void showData(JSONArray jsonArray, Gson gson, EventHubClient ehClient) throws EventHubException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            System.out.println("The information is recorded in Event Hub: " + i);
            byte[] payloadBytes = gson.toJson(jsonObject).getBytes(Charset.defaultCharset());
            EventData sendEvent = EventData.create(payloadBytes);

            ehClient.sendSync(sendEvent);
        }
    }


    @Override
    public void sendAndLog(String url) throws IOException, EventHubException {

    }

    @Override
    public void sendAndLog(JSONArray jsonArray, int startRaw, int endRaw) {

    }
}
