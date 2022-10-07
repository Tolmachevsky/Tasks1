package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;

public class Main {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    public static void main(String[] args) throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("testName", "testLastName", (byte) 12);
        userService.saveUser("testName", "testLastName", (byte) 13);
        userService.saveUser("testName", "testLastName", (byte) 14);
        System.out.println(userService.getAllUsers().size());

    }
}
