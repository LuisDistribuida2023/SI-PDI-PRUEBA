package com.programacion.distribuida;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import javax.ws.rs.core.Application;


@OpenAPIDefinition(info = @Info(
        title = "app-book",
        version = "1.0.0",
        description = "API para buscar y agregar libros, a una base de datos de catálogo",
        contact = @Contact(
                name = "lebriones",
                email = "lebriones@uce.edu.ec"
        ),
        license = @License(
                name = "Licencia"
        )
))

public class RestApplication extends Application {
}
