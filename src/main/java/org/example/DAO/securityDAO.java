package org.example.DAO;

import io.javalin.validation.ValidationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.example.Entity.Role;
import org.example.Entity.User;

import java.util.List;

public class securityDAO implements ISecurityDAO{

    private static ISecurityDAO instance;
    private EntityManagerFactory emf;
    public securityDAO(EntityManagerFactory _emf) {
        this.emf = _emf;
    }
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    @Override
    public User CreateUser(String username, String password) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = new User( username, password);
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        return user;

    }

    @Override
    public Role CreateRole(String name) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Role role = new Role(name);
        em.persist(role);
        em.getTransaction().commit();
        em.close();
        return role;
    }

    @Override
    public User addRoleToUser(User user, Role role) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        user.addRole(role);
        em.merge(user);
        em.getTransaction().commit();
        em.close();
        return user;
    }


    @Override
    public User getVerifiedUser(String username, String password) throws ValidationException {
        try (EntityManager em = getEntityManager()) {
            System.out.println("Username inside GET-verifid-uder " + username + " Password: " + password);
            List<User> users = em.createQuery("SELECT u FROM User u").getResultList();
            System.out.println("Size of USERS: " + users.size());
            users.stream().forEach(user -> System.out.println(user.getUsername() + " " + user.getPassword()));
            User user = em.find(User.class, username);
            if (user == null)
                throw new EntityNotFoundException("No user found with username: " + username); //RuntimeException
            user.getRoles().size(); // force roles to be fetched from db
            if (!user.verifyPassword(password))
                throw new ValidationException("Incorrect password");
            return user;
        }
    }
}
