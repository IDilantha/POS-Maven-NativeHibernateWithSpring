package io.github.idilantha.pos.db;

import io.github.idilantha.pos.entity.Customer;
import io.github.idilantha.pos.entity.Item;
import io.github.idilantha.pos.entity.Order;
import io.github.idilantha.pos.entity.OrderDetail;
import javafx.scene.control.Alert;
import lk.ijse.dep.crypto.DEPCrypt;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class HibernateUtil {
    private static String username;
    private static String password;
    private static String db;
    private static String port;
    private static String ip;

    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        File file = new File("src/main/resources/application.properties");
        Properties properties = new Properties();

        try(FileInputStream fis = new FileInputStream(file)){
            properties.load(fis);
        }catch(Exception e){
            new Alert(Alert.AlertType.ERROR,"FIS EKE AULA");
        }
        username = DEPCrypt.decode(properties.getProperty("hibernate.connection.username"),"123");
        password = DEPCrypt.decode(properties.getProperty("hibernate.connection.password"),"123");
        db = properties.getProperty("pos.db");
        port = properties.getProperty("pos.port");
        ip = properties.getProperty("pos.ip");

        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .loadProperties(file)
                .applySetting("hibernate.connection.username", username)
                .applySetting("hibernate.connection.password", password)
                .build();

        Metadata metadata = new MetadataSources( standardRegistry )
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderDetail.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy( ImplicitNamingStrategyJpaCompliantImpl.INSTANCE )
                .build();

        return metadata.getSessionFactoryBuilder()
                .build();
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getDb() {
        return db;
    }

    public static String getPort() {
        return port;
    }

    public static String getIp() {
        return ip;
    }
}
