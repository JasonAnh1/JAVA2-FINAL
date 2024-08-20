package com.samsung.bookmanagerment.service.category;

import com.samsung.bookmanagerment.entity.Catalog;

import java.util.List;

public interface CategoryService {
    Catalog createCategory(Catalog category);
    List<Catalog> getAllCategory();
}
