package nashtech.training.ordersystem.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import nashtech.training.ordersystem.dto.request.payment.PaymentRequestDTO;

public interface PaymentService {
    PaymentIntent createPaymentIntent(PaymentRequestDTO paymentRequest) throws StripeException;
}
