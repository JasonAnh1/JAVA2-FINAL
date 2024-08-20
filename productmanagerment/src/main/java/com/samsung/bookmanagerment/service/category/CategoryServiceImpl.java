package com.samsung.bookmanagerment.service.category;

import com.samsung.bookmanagerment.entity.Catalog;
import com.samsung.bookmanagerment.repository.category.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CatalogRepository categoryRepository;
    @Override
    public Catalog createCategory(Catalog category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Catalog> getAllCategory() {

        return categoryRepository.findAll();
    }
}
