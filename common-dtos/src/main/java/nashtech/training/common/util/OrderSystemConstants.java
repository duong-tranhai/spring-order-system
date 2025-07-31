package nashtech.training.common.util;

public final class OrderSystemConstants {

    private OrderSystemConstants() {
        // Prevent instantiation
    }

    public final static String RABBITMQ_PAYMENT_EXCHANGE = "payment.exchange";
    public final static String RABBITMQ_EMAIL_EXCHANGE = "email.exchange";
    public final static String RABBITMQ_PAYMENT_QUEUE_SUCCEEDED = "payment.succeeded.queue";
    public final static String RABBITMQ_PAYMENT_QUEUE_FAILED = "payment.failed.queue";
    public final static String RABBITMQ_PAYMENT_ROUTINGKEY_SUCCEEDED = "payment.succeeded.routingkey";
    public final static String RABBITMQ_PAYMENT_ROUTINGKEY_FAILED = "payment.failed.routingkey";
    public final static String RABBITMQ_EMAIL_QUEUE = "email.queue";
    public final static String RABBITMQ_EMAIL_ROUTINGKEY = "email.send.routingkey";
}
