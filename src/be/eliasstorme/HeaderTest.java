package be.eliasstorme;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by elias on 20/03/18.
 */
public class HeaderTest {

    Header testHeader;

    @Before
    public void setUp() throws Exception{
        testHeader = new Header("GET / HTTP/1.1", true);
    }

    @Test
    public void toStringTest() throws Exception {
        testHeader.setStatusCode(200);
        System.out.println(testHeader);
    }

}
