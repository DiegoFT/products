package com.store.products.application.availability;

import com.store.products.domain.availability.StockRepository;
import com.store.products.domain.availability.entity.Stock;
import com.store.products.domain.availability.exception.ProductsAvailableException;
import com.store.products.domain.shared.ProductRepository;
import com.store.products.domain.shared.SizeRepository;
import com.store.products.domain.shared.entity.Product;
import com.store.products.domain.shared.entity.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.frequency;

public class ProductsAvailableHandler {

    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final StockRepository stockRepository;

    public ProductsAvailableHandler(ProductRepository productRepository,
                                    SizeRepository sizeRepository,
                                    StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.sizeRepository = sizeRepository;
        this.stockRepository = stockRepository;
    }

    public Set<Product> handle() {
        LOGGER.debug("Starting the product availability process");

        try {
            var productSet = productRepository.findAll();
            var sizeSet = sizeRepository.findAll();
            var stockSet = stockRepository.findAll();

            var products = productSet.stream()
                .filter(product -> isAvailable(product, sizeSet, stockSet))
                .collect(Collectors.toSet());

            LOGGER.info("Products available: {}", products.size());

            return products;

        } catch (RuntimeException e) {
            throw new ProductsAvailableException("Error handling product availability process");
        }
    }

    private boolean isAvailable(Product product, Set<Size> sizeSet, Set<Stock> stockSet) {
        var sizesForProduct = getProductSizes(product, sizeSet);

        if (sizesForProduct.isEmpty()) return false;

        var hasDifferentTypes = hasSpecialAndNonSpecialSizes(sizesForProduct);
        if (hasDifferentTypes) {
            return hasStockForDifferentSizeTypes(stockSet, sizesForProduct);
        }

        return sizesForProduct.stream()
            .anyMatch(size -> hasStockForSize(size, stockSet))
            || hasBackSoonSizes(sizesForProduct);
    }

    private boolean hasStockForDifferentSizeTypes(Set<Stock> stockSet, Set<Size> sizesForProduct) {
        var specialSizesWithStock = getSizesWithStock(sizesForProduct, stockSet, true);
        var nonSpecialSizesWithStock = getSizesWithStock(sizesForProduct, stockSet, false);

        return specialSizesWithStock > 0 && nonSpecialSizesWithStock > 0;
    }

    private Set<Size> getProductSizes(Product product, Set<Size> sizeSet) {
        return sizeSet.stream()
            .filter(size -> size.productId().equals(product.id()))
            .collect(Collectors.toSet());
    }

    private boolean hasBackSoonSizes(Set<Size> sizeSet) {
        return sizeSet.stream().anyMatch(Size::backSoon);
    }

    private boolean hasStockForSize(Size size, Set<Stock> stockSet) {
        return stockSet.stream()
            .anyMatch(stockItem -> stockItem.sizeId().equals(size.sizeId()) && stockItem.quantity() > 0);
    }

    private Integer getSizesWithStock(Set<Size> sizeSet, Set<Stock> stockSet, boolean isSpecial) {
        return sizeSet.stream()
            .filter(size -> size.special() == isSpecial)
            .filter(size -> hasStockForSize(size, stockSet))
            .collect(Collectors.toSet())
            .size();
    }

    private boolean hasSpecialAndNonSpecialSizes(Set<Size> sizeSet) {
        var specialSet = sizeSet.stream()
            .map(Size::special)
            .collect(Collectors.toSet());
        return frequency(specialSet, true) > 0 && frequency(specialSet, false) > 0;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsAvailableHandler.class);

}
