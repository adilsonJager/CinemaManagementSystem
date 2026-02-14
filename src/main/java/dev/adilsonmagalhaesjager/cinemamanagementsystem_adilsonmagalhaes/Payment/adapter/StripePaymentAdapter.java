package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.adapter;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentAdapter implements PaymentGateway {
        @Value("${stripe.api.key}")
        private String secretKey;

        @PostConstruct
        public void init(){
            Stripe.apiKey = secretKey;
        }


    @Override
    public boolean process(Long amount, String methodId, String email) {
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

            return "succeeded".equals(intent.getStatus());
        } catch (StripeException e) {
            return false;
        }
    }
}
