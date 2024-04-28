package Owner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.sql.SQLException;

public class LoginPanell extends JDialog{
    private JTextField textField1;
    private JPasswordField passwordField1;
    public JButton loginButton;
    private JPanel LoginPanel;
    ProduitDAO daoa;
    LoginPanell(JFrame parent,ProduitDAO dao,GestionProduits gp){
        super(parent);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("daoa==null");
                if(daoa==null)
                {
                    System.out.println("daoa==null");
                    return;
                }
                try {
                    String req = "select * from users where email=? and password=?";
                    var rs = daoa.GetLoginInfos(req, textField1.getText(), passwordField1.getText());
                    if (rs == null) {
                        JOptionPane.showMessageDialog(LoginPanel, "Invalid email or password", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (!rs.next())
                            JOptionPane.showMessageDialog(LoginPanel, "Invalid email or password", "Error", JOptionPane.ERROR_MESSAGE);
                        else {
                            dispose();
                            gp.setVisible(true);
                            return;
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(LoginPanel, "Connection problem", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.daoa = dao;
        setTitle("Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}

