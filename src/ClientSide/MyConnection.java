package ClientSide;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
    public static Connection getConnection(String url, String username, String password){
        //chargement Driver
        String nomDriver = "com.mysql.cj.jdbc.Driver";
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
