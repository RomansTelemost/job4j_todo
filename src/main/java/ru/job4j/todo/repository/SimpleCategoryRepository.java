package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;

@Data
@AllArgsConstructor
@Repository
public class SimpleCategoryRepository implements CategoryRepository {

    private final CrudRepository cr;

    @Override
    public List<Category> findAll() {
        return cr.query(
                "FROM Category order by name",
                Category.class);
    }
}
