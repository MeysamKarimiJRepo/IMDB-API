package com.imdb.ws.filter;


import com.imdb.ws.service.RequestCountService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestCountFilter implements Filter {


    private final RequestCountService requestCountService;

    public RequestCountFilter(RequestCountService requestCountService) {
        this.requestCountService = requestCountService;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        //TODO: Initialization code needed
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            requestCountService.incrementRequestCount();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //TODO: Cleanup code if needed
    }
}

