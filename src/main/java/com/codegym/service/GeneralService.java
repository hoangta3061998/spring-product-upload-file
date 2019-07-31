package com.codegym.service;

import java.util.List;

public interface GeneralService<T> {
    List<T> findAll();
    void add(T t);
    T findById(int id);
}
