package com.ivanyuk.azure.service;

import com.microsoft.azure.eventhubs.EventHubException;
import java.io.IOException;

public interface InterfaceForSending {
    void sendAndLog(String url) throws IOException, EventHubException;
}
