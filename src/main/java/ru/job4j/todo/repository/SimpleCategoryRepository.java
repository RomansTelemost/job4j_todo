package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Override
    public Set<Category> findByIds(Set<Integer> ids) {
        return new HashSet<>(cr.query(
                "from Category WHERE id in :ids",
                Category.class,
                Map.of("ids", ids)));
    }
}
