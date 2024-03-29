package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Set;

public interface CategoryRepository {

    List<Category> findAll();

    Set<Category> findByIds(Set<Integer> ids);

}
