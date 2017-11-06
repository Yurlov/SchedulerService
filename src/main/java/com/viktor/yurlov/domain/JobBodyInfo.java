package com.viktor.yurlov.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobBodyInfo<T> {

    @JsonProperty("body")
    private T body;

    public JobBodyInfo(T body) {
        this.body = body;
    }

    public JobBodyInfo() {
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
