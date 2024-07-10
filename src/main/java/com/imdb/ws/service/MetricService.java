package com.imdb.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricService {

    @Autowired
    private RequestCountService requestCountService;

    public long getRequestCount() {
        return requestCountService.getRequestCount();
    }
}
