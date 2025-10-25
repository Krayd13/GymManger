package com.example.gymmanager;

import java.sql.*;

public class DatabaseHandler extends Configs{

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public int executeUpdate(Connection dbConnection, String sql, Object... params) throws SQLException {
        PreparedStatement preparedStatement = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for(int i = 0; i < params.length; i++){
            preparedStatement.setObject(i + 1, params[i]);
        }

        int affectedRows = preparedStatement.executeUpdate();
        return affectedRows;
    }

    public ResultSet executeQuery(Connection dbConnection, String sql, Object... params) throws SQLException {
        PreparedStatement preparedStatement = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for(int i = 0; i < params.length; i++){
            preparedStatement.setObject(i + 1, params[i]);
        }

        return preparedStatement.executeQuery();
    }
}
