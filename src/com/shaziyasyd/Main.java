package com.shaziyasyd;

import com.shaziyasyd.pool.Connection;
import com.shaziyasyd.pool.ConnectionPool;
import com.shaziyasyd.pool.ObjectPoolFullException;

public class Main {

    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getInstance(2);

        try {
            Connection con1 = pool.acquire();
            Connection con2 = pool.acquire();
            pool.release(con2);
            Connection con3 = pool.acquire();
            con1.execute("Select * from table x");
            pool.release(con1);
        } catch (ObjectPoolFullException e) {
            e.printStackTrace();
        }
    }
}
