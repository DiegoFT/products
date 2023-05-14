package com.store.products.application.availability;

import com.store.products.domain.availability.StockRepository;
import com.store.products.domain.availability.entity.Stock;
import com.store.products.domain.shared.ProductRepository;
import com.store.products.domain.shared.SizeRepository;
import com.store.products.domain.shared.entity.Product;
import com.store.products.domain.shared.entity.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

    public List<Product> handle() {
        var productList = productRepository.findAll();
        var sizeList = sizeRepository.findAll();
        var stockList = stockRepository.findAll();

        return productList.stream()
            .filter(product -> {
                var sizesForProduct = getSizesForProduct(product, sizeList);
                if (sizesForProduct.isEmpty()) return false;
                return (isBackSoon(sizesForProduct) && !hasSpecialAndNonSpecialSizes(product, sizesForProduct))
                    || hasStock(product, sizesForProduct, stockList)
                    || hasSpecialSizes(sizesForProduct);
            })
            .toList();
    }


    private List<Size> getSizesForProduct(Product product, List<Size> sizeList) {
        return sizeList.stream()
            .filter(size -> size.productId().equals(product.id())).toList();
    }

    private boolean isBackSoon(List<Size> sizeList) {
        return sizeList.stream().anyMatch(Size::backSoon);
    }

    private boolean hasStock(Product product, List<Size> sizeList, List<Stock> stockList) {
        return getSizesForProduct(product, sizeList).stream()
            .anyMatch(size -> hasStockForSize(size, stockList));
    }

    private boolean hasSpecialSizes(List<Size> sizeList) {
        return !getSpecialSizes(sizeList).isEmpty();
    }

    private boolean hasStockForSize(Size size, List<Stock> stockList) {
        return stockList.stream()
            .anyMatch(stockItem -> stockItem.sizeId().equals(size.sizeId()) && stockItem.quantity() > 0);
    }

    private List<Size> getSpecialSizes(List<Size> sizeList) {
        return sizeList.stream()
            .filter(Size::special)
            .toList();
    }

    private boolean hasSpecialAndNonSpecialSizes(Product product, List<Size> sizeList) {
        List<Boolean> specialList = getSizesForProduct(product, sizeList).stream()
            .map(Size::special)
            .toList();
        return frequency(specialList, true) > 0 && frequency(specialList, false) > 0;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsAvailableHandler.class);

}
