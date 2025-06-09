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

    @Autowired // Зависимости подтягиваются автоматический
    private UserService userService;


    @PostMapping("/tasks") // Метод для создания задачи
    public Task create(@RequestBody Task task) {
        User currentUser = userService.getCurrentUser();  // Получаем авторизованного юзера
        task.setUser(currentUser);                        // Сохраняем в задачу
        return taskRepository.save(task);
    }

    @GetMapping("/tasks") // Метод для получения всех задач
    public List<Task> getTasksForCurrentUser() {
        User currentUser = userService.getCurrentUser();
        return taskRepository.findByUser(currentUser);
    }

    @GetMapping("/tasks/{id}") // Метод для получения задачи по айди
    public Task getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Задача не найдена"));
    }

    @PutMapping("/tasks/{id}") // Метод для изменения тела задачи по айди, сделал что бы не надо было передавать юзера в теле запроса
    public Task update(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDate(updatedTask.getDate());
        existingTask.setDone(updatedTask.isDone());

        return taskRepository.save(existingTask);
    }

    @DeleteMapping("/tasks/{id}") // Метод для удаления задачи
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    @PatchMapping("/tasks/{id}") // Метод для обновления поля done
    public void patchMethod(@PathVariable Long id, @RequestBody Task task) {
        if(task.isDone()){
            taskRepository.markAsDone(id);
        }
    }

    @Transactional
    @PatchMapping("/tasks/{id}:mark-as-done") // Метод для обновления поля done
    public void patchMethod(@PathVariable Long id){
        taskRepository.markAsDone(id);
    }

}