package ClientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReceiptPanel extends JPanel {
    private DefaultListModel<Item> receiptListModel;
    private JList<Item> receiptList;
    private JLabel totalLabel;
    private double totalPrice;
    ReceiptPanel()
    {
        setLayout(new BorderLayout());
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
                if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) {
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
        add(scrollPane, BorderLayout.CENTER);
        add(totalLabel, BorderLayout.SOUTH);
        add(title, BorderLayout.NORTH);
    }
    private void deleteFromReceipt(Item item) {
        receiptListModel.removeElement(item);
        totalPrice -= item.price;
        updateTotalLabel();
    }
    void addToReceipt(String itemName, double itemPrice, int ItemID) {
        //System.out.println(itemName);
        Item newItem = new Item(itemName, itemPrice, ItemID);
        receiptListModel.addElement(newItem);
        totalPrice += itemPrice;
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
    public double GetTotal(){return totalPrice;}
}
