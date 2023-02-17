package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Author;
import com.programacion.distribuida.repository.AuthorRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorRest {

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener autor por ID")
    @APIResponse(responseCode = "200", description = "Autor encontrado exitosamente", content =
    @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)))
    @APIResponse(responseCode = "400", description = "Búsqueda del autor fallida")
    public Author findById(
            @PathParam("id") Long id) {
        return repository.findById(id);
    }

    @Inject AuthorRepository repository;

    @GET
    @Operation(summary = "Obtener autores")
    @APIResponse(responseCode = "200", description = "Autores", content =
    @Content(mediaType = "application/json", schema = @Schema(allOf = Author.class)))
    @APIResponse(responseCode = "500", description = "Error al buscar los autores")
    public List<Author> findAll() {
        try {
            return repository.findAll().list();
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al buscar los autores");
        }
    }

    @POST
    @Operation(description = "Ingresar Autor")
    @APIResponse(responseCode = "201", description = "Ingresado correctamente")
    @APIResponse(responseCode = "500", description = "Error")
    public void insert(Author obj) {
        repository.persist(obj);
    }

    @PUT
    @Path("/{id}")
    @Operation(description = "Actualizar")
    @APIResponse(responseCode = "200", description = "Actualizado correctamente")
    @APIResponse(responseCode = "500", description = "Error")
    public void update(Author obj, @PathParam("id") Long id) {

        var author = repository.findById(id);

        author.setFirst_name(obj.getFirst_name());
        author.setLast_name(obj.getLast_name());
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar")
    @APIResponse(responseCode = "204",description = "Eliminado correctamente")
    public void delete(
            @Parameter(description = "ID del autor a eliminar", required = true)
            @PathParam("id") Long id ) {
        repository.deleteById(id);
    }
}
