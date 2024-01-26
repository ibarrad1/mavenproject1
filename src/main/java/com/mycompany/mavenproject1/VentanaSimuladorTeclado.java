import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaSimuladorTeclado extends JFrame {
    private JTextField campoTexto;
    private JButton botonMostrar, botonVerificar;
    private JLabel etiquetaPangrama, etiquetaResultado;
    private GestorTeclado controlador;
    private PanelSimuladorTeclado panelTeclado;

    public VentanaSimuladorTeclado() {
        controlador = new GestorTeclado();
        initComponents();
    }

    private void initComponents() {
        setTitle("Simulador de Teclado");
        setSize(600, 300);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        campoTexto = new JTextField(20);
        campoTexto.setEditable(false);
        botonMostrar = new JButton("Mostrar Frase");
        botonVerificar = new JButton("Comprobar");
        etiquetaPangrama = new JLabel("Haz clic en 'Mostrar Frase' para comenzar");
        etiquetaResultado = new JLabel("");

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarNuevoPangrama();
            }
        });

        botonVerificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarPangrama();
            }
        });

        panelTeclado = new PanelSimuladorTeclado(campoTexto, this);

        add(botonMostrar);
        add(etiquetaPangrama);
        add(campoTexto);
        add(botonVerificar);
        add(etiquetaResultado);
        add(panelTeclado);
    }

    private void mostrarNuevoPangrama() {
        String nuevoPangrama = controlador.nuevoPangrama();
        etiquetaPangrama.setText(nuevoPangrama);
        campoTexto.setText("");
    }

    private void verificarPangrama() {
        String intento = campoTexto.getText();
        boolean correcto = controlador.verificarPangrama(intento);
        etiquetaResultado.setText(correcto ? "¡Correcto!" : "Incorrecto. Intenta de nuevo.");
        if (correcto) {
            mostrarInforme();
        }
    }

    public void procesarTecla(String tecla) {
        char teclaPresionada = tecla.charAt(0);

        if (controlador.getPangramaActual().length() > 0 && teclaPresionada == controlador.getPangramaActual().charAt(0)) {
            campoTexto.setText(campoTexto.getText() + tecla);
        }

        controlador.procesarTecla(teclaPresionada);

        if (tecla.equals("Borrar") && campoTexto.getText().length() > 0) {
            campoTexto.setText(campoTexto.getText().substring(0, campoTexto.getText().length() - 1));
            controlador.retrocederPosicion();
        }
    }

    private void mostrarInforme() {
        String informe = controlador.generarInforme();
        JOptionPane.showMessageDialog(this, informe, "Informe del Simulador de Teclado", JOptionPane.INFORMATION_MESSAGE);
    }
}

