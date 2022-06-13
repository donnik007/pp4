package pl.dskowron.sales.offer;

import pl.dskowron.sales.cart.Cart;
import pl.dskowron.sales.cart.CartItem;

import java.math.BigDecimal;

public class OfferMaker {
    public Offer calculateOffer(Cart cart) {
        BigDecimal total = cart
                .getItems()
                .stream()
                .map(this::calculateLineTotal)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        return Offer.of(total, cart.getItems().size());
    }

    private BigDecimal calculateLineTotal(CartItem cartItem) {
        return cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }
}
