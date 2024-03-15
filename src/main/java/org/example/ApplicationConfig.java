package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import org.example.Config.HibernateConfig;

import javax.naming.Context;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class ApplicationConfig {


    private ObjectMapper jsonMapper = new ObjectMapper();
    private static ApplicationConfig appConfig;
    private static Javalin app;
    private ApplicationConfig() {
    }

    public static ApplicationConfig getInstance() {
        if (appConfig == null) {
            appConfig = new ApplicationConfig();
        }
        return appConfig;
    }

private static void setCorsHeaders(Context ctx) {
    ctx.header("Access-Control-Allow-Origin", "*");
    ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
    ctx.header("Access-Control-Allow-Credentials", "true");
}





    public static String getProperty(String propName) throws IOException
    {
        try (InputStream is = HibernateConfig.class.getClassLoader().getResourceAsStream("properties-from-pom.properties"))
        {
            Properties prop = new Properties();
            prop.load(is);
            return prop.getProperty(propName);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IOException("Could not read property from pom file. Build Maven!");
        }
    }

    public static void startServer(Javalin app, int port) {
        Routes routes = new Routes();
        app.updateConfig(ApplicationConfig::configuration);
        app.routes(routes.getRoutes(app));
        //HibernateConfig.setTest(false);
        app.start(port);
    }



    public static void stopServer(Javalin app) {
        app.stop();
    }

}
