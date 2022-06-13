package pl.dskowron.sales.products;

import java.util.Optional;

public interface ProductDetailsProvider {
    Optional<ProductDetails> findById(String productId);
}
