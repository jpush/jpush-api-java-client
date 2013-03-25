package cn.jpush.example;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestGenerateSendNo {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetRandomSendNo() {
        System.out.println("max int: " + Integer.MAX_VALUE);
        long sendNo = JPushClientExample.getRandomSendNo();
        System.out.println("sendNo: " + sendNo + ", len: " + (sendNo + "").length());
        Assert.assertTrue("", sendNo > 0);
    }

}
