package be.eliasstorme;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by elias on 14/03/2018.
 */
public class HeaderTest {

    String headertext = "HTTP/1.1 302 Found\n" +
            "Cache-Control: private\n" +
            "Content-Type: text/html; charset=UTF-8\n" +
            "Referrer-Policy: no-referrer\n" +
            "Location: http://www.google.be/index.html?gfe_rd=cr&dcr=0&ei=OYCpWsHiFI7Z8Afw34y4Dg\n" +
            "Content-Length: 278\n" +
            "Date: Wed, 14 Mar 2018 20:04:09 GMT";

    Header testHeader;

    @org.junit.Before
    public void setUp() throws Exception {
    }
    /*
    @Test
    public void testRetrieveTag(){
        System.out.println(testHeader.retrieveTag("Cache-Control", headertext));
    }
*/
}