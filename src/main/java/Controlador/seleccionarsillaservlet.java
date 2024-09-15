package Controlador;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class seleccionarsillaservlet extends JFrame {

    private salaservlet sala; // Clase que maneja la lógica de ocupación de butacas
    private JButton[][] botonesButacas;
    private final int FILAS = 8;
    private final int COLUMNAS = 5;

    public seleccionarsillaservlet() {
        sala = new salaservlet(); // Asegúrate de que esta clase tenga el método "ocuparButaca"
        botonesButacas = new JButton[FILAS][COLUMNAS];

        // Configuraciones de la ventana
        setTitle("Selección de Sillas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        // Panel para las butacas (grid de 8x5)
        JPanel panelButacas = new JPanel(new GridLayout(FILAS, COLUMNAS));

        // Inicializar los botones de las butacas y asignarles acción
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                botonesButacas[i][j] = new JButton("Desocupada");
                final int fila = i;
                final int columna = j;

                // Acción al presionar el botón (seleccionar butaca)
                botonesButacas[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Cambiar a sala.ocuparButaca en vez de seleccionarsillaservlet
                        if (sala.ocuparButaca(fila, columna)) {
                            botonesButacas[fila][columna].setText("Ocupada");
                            botonesButacas[fila][columna].setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Butaca ya ocupada.");
                        }
                    }
                });

                // Añadir cada botón al panel de butacas
                panelButacas.add(botonesButacas[i][j]);
            }
        }

        // Botón para finalizar la selección
        JButton finalizarButton = new JButton("Finalizar Selección");
        finalizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Selección de butacas finalizada.");
                System.exit(0); // Cerrar la aplicación
            }
        });

        // Añadir el panel de butacas y el botón al JFrame
        add(panelButacas, BorderLayout.CENTER);
        add(finalizarButton, BorderLayout.SOUTH);

        // Mostrar la ventana
        setVisible(true);
    }

    public static void main(String[] args) {
        // Iniciar la aplicación en el hilo de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new seleccionarsillaservlet();
            }
        });
    }
}
