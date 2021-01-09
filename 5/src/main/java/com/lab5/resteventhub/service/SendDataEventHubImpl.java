package com.lab5.resteventhub.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class SendDataEventHubImpl implements SendDataService {

    public void sendAndLog(String url) throws IOException, EventHubException {
        final ConnectionStringBuilder connStr = new ConnectionStringBuilder()
                .setNamespaceName("naidaspace")//namespace
                .setEventHubName("naidapolicy")//hub name
                /*Connection string–primary key*/
                .setSasKeyName("Endpoint=sb://naidaspace.servicebus.windows.net/;SharedAccessKeyName=naidapolicy;SharedAccessKey=g5KmnS9i688ZCmn0YKsKnzg4dVEYvDavKQ/QorzEJro=;EntityPath=naidavitalii")
                /*Primary key*/
                .setSasKey("g5KmnS9i688ZCmn0YKsKnzg4dVEYvDavKQ/QorzEJro=");

        final Gson gson = new GsonBuilder().create();
        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        final EventHubClient ehClient = EventHubClient.createSync(connStr.toString(), executorService);

        try {
            URL data = new URL(url);
            HttpURLConnection con = (HttpURLConnection) data.openConnection();
            int responseCode = con.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            showData(jsonArray, gson, ehClient);

            System.out.println(Instant.now() + ": Send Complete...");
            System.out.println("Press Enter to stop.");
            System.in.read();
        } finally {
            ehClient.closeSync();
            executorService.shutdown();
        }
    }

    public void showData(JSONArray jsonArray, Gson gson, EventHubClient ehClient) throws EventHubException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            System.out.println("Document: " + i);
            byte[] payloadBytes = gson.toJson(jsonObject).getBytes(Charset.defaultCharset());
            EventData sendEvent = EventData.create(payloadBytes);

            ehClient.sendSync(sendEvent);
        }
    }
}
