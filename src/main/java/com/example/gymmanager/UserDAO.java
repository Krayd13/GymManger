package com.example.gymmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private DatabaseHandler databaseHandler;


    public UserDAO(DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
    }

    public void addUser(User user){
        String sql = "INSERT INTO " + DbConst.USER_TABLE + "(" +
                DbConst.USERS_NAME + ", " +
                DbConst.USERS_SURNAME + ", " +
                DbConst.USERS_FATHERSNAME + ", " +
                DbConst.USERS_EMAIL + ", " +
                DbConst.USERS_PASSWORD + ", " +
                DbConst.USERS_ROLE + ") VALUES(?, ?, ?, ?, ?, ?)";
        try(Connection connection = databaseHandler.getDbConnection()) {
            databaseHandler.executeUpdate(connection, sql, user.getName(), user.getSurname(), user.getFathersname(), user.getEmail(),
                    user.getPassword(), user.getRole());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean userExist(User user){
        String sql = "SELECT COUNT(*) FROM " + DbConst.USER_TABLE + " WHERE " + DbConst.USERS_EMAIL + " = ?";
        try(Connection connection = databaseHandler.getDbConnection()){
            ResultSet result = databaseHandler.executeQuery(connection, sql, user.getEmail());
            if(result.next()){
                int count = result.getInt(1);
                if(count > 0) {
                    return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Problem with check user in method userExist");
        return true;
    }
}
