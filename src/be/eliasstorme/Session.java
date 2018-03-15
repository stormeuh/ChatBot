package be.eliasstorme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by elias on 14/03/2018.
 */
public class Session implements Runnable {

    Socket socket;

    public Session(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Session started");
        try {
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String headerText = "";
            String next;
            while(!(next = fromClient.readLine()).equals("")){
                headerText += next + "\n";
            }
            System.out.println(headerText);
            Header receivedHeader = new Header(headerText);
            switch(receivedHeader.getCommand()){
                case("GET"):
            }

        } catch (IOException e) {
            System.err.println("IOException");
        }
    }


}
