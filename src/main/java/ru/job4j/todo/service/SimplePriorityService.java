package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.List;

@Data
@AllArgsConstructor
@Service
public class SimplePriorityService implements PriorityService {

    private final PriorityRepository pr;

    @Override
    public List<Priority> findAll() {
        return pr.findAll();
    }
}
