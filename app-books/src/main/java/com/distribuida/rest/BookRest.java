package com.distribuida.rest;

import com.distribuida.db.Book;
import com.distribuida.servicios.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Path("/books")
public class BookRest {
    @Inject
    private BookService bookService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener libro por ID")
    @APIResponse(responseCode = "200", description = "El libro ha sido encontrado", content =
    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @APIResponse(responseCode = "404", description = "El libro no ha sido encontrado")

    public Book findById(
            @Parameter(description = "ID del libro a buscar", required = true)
            @PathParam("id") Integer id)  {

        Book book = bookService.getBookById(id);
        return bookService.getBookById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "GET BOOKS")
    @APIResponse(responseCode = "200", description = "Sus libros son: ", content =
    @Content(mediaType = "application/json", schema = @Schema(allOf = Book.class)))
    public List<Book> findAll() {
        return bookService.getBooks();
    }

    @DELETE
    @Path("/{id}")
    @Operation(description = "Borrar archivo")
    @APIResponse(responseCode = "204", description = "Libro borrado correctamente")
    @APIResponse(responseCode = "500", description = "Error")
    public Response delete(
            @Parameter(description = "ID BOOK", required = true)
            @PathParam("id") Integer id) {
        bookService.delete(id);
        return Response.status((Response.Status.NO_CONTENT)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "CREATE BOOK")
    @APIResponse(responseCode = "201", description = "El libro ha sido creado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Book.class)))
    @APIResponse(responseCode = "400", description = "Petición inválida")
    public Response create(
            @RequestBody(description = "Datos del nuevo libro", required = true,
                    content = @Content(schema = @Schema(implementation = Book.class)))
            Book b){
        bookService.creteBook(b);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(description = "UPDATE BOOK")
    @APIResponse(responseCode = "200", description = "El libro ha sido actualizado")
    @APIResponse(responseCode = "400", description = "Petición inválida")
    @APIResponse(responseCode = "404", description = "El libro no se ha encontrado")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(
            @RequestBody(description = "D del libro a actualizar", required = true,
                    content = @Content(schema = @Schema(implementation = Book.class)))
            Book b,
            @Parameter(description = "Datos del libro actualizado", required = true)
            @PathParam("id") Integer id){
        bookService.updateBook(id, b);
        return Response.status((Response.Status.OK)).build();
    }

}
