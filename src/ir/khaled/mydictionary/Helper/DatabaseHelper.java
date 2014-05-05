package ir.khaled.mydictionary.Helper;

import java.sql.*;

/**
 * Created by khaled.bakhtiari on 5/2/2014.
 */
public class DatabaseHelper {
    private static DatabaseHelper instance;
    private static final String DATABASE_NAME = "mydictionary";

    private Connection connection;


    public static DatabaseHelper getInstance() {
        if (instance == null)
            instance = new DatabaseHelper();
        return instance;
    }

    private DatabaseHelper() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            Statement statement = connection.createStatement();
            statement.execute("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
