package pl.dskowron.sales;

import pl.dskowron.sales.cart.Cart;
import pl.dskowron.sales.cart.CartItem;
import pl.dskowron.sales.cart.CartStorage;
import pl.dskowron.sales.offer.Offer;
import pl.dskowron.sales.offer.OfferMaker;
import pl.dskowron.sales.payment.PaymentData;
import pl.dskowron.sales.payment.PaymentGateway;
import pl.dskowron.sales.products.ProductDetails;
import pl.dskowron.sales.products.ProductDetailsProvider;
import pl.dskowron.sales.products.ProductNotAvailableException;
import pl.dskowron.sales.reservation.Reservation;
import pl.dskowron.sales.reservation.ReservationStorage;

import java.util.UUID;

public class Sales {
    CartStorage cartStorage;
    pl.dskowron.sales.products.ProductDetailsProvider productDetailsProvider;
    PaymentGateway paymentGateway;
    ReservationStorage reservationStorage;

    public Sales(
            CartStorage cartStorage,
            ProductDetailsProvider productDetailsProvider,
            PaymentGateway paymentGateway,
            ReservationStorage reservationStorage) {
        this.cartStorage = cartStorage;
        this.productDetailsProvider = productDetailsProvider;
        this.paymentGateway = paymentGateway;
        this.reservationStorage = reservationStorage;
    }

    public Offer getCurrentOffer(String customerId) {
        Cart cart = cartStorage.getForCustomer(customerId)
                .orElse(Cart.empty());

        return calculateOffer(cart);
    }

    private Offer calculateOffer(Cart cart) {
        OfferMaker offerMaker = new OfferMaker();
        return offerMaker.calculateOffer(cart);
    }

    public void addToCart(String customerId, String productId) {
        Cart cart = cartStorage.getForCustomer(customerId)
                .orElse(Cart.empty());

        ProductDetails productDetails = productDetailsProvider.findById(productId)
                .orElseThrow(() -> new ProductNotAvailableException());

        cart.addItem(CartItem.of(
                productId,
                productDetails.getName(),
                productDetails.getPrice()));

        cartStorage.save(customerId, cart);
    }

    public PaymentData acceptOffer(String customerId, ClientData clientData) {
        Cart cart = cartStorage.getForCustomer(customerId)
                .orElse(Cart.empty());

        Offer currentOffer = calculateOffer(cart);

        String id = UUID.randomUUID().toString();
        Reservation reservation = Reservation.of(
                id,
                currentOffer.getTotal(),
                clientData
        );

        PaymentData paymentData = reservation
                .registerPayment(paymentGateway);

        reservationStorage.save(reservation);

        return paymentData;
    }
}
