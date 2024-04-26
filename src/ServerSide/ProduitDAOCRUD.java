package ServerSide;

import java.sql.ResultSet;

public interface ProduitDAOCRUD {
    public int insertProd(String name, String category, int id, double price);
    public int suppProd(int id);
    public int modifProd(String name, String category, int id, double price);
    public ResultSet afficheAll(String req);
}
