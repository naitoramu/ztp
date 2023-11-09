package io.nightovis.ztp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String DB_URL = "jdbc:h2:mem:test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWD = "";

    private static Connection INSTANCE;

    public static Connection getConnection(){
        if (INSTANCE == null) {
            try {
                Class.forName("org.h2.Driver");
                INSTANCE = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return INSTANCE;
    }
}
