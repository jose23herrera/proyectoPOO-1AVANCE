package Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Clase para interactuar con la base de datos para gestionar el estado de las butacas
class ButacaDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/primecinema"; // Cambia esto por tu configuración
    private static final String DB_USER = "root"; // Cambia esto por tu usuario de MySQL
    private static final String DB_PASSWORD = ""; // Cambia esto por tu contraseña de MySQL

    // Método para actualizar el estado de una butaca
    public boolean actualizarEstadoButaca(int fila, int columna, String estado) {
        String sql = "INSERT INTO butacas (fila, columna, estado) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE estado = VALUES(estado), fecha_modificacion = CURRENT_TIMESTAMP";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, fila);
            ps.setInt(2, columna);
            ps.setString(3, estado);

            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

public class salaservlet extends JFrame {

    private String[][] butacas;
    private final int FILAS = 8;
    private final int COLUMNAS = 5;
    private JButton[][] botonesButacas;
    private ButacaDAO butacaDAO; // Añadimos la instancia de ButacaDAO

    public salaservlet() {
        // Inicializar las butacas
        butacas = new String[FILAS][COLUMNAS];
        inicializarButacas();
        botonesButacas = new JButton[FILAS][COLUMNAS];
        butacaDAO = new ButacaDAO(); // Crear instancia de ButacaDAO

        // Configuraciones de la ventana
        setTitle("Selección de Butacas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        // Panel principal para las butacas
        JPanel panelButacas = new JPanel(new GridLayout(FILAS, COLUMNAS));

        // Inicializar botones y asignarles acción
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                botonesButacas[i][j] = new JButton("Desocupada");
                final int fila = i;
                final int columna = j;

                botonesButacas[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Llamar al método ocuparButaca
                        if (ocuparButaca(fila, columna)) {
                            botonesButacas[fila][columna].setText("Ocupada");
                            botonesButacas[fila][columna].setEnabled(false);

                            // Actualizar el estado de la butaca en la base de datos
                            butacaDAO.actualizarEstadoButaca(fila, columna, "Ocupada");
                        } else {
                            JOptionPane.showMessageDialog(null, "Butaca ya ocupada.");
                        }
                    }
                });

                panelButacas.add(botonesButacas[i][j]);
            }
        }

        // Botón para desocupar todas las butacas
        JButton desocuparButton = new JButton("Desocupar todas las butacas");
        desocuparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desocuparTodasButacas();
                for (int i = 0; i < FILAS; i++) {
                    for (int j = 0; j < COLUMNAS; j++) {
                        botonesButacas[i][j].setText("Desocupada");
                        botonesButacas[i][j].setEnabled(true);

                        // Actualizar el estado de la butaca en la base de datos
                        butacaDAO.actualizarEstadoButaca(i, j, "Desocupada");
                    }
                }
            }
        });

        // Añadir los paneles al frame
        add(panelButacas, BorderLayout.CENTER);
        add(desocuparButton, BorderLayout.SOUTH);

        // Mostrar la ventana
        setVisible(true);
    }

    // Inicializar las butacas como "Desocupada"
    private void inicializarButacas() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                butacas[i][j] = "Desocupada";
            }
        }
    }

    // Método para ocupar una butaca
    public boolean ocuparButaca(int fila, int columna) {
        if (butacas[fila][columna].equals("Desocupada")) {
            butacas[fila][columna] = "Ocupada";
            return true;
        }
        return false;
    }

    // Método para desocupar todas las butacas
    public void desocuparTodasButacas() {
        inicializarButacas();
    }

    public static void main(String[] args) {
        // Iniciar la aplicación
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new salaservlet();
            }
        });
    }
}
