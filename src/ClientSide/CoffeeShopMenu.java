package ClientSide;

import Owner.CategoryInfos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.Naming;
import java.util.HashMap;

public class CoffeeShopMenu extends JFrame {
    ProduitDAO dao;
    private JTextArea receiptTextArea;
    ReceiptPanel receiptPanel;
    public CoffeeShopMenu(TableReservationRemote tableReservationRemote) {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        dao = new ProduitDAO(Config.URL, Config.USERNAME, Config.PASSWORD);

        JPanel headerPanel = new JPanel();

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        receiptPanel = new ReceiptPanel();

        JLabel headerLabel = new JLabel("Welcome to Our Coffee Shop", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);

        AddSection(dao.getCategories(), contentPane);
        contentPane.add(receiptPanel, BorderLayout.EAST);

        JButton checkoutButton = new JButton("Send Feedback");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new feedbackPanell(null);
            }
        });
        buttonsPanel.add(checkoutButton);

        // Add panels to the content pane
        contentPane.add(headerPanel, BorderLayout.NORTH);
        contentPane.add(buttonsPanel, BorderLayout.SOUTH);
        contentPane.add(new TablesSelection(tableReservationRemote,receiptPanel), BorderLayout.CENTER);
        setContentPane(contentPane);

        setVisible(true);
        setTitle("Coffee Shop Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
    }

    void AddSection(HashMap<String, CategoryInfos> categoryInfos, JPanel mainPanel) {
        int j = 0;
        JPanel ButtonsPanel = new JPanel(new GridLayout(0, 1, 100, 20));
        for (String a : categoryInfos.keySet()) {
            JButton button = new JButton(a);
            button.setPreferredSize(new Dimension(200, 100));
            ButtonsPanel.add(button);
            var coffeeShop = this;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MenuPanel menuPanel = new MenuPanel(receiptPanel, a, categoryInfos.get(a).items);
                }
            });
        }
        mainPanel.add(ButtonsPanel, BorderLayout.WEST);
    }

    public static void main(String[] args) {
        System.out.println("traitement client");
        String url = "rmi://127.0.0.1:9001/chat";
        try {
            var tableReservation = (TableReservationRemote) Naming.lookup(url);
            new CoffeeShopMenu(tableReservation);
        } catch (Exception e) {
            System.out.println("Main client :"+e);
        }
    }
}
