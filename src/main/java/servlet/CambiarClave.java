/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.UsuariosJpaController;
import dto.Usuarios;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "CambiarClave", urlPatterns = {"/cambiarclave"})
public class CambiarClave extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        JSONObject jsonResponse = new JSONObject();

        try {
            // Leer datos del cuerpo de la petición
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonRequest = new JSONObject(sb.toString());

            String user = jsonRequest.getString("user");
            String currentPassHash = jsonRequest.getString("currentPass");
            String newPassHash = jsonRequest.getString("newPass");

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_ExamenMod2CriptoJara_war_1.0-SNAPSHOTPU");
            UsuariosJpaController usuDAO = new UsuariosJpaController(emf);

            // 1. Verificar contraseña actual
            if (usuDAO.validar(user, currentPassHash)) {
                // 2. Actualizar contraseña
                Usuarios usuario = usuDAO.findUsuariosByLogiUsua(user); // Necesitarás implementar este método
                usuario.setPassUsua(newPassHash);
                usuDAO.edit(usuario);

                jsonResponse.put("success", true);
                jsonResponse.put("message", "Contraseña actualizada");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Contraseña actual incorrecta");
            }

            emf.close();
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error en el servidor: " + e.getMessage());
        }

        response.getWriter().print(jsonResponse.toString());
    }
}
