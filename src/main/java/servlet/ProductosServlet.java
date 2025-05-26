package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ProductosJpaController;
import dao.exceptions.NonexistentEntityException;
import dto.Productos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductosServlet", urlPatterns = {"/productosservlet"})
public class ProductosServlet extends HttpServlet {

    private ProductosJpaController productosController;

    @Override
    public void init() throws ServletException {
        productosController = new ProductosJpaController();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") // Formato ISO para fechas
            .create();
        
        try {
            switch (action) {
                case "list":
                    handleList(request, response, out, gson);
                    break;
                    
                case "get":
                    handleGet(request, response, out, gson);
                    break;
                    
                case "create":
                    handleCreate(request, response, out, gson);
                    break;
                    
                case "edit":
                    handleEdit(request, response, out, gson);
                    break;
                    
                case "delete":
                    handleDelete(request, response, out, gson);
                    break;
                    
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(gson.toJson(new Response(false, "Acción no válida")));
                    break;
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(new Response(false, "Error: " + e.getMessage())));
        } finally {
            out.close();
        }
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response, 
                          PrintWriter out, Gson gson) throws IOException {
        List<Productos> productos = productosController.findProductosEntities();
        out.print(gson.toJson(productos));
    }

    private void handleGet(HttpServletRequest request, HttpServletResponse response,
                         PrintWriter out, Gson gson) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Productos producto = productosController.findProductos(id);
        if (producto == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print(gson.toJson(new Response(false, "Producto no encontrado")));
        } else {
            out.print(gson.toJson(producto));
        }
    }

    private void handleCreate(HttpServletRequest request, HttpServletResponse response,
                            PrintWriter out, Gson gson) throws Exception {
        Productos nuevo = new Productos();
        nuevo.setNombProd(request.getParameter("nombre"));
        nuevo.setDescProd(request.getParameter("descripcion"));
        nuevo.setPrecProd(parseDouble(request.getParameter("precio")));
        nuevo.setStockProd(parseInt(request.getParameter("stock")));
        nuevo.setFechProd(new Date()); // Establece fecha actual
        
        productosController.create(nuevo);
        out.print(gson.toJson(new Response(true, "Producto creado exitosamente")));
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response,
                          PrintWriter out, Gson gson) throws Exception {
        Integer id = parseInt(request.getParameter("id"));
        Productos existente = productosController.findProductos(id);
        
        if (existente == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print(gson.toJson(new Response(false, "Producto no encontrado")));
            return;
        }
        
        existente.setNombProd(request.getParameter("nombre"));
        existente.setDescProd(request.getParameter("descripcion"));
        existente.setPrecProd(parseDouble(request.getParameter("precio")));
        existente.setStockProd(parseInt(request.getParameter("stock")));
        existente.setFechProd(new Date()); // Actualiza fecha de modificación
        
        productosController.edit(existente);
        out.print(gson.toJson(new Response(true, "Producto actualizado exitosamente")));
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response,
                            PrintWriter out, Gson gson) throws Exception {
        Integer id = parseInt(request.getParameter("id"));
        productosController.destroy(id);
        out.print(gson.toJson(new Response(true, "Producto eliminado exitosamente")));
    }

    private Double parseDouble(String value) throws Exception {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new Exception("Valor numérico inválido: " + value);
        }
    }

    private Integer parseInt(String value) throws Exception {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new Exception("Valor entero inválido: " + value);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private static class Response {
        boolean success;
        String message;

        public Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}