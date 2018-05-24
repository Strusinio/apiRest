package rest;

import domain.entity.Product;
import domain.services.ProductService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
@Stateless
public class ProductRestService {

    @EJB
    private ProductService productService;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Product product) {
        Product result = productService.add(product);
        return Response.ok(result.getId()).build();
    }

    @POST
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Integer id,Product product) {
        Product productFromDb = productService.findById(id);
        if (productFromDb == null) {
            return Response.status(404).build();
        }
        productFromDb.setName(product.getName());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setProductCategory(product.getProductCategory());
        productService.update(productFromDb);
        return Response.ok().build();
    }

    @GET
    @Path("/find/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Integer id) {
        Product product = productService.findById(id);
        if (product == null) {
            return Response.status(404).build();
        }
        return Response.ok(product).build();
    }

    @GET
    @Path("/findAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GET
    @Path("/findByPrice/{price}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> findByPrice(@PathParam("price") Integer price) {
      return productService.findByPrice(price);
    }
    @GET
    @Path("/findByName")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> findByName(@QueryParam("name") String name) {
        return productService.findByName(name);

    }
    @GET
    @Path("/findByProductType/{productType}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> findByProductType(@PathParam("productType") String productType) {
        return productService.productType(productType);
    }
}
