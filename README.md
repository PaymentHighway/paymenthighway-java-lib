# Payment Highway Java Client
Payment Highway Java API Library

This is an example implementation of the communication with the Payment Highway API using Java. The Form API and Payment API implement the basic functionality of the Payment Highway.

This code is provided as-is, use it as inspiration, reference or drop it directly into your own project and use it.

For full documentation on the PaymentHighway API visit our developer website: https://paymenthighway.fi/dev/

TODO: 
- Test Form API submit form (get redirect and token)
- Test debit transaction with token.
- JSON model coherence check

DONE: - daily batch report might be empty? note: only for today. reports available from previous day.
DONE: - Form API probably doesn't need merchant and account data everywhere. remove. 

- should we do init transaction on behalf of customer when doing transactions?

- daily batch report response model is not done. 

- response authentication
- response handling design?
- write documents ( https://paymenthighway.fi/dev/ )

