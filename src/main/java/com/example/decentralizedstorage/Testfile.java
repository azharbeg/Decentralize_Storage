package com.example.decentralizedstorage;

import java.sql.Connection;
import java.sql.DriverManager;

public class Testfile {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://storagedb.cx4ao4wy8ki0.eu-north-1.rds.amazonaws.com:5432/database-1";
        String username = "adminnew";
        String password = "adminadmin";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
