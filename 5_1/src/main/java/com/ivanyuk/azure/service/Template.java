package com.ivanyuk.azure.service;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public abstract class Template {
    private InterfaceForSending interfaceForSending;

    public Template(EventHub eventHub) {
    }

    protected void Templete(InterfaceForSending interfaceForSending) {
        this.interfaceForSending = interfaceForSending;
    }
        public void sendAndLog (String url){
            try {
                URL data = new URL(url);
                HttpURLConnection con = (HttpURLConnection) data.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                int count = 1;
                int startRaw = 1;
                int limit = 100;
                int endRaw = 0;
                JSONArray jsonArray;
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                    if (count == limit) {
                        jsonArray = new JSONArray(response.toString() + ']');
                        endRaw = endRaw + count;
                        startRaw = startRaw + count;
                        interfaceForSending.sendAndLog(jsonArray, startRaw, endRaw);
                        count = 0;
                    }count += 1;
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}