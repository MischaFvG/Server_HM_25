package com.cat;

import com.cat.serverAPI.ServerAPI;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ServerAPI serverAPI = new ServerAPI();
        System.out.println(serverAPI.getAllStudentsByAge("2021-01-03 00:00:00"));
        System.out.println(serverAPI.getStudentByName("Tom"));
    }
}
