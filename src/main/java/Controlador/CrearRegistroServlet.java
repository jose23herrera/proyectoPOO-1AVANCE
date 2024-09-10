package Controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.PrintWriter;



@WebServlet("/CrearRegistroServlet")
public class CrearRegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");
        String nombre_completo = request.getParameter("nombre_completo");
        String dui = request.getParameter("dui");
        String direccion = request.getParameter("direccion");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");

        String url = "jdbc:mysql://localhost:3306/primecinema";
        String user = "root";
        String password = "";

        String sql = "INSERT INTO usuarios (usuario, contrasena, nombre_completo, dui, direccion, correo, telefono) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            ps.setString(3, nombre_completo);
            ps.setString(4, dui);
            ps.setString(5, direccion);
            ps.setString(6, correo);
            ps.setString(7, telefono);

            int rowsAffected = ps.executeUpdate();
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.print(rowsAffected > 0 ? "Success" : "Error");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
