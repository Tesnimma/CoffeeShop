package Owner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;

public class GestionProduits extends JFrame {
    JTable jtProd;
    Owner.MyTableModel model;
    ProduitDAO dao;
    GestionProduits(ProduitDAO daao) {
        this.dao = daao;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Owner/coffee background.png"));
        Image i2 = i1.getImage().getScaledInstance(1000,500,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0,0,1000,500);

        JLabel jlName = new JLabel("Name:");
        JTextField jtName = new JTextField(15);
        JLabel jlCategory = new JLabel("Category:");
        JTextField jtCategory = new JTextField(15);
        JLabel jlID = new JLabel("ID:");
        JTextField jtID = new JTextField(15);
        JLabel jlPrice = new JLabel("Price:");
        JTextField jtPrice = new JTextField(15);

        JButton btn = new JButton("Add");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel JNorth = new JPanel();
        JPanel southPanel = new JPanel();
        JButton myFeedbacks = new JButton("My Feedbacks");
        var feedbackPanel = new FeedbacksPanel();
        myFeedbacks.addActionListener((ActionEvent e) -> {
            feedbackPanel.setVisible(true);
        });
        mainPanel.add(JNorth,BorderLayout.NORTH);
        southPanel.add(myFeedbacks);
        add(southPanel,BorderLayout.SOUTH);


        JNorth.add(jlName);
        JNorth.add(jtName);
        JNorth.add(jlCategory);
        JNorth.add(jtCategory);
        JNorth.add(jlID);
        JNorth.add(jtID);
        JNorth.add(jlPrice);
        JNorth.add(jtPrice);
        JNorth.add(btn);

        jtProd = new JTable();
        String req ="select * from produit";
        ResultSet rs = dao.selection(req);
        model = new MyTableModel(rs, dao);
        jtProd.setModel(model);
        this.add(new JScrollPane(jtProd));
        this.add(JNorth, BorderLayout.NORTH);
        //setBackground();
        //add(l3);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = jtName.getText();
                String cat = jtCategory.getText();
                int id = Integer.parseInt(jtID.getText());
                double price = Double.parseDouble(jtPrice.getText());
                model.insertProd(name,cat,id,price);
            }
        });

        jtProd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON3){
                    int row = jtProd.rowAtPoint(e.getPoint());
                    JPopupMenu jpm = new JPopupMenu();
                    JMenuItem supprimer = new JMenuItem("Delete");
                    jpm.add(supprimer);
                    jpm.show(jtProd, e.getX(), e.getY());
                    supprimer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int id = (int) model.getValueAt(row, 2);
                            model.suppProd(id);
                        }
                    });
                }
            }
        });
        this.setBackground(Color.blue);
        this.setTitle("Coffee Shop Management");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 500);
        this.setVisible(true);

        new ServerHandler(feedbackPanel).start();


    }

}