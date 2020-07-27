package com.example.message;

import java.io.Serializable;

import lombok.Getter;

/**
 * @author Reese
 * 消息始终都应该是不可变的
 */
public class GetRequest implements Serializable {

    public final String key;

    public GetRequest(String key) {
        this.key = key;
    }
}