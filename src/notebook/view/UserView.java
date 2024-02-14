package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;
import notebook.util.HelpMessage;
import notebook.util.InputUtil;

import java.util.List;

public class UserView {
    private final UserController userController;
    private final InputUtil input;

    public UserView(UserController userController) {
        this.userController = userController;
        this.input = new InputUtil();
    }

    public void run() {
        Commands com;
        HelpMessage.print();

        while (true) {
            String command = input.prompt("Введите команду: ");
            com = Commands.valueOf(command.toUpperCase());
            if (com == Commands.EXIT)
                return;
            switch (com) {
                case CREATE:
                    User u = userController.createUser();
                    userController.saveUser(u);
                    break;
                case READ:
                    String id = input.prompt("Идентификатор пользователя: ");
                    try {
                        User user = userController.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case UPDATE:
                    String userId = input.prompt("Enter user id: ");
                    userController.updateUser(userId, userController.createUser());
                    break;
                case LIST:
                    List<User> users = userController.readAll();
                    System.out.println(users);
                    break;
                case DELETE:
                    userId = input.prompt("Enter user id: ");
                    userController.deleteUser(userId);
                    break;
                case HELP:
                    HelpMessage.print();
                    break;
            }
        }
    }
}
