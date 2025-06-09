# TaskScheduler
RESTful API-приложение для управления задачами. 
Реализована авторизация, регистрация, хранение задач для пользователей. Используется стек: Spring Boot, Spring Security, JPA, PostgreSQL, Docker, Flyway.
    


    Структура проекта

    AuthController.java — контроллер для регистрации и логина
    TaskController.java — работа с задачами (CRUD + mark as done)
    UserController.java — получение текущего пользователя
    application.yml — конфигурация Spring-приложения
    docker-compose.yml — запуск PostgreSQL в Docker
    db/migration — миграции Flyway


Запуск проекта
1. Клонировать репозиторий: "git clone https://github.com/AssylBoribay/TaskScheduler"
2. Запустить PostgreSQL через Docker
"docker-compose up -d"
3. Собрать и запустить приложение
"./gradlew bootRun"


1. POST /auth/register — регистрация в формате json, raw
    Пример запроса: 
    {
    "login": "user",
    "password": "123456"
    }
2. POST /login — авторизация в x-www-form-urlencoded
3. GET /tasks — список задач
4. POST /tasks — создать задачу
   Пример запроса:
   {
   "description": "example task",
   "date": "2025-06-20"
   }
5. PATCH /tasks/{id} - обновление поля done
   Пример запроса:
   "done": "true"
6. DELETE /tasks/{id} - удаление задачи по айди
7. PUT /tasks/{id} - обновление тела задачи по айди
   Пример запроса:
   {
   "description": "updated example task",
   "date": "2025-06-21"
   }

Автор: Boribay Assyl 