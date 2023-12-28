package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Anton", "Trunov", (byte) 35);
        userService.saveUser("Sasha", "Belov", (byte) 25);
        userService.saveUser("Artur", "Suhanov", (byte) 34);
        userService.saveUser("Viktor", "Davidov", (byte) 28);
        userService.removeUserById(1);
        for (User user : userService.getAllUsers() ){
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.closeSessionFactory();
    }
}

