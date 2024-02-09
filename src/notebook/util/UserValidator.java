package notebook.util;

import notebook.model.User;

public class UserValidator {
    public User validate(User user) {
        user.setFirstName(user.getFirstName().replaceAll(" ", ""));
        user.setLastName(user.getLastName().replaceAll(" ", ""));
        return user;
    }
}
