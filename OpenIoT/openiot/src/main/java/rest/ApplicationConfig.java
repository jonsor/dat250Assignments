package rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@ApplicationPath("/api")
@OpenAPIDefinition (info = @Info (
                    title = "OpenIot Service",
                    description = "An open service to share iot devices",
                    version = "1.0.0",
                    contact = @Contact (
                        name = "Group 5", 
                        email = "jonas@test.com"
                    )
                ),
			servers = {
			        @Server(url = "/docs",description = "localhost")
			}
            )
public class ApplicationConfig extends Application {

}