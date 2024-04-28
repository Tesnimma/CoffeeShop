package Owner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPanel extends JFrame {

    JTextField login, password;
    ProduitDAO daoa;

    LoginPanel(ProduitDAO dao,GestionProduits gp) {
        this.daoa = dao;
        setLayout(new BorderLayout());
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        login = new JTextField(40);
        password = new JTextField(40);
        JPanel centrePanel = new JPanel(new GridLayout(4, 1));
        JPanel loginPanel = new JPanel(new GridLayout(2, 1));
        JLabel loginLabel = new JLabel("Email");
        loginPanel.add(loginLabel);
        loginPanel.add(login);
        JPanel passwordPanel = new JPanel(new GridLayout(2, 1));
        JLabel passwordLabel = new JLabel("Password");
        passwordPanel.add(passwordLabel);
        passwordPanel.add(password);
        centrePanel.add(loginPanel);
        centrePanel.add(passwordPanel);

        JPanel southPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        var loginPanel_ = this;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(daoa==null)
                {
                    System.out.println("daoa==null");
                    return;
                }
                try {
                    String req = "select * from users where email=? and password=?";
                    var rs = daoa.GetLoginInfos(req, login.getText(), password.getText());
                    if (rs == null) {
                        JOptionPane.showMessageDialog(loginPanel_, "Invalid email or password", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (!rs.next())
                            JOptionPane.showMessageDialog(loginPanel_, "Invalid email or password", "Error", JOptionPane.ERROR_MESSAGE);
                        else {
                            dispose();
                            gp.setVisible(true);
                            return;
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(loginPanel_, "Connection problem", "Error", JOptionPane.ERROR_MESSAGE);

                }

            }
        });
        southPanel.add(loginButton);
        add(southPanel, BorderLayout.SOUTH);
        add(new JLabel("Enter admin login infos", JLabel.CENTER), BorderLayout.NORTH);
        add(centrePanel, BorderLayout.CENTER);
        this.setSize(1000, 500);
        this.setVisible(true);
    }

    void TryLogin() {


    }
}
