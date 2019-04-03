package com.liakhovskyi.service;

import com.liakhovskyi.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAll();

    Optional<Category> getById(Long id);
}
