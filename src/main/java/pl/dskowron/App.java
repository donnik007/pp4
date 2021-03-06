package pl.dskowron;
import java.math.BigDecimal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.dskowron.creditcard.NameProvider;
import pl.dskowron.payu.PayU;
import pl.dskowron.productcatalog.MapProductStorage;
import pl.dskowron.productcatalog.ProductCatalog;
import pl.dskowron.productcatalog.ProductData;
import pl.dskowron.productcatalog.ProductStorage;
import pl.dskowron.sales.Sales;
import pl.dskowron.sales.cart.CartStorage;
import pl.dskowron.sales.payment.PayUPaymentGateway;
import pl.dskowron.sales.payment.PaymentGateway;
import pl.dskowron.sales.products.ProductDetails;
import pl.dskowron.sales.products.ProductDetailsProvider;
import pl.dskowron.sales.reservation.ReservationStorage;



@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

    }

    @Bean
    NameProvider createNameProvider() {
        return new NameProvider();
    }

    @Bean
    ProductStorage createMyProductStorage() {
        return new MapProductStorage();
    }

    @Bean
    ProductCatalog createMyProductCatalog(ProductStorage productStorage) {
        ProductCatalog productCatalog = new ProductCatalog(productStorage);
        String productId1 = productCatalog.addProduct("lego-set-1", "Nice Lego set");
        productCatalog.assignImage(productId1, "https://picsum.photos/id/237/200/300");
        productCatalog.assignPrice(productId1, BigDecimal.TEN);
        productCatalog.publish(productId1);

        String productId2 = productCatalog.addProduct("lego-set-2", "Even nicer Lego set");
        productCatalog.assignImage(productId2, "https://picsum.photos/id/238/200/300");
        productCatalog.assignPrice(productId2, BigDecimal.valueOf(20.20));
        productCatalog.publish(productId2);

        return productCatalog;
    }

    @Bean
    PaymentGateway createPaymentGateway() {
        return new PayUPaymentGateway(
                new PayU(System.getenv("PAYU_MERCHANT_POS_ID")));

    }

    @Bean
    Sales createSales(ProductDetailsProvider productDetailsProvider, PaymentGateway paymentGateway) {
        return new Sales(
                new CartStorage(),
                productDetailsProvider,
                paymentGateway,
                new ReservationStorage()
        );
    }

    @Bean
    ProductDetailsProvider detailsProvider(ProductCatalog catalog) {
        return (productId -> {
            ProductData data = catalog.getDetails(productId);
            return java.util.Optional.of(new ProductDetails(
                    data.getId(),
                    data.getName(),
                    data.getPrice()));
        });
    }
}
