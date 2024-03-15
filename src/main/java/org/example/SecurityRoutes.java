package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import org.example.Controller.SecurityController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class SecurityRoutes {

        private static ObjectMapper jsonMapper = new ObjectMapper();

        private static SecurityController securityController = SecurityController.getInstance();
        public static EndpointGroup getSecurityRoutes() {
            return ()->{
                path("/auth", ()->{
                    post("/login", securityController.login(),Role.ANYONE);
                    post("/register", securityController.register(),Role.ANYONE);
//                post("/authenticate", securityController.authenticate());
//                get("/logout", securityController.logout());
                });
            };
        }
        public static EndpointGroup getSecuredRoutes(){
            return ()->{
                path("/protected", ()->{
                    before(securityController.authenticate());
                    get("/user_demo",(ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from USER Protected")),Role.USER);
                    get("/admin_demo",(ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from ADMIN Protected")),Role.ADMIN);
                });
            };
        }
        public enum Role implements RouteRole { ANYONE, USER, ADMIN }
    }
}
