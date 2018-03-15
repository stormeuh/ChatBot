package be.eliasstorme;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    protected static final String BASE_DIRECTORY = "C:";

    private static int port = 10080;

    public static void main(String[] args) throws IOException{
        if(args.length>0){port = Integer.parseInt(args[0]);}
        ServerSocket clientSocket = new ServerSocket(port);
        while(true){
            Socket acceptedSocket = clientSocket.accept();
            if(acceptedSocket!=null){
                Session s = new Session(acceptedSocket);
                Thread sessionThread = new Thread(s);
                sessionThread.run();
            }
        }
    }
}
