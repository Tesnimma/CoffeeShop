package Owner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler extends Thread{

    FeedbacksPanel feedbacksPanel;
    ServerHandler(FeedbacksPanel feedbacksPanel){this.feedbacksPanel = feedbacksPanel;}
    public void run(){
        try
        {
            ServerSocket server = new ServerSocket(9000);
            while (true) {
                Socket socket = server.accept();
                System.out.println("client connected");

                FeedbackDiscussion feedbackDiscussion = new FeedbackDiscussion(socket,feedbacksPanel);
                feedbackDiscussion.start();
            }
        } catch (Exception e)
        {

        }
    }
}
