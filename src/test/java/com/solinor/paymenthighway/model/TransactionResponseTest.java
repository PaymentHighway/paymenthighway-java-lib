package com.solinor.paymenthighway.model;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionResponseTest {

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
	public void test1() {
		Method method = null;
		 try {
		      method = TransactionResponse.class.getMethod("getResult");
		    } catch (Exception e) {
		    	System.out.print("exception e=" + e);
		    }
		 assertEquals("getResult", method.getName());
	}
}
