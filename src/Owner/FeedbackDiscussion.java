package Owner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FeedbackDiscussion extends Thread{
    Socket socket;
    PrintWriter pw;
    BufferedReader br;
    FeedbacksPanel feedbacksPanel;
    FeedbackDiscussion(Socket socket,FeedbacksPanel feedbacksPanel){
        this.socket = socket;
        this.feedbacksPanel = feedbacksPanel;
    }
    public void run(){
        try {
            pw = new PrintWriter(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Lecture(br,feedbacksPanel).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void Write(String msg)
    {
        pw.println(msg);
        pw.flush();
    }
    public  String GetFeedback()
    {
        try {
            return br.readLine();
        } catch (IOException e) {
            return "";
        }
    }
}
