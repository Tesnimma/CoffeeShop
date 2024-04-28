package ClientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class feedbackPanell extends JDialog{
    FeedbackDiscussion feedbackDiscussion;
    private JTextField textField1;
    private JButton button1;
    private JPanel FeedbackPanel;

    public feedbackPanell(JFrame parent){
        TryConnection();
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                feedbackDiscussion.Write(textField1.getText());
                JOptionPane.showMessageDialog(null, "Thank you for sending your feedback.", "Feedback Sent", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        setTitle("Feedback");
        setContentPane(FeedbackPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
