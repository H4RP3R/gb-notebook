package notebook.controller;

import notebook.model.User;
import notebook.model.repository.GBRepository;
import notebook.util.InputUtil;

import java.util.List;

public class UserController {
    private final GBRepository repository;
    private final InputUtil input;

    public UserController(GBRepository repository) {
        this.repository = repository;
        this.input = new InputUtil();
    }

    public void saveUser(User user) {
        repository.create(user);
    }

    public User readUser(Long userId) {
        return repository.read(userId);
    }

    public void updateUser(String userId, User update) {
        update.setId(Long.parseLong(userId));
        repository.update(Long.parseLong(userId), update);
    }

    public List<User> readAll() {
        return repository.findAll();
    }

    public void deleteUser(String userId) {
        repository.delete(Long.parseLong(userId));
    }

    public User createUser() {
        String firstName = input.prompt("Имя: ");
        String lastName = input.prompt("Фамилия: ");
        String phone = input.prompt("Номер телефона: ");
        return new User(firstName, lastName, phone);
    }
}
