package com.store.products.domain.shared;

import com.store.products.domain.shared.entity.Size;

import java.util.List;

public interface SizeRepository {
    List<Size> findAll();
}
