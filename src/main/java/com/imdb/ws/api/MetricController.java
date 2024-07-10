package com.imdb.ws.api;

import com.imdb.ws.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrics")
public class MetricController {

    @Autowired
    private MetricService metricService;

    @GetMapping("/request-count")
    public long getRequestCount() {
        return metricService.getRequestCount();
    }
}
