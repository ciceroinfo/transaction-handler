package com.ciceroinfo.transactionhandler.util;

import com.github.dockerjava.api.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Slf4j
@Component
public class RestTemplateResponseErrorHandler
        implements ResponseErrorHandler {
    
    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {
        
        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }
    
    // Bypass any http error
    // only to be validated for Cucumber
    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        log.info("Http status code {}", httpResponse.getStatusCode());
    }
}