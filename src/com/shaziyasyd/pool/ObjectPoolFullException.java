package com.shaziyasyd.pool;

public class ObjectPoolFullException extends Exception {
    public ObjectPoolFullException(int poolsize) {
        super("Object pool is full. Maximum pool size is "+poolsize);
    }
}
