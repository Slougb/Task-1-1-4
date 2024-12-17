package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Util {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/mybase" , "root", "root");
    }

    public static SessionFactory getSessionFactory() {

        SessionFactory sessionFactory = null;
        try {
            // Создание конфигурации
            Configuration configuration = new Configuration();

//             Установка параметров
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mybase");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "root");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.current_session_context_class", "thread");

            // Регистрация сущностей
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

}
