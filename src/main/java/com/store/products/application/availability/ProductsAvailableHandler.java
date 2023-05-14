package com.store.products.application.availability;

import com.store.products.domain.availability.StockRepository;
import com.store.products.domain.availability.entity.Stock;
import com.store.products.domain.shared.ProductRepository;
import com.store.products.domain.shared.SizeRepository;
import com.store.products.domain.shared.entity.Product;
import com.store.products.domain.shared.entity.Size;

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
            .filter(product -> isAvailable(product, sizeList, stockList))
            .toList();
    }

    private boolean isAvailable(Product product, List<Size> sizeList, List<Stock> stockList) {
        var sizesForProduct = getProductSizes(product, sizeList);

        if (sizesForProduct.isEmpty()) return false;

        var hasDifferentTypes = hasSpecialAndNonSpecialSizes(sizesForProduct);
        if (hasDifferentTypes) {
            return hasStockForDifferentSizeTypes(stockList, sizesForProduct);
        }

        return sizesForProduct.stream()
            .anyMatch(size -> hasStockForSize(size, stockList))
            || hasBackSoonSizes(sizesForProduct);
    }

    private boolean hasStockForDifferentSizeTypes(List<Stock> stockList, List<Size> sizesForProduct) {
        var specialSizesWithStock = getSizesWithStock(sizesForProduct, stockList, true);
        var nonSpecialSizesWithStock = getSizesWithStock(sizesForProduct, stockList, false);

        return specialSizesWithStock > 0 && nonSpecialSizesWithStock > 0;
    }

    private List<Size> getProductSizes(Product product, List<Size> sizeList) {
        return sizeList.stream()
            .filter(size -> size.productId().equals(product.id())).toList();
    }

    private boolean hasBackSoonSizes(List<Size> sizeList) {
        return sizeList.stream().anyMatch(Size::backSoon);
    }

    private boolean hasStockForSize(Size size, List<Stock> stockList) {
        return stockList.stream()
            .anyMatch(stockItem -> stockItem.sizeId().equals(size.sizeId()) && stockItem.quantity() > 0);
    }

    private Integer getSizesWithStock(List<Size> sizeList, List<Stock> stockList, boolean isSpecial) {
        return sizeList.stream()
            .filter(size -> size.special() == isSpecial)
            .filter(size -> hasStockForSize(size, stockList))
            .toList()
            .size();
    }

    private boolean hasSpecialAndNonSpecialSizes(List<Size> sizeList) {
        var specialList = sizeList.stream()
            .map(Size::special)
            .toList();
        return frequency(specialList, true) > 0 && frequency(specialList, false) > 0;
    }

}
