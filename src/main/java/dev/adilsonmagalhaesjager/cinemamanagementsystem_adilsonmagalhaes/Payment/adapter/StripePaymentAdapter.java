package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.adapter;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.model.PaymentResponse;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ReservationException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Slf4j
@Service
public class StripePaymentAdapter implements PaymentGateway {

        @Value("${stripe.api.key}")
        private String secretKey;

        @PostConstruct
        public void init() {
            Stripe.apiKey = secretKey;
         }

    @Override
    public PaymentResponse process(Long amount, String methodId, String email) {
        try{

            RequestOptions options = RequestOptions.builder()
                    .setConnectTimeout(30 * 1000)
                    .setReadTimeout(80 * 1000)
                    .build();

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency("EUR")
                    .setPaymentMethod(methodId)
                    .setConfirm(true)
                    .setReceiptEmail(email)
                    .setReturnUrl("http://localhost:8080/sucess")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params, options);

            String last4 = null;


            if (intent.getLatestChargeObject() != null &&
                intent.getLatestChargeObject().getPaymentMethodDetails() != null &&
                intent.getLatestChargeObject().getPaymentMethodDetails().getCard() != null ) {
                last4 = intent.getLatestChargeObject().getPaymentMethodDetails().getCard().getLast4();
            }

            return PaymentResponse.builder()
                    .id(intent.getId())
                    .status(intent.getStatus())
                    .last4(last4)
                    .email(intent.getReceiptEmail())
                    .build();
        } catch (StripeException e) {
            log.info("ERRO: " + e.getMessage());
            throw ReservationException.paymentDenied();
        }
    }
}
