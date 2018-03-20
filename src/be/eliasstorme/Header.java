package be.eliasstorme;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Created by elias on 13/03/18.
 */

public class Header {

    //DateTimeFormatter for sending the time in the RFC2616 format
    private static final DateTimeFormatter HTTP_FORMATTER = DateTimeFormatter
            .ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz")
            .withZone(ZoneId.of("GMT"));

    private String command;
    private String path;
    private boolean keepAlive = true;
    private int contentSize = 0;
    private LocalDateTime ifModifiedSince = LocalDateTime.MIN;


    private int statusCode;
    private String alternateLocation = "";

    private boolean isRequest;

    private static HashMap<Integer,String> STATUS_CODES = new HashMap<>();
    static{
        STATUS_CODES.put(200," OK");
        STATUS_CODES.put(304," Not Modified");
        STATUS_CODES.put(400," Bad Request");
        STATUS_CODES.put(404," Not Found");
        STATUS_CODES.put(500," Server Error");
    }

    public Header(String headerText, boolean isRequest) throws IllegalArgumentException{
        String[] initial = headerText.split("\n")[0].split(" ");
        this.isRequest = isRequest;
        if(isRequest) {
            if(!initial[2].contains("HTTP/1.1")){
                throw new IllegalArgumentException("Illegal request: only HTTP/1.1 supported.");
            }
            this.command = initial[0].toUpperCase();
            this.path = initial[1];
            setIfModifiedSince(parseField("If-Modified-Since", headerText));
        } else {
            this.statusCode = Integer.parseInt(initial[1]);
        }
        setKeepAlive(parseField("Connection", headerText));
        setContentSize(parseField("Content-size", headerText));
    }

    public static String parseField(String fieldName, String headerText){
        //searches for the start index of the tag in the header, adds the length of the tag to it and adds 2 for
        // the ": " after the tag
        int start = headerText.indexOf(fieldName);
        if(start == -1){
            return null;
        } else {
            start += 2 + fieldName.length();
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

    @Override
    public String toString(){
        String headerString = "";
        if(isRequest) {
            headerString += "HTTP/1.1 " + Integer.toString(this.statusCode) + STATUS_CODES.get(this.statusCode) + "\n";
            headerString += buildField("Content-Size", getContentSize());
            headerString += buildField("Content-Location", getAlternateLocation());
            headerString += buildField("Date", ZonedDateTime.now().format(HTTP_FORMATTER));
        } else {
        }
        headerString += "\n";
        return headerString;
    }

    public String buildField(String fieldName, String content){
        String field = fieldName;
        field += ": ";
        field += content;
        field += "\n";
        return field;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public void setAlternateLocation(String alternateLocation) {
        this.alternateLocation = alternateLocation;
    }

    public String getAlternateLocation() {
        return this.alternateLocation;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setIsRequest(boolean isRequest){
        this.isRequest = isRequest;
    }

    public void setIfModifiedSince(String dateTime){
        if (dateTime == null) {
            this.ifModifiedSince = LocalDateTime.parse(dateTime);
        }
    }

    public LocalDateTime getIfModifiedSince(){
        return this.ifModifiedSince;
    }

    public void setContentSize(int size){
        this.contentSize = size;
    }

    public String getContentSize(){
        return Integer.toString(this.contentSize);
    }
}
