package notebook.model.repository.impl;

import notebook.util.DBConnector;
import notebook.util.UserValidator;
import notebook.util.mapper.impl.UserMapper;
import notebook.model.User;
import notebook.model.repository.GBRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements GBRepository {
    private final UserMapper mapper;

    public UserRepository() {
        this.mapper = new UserMapper();
    }

    @Override
    public List<User> findAll() {
        List<String> lines = readAll();
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            users.add(mapper.toOutput(line));
        }
        return users;
    }

    @Override
    public User create(User user) {
        UserValidator userValidator = new UserValidator();
        user = userValidator.validate(user);
        List<User> users = findAll();
        long max = 0L;
        for (User u : users) {
            long id = u.getId();
            if (max < id) {
                max = id;
            }
        }
        long next = max + 1;
        user.setId(next);
        users.add(user);
        write(users);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        List<User> users = findAll();

        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<User> update(Long userId, User update) {
        List<User> users = findAll();
        User editUser = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        UserValidator userValidator = new UserValidator();
        update = userValidator.validate(update);

        editUser.setFirstName(update.getFirstName().isEmpty() ? editUser.getFirstName() : update.getFirstName());
        editUser.setLastName(update.getLastName().isEmpty() ? editUser.getLastName() : update.getLastName());
        editUser.setPhone(update.getPhone().isEmpty() ? editUser.getPhone() : update.getPhone());

        users.set(users.indexOf(editUser), editUser);
        write(users);

        return Optional.of(update);
    }

    @Override
    public void delete(Long id) {
        List<User> users = findAll();
        User deleteUser = findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        users.remove(deleteUser);
        write(users);
    }

    private void write(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User u : users) {
            lines.add(mapper.toInput(u));
        }
        saveAll(lines);
    }

    private List<String> readAll() {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(DBConnector.DB_PATH);
            // создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            // создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            if (line != null) {
                lines.add(line);
            }
            while (line != null) {
                // считываем остальные строки в цикле
                line = reader.readLine();
                if (line != null) {
                    lines.add(line);
                }
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private void saveAll(List<String> data) {
        try (FileWriter writer = new FileWriter(DBConnector.DB_PATH, false)) {
            for (String line : data) {
                // запись всей строки
                writer.write(line);
                // запись по символам
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User read(Long userId) {
        return findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
