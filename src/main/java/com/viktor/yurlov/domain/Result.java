package com.viktor.yurlov.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue
    @JsonIgnore
    private int id;
    @JsonProperty("code")
    private int code;
    @JsonProperty("body")
    private String body;

    public Result(int code, String body) {
        this.code = code;
        this.body = body;
    }

    public Result() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
