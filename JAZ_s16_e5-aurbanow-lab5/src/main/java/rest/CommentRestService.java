package rest;

import domain.entity.Comment;
import domain.entity.Product;
import domain.services.CommentService;
import domain.services.ProductService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/comments")
@Stateless
public class CommentRestService {

    @EJB
    private CommentService commentService;

    @EJB
    private ProductService productService;

    @POST
    @Path("/add/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(@PathParam("id") Integer productId, Comment comment) {
        Product product = productService.findById(productId);
        if (product == null) {
            return Response.status(404).build();
        }
        product.getComments().add(comment);
        comment.setProduct(product);
        productService.update(product);
        commentService.add(comment);
        return Response.ok().build();
    }

    @GET
    @Path("/find/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> find(@PathParam("id") Integer productId) {
        return commentService.findByProductId(productId);
    }

    @GET
    @Path("/findAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> findAll() {
        return commentService.findAll();
    }

    @POST
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@PathParam("id") Integer id) {
        Comment comment = commentService.find(id);
        if (comment == null) {
            return Response.status(404).build();
        }
        commentService.remove(comment);
        return Response.ok().build();
    }
}
