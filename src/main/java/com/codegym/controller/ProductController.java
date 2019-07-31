package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.ProductForm;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping({"/product"})
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private Environment env;
    @GetMapping("/list")
    public ModelAndView findAll(){
        List<Product> productList = productService.findAll();
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("productList",productList);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("productForm",new ProductForm());
        return modelAndView;
    }
    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute("product") ProductForm productForm){
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload".toString());
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(),new File(fileUpload+fileName));
        }catch (IOException ex){
            ex.printStackTrace();
        }
        Product product = new Product(productForm.getId(),productForm.getName(),fileName);
        productService.add(product);
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("message","Created");
        modelAndView.addObject("productForm",new ProductForm());
        return modelAndView;

    }
    @GetMapping({"/detail/{id}"})
    public ModelAndView viewProduct(@PathVariable("id") int id){
        Product product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/product/detail");
        modelAndView.addObject("product",product);
        return modelAndView;
    }
}
