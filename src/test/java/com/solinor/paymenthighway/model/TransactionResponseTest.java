package com.solinor.paymenthighway.model;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

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
		    	System.err.print("exception e=" + e);
		    }
		assert method != null;
		assertEquals("getResult", method.getName());
	}
}
