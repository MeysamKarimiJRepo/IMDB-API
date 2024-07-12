package com.imdb.ws.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class FileConfiguration {
    @Value("${file.list}")
    private String fileList;

    public List<String> getFileList() {
        return Arrays.asList(fileList.split(","));
    }
}
