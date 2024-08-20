package com.samsung.bookmanagerment.repository.book;

import com.samsung.bookmanagerment.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
//    @Procedure(name = "GetBooksByTitle")
//    List<Product> getProductByTitle(@Param("p_title") String title);
//    @Procedure(name = "GetBooksByCategoryId")
//    List<Product> getProductByCategoryId(@Param("p_categoryId") Long categoryId);
//    @Procedure(name = "GetBooksByTitleAndCategoryId")
//    List<Product> getBooksByTitleAndCategoryId(@Param("p_title") String title,@Param("p_categoryId") Long categoryId);
    List<Product> getProductByNameLike(String name);
    List<Product> getProductByCatalogId(Long catalogId);
    List<Product> getProductByNameLikeAndCatalogId(String name,Long catalogId);
}

