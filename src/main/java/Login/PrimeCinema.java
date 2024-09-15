package Login;

import Controlador.salaservlet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrimeCinema extends JFrame {

    private JPanel panelLogin, panelSignUp, panelIntermedio, panelRegistrarSucursal;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public PrimeCinema() {
        // Configuración de la ventana
        setTitle("Bienvenido a PrimeCinema");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Usamos CardLayout para cambiar entre los paneles de inicio de sesión, registro y panel intermedio
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Crear paneles de inicio de sesión, registro, panel intermedio y registro de sucursales
        panelLogin = createLoginPanel();
        panelSignUp = createSignUpPanel();
        panelIntermedio = createIntermedioPanel();
        panelRegistrarSucursal = createRegistrarSucursalPanel();

        // Agregar paneles al contenedor principal
        mainPanel.add(panelLogin, "login");
        mainPanel.add(panelSignUp, "signUp");
        mainPanel.add(panelIntermedio, "intermedio");
        mainPanel.add(panelRegistrarSucursal, "registrarSucursal");

        // Agregar el contenedor principal a la ventana
        add(mainPanel);

        // Mostrar el panel de inicio de sesión por defecto
        cardLayout.show(mainPanel, "login");
    }

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
                if (autenticarUsuario(usuarioField.getText(), new String(contrasenaField.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso!");
                    abrirIntermedio(); // Abre el panel intermedio tras el login
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                }
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
                cardLayout.show(mainPanel, "signUp");
            }
        });

        registerPanel.add(noAccountLabel);
        registerPanel.add(registerButton);
        panel.add(registerPanel);

        return panel;
    }

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
                    cardLayout.show(mainPanel, "login");
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
                cardLayout.show(mainPanel, "login");
            }
        });
        panel.add(switchToSignIn);

        return panel;
    }

    private JPanel createIntermedioPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelTitle = new JLabel("¡Bienvenido a PrimeCinema!", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Roboto", Font.BOLD, 18));
        panel.add(labelTitle);

        JButton goToButacasButton = new JButton("Ir a Selección de Butacas");
        goToButacasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSeleccionButacas(); // Redirigir al panel de selección de butacas
            }
        });
        panel.add(goToButacasButton);

        JButton goToRegistrarSucursalButton = new JButton("Registrar Sucursal");
        goToRegistrarSucursalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "registrarSucursal"); // Redirigir al panel de registro de sucursales
            }
        });
        panel.add(goToRegistrarSucursalButton);

        return panel;
    }

    private JPanel createRegistrarSucursalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelTitle = new JLabel("Registrar Sucursal", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Roboto", Font.BOLD, 18));
        panel.add(labelTitle);

        // Campos de formulario
        JTextField nombreSucursalField = new JTextField(20);
        nombreSucursalField.setBorder(BorderFactory.createTitledBorder("Nombre de la Sucursal"));

        JTextField gerenteField = new JTextField(20);
        gerenteField.setBorder(BorderFactory.createTitledBorder("Gerente"));

        JTextField direccionSucursalField = new JTextField(20);
        direccionSucursalField.setBorder(BorderFactory.createTitledBorder("Dirección"));

        JTextField telefonoSucursalField = new JTextField(20);
        telefonoSucursalField.setBorder(BorderFactory.createTitledBorder("Teléfono"));

        // Añadir los campos al panel
        panel.add(nombreSucursalField);
        panel.add(gerenteField);
        panel.add(direccionSucursalField);
        panel.add(telefonoSucursalField);

        // Botón de registro
        JButton registerButton = new JButton("Registrar Sucursal");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los datos de los campos
                String nombreSucursal = nombreSucursalField.getText();
                String gerente = gerenteField.getText();
                String direccionSucursal = direccionSucursalField.getText();
                String telefonoSucursal = telefonoSucursalField.getText();

                // Insertar los datos en la base de datos
                if (guardarSucursal(nombreSucursal, gerente, direccionSucursal, telefonoSucursal)) {
                    JOptionPane.showMessageDialog(null, "Sucursal registrada exitosamente!");
                    cardLayout.show(mainPanel, "intermedio"); // Volver al panel intermedio
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la sucursal.");
                }
            }
        });
        panel.add(registerButton);

        // Botón para cambiar al panel intermedio
        JButton backToIntermedioButton = new JButton("Volver al Panel Intermedio");
        backToIntermedioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "intermedio");
            }
        });
        panel.add(backToIntermedioButton);

        return panel;
    }

    private boolean guardarUsuario(String usuario, String contrasena, String nombre_completo, String dui, String direccion, String correo, String telefono) {
        String url = "jdbc:mysql://localhost:3306/primecinema"; // Cambia por tu URL y nombre de la base de datos
        String user = "root"; // Cambia por tu usuario de MySQL
        String password = ""; // Cambia por tu contraseña de MySQL

        String sql = "INSERT INTO usuarios (usuario, contrasena, nombre_completo, dui, direccion, correo, telefono) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena); // En producción, hash la contraseña antes de guardarla
            ps.setString(3, nombre_completo);
            ps.setString(4, dui);
            ps.setString(5, direccion);
            ps.setString(6, correo);
            ps.setString(7, telefono);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean guardarSucursal(String nombreSucursal, String gerente, String direccionSucursal, String telefonoSucursal) {
        String url = "jdbc:mysql://localhost:3306/primecinema"; // Cambia por tu URL y nombre de la base de datos
        String user = "root"; // Cambia por tu usuario de MySQL
        String password = ""; // Cambia por tu contraseña de MySQL

        String sql = "INSERT INTO sucursales (nombre_sucursal, gerente, direccion_sucursal, telefono_sucursal) VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreSucursal);
            ps.setString(2, gerente);
            ps.setString(3, direccionSucursal);
            ps.setString(4, telefonoSucursal);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean autenticarUsuario(String usuario, String contrasena) {
        String url = "jdbc:mysql://localhost:3306/primecinema"; // Cambia por tu URL y nombre de la base de datos
        String user = "root"; // Cambia por tu usuario de MySQL
        String password = ""; // Cambia por tu contraseña de MySQL

        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena); // En producción, compara el hash de la contraseña

            return ps.executeQuery().next();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void abrirIntermedio() {
        cardLayout.show(mainPanel, "intermedio"); // Muestra el panel intermedio
    }

    private void abrirSeleccionButacas() {
        this.dispose(); // Cierra la ventana actual (intermedio)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new salaservlet().setVisible(true); // Asegúrate de que salaservlet sea visible
            }
        });
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


