package ClientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class TablesSelection extends JPanel {
    TableReservationRemote tableReservation;
    boolean haveReservation = false;
    public TablesSelection(TableReservationRemote t,ReceiptPanel receiptPanel)
    {
        tableReservation = t;
        setLayout(new BorderLayout());
        JPanel tablesPanel = new JPanel(new GridLayout(5,0,10,20));
        var nameLabel = new JLabel("Tables Selection", JLabel.CENTER);
        add(nameLabel, BorderLayout.NORTH);
        int tablesNumber  = 0;
        try {
            tablesNumber = tableReservation.GetTableNumber();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < tablesNumber; i++) {
            JButton b = new JButton("Table " + (i+1));
            b.setBackground(new Color(88,57,39));
            b.setForeground(Color.white);
            int k = i;
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if(haveReservation)
                        {
                            JOptionPane.showMessageDialog(null, "You already reserved table");
                            return;
                        }
                        if(receiptPanel.GetTotal()<=0.001)
                        {
                            JOptionPane.showMessageDialog(null, "You should buy something to reserve a table");
                            return;
                        }
                        if(tableReservation.isTableAvailable(k))
                        {
                            tableReservation.ReserveTable(k);
                            haveReservation = true;
                            JOptionPane.showMessageDialog(null, "You reserved table number: "+(k+1));
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Sorry Table Not Available");
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            tablesPanel.add(b);
        }
        add(tablesPanel, BorderLayout.CENTER);
    }
}
