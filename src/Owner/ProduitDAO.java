package Owner;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProduitDAO implements ProduitDAOCRUD {
    Connection con = null;
    public ProduitDAO(String url, String username, String password){
        con = MyConnection.getConnection(url, username, password);
    }

    @Override
    public int insertProd(String name, String category, int id, double price) {
        String req = "insert into Produit values(?, ?, ?, ?)";
        PreparedStatement ps = null;
        if (con != null){
            try {
                ps = con.prepareStatement(req);
                ps.setString(1, name);
                ps.setString(2, category);
                ps.setInt(3, id);
                ps.setDouble(4, price);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else System.out.println("Error in insert produit");
        try {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int suppProd(int id) {
        //kamalni
        PreparedStatement ps = null;
        int lignesSupprimes = 0;

        try {
            if (con != null) {
                String deleteQuery = "DELETE FROM Produit WHERE id=?";
                ps = con.prepareStatement(deleteQuery);
                ps.setInt(1, id);
                lignesSupprimes = ps.executeUpdate();
                System.out.println(lignesSupprimes + " lignes supprimes.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
        return lignesSupprimes;
    }

    @Override
    public int modifProd(String name, String category, int id, double price) {
        PreparedStatement ps = null;
        int lignesModifies = 0;
        try {
            if (con != null) {
                String updateQuery = "UPDATE Produit SET name=?, category=?, price=? WHERE id=?";
                ps = con.prepareStatement(updateQuery);
                ps.setString(1, name);
                ps.setString(2, category);
                ps.setInt(4, id);
                ps.setDouble(3, price);
                lignesModifies = ps.executeUpdate();
                System.out.println(lignesModifies + " lignes modifiees.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
        return lignesModifies;
    }

    @Override
    public ResultSet afficheAll(String req) {
        afficheResultSet(selection("select * from Produit;"));
        return null;
    }
    ResultSet selection(String req){
        PreparedStatement st2 = null;
        ResultSet rs = null;
        try {
            st2 = con.prepareStatement(req);
            rs = st2.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
            return null;
        }
    }
    ResultSet GetLoginInfos(String req, String email, String password){
        PreparedStatement st2 = null;
        ResultSet rs = null;
        try {
            st2 = con.prepareStatement(req);
            st2.setString(1,email);
            st2.setString(2,password);
            rs = st2.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
            return null;
        }
    }
    public HashMap<String,CategoryInfos> getCategories()
    {
        HashMap<String,CategoryInfos> categoryInfos = new HashMap<>();
        var rs = selection("select * from Produit;");
        try{
            while(rs.next()){
                String name = rs.getString(1);
                String category = rs.getString(2);
                int id = rs.getInt(3);
                double price = rs.getDouble(4);
                var itemsList = categoryInfos.get(category);
                if(itemsList!=null)
                    itemsList.items.add(new Item(name,price,id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  categoryInfos;
    }
    void afficheResultSet(ResultSet rs){
        try{
            while(rs.next()){
                String name = rs.getString(1);
                String category = rs.getString(2);
                int id = rs.getInt(3);
                double price = rs.getDouble(4);
                System.out.println("ID: "+id+" Category: "+category+" Name: "+name+" Price: "+price);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
