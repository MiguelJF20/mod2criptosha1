package servlet;

import dao.UsuariosJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.Sha1Util; // <-- Importar la nueva clase de utilidad

@WebServlet(name = "LogueoUsuario", urlPatterns = {"/logueousuario"})
public class LogueoUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8"); // Cambiado a application/json
        try (PrintWriter out = response.getWriter()) {
            
            String user = request.getParameter("user");
            String pass = request.getParameter("pass");

            // Validación básica de parámetros
            if (user == null || pass == null || user.isEmpty() || pass.isEmpty()) {
                out.println("{\"resultado\":\"error\", \"mensaje\":\"Usuario y contraseña requeridos\"}");
                return;
            }

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_ExamenMod2CriptoJara_war_1.0-SNAPSHOTPU");
            UsuariosJpaController usuDAO = new UsuariosJpaController(emf);

            // Opción 1: Si el frontend ya envía el hash SHA-1 (recomendado)
            boolean b = usuDAO.validar(user, pass.toLowerCase()); // Asegurar minúsculas

            // Opción 2: Si prefieres hashear en el servidor (comenta la línea anterior y descomenta esta)
            // boolean b = usuDAO.validar(user, Sha1Util.toSha1(pass));

            if (b) {
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", user);
                String token = utils.JwtUtil.generarToken(user);

                out.println("{\"resultado\":\"ok\", \"token\":\"" + token + "\"}");
            } else {
                out.println("{\"resultado\":\"error\", \"mensaje\":\"Credenciales inválidas\"}");
            }
            
            emf.close(); // Cerrar el EntityManagerFactory
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"resultado\":\"error\", \"mensaje\":\"Error en el servidor\"}");
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

    @Override
    public String getServletInfo() {
        return "Servlet para autenticación tradicional con SHA-1";
    }
}