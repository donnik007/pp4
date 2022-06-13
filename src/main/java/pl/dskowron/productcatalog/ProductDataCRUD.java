package pl.dskowron.productcatalog;

import org.springframework.data.repository.CrudRepository;

public interface ProductDataCRUD
    extends CrudRepository<ProductData, String> {
}
