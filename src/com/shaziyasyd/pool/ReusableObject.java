package com.shaziyasyd.pool;

import java.util.Date;

public abstract class ReusableObject {
    private final long createdAt =  new Date().getTime();
    private boolean isClosed;

    public boolean isClosed() {
        return this.isClosed;
    }

    public void close() {
        this.isClosed = true;
    }

    public long getCreatedAt() {
        return this.createdAt;
    }

}
