package com.viktor.yurlov.adapter;


import com.viktor.yurlov.domain.Task;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Component
public class HttpAdapter {
    private String METHOD;
    private String URL;
    private HttpHeaders httpHeaders;
    private String DATA;
    private RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    public ResponseEntity send(Task task) {
        this.DATA = task.getData();
        this.METHOD = task.getMethod();
        this.URL = task.getUrl();
        for (Object key : task.getHeaders().keySet()) {
            httpHeaders.set(String.valueOf(key), String.valueOf(task.getHeaders().get(key)));
        }
        switch (METHOD) {
            case "POST":
                return doPost();
            case "DELETE":
                return doDelete();
            case "PUT":
                return doPut();
            default:
                return doGet();
        }

    }

    private ResponseEntity doGet() throws RestClientException {
        return restTemplate.getForObject(URL, ResponseEntity.class, httpHeaders);
    }

    private ResponseEntity doPost() throws RestClientException {
        HttpEntity<?> requestEntity = new HttpEntity<>(DATA, httpHeaders);
        return restTemplate.postForObject(URL, requestEntity, ResponseEntity.class);
    }


    private ResponseEntity doPut() {
        HttpEntity<?> requestEntity = new HttpEntity<>(DATA, httpHeaders);
        restTemplate.put(URL, requestEntity, ResponseEntity.class);
        return new ResponseEntity(HttpStatus.OK);
    }


    private ResponseEntity doDelete() {
        HttpEntity<?> requestEntity = new HttpEntity<>(DATA, httpHeaders);
        restTemplate.delete(URL, requestEntity);
        return new ResponseEntity(HttpStatus.OK);

    }
}
