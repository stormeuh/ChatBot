package be.eliasstorme;

/**
 * Created by elias on 13/03/18.
 */

public class Header {

    private String command;
    private String path;
    private boolean keepAlive = true;
    private int contentSize = 0;

    public Header(String headerText) throws IllegalArgumentException{
        String[] requestText = headerText.split(" ");
        this.command = requestText[0].toUpperCase();
        this.path = requestText[1];
        if(!requestText[2].contains("HTTP/1.1")){
            throw new IllegalArgumentException("Illegal request: only HTTP/1.1 supported.");
        }
        setKeepAlive(parseField("Connection", headerText));
        setContentSize(parseField("Content-size", headerText));
    }

    public String parseField(String fieldName, String headerText){
        //searches for the start index of the tag in the header, adds the length of the tag to it and adds 2 for
        // the ": " after the tag
        int start;
        if((start = headerText.indexOf(fieldName)) == -1){
            return null;
        } else {
            start += 2 + fieldName.length();
            System.out.println(headerText.substring(start).split("\n")[0]);
            return headerText.substring(start).split("\n")[0];
        }
    }

    public void setKeepAlive(String keepAliveString){
        if(keepAliveString!=null){
            this.keepAlive = keepAliveString.contains("keep-alive");
        }
    }

    public void setContentSize(String contentSizeString){
        if(contentSizeString!=null){
            this.contentSize = Integer.parseInt(contentSizeString);
        }
    }

    public String getCommand() {
        return command;
    }
}
