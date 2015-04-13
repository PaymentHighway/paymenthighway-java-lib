# Payment Highway Java Client
Payment Highway Java API Library

This is an example implementation of the communication with the Payment Highway API using Java. The Form API and Payment API implement the basic functionality of the Payment Highway.

This code is provided as-is, use it as inspiration, reference or drop it directly into your own project and use it.

For full documentation on the PaymentHighway API visit our developer website: https://paymenthighway.fi/dev/

The Java Client is a Maven project built so that it will work on Java 1.7 and Java 1.8. It requires the following third party frameworks: Apache HttpComponents, Jackson JSON. It also uses JUnit test packages.

# Structure 


* com.solinor.paymenthighway

Contains API classes. Use these to create Payment Highway API requests.

* connect

Contains the actual classes that are responsible of the communication with Payment Highway.

* json

Contains classes that serialize and deserialize objects to and from JSON.

* model

Data structures that will be serialized and deserialized

* security

Contains classes that take care of keys and signatures.

# TODO

- [ ] Test Form API submit form (get redirect and token)
- [ ] Test debit transaction with token.
- [ ] JSON model coherence check
- [x] daily batch report might be empty? note: only for today. reports available from previous day.
- [x] Form API probably doesn't need merchant and account data everywhere. remove. 
- [ ]should we do init transaction on behalf of customer when doing transactions?
- [ ] daily batch report response model is not done. 
- [ ] response authentication
- [ ] response handling design?
- [ ] write documents ( https://paymenthighway.fi/dev/ )

