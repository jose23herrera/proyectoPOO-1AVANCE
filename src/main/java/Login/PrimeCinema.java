package Login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PrimeCinema extends JFrame {

    private JPanel panelLogin, panelSignUp;
    private CardLayout cardLayout;
    private JPanel mainPanel; // Añadido como variable de instancia

    public PrimeCinema() {
        // Configuración de la ventana
        setTitle("Bienvenido a PrimeCinema");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Usamos CardLayout para cambiar entre los paneles de inicio de sesión y registro
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout); // Este es el panel que debe tener el CardLayout

        // Crear paneles de inicio de sesión y registro
        panelLogin = createLoginPanel();
        panelSignUp = createSignUpPanel();

        // Agregar paneles al contenedor principal
        mainPanel.add(panelLogin, "login");
        mainPanel.add(panelSignUp, "signUp");

        // Agregar el contenedor principal a la ventana
        add(mainPanel);

        // Mostrar el panel de inicio de sesión por defecto
        cardLayout.show(mainPanel, "login"); // Cambia "getContentPane()" por "mainPanel"
    }

    // Panel de inicio de sesión (Login)
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelTitle = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Roboto", Font.BOLD, 18));
        panel.add(labelTitle);

        // Campos de formulario
        JTextField usuarioField = new JTextField(20);
        usuarioField.setBorder(BorderFactory.createTitledBorder("Usuario"));

        JPasswordField contrasenaField = new JPasswordField(20);
        contrasenaField.setBorder(BorderFactory.createTitledBorder("Contraseña"));

        // Añadir los campos al panel
        panel.add(usuarioField);
        panel.add(contrasenaField);

        // Botón de inicio de sesión
        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la lógica para el inicio de sesión
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso!");
            }
        });
        panel.add(loginButton);

        // Mensaje y botón para registrarse
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel noAccountLabel = new JLabel("¿No tienes cuenta?");
        JButton registerButton = new JButton("Regístrate aquí");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "signUp"); // Cambia "getContentPane()" por "mainPanel"
            }
        });

        registerPanel.add(noAccountLabel);
        registerPanel.add(registerButton);
        panel.add(registerPanel);

        return panel;
    }

    // Panel de registro (Sign Up)
    private JPanel createSignUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelTitle = new JLabel("Crear una cuenta", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Jose", Font.BOLD, 18));
        panel.add(labelTitle);

        // Campos de formulario
        JTextField usuarioField = new JTextField(20);
        usuarioField.setBorder(BorderFactory.createTitledBorder("Usuario"));

        JPasswordField contrasenaField = new JPasswordField(20);
        contrasenaField.setBorder(BorderFactory.createTitledBorder("Contraseña"));

        JTextField nombre_completoField = new JTextField(20);
        nombre_completoField.setBorder(BorderFactory.createTitledBorder("Nombre Completo"));

        JTextField duiField = new JTextField(20);
        duiField.setBorder(BorderFactory.createTitledBorder("DUI"));

        JTextField direccionField = new JTextField(20);
        direccionField.setBorder(BorderFactory.createTitledBorder("Dirección"));

        JTextField correoField = new JTextField(20);
        correoField.setBorder(BorderFactory.createTitledBorder("Correo Electrónico"));

        JTextField telefonoField = new JTextField(20);
        telefonoField.setBorder(BorderFactory.createTitledBorder("Teléfono"));

        // Añadir los campos al panel
        panel.add(usuarioField);
        panel.add(contrasenaField);
        panel.add(nombre_completoField);
        panel.add(duiField);
        panel.add(direccionField);
        panel.add(correoField);
        panel.add(telefonoField);

        // Botón de registro
        JButton registerButton = new JButton("Registrarse");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los datos de los campos
                String usuario = usuarioField.getText();
                String contrasena = new String(contrasenaField.getPassword());
                String nombre_completo = nombre_completoField.getText();
                String dui = duiField.getText();
                String direccion = direccionField.getText();
                String correo = correoField.getText();
                String telefono = telefonoField.getText();

                // Insertar los datos en la base de datos
                if (guardarUsuario(usuario, contrasena, nombre_completo, dui, direccion, correo, telefono)) {
                    JOptionPane.showMessageDialog(null, "Registro exitoso!");
                    cardLayout.show(mainPanel, "login"); // Cambia "getContentPane()" por "mainPanel"
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar el usuario.");
                }
            }
        });
        panel.add(registerButton);

        // Botón para cambiar al inicio de sesión
        JButton switchToSignIn = new JButton("Volver a Iniciar Sesión");
        switchToSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "login"); // Cambia "getContentPane()" por "mainPanel"
            }
        });
        panel.add(switchToSignIn);

        return panel;
    }

    // Método para guardar el usuario en la base de datos
    private boolean guardarUsuario(String usuario, String contrasena, String nombre_completo, String dui, String direccion, String correo, String telefono) {
        String url = "jdbc:mysql://localhost:3306/primecinema"; // Cambia por tu URL y nombre de la base de datos
        String user = "root"; // Cambia por tu usuario de MySQL
        String password = ""; // Cambia por tu contraseña de MySQL

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
            return rowsAffected > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PrimeCinema().setVisible(true);
            }
        });
    }
}
