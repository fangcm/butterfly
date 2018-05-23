package com.fangcm.modules.demo.service;

import com.fangcm.modules.demo.entity.Todo;
import com.fangcm.modules.demo.dao.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by FangCM on 2018/5/23.
 */
@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    @Transactional
    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }
}
