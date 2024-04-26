package ClientSide;

import ServerSide.CategoryInfos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Dictionary;
import java.util.HashMap;

public class CoffeeShopMenu extends JFrame {
    ProduitDAO dao;
    private JTextArea receiptTextArea;
    private double totalPrice;
    private JLabel totalLabel;
    private JList<Item> receiptList;
    private DefaultListModel<Item> receiptListModel;
    public CoffeeShopMenu() {
        // Create a JPanel to hold the components
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        //contentPane.add(new ButtonPanel());
        dao = new ProduitDAO(Config.URL,Config.USERNAME,Config.PASSWORD);
        // Add the background image to the content pane
       /* ImageIcon backgroundImageIcon = new ImageIcon(ClassLoader.getSystemResource("ClientSide/coffee background.png"));
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(1000, 500, Image.SCALE_DEFAULT);
        ImageIcon scaledBackgroundImageIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImageIcon);
        contentPane.add(backgroundLabel);*/

        // Create panels for different sections of the menu
        JPanel headerPanel = new JPanel();
        /*JPanel drinksPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel pastriesPanel = new JPanel(new GridLayout(0, 2, 10, 10));*/
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Header label
        JLabel headerLabel = new JLabel("Welcome to Our Coffee Shop", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);

       /* // Drinks section
        JLabel drinksLabel = new JLabel("Drinks:", JLabel.CENTER);
        drinksLabel.setFont(new Font("Arial", Font.BOLD, 18));
        drinksPanel.add(drinksLabel);
        addButton(drinksPanel, "Espresso", "2.50");
        addButton(drinksPanel, "Latte", "3.00");
        addButton(drinksPanel, "Cappuccino", "3.00");
        addButton(drinksPanel, "Mocha", "3.50");

        // Pastries section
        JLabel pastriesLabel = new JLabel("Pastries:", JLabel.CENTER);
        pastriesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        pastriesPanel.add(pastriesLabel);
        addButton(pastriesPanel, "Croissant", "2.00");
        addButton(pastriesPanel, "Muffin", "2.50");
        addButton(pastriesPanel, "Donut", "1.50");*/
        AddSection(dao.getCategories(),contentPane);
        contentPane.add(createReceiptPanel(),BorderLayout.EAST);
        // Checkout button
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement checkout logic here
                JOptionPane.showMessageDialog(null, "Implement checkout logic here!");
            }
        });
        buttonsPanel.add(checkoutButton);

        // Add panels to the content pane
        contentPane.add(headerPanel, BorderLayout.NORTH);
       // contentPane.add(drinksPanel, BorderLayout.WEST);
       // contentPane.add(pastriesPanel, BorderLayout.EAST);
        contentPane.add(buttonsPanel, BorderLayout.SOUTH);

        // Set the content pane of the frame
        setContentPane(contentPane);

        setVisible(true);
        setTitle("Coffee Shop Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
    }
    void AddSection(HashMap<String, CategoryInfos> categoryInfos,JPanel mainPanel)
    {
        int j =0;
        JPanel ButtonsPanel = new JPanel(new GridLayout(0,1,100,20));
        for (String a: categoryInfos.keySet()) {
            JButton button = new JButton(a);
            button.setPreferredSize(new Dimension(200, 100));
            ButtonsPanel.add(button);
            var coffeShop = this;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Implement button click logic here
                    MenuPanel menuPanel = new  MenuPanel(coffeShop,a,categoryInfos.get(a).items);
                }
            });

        }
        mainPanel.add(ButtonsPanel,BorderLayout.WEST);
    }

    private JPanel createReceiptPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        totalLabel = new JLabel("Total: 0.0 dt", JLabel.CENTER);
        var title = new JLabel("Your Receipt", JLabel.CENTER);
        receiptListModel = new DefaultListModel<>();
        receiptList = new JList<>(receiptListModel);
        receiptList.setFixedCellHeight(25);
        receiptList.setFixedCellWidth(300);
        receiptList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        receiptList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && e.getButton()== MouseEvent.BUTTON3) {
                    JPopupMenu jpm = new JPopupMenu();
                    JMenuItem supprimer = new JMenuItem("Delete");
                    JMenuItem supprimerTous = new JMenuItem("Delete All");
                    jpm.add(supprimer);
                    jpm.add(supprimerTous);
                    jpm.show(receiptList, e.getX(), e.getY());
                    supprimer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent a) {
                            int index = receiptList.locationToIndex(e.getPoint());
                            if (index != -1) {
                                Item selected = receiptListModel.getElementAt(index);
                                deleteFromReceipt(selected);
                            }
                        }
                    });
                    supprimerTous.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent a) {
                            clearReceipt();
                        }
                    });

                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(receiptList);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(totalLabel,BorderLayout.SOUTH);
        panel.add(title,BorderLayout.NORTH);
        return panel;
    }

    void addToReceipt(String itemName, double itemPrice,int ItemID) {
        //System.out.println(itemName);
        Item newItem = new Item(itemName, itemPrice,ItemID);
        receiptListModel.addElement(newItem);
        totalPrice += itemPrice;
        updateTotalLabel();
    }

    private void deleteFromReceipt(Item item) {
        receiptListModel.removeElement(item);
        totalPrice -= item.price;
        updateTotalLabel();
    }

    private void updateTotalLabel() {
        totalLabel.setText("Total: $" + totalPrice);
    }

    private void clearReceipt() {
        receiptListModel.clear();
        totalPrice = 0.0;
        updateTotalLabel();
    }
    public static void main(String[] args) {
        new CoffeeShopMenu();
    }
}
