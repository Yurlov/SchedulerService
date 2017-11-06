package com.viktor.yurlov.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name = "id")
    private long id;
    @Pattern(regexp = "POST|GET|PUT|DELETE")
    private String method;
    private String url;
    private String data;


    @JsonProperty("headers")
    @ElementCollection
    @MapKeyColumn(name = "key_column")
    @Column(name = "value_column")
    @CollectionTable(name = "headers",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"))
    private Map<String, String> headers = new HashMap<>();

    public Task() {
    }

    public Task(String method, String url, Map<String, String> headers, String data) {
        this.method = method;
        this.url = url;
        this.data = data;
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
