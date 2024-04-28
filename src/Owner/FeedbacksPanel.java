package Owner;

import javax.swing.*;
import java.awt.*;

public class FeedbacksPanel extends JFrame {
    JTextArea textArea;
    public FeedbacksPanel() {
        setLayout(new BorderLayout());
        setTitle("Feedbacks");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setVisible(false);
        JLabel lblFeedbacks = new JLabel("Feedbacks", SwingConstants.CENTER);
        add(lblFeedbacks, BorderLayout.NORTH);
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(textArea, BorderLayout.CENTER);
    }
    public void AddFeedback(String feedback) {
        textArea.append("Feedback: "+feedback + "\n");
    }
}
