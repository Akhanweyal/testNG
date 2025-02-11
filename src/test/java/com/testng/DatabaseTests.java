package com.testng;

import java.sql.SQLException;

import org.testng.annotations.Test;

import utilities.db;

public class DatabaseTests {
    db DB = new db();

    @Test(priority = 1)
    public void testInsertUser() {

        db.insertUser("Khan", "Khan786");
        db.insertUser("Khan2", "Khan2786");

    }

    @Test(priority = 2)
    public void testGetUsers() {

        db.getUsers();
    }

    @Test(priority = 3)
    public void removeDoplicate() {
        db.removeDuplicateUsers();
    }

    @Test(priority = 4)
    public void testGetUsersAfterDelete() {
        db.getUsers();
        System.out.println("After deleting duplicate users");

    }

    @Test
    public void closeconnection() {
        try {
            db.closeConnection(db.getConnection());
            System.out.println("Connection closed successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}