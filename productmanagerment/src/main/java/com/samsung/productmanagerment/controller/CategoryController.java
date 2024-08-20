package com.samsung.bookmanagerment.controller;

import com.samsung.bookmanagerment.configuration.Translator;
import com.samsung.bookmanagerment.entity.Catalog;
import com.samsung.bookmanagerment.entity.response.BaseResponse;
import com.samsung.bookmanagerment.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
@Transactional
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("v1/create-category")
    public ResponseEntity<?> createCourseCategory(@Valid @RequestBody final Catalog request) {
        try {
            if(request == null || request.getTitle() == null) {
                throw new Exception(Translator.toLocale("required_fields"));
            }
            return ResponseEntity.ok( categoryService.createCategory(request));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage(), null));
        }
    }
    @GetMapping("v1/publish/list-category")
    public ResponseEntity<?> listCategory() {
        try {

            return ResponseEntity.ok( categoryService.getAllCategory());
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage(), null));
        }
    }
}
