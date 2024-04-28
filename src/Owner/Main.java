package Owner;

import java.io.IOException;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        ProduitDAO dao = new ProduitDAO(Config.URL, Config.USERNAME, Config.PASSWORD);
        var gp =new  GestionProduits(dao);
        gp.setVisible(false);
        LoginPanell loginPanel = new LoginPanell(null,dao,gp);

    }

}