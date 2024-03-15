package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.Config.HibernateConfig;
import org.example.Entity.Role;
import org.example.Entity.User;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
        EntityManager em = emf.createEntityManager();
        UserDAO userDAO = new UserDAO(emf);


        User u = userDAO.CreateUser("user1", "password1");
        System.out.println(u.getUsername());

        Role r = userDAO.CreateRole("role1");
        System.out.println(r.getName());

        User u2 = userDAO.addRoleToUser(u, r);





    }
}