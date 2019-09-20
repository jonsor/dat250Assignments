package rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@ApplicationPath("/api")
@SwaggerDefinition (info = @Info (
                    title = "OpenIot Service",
                    description = "An open service to share iot devices",
                    version = "1.0.0",
                    contact = @Contact (
                        name = "Jonas Sørsdal", 
                        email = "jonas@test.com"
                    )
                )
            )
public class ApplicationConfig extends Application {

}