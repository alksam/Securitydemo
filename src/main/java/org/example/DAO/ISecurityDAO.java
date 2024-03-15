package org.example.DAO;


import io.javalin.validation.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.example.Entity.Role;
import org.example.Entity.User;

public interface ISecurityDAO {

       User getVerifiedUser(String username, String password) throws ValidationException;

    User CreateUser(String username, String password);
    Role CreateRole(String name);
    User addRoleToUser(User user, Role role);
}


