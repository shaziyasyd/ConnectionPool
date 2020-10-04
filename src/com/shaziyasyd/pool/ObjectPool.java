package com.shaziyasyd.pool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class ObjectPool<T extends ReusableObject> {

    private int ttlInMillis = 50000;
    private List<T> inUse;
    private List<T> available;
    private final int maxPoolSize;

    protected ObjectPool(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        this.available = new ArrayList<>();
        this.inUse = new ArrayList<>();
    }

    //Object related methods to  be implemented in child class
    protected abstract T create();
    protected abstract void close(T t);
    protected abstract boolean isValid(T t);

    protected boolean isExpired(T t) {
        long now = new Date().getTime();
        return now - t.getCreatedAt() >= ttlInMillis;
    }

    public synchronized T acquire() throws ObjectPoolFullException {
        int availableCount = available.size();
        T objectToAcquire = null;
        while (availableCount > 0) {
            objectToAcquire = available.get(0);
            boolean isExpired = isExpired(objectToAcquire);
            boolean isValid = isValid(objectToAcquire);
            if(isExpired || !isValid) {
                close(objectToAcquire);
                available.remove(objectToAcquire);
                objectToAcquire = null;
                availableCount--;
            } else {
                available.remove(objectToAcquire);
                break;
            }
        }
        if(maxPoolSize == inUse.size()) {
            throw new ObjectPoolFullException(maxPoolSize);
        }
        if(objectToAcquire == null) {
            objectToAcquire = create();
        }
        inUse.add(objectToAcquire);
        return objectToAcquire;
    }

    public void release(T t) {
        boolean isExpired = isExpired(t);
        boolean isValid = isValid(t);
        inUse.remove(t);
        if(isExpired || !isValid) {
            close(t);
        } else {
            available.add(t);
        }
    }


}
