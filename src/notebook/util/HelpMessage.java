package notebook.util;

public class HelpMessage {
    public static void print() {
        System.out.println(
                "Доступные команды:\n" +
                        "CREATE\t- создать пользователя\n" +
                        "READ\t- получить пользователя по ID\n" +
                        "LIST\t- вывести список всех пользователей\n" +
                        "UPDATE\t- обновить информацию о пользователе по ID\n" +
                        "DELETE\t- удалить пользователя по ID\n" +
                        "HELP\t- вывести это сообщение\n" +
                        "EXIT\t- выход\n");
    }
}
