package ClientSide;
import Owner.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuPanel extends JFrame {
    ReceiptPanel shopMenu;
    public MenuPanel(ReceiptPanel receiptPanel,String category, ArrayList<Item> items) {
        setTitle(category);
        shopMenu = receiptPanel;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        JPanel CateGPanel = new JPanel();
        JLabel CateLabel = new JLabel(category+":", JLabel.CENTER);
        CateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel headerPanel = new JPanel();
        headerPanel.add(CateLabel);

        for (var i:items) {
            addButton(CateGPanel, i.name,i.price+"",i.ID);
        }
        getContentPane().add(headerPanel,BorderLayout.NORTH);
        getContentPane().add(CateGPanel,BorderLayout.CENTER);
        setSize(700, 300);
    }
    private void addButton(JPanel panel, String name, String price,int id) {
        JButton button = new JButton(name + " "+ price+ " dt");
        button.setPreferredSize(new Dimension(150, 100));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You ordered: " + name);
                shopMenu.addToReceipt(name,Double.parseDouble(price),id);
            }
        });
        panel.add(button);
    }
}

