package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HttpClientUtils {

    private static final String USERNAME = "testuser";

    @Autowired
    private TestRestTemplate restTemplate;

    public <B, R> ResponseEntity<R> getWithoutAuthentication(String url, Class<R> returnType) {
        return restTemplate.exchange(url, HttpMethod.GET, null, returnType);
    }

    public <B, R> ResponseEntity<R> get(String url, Class<R> returnType) {
        return restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(USERNAME, null), returnType);
    }

    public <B, R> ResponseEntity<R> delete(String url, Class<R> returnType) {
        return restTemplate.exchange(url, HttpMethod.DELETE, getHttpEntity(USERNAME, null), returnType);
    }

    public <B, R> ResponseEntity<R> post(String url, B body, Class<R> returnType) {
        return restTemplate.exchange(url, HttpMethod.POST, getHttpEntity(USERNAME, body), returnType);
    }

    public <B, R> ResponseEntity<R> put(String url, B body, Class<R> returnType) {
        return restTemplate.exchange(url, HttpMethod.PUT, getHttpEntity(USERNAME, body), returnType);
    }

    private <T> HttpEntity getHttpEntity(String username, T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("username", username);
        return new HttpEntity<>(body, headers);
    }

}
