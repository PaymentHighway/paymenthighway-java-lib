# Payment Highway Java Client
Payment Highway Java API Library

This is an example implementation of the communication with the Payment Highway API using Java. The Form API and Payment API implement the basic functionality of the Payment Highway.

This code is provided as-is, use it as inspiration, reference or drop it directly into your own project and use it.

For full documentation on the PaymentHighway API visit our developer website: https://paymenthighway.fi/dev/

The Java Client is a Maven project built so that it will work on Java 1.7 and Java 1.8. It requires the following third party frameworks: Apache HttpComponents and Jackson JSON. It also uses JUnit test packages.

# Structure 

* `com.solinor.paymenthighway`

Contains API classes. Use these to create Payment Highway API requests.

* `com.solinor.paymenthighway.connect`

Contains the actual classes that are responsible of the communication with Payment Highway.

* `com.solinor.paymenthighway.json`

Contains classes that serialize and deserialize objects to and from JSON.

* `com.solinor.paymenthighway.model`

Data structures that will be serialized and deserialized

* `com.solinor.paymenthighway.security`

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

In order to charge a card given in the Form API, the corresponding transaction id must be committed by using Payment API.

- `PaymentApi`

In order to do safe transactions, an execution model is used where the first call to InitTransaction acquires a financial transaction handle, (InitTransactionResponse.getId()) which ensures the transaction is executed exactly once. Afterwards it is possible to execute a debit transaction by calling PaymentAPI.debitTransaction() by using the received id handle. If the execution fails, the command can be repeated in order to confirm the transaction with the particular id has been processed. After executing the command, the status of the transaction can be checked by executing the PaymentAPI.transactionStatus("id") request. 

In order to be sure that a tokenized card is valid and is able to process payment transactions the corresponding tokenization id must be used to get the actual card token. 

Initializing the Payment API

	PaymentAPI paymentAPI = new PaymentAPI("serviceUrl",
		"signatureKeyId", "signatureSecret", "account", "merchant");

Example Init transaction

	InitTransactionResponse initResponse = paymentAPI.initTransaction();
	
Example Tokenize (get the actual card token by using token id)

	TokenizationResponse tokenResponse = 
		paymentAPI.tokenize(tokenizationId);
	
Example Commit

	CommitTransactionResponse response = 
		paymentAPI.commitTransaction("transactionId", "amount", "currency");
		
Example Debit with Token

	Token token = new Token("id", "cvc");
	TransactionRequest transaction = 
		new TransactionRequest("amount", "currency", token);
	TransactionResponse response = 
		paymentAPI.debitTransaction("transactionId", transaction);

Example Debit with Card

	Card card = new Card("pan", "expiryYear", "expiryMonth", "cvc", "verification");
	TransactionRequest transaction = 
		new TransactionRequest("amount", "currency", card);
	TransactionResponse response = 
		paymentAPI.debitTransaction("transactionId", transaction);
		
Example Revert

	TransactionResponse response = 
		paymentAPI.revertTransaction("transactionId", "amount");

Example Transaction Status

	TransactionStatusResponse status = paymentAPI.transactionStatus("transactionId");
	
Example Daily Batch Report

	ReportResponse report = paymentAPI.fetchDailyReport("yyyyMMdd");
	

# Help us make it better

Please tell us how we can make the API better. If you have a specific feature request or if you found a bug, please use GitHub issues. Fork these docs and send a pull request with improvements.

# Test it

You can run the test suite normally by 'mvn test'
