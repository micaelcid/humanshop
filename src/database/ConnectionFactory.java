package database;

import java.sql.*;

public class ConnectionFactory {
    public static Connection getConnection() throws SQLException {
        String host = "ec2-174-129-229-162.compute-1.amazonaws.com";
        String database = "d9m9tm9qalohse";
        String user = "pvdgbtllnprbeb";
        String password = "f2ab9f017384e11dce4f4b1891fa7fc249a50b0b311c5084a3063065d4bdf0c9";
        String url = "jdbc:postgresql://" + host + "/" + database + "?sslmode=require";

        return DriverManager.getConnection(url, user, password);
    }
}
