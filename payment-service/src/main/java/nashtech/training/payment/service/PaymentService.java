package nashtech.training.payment.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import nashtech.training.payment.dto.request.CreatePaymentRequest;

public interface PaymentService {
    PaymentIntent createPaymentIntent(CreatePaymentRequest paymentRequest) throws StripeException;
}

