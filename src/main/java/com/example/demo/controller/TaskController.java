package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TaskController {

    @Autowired // Зависимости подтягиваются автоматически
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;


    @PostMapping("/tasks")
    public Task create(@RequestBody Task task) {
        User currentUser = userService.getCurrentUser();  // ← Получаем авторизованного юзера
        task.setUser(currentUser);                        // ← Сохраняем в задачу
        return taskRepository.save(task);
    }

    @GetMapping("/tasks")
   public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Задача не найдена"));
    }

    @PutMapping("/tasks/{id}")
    public Task update(@PathVariable long id, @RequestBody Task task) {
        task.setId(id);
        return taskRepository.save(task);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    @PatchMapping("/tasks/{id}")
    public void patchMethod(@PathVariable Long id, @RequestBody Task task) {
        if(task.isDone()){
            taskRepository.markAsDone(id);
        }
    }

    @Transactional
    @PatchMapping("/tasks/{id}:mark-as-done")
    public void patchMethod(@PathVariable Long id){
        taskRepository.markAsDone(id);
    }

}