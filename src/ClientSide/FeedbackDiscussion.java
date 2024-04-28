package ClientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FeedbackDiscussion extends Thread{
    Socket socket;
    PrintWriter pw;
    BufferedReader br;
    FeedbackDiscussion(Socket socket){
        this.socket = socket;
    }
    public void run(){
        try {
            pw = new PrintWriter(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
