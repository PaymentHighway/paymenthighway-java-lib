package com.solinor.paymenthighway.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.solinor.paymenthighway.json.JsonParser;

public class ReportResponseTest {

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
	public void test() {
		String json = "{\"settlements\":[{\"id\":\"11d22268-cc71-4894-bf41-e13a9039c327\",\"batch\":\"000016\",\"timestamp\":\"2015-03-20T22:00:09Z\",\"merchant\":{\"id\":\"test_merchantId\",\"name\":\"Test user\"},\"transaction_count\":1,\"net_amount\":320,\"currency\":\"EUR\",\"acquirer\":{\"id\":\"nets\",\"name\":\"Nets\"},\"transactions\":[{\"id\":\"f9a48e02-4301-49ff-a4f1-65749435924a\",\"timestamp\":\"2015-03-20T13:09:36Z\",\"type\":\"debit\",\"partial_pan\":\"0024\",\"amount\":320,\"currency\":\"EUR\",\"filing_code\":\"150320000263\",\"status\":{\"state\":\"ok\",\"code\":4000},\"authorization_code\":\"894463\"}],\"status\":{\"state\":\"ok\",\"code\":4000},\"reference\":\"11503201000000162\"}],\"result\":{\"code\":100,\"message\":\"OK\"}}";

		JsonParser parser = new JsonParser();
 		ReportResponse response = parser.mapReportResponse(json);
 		
 		assertEquals("100", response.getResult().getCode());
 		assertEquals("OK", response.getResult().getMessage());
 		
 		Settlement[] settlements = response.getSettlements();
 		
 		assertEquals(1, settlements.length);
 		
 		Settlement settlement = settlements[0];
 		
 		assertEquals("nets", settlement.getAcquirer().getId());
 		assertEquals("Nets", settlement.getAcquirer().getName());
 		assertEquals("000016", settlement.getBatch());
 		assertEquals("EUR", settlement.getCurrency());
 		assertEquals("11d22268-cc71-4894-bf41-e13a9039c327", settlement.getId().toString());
 		assertEquals("1", settlement.getTransactionCount());
 		assertEquals("Test user", settlement.getMerchant().getName());
 		assertEquals("test_merchantId", settlement.getMerchant().getId());

	}
	/**
	 * Robustness test. Unknown json fields should not cause issues.
	 */
	@Test
	public void test2() {
		String json = "{\"settlements\":[{\"id\":\"11d22268-cc71-4894-bf41-e13a9039c327\",\"batch\":\"000016\",\"timestamp\":\"2015-03-20T22:00:09Z\",\"merchant\":{\"id\":\"test_merchantId\",\"name\":\"Test user\"},\"transaction_count\":1,\"net_amount\":320,\"currency\":\"EUR\",\"acquirer\":{\"id\":\"nets\",\"name\":\"Nets\"},\"transactions\":[{\"id\":\"f9a48e02-4301-49ff-a4f1-65749435924a\",\"timestamp\":\"2015-03-20T13:09:36Z\",\"type\":\"debit\",\"partial_pan\":\"0024\",\"amount\":320,\"robustnesstest1\":true,\"robustnesstest2\":\"xxxxxx\",\"currency\":\"EUR\",\"filing_code\":\"150320000263\",\"status\":{\"state\":\"ok\",\"code\":4000},\"authorization_code\":\"894463\"}],\"status\":{\"state\":\"ok\",\"code\":4000},\"reference\":\"11503201000000162\"}],\"result\":{\"code\":100,\"message\":\"OK\"}}";

		JsonParser parser = new JsonParser();
 		ReportResponse response = parser.mapReportResponse(json);
 		
 		assertEquals("100", response.getResult().getCode());
 		assertEquals("OK", response.getResult().getMessage());
 		
 		Settlement[] settlements = response.getSettlements();
 		
 		assertEquals(1, settlements.length);
 		
 		Settlement settlement = settlements[0];
 		
 		assertEquals("nets", settlement.getAcquirer().getId());
 		assertEquals("Nets", settlement.getAcquirer().getName());
 		assertEquals("000016", settlement.getBatch());
 		assertEquals("EUR", settlement.getCurrency());
 		assertEquals("11d22268-cc71-4894-bf41-e13a9039c327", settlement.getId().toString());
 		assertEquals("1", settlement.getTransactionCount());
 		assertEquals("Test user", settlement.getMerchant().getName());
 		assertEquals("test_merchantId", settlement.getMerchant().getId());

	}
}
