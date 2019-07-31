package com.codegym.repository;

import com.codegym.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements GeneralRepository<Product> {
    private  ArrayList<Product> listProduct = new ArrayList<>();

    @Override
    public List<Product> findAll() {
        return listProduct;
    }

    @Override
    public void add(Product product) {
        listProduct.add(product);
    }

    @Override
    public Product findById(int id) {
        for (int i = 0; i < listProduct.size(); i++) {
            if (listProduct.get(i).getId() == id) {
                return listProduct.get(i);
            }
        }
        return null;
    }

}
