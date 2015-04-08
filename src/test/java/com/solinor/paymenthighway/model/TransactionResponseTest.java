package com.solinor.paymenthighway.model;

import static org.junit.Assert.*;

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
		TransactionResponse response = new TransactionResponse();
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		response.setResult(map);
		
		Map<String, String> result = response.getResult();
		assertTrue(result.containsKey("key1"));
		assertTrue(result.containsValue("value1"));
		assertTrue(result.containsKey("key2"));
		assertTrue(result.containsValue("value2"));
		assertTrue(result.size() == 2);
	}

}
