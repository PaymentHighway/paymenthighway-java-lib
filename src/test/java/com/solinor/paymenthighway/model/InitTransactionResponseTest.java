/**
 * 
 */
package com.solinor.paymenthighway.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.solinor.paymenthighway.json.JsonParser;

/**
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class InitTransactionResponseTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInitTransactionResponse() {
		String json = "{\"id\":\"4b402052-c2fd-44cf-bf44-de4cbe7d2d18\",\"result\":{\"code\":100,\"message\":\"OK\"}}";
		JsonParser parser = new JsonParser();
 		InitTransactionResponse response = parser.mapInitTransactionResponse(json);
 		
 		assertEquals("4b402052-c2fd-44cf-bf44-de4cbe7d2d18", response.getId().toString());
 		assertEquals("100", response.getResultCode());
 		assertEquals("OK", response.getResultMessage());
	}

}
