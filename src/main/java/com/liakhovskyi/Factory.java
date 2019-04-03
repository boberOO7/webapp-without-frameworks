package com.liakhovskyi;

import com.liakhovskyi.controller.GetAllCategoriesController;
import com.liakhovskyi.controller.GetCategoryByIdController;
import com.liakhovskyi.controller.LoginUserController;
import com.liakhovskyi.dao.CategoryDao;
import com.liakhovskyi.dao.CategoryDaoImpl;
import com.liakhovskyi.dao.UserDao;
import com.liakhovskyi.dao.UserDaoImpl;
import com.liakhovskyi.service.CategoryService;
import com.liakhovskyi.service.CategoryServiceImpl;
import com.liakhovskyi.service.UserService;
import com.liakhovskyi.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Factory {

    private static Connection connection;

    static {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/jv-jan-2019", "sa", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static LoginUserController getLoginUserController(UserService userService) {
        return new LoginUserController(userService);
    }

    public static UserService getUserServiceImpl(UserDao userDao) {
        return new UserServiceImpl(userDao);
    }

    public static UserDao getUserDaoImpl(Connection connection) {
        return new UserDaoImpl(connection);
    }

    public static GetAllCategoriesController getGetAllCategoriesController(CategoryService categoryService) {
        return new GetAllCategoriesController(categoryService);
    }

    public static CategoryService getCategoryService(CategoryDao categoryDao) {
        return new CategoryServiceImpl(categoryDao);
    }

    public static CategoryDao getCategoryDao(Connection connection) {
        return new CategoryDaoImpl(connection);
    }

    public static GetCategoryByIdController getGetCategoryByIdController(CategoryService categoryService) {
        return new GetCategoryByIdController(categoryService);
    }
}
