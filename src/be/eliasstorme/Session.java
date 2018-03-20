package be.eliasstorme;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
                    executeGet(receivedHeader, this.socket.getOutputStream());
            }
            socket.close();
        } catch (IOException e) {
            System.err.println("IOException");
        }
    }

    public void executeGet(Header receivedHeader, OutputStream toClient) throws IOException{
        PrintWriter headerWriter = new PrintWriter(toClient);
        if(receivedHeader.getPath().equals("/")){
            receivedHeader.setAlternateLocation("/index.html");
            receivedHeader.setPath("/index.html");
        }
        File f = new File(Main.BASE_DIRECTORY + receivedHeader.getPath().substring(1));
        LocalDateTime lastModified = LocalDateTime.ofInstant(Instant.ofEpochMilli(f.lastModified()), ZoneId.systemDefault());
        receivedHeader.setIsRequest(false);
        //404 Not found
        if(!f.exists() || !f.canRead()) {
            receivedHeader.setStatusCode(404);
            printAndFlush(headerWriter,receivedHeader);
        //304 Not modified
        } else if(receivedHeader.getIfModifiedSince().compareTo(lastModified) > 0){
            receivedHeader.setStatusCode(304);
            printAndFlush(headerWriter,receivedHeader);
        //200 Found
        } else {
            receivedHeader.setStatusCode(200);
            receivedHeader.setContentSize((int) f.length());
            printAndFlush(headerWriter,receivedHeader);
            Files.copy(f.toPath(),toClient);
            toClient.flush();
        }
    }

    public void printAndFlush(PrintWriter headerWriter, Header header){
        headerWriter.print(header);
        headerWriter.flush();
    }

}
