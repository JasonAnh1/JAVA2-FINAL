package com.samsung.bookmanagerment.controller;

import com.samsung.bookmanagerment.configuration.Translator;
import com.samsung.bookmanagerment.entity.Product;
import com.samsung.bookmanagerment.entity.response.BaseResponse;
import com.samsung.bookmanagerment.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
@Transactional
public class ProductController extends BaseController {
    @Autowired
    private ProductService productService;
    @PostMapping("v1/create-product")
    public ResponseEntity<?> createCourseCategory(@Valid @RequestBody final Product request) {
        try {
            if(request == null || request.getName() == null) {
                throw new Exception(Translator.toLocale("required_fields"));
            }
            return ResponseEntity.ok( productService.createProduct(request));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage(), null));
        }
    }
    @GetMapping("v1/publish/get-products")
    public ResponseEntity<?> getBooks(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) Long categoryId) {
        try {
//
            return ResponseEntity.ok( productService.listProduct(name,categoryId));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage(), null));
        }
    }

}
