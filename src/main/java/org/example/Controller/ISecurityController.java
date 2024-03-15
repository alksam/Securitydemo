package org.example.Controller;

import org.example.DTO.UserDTO;

import java.util.logging.Handler;

public interface ISecurityController {

    String createToken(UserDTO user);
    Handler register();

    Handler login();
    Handler authenticate();
}
