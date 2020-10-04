package com.shaziyasyd.pool;

import java.util.Date;

public class Connection extends ReusableObject {

    public void execute(String q) {
        System.out.println("Executing query "+q);
    }
}
