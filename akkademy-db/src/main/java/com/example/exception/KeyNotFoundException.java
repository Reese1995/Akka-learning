package com.example.exception;

import java.io.Serializable;

/**
 * @author Reese
 */
public class KeyNotFoundException extends Exception implements
  Serializable {

    public final String key;

    public KeyNotFoundException(String key) {
        this.key = key;
    }
}