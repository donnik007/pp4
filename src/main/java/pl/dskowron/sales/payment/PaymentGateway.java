package pl.dskowron.sales.payment;

public interface PaymentGateway {

    RegisterPaymentResponse handle(RegisterPaymentRequest registerPaymentRequest);
}
