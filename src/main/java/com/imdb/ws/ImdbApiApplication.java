package com.imdb.ws;

import com.imdb.ws.filter.RequestCountFilter;
import com.imdb.ws.service.RequestCountService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ImdbApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImdbApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public FilterRegistrationBean<RequestCountFilter> loggingFilter(RequestCountService requestCountService) {
        FilterRegistrationBean<RequestCountFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestCountFilter(requestCountService));
        registrationBean.addUrlPatterns("/api/titles/*");
        return registrationBean;
    }

}
