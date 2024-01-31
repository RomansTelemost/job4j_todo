package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Service
public class SimpleCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Set<Category> findByIds(Set<Integer> ids) {
        return categoryRepository.findByIds(ids);
    }
}
