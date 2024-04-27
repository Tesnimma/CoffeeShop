package Owner;

import java.sql.*;

public class MyConnection {
    public static Connection getConnection(String url, String username, String password){
        //chargement Driver
        String nomDriver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(nomDriver);
            System.out.println("Driver chargé");
        } catch (ClassNotFoundException e) {
            System.out.println("erreur " + e.getMessage());
        }

        //cnx a la base

        Connection con = null;
        Statement st = null;

        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("connected!");
        } catch (SQLException e) {
            System.out.println("erreur cnx" + e.getMessage());
            return null;
        }

        return con;
    }
}
