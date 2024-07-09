package com.imdb.ws.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class RequestCountService {

    private AtomicLong requestCount = new AtomicLong(0);

    public void incrementRequestCount() {
        requestCount.incrementAndGet();
    }

    public long getRequestCount() {
        return requestCount.get();
    }
}
