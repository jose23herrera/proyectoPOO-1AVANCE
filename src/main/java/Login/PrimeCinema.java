package Login;

import Controlador.salaservlet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrimeCinema extends JFrame {

    private JPanel panelLogin, panelSignUp, panelIntermedio, panelRegistrarSucursal, panelRegistrarSala, panelRegistrarPelicula;
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

        // Crear paneles de inicio de sesión, registro, panel intermedio, registro de sucursales, salas y películas
        panelLogin = createLoginPanel();
        panelSignUp = createSignUpPanel();
        panelIntermedio = createIntermedioPanel();
        panelRegistrarSucursal = createRegistrarSucursalPanel();
        panelRegistrarSala = createRegistrarSalaPanel();
        panelRegistrarPelicula = createRegistrarPeliculaPanel();

        // Agregar paneles al contenedor principal
        mainPanel.add(panelLogin, "login");
        mainPanel.add(panelSignUp, "signUp");
        mainPanel.add(panelIntermedio, "intermedio");
        mainPanel.add(panelRegistrarSucursal, "registrarSucursal");
        mainPanel.add(panelRegistrarSala, "registrarSala");
        mainPanel.add(panelRegistrarPelicula, "registrarPelicula");

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
        panel.setLayout(new GridLayout(4, 1, 10, 10));
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

        JButton goToRegistrarSalaButton = new JButton("Registrar Sala");
        goToRegistrarSalaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "registrarSala"); // Redirigir al panel de registro de salas
            }
        });
        panel.add(goToRegistrarSalaButton);

        JButton goToRegistrarPeliculaButton = new JButton("Registrar Película");
        goToRegistrarPeliculaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "registrarPelicula"); // Redirigir al panel de registro de películas
            }
        });
        panel.add(goToRegistrarPeliculaButton);

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

        JTextField direccionField = new JTextField(20);
        direccionField.setBorder(BorderFactory.createTitledBorder("Dirección"));

        JTextField telefonoField = new JTextField(20);
        telefonoField.setBorder(BorderFactory.createTitledBorder("Teléfono"));

        // Añadir los campos al panel
        panel.add(nombreSucursalField);
        panel.add(gerenteField);
        panel.add(direccionField);
        panel.add(telefonoField);

        // Botón de registro de sucursal
        JButton registerSucursalButton = new JButton("Registrar Sucursal");
        registerSucursalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los datos de los campos
                String nombreSucursal = nombreSucursalField.getText();
                String gerente = gerenteField.getText();
                String direccion = direccionField.getText();
                String telefono = telefonoField.getText();

                // Insertar los datos en la base de datos
                if (guardarSucursal(nombreSucursal, gerente, direccion, telefono)) {
                    JOptionPane.showMessageDialog(null, "Sucursal registrada exitosamente!");
                    cardLayout.show(mainPanel, "intermedio");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la sucursal.");
                }
            }
        });
        panel.add(registerSucursalButton);

        // Botón para volver al panel intermedio
        JButton switchToIntermedio = new JButton("Volver");
        switchToIntermedio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "intermedio");
            }
        });
        panel.add(switchToIntermedio);

        return panel;
    }

    private JPanel createRegistrarSalaPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelTitle = new JLabel("Registrar Sala", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Roboto", Font.BOLD, 18));
        panel.add(labelTitle);

        // Campos de formulario
        JTextField numeroSalaField = new JTextField(20);
        numeroSalaField.setBorder(BorderFactory.createTitledBorder("Número de Sala"));

        JTextField sucursalField = new JTextField(20);
        sucursalField.setBorder(BorderFactory.createTitledBorder("Sucursal"));

        JTextField peliculaField = new JTextField(20);
        peliculaField.setBorder(BorderFactory.createTitledBorder("Película"));

        JTextField horariosField = new JTextField(20);
        horariosField.setBorder(BorderFactory.createTitledBorder("Horarios de Proyección"));

        // Añadir los campos al panel
        panel.add(numeroSalaField);
        panel.add(sucursalField);
        panel.add(peliculaField);
        panel.add(horariosField);

        // Botón de registro de sala
        JButton registerSalaButton = new JButton("Registrar Sala");
        registerSalaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los datos de los campos
                String numeroSala = numeroSalaField.getText();
                String sucursal = sucursalField.getText();
                String pelicula = peliculaField.getText();
                String horarios = horariosField.getText();

                // Insertar los datos en la base de datos
                if (guardarSala(numeroSala, sucursal, pelicula, horarios)) {
                    JOptionPane.showMessageDialog(null, "Sala registrada exitosamente!");
                    cardLayout.show(mainPanel, "intermedio");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la sala.");
                }
            }
        });
        panel.add(registerSalaButton);

        // Botón para volver al panel intermedio
        JButton switchToIntermedio = new JButton("Volver");
        switchToIntermedio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "intermedio");
            }
        });
        panel.add(switchToIntermedio);

        return panel;
    }

    private JPanel createRegistrarPeliculaPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelTitle = new JLabel("Registrar Película", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Roboto", Font.BOLD, 18));
        panel.add(labelTitle);

        // Campos de formulario
        JTextField nombrePeliculaField = new JTextField(20);
        nombrePeliculaField.setBorder(BorderFactory.createTitledBorder("Nombre de la Película"));

        JTextField generoField = new JTextField(20);
        generoField.setBorder(BorderFactory.createTitledBorder("Género"));

        JTextField clasificacionField = new JTextField(20);
        clasificacionField.setBorder(BorderFactory.createTitledBorder("Clasificación"));

        String[] formatos = {"Tradicional", "3D"};
        JComboBox<String> formatoComboBox = new JComboBox<>(formatos);
        formatoComboBox.setBorder(BorderFactory.createTitledBorder("Formato"));

        // Valor de función (usaremos un JLabel para mostrar el valor basado en la selección de formato)
        JLabel valorFuncionLabel = new JLabel("Valor de la Función: $0.00");
        valorFuncionLabel.setBorder(BorderFactory.createTitledBorder("Valor de Función"));

        // Añadir los campos al panel
        panel.add(nombrePeliculaField);
        panel.add(generoField);
        panel.add(clasificacionField);
        panel.add(formatoComboBox);
        panel.add(valorFuncionLabel);

        // Actualizar valor de función al seleccionar formato
        formatoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String formato = (String) formatoComboBox.getSelectedItem();
                double valor = 0.0;
                if (formato.equals("Tradicional")) {
                    valor = 5.00; // Valor por defecto para Adulto
                } else if (formato.equals("3D")) {
                    valor = 6.55; // Valor por defecto para Adulto
                }
                valorFuncionLabel.setText("Valor de la Función: $" + valor);
            }
        });

        // Botón de registro de película
        JButton registerPeliculaButton = new JButton("Registrar Película");
        registerPeliculaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los datos de los campos
                String nombrePelicula = nombrePeliculaField.getText();
                String genero = generoField.getText();
                String clasificacion = clasificacionField.getText();
                String formato = (String) formatoComboBox.getSelectedItem();
                double valorFuncion = formato.equals("Tradicional") ? 5.00 : 6.55; // Valor por defecto para Adulto

                // Insertar los datos en la base de datos
                if (guardarPelicula(nombrePelicula, genero, clasificacion, formato, valorFuncion)) {
                    JOptionPane.showMessageDialog(null, "Película registrada exitosamente!");
                    cardLayout.show(mainPanel, "intermedio");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la película.");
                }
            }
        });
        panel.add(registerPeliculaButton);

        // Botón para volver al panel intermedio
        JButton switchToIntermedio = new JButton("Volver");
        switchToIntermedio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "intermedio");
            }
        });
        panel.add(switchToIntermedio);

        return panel;
    }

    private boolean autenticarUsuario(String usuario, String contrasena) {
        // Lógica para autenticar al usuario
        return true; // Cambiar según la lógica de autenticación
    }

    private boolean guardarUsuario(String usuario, String contrasena, String nombre_completo, String dui, String direccion, String correo, String telefono) {
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
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean guardarSucursal(String nombreSucursal, String gerente, String direccion, String telefono) {
        String url = "jdbc:mysql://localhost:3306/primecinema";
        String user = "root";
        String password = "";

        String sql = "INSERT INTO sucursales (nombre_sucursal, gerente, direccion_sucursal, telefono_sucursal) VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreSucursal);
            ps.setString(2, gerente);
            ps.setString(3, direccion);
            ps.setString(4, telefono);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean guardarSala(String numeroSala, String sucursal, String pelicula, String horarios) {
        String url = "jdbc:mysql://localhost:3306/primecinema";
        String user = "root";
        String password = "";

        String sql = "INSERT INTO salas (numero_sala, sucursal, pelicula, horarios) VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numeroSala);
            ps.setString(2, sucursal);
            ps.setString(3, pelicula);
            ps.setString(4, horarios);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean guardarPelicula(String nombrePelicula, String genero, String clasificacion, String formato, double valorFuncion) {
        String url = "jdbc:mysql://localhost:3306/primecinema";
        String user = "root";
        String password = "";

        String sql = "INSERT INTO peliculas (nombre_pelicula, genero, clasificacion, formato, valor_funcion) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombrePelicula);
            ps.setString(2, genero);
            ps.setString(3, clasificacion);
            ps.setString(4, formato);
            ps.setDouble(5, valorFuncion);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void abrirIntermedio() {
        cardLayout.show(mainPanel, "intermedio");
    }

    private void abrirSeleccionButacas() {
        // Lógica para abrir el panel de selección de butacas
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
