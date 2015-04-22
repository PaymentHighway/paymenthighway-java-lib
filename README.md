# Payment Highway Java Client
Payment Highway Java API Library

This is an example implementation of the communication with the Payment Highway API using Java. The Form API and Payment API implement the basic functionality of the Payment Highway.

This code is provided as-is, use it as inspiration, reference or drop it directly into your own project and use it.

For full documentation on the PaymentHighway API visit our developer website: https://paymenthighway.fi/dev/

The Java Client is a Maven project built so that it will work on Java 1.7 and Java 1.8. It requires the following third party frameworks: Apache HttpComponents and Jackson JSON. It also uses JUnit test packages.

# Structure 

* com.solinor.paymenthighway

Contains API classes. Use these to create Payment Highway API requests.

* com.solinor.paymenthighway.connect

Contains the actual classes that are responsible of the communication with Payment Highway.

* com.solinor.paymenthighway.json

Contains classes that serialize and deserialize objects to and from JSON.

* com.solinor.paymenthighway.model

Data structures that will be serialized and deserialized

* com.solinor.paymenthighway.security

Contains classes that take care of keys and signatures.

# Overview

Start with building the HTTP form parameters by using the FormParameterBuilder. 

- `FormParameterBuilder`

Create an instance of the builder with your signature id and signature secret, then use the getAddCardParameters, getPaymentParameters, and getAddCardAndPayment methods to receive a list of parameters for each API call.

Initializing the builder

	FormParameterBuilder formBuilder = 
    	new FormParameterBuilder("signatureKeyId", "signatureSecret");

Example getAddCardParameters

	List<NameValuePair> nameValuePairs = 
		formBuilder.getAddCardParameters("account", "merchant", "amount",
	    	"currency", "orderId", "successUrl", "failureUrl", "cancelUrl", 
        	"language");

Example getPaymentParameters 

	List<NameValuePair> nameValuePairs = 
		formBuilder.getPaymentParameters("account", "merchant", "amount",
	    	"currency", "orderId", "successUrl", "failureUrl", "cancelUrl", 
        	"language", "description");
        	
Example getGetAddCardAndPaymentParameters

	List<NameValuePair> nameValuePairs = 
		formBuilder.getAddCardAndPaymentParameters("account", "merchant", "amount",
	    	"currency", "orderId", "successUrl", "failureUrl", "cancelUrl", 
        	"language", "description");	

Each method returns a List of NameValuePairs that must be used in the HTML form as hidden fields to make a successful transaction to Form API. The builder will generate a request id, timestamp, and secure signature for the transactions, and are included in the returned list.

- `PaymentApi`

Initializing the Payment API

	PaymentAPI paymentAPI = new PaymentAPI("serviceUrl",
		"signatureKeyId", "signatureSecret", "account", "merchant");

Example Init transaction call

	InitTransactionResponse initResponse = paymentAPI.initTransaction();
	
Example Debit with Token transaction call

	Token token = new Token("id", "cvc");
	TransactionRequest transaction = 
		new TransactionRequest("amount", "currency", true, token);
	TransactionResponse response = 
		paymentAPI.debitTransaction(initResponse.getId(), transaction);

Example Debit with Card transaction call

	Card card = new Card("pan", "expiryYear", "expiryMonth", "cvc", "verification");
	TransactionRequest transaction = 
		new TransactionRequest("amount", "currency", true, card);
	TransactionResponse response = 
		paymentAPI.debitTransaction(initResponse.getId(), transaction);

Example Commit transaction call

	CommitTransactionRequest commitRequest = 
		new CommitTransactionRequest("amount", "currency", true);
	CommitTransactionResponse response = 
		paymentAPI.commitTransaction(initResponse.getId(), commitRequest);
	
Example Revert transaction call

	RevertTransactionRequest revertRequest = 
		new RevertTransactionRequest("amount", true);
	TransactionResponse response = 
		paymentAPI.revertTransaction(initResponse.getId(), revertRequest);

Example Transaction Status call

	TransactionStatusResponse status = paymentAPI.transactionStatus("transactionId");
	
Example Daily Batch Report call

	ReportResponse report = paymentAPI.fetchDailyReport("yyyyMMdd");
	

# Help us make it better

Please tell us how we can make the API better. If you have a specific feature request or if you found a bug, please use GitHub issues. Fork these docs and send a pull request with improvements.

# Test it

You can run the test suite normally by 'mvn test'
