package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;

@Data
@AllArgsConstructor
@Repository
public class PriorityStore implements PriorityRepository {

    private final CrudRepository cr;

    @Override
    public List<Priority> findAll() {
        return cr.query(
                "FROM Priority order by id",
                Priority.class);
    }
}
