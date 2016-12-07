package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;

public interface AddCardParametersInterface {
    AddCardParametersInterface skipFormNotifications(Boolean skipFormNotifications);
    AddCardParametersInterface exitIframeOnResult(Boolean exitIframeOnResult);
    AddCardParametersInterface exitIframeOn3ds(Boolean exitIframeOn3ds);
    AddCardParametersInterface use3ds(Boolean use3ds);
    AddCardParametersInterface language(String language);
    AddCardParametersInterface acceptCvcRequired(Boolean acceptCvcRequired);
    FormContainer build();
}
