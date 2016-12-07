package io.paymenthighway.formBuilders;

import io.paymenthighway.FormContainer;

public interface CardFormParametersInterface {
    CardFormParametersInterface skipFormNotifications(Boolean skipFormNotifications);
    CardFormParametersInterface exitIframeOnResult(Boolean exitIframeOnResult);
    CardFormParametersInterface exitIframeOn3ds(Boolean exitIframeOn3ds);
    CardFormParametersInterface use3ds(Boolean use3ds);
    CardFormParametersInterface language(String language);
    FormContainer build();
}
