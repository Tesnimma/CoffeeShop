package ClientSide;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;

public class FeedbackPanel extends JFrame {
    FeedbackDiscussion feedbackDiscussion;
    public FeedbackPanel() {
        TryConnection();
        setLayout(new BorderLayout());
        setTitle("Feedback Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);

        JLabel feedbackLabel = new JLabel("Feedback", JLabel.CENTER);
        JTextField textField = new JTextField();
        // Add margin to the text field
        textField.setBorder(new EmptyBorder(10, 10, 10, 10)); // 10 pixels padding on all sides
        textField.setPreferredSize(new Dimension(700, 120));

        add(feedbackLabel, BorderLayout.NORTH);

        JPanel centrePanel = new JPanel(new GridLayout(2,1));
        JPanel textPanel = new JPanel();
        JLabel responseLabel = new JLabel("", JLabel.CENTER);
        textPanel.add(textField);
        centrePanel.add(textPanel, BorderLayout.CENTER);
        centrePanel.add(responseLabel, BorderLayout.SOUTH);
        add(centrePanel, BorderLayout.CENTER);

        JButton sendFeedback = new JButton("Send");
        sendFeedback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                feedbackDiscussion.Write(textField.getText());
                JOptionPane.showMessageDialog(null, "Thank you for sending your feedback.", "Feedback Sent", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        sendFeedback.setPreferredSize(new Dimension(100, 50));

        JPanel southPanel = new JPanel();
        southPanel.add(sendFeedback);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    void TryConnection()
    {
        try {
            Socket s = new Socket("127.0.0.1",9000);
            feedbackDiscussion = new FeedbackDiscussion(s);
            feedbackDiscussion.start();
        } catch (IOException e) {
            System.out.println("Connection failed");
        }
    }

}
