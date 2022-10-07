package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String TABLE_PARAMS = "create table if not exists users (id bigint primary key auto_increment, name varchar(256), lastName varchar(256), age tinyint)";
    private static final Connection connection = Util.openConnection();
    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(TABLE_PARAMS);
            statement.close();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("drop table users");
            statement.close();
        } catch (SQLException e){
            if (e.getMessage().equals("Unknown table 'mydb.users'")){
                System.out.println("Таблица не существует");
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("insert into users (name, lastName, age) values('" + name + "', '" + lastName+"' ," + age + ")");
            statement.close();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("delete from users where id = " + id);
            statement.close();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        ArrayList <User> userArrayList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            statement.execute("select * from users");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()){
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                User user = new User(name,lastName,age);
                user.setId(id);
                userArrayList.add(user);
            }
            statement.close();
            return userArrayList;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("truncate users");
            statement.close();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
