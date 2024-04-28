package Owner;


import java.io.BufferedReader;
import java.io.IOException;

public class Lecture extends Thread{
    BufferedReader br;
    FeedbacksPanel feedbacksPanel;
    Lecture(BufferedReader br,FeedbacksPanel feedbacksPanel){
        this.br = br;
        this.feedbacksPanel = feedbacksPanel;
    }
    public void run(){
        while (true){
            try {
                //System.out.println();
                feedbacksPanel.AddFeedback(br.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}