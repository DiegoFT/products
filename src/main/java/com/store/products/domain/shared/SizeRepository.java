package com.store.products.domain.shared;

import com.store.products.domain.shared.entity.Size;

import java.util.Set;

public interface SizeRepository {
    Set<Size> findAll();
}
