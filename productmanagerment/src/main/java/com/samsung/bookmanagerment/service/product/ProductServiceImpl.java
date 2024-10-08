package com.samsung.bookmanagerment.service.product;

import com.samsung.bookmanagerment.configuration.Translator;
import com.samsung.bookmanagerment.entity.Product;
import com.samsung.bookmanagerment.entity.User;
import com.samsung.bookmanagerment.entity.contants.RoleName;
import com.samsung.bookmanagerment.repository.book.ProductRepository;
import com.samsung.bookmanagerment.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseService implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product createProduct(Product product) throws Exception{
        User user = getUser();
        if (user == null || user.getRoles().get(0).getName() == RoleName.ROLE_USER) {
            throw new Exception(Translator.toLocale("access_denied"));
        }
      return   productRepository.save(product);
    }

    @Override
    public List<Product> listProduct(String name, Long categoryId) {
        if(name != null || categoryId != null){
            if(name != null && categoryId != null){
                return productRepository.findByNameAndCatalogId(name,categoryId);
            }
            if(name != null){
                return productRepository.findByName(name);
            }
            return productRepository.getProductByCatalogId(categoryId);
        }
        return productRepository.findAll();
    }
}
