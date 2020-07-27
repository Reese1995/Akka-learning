package com.example.message;

import java.io.Serializable;

import lombok.AllArgsConstructor;

/**
 * @author Reese
 * 消息始终都应该是不可变的
 */
@AllArgsConstructor
public class SetRequest implements Serializable {

    public final String key;
    public final Object value;
}
