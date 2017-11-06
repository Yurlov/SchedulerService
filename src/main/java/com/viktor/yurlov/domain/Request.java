package com.viktor.yurlov.domain;


public class Request<T> {

    private T body;

    public Request(T body) {
        this.body = body;
    }

    public Request() {
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
