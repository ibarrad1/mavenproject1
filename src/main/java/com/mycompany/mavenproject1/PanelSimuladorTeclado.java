/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelSimuladorTeclado extends JPanel {
    private JButton[] botonesTeclado;
    private boolean mayusculasActivadas;
    private boolean primeraTeclaPresionada;
    private JTextField campoTexto;
    private VentanaSimuladorTeclado ventana;

    public PanelSimuladorTeclado(JTextField campoTexto, VentanaSimuladorTeclado ventana) {
        this.campoTexto = campoTexto;
        this.ventana = ventana;
        primeraTeclaPresionada = false;
        setLayout(new GridLayout(4, 10));
        inicializarTeclado();
    }

    private void inicializarTeclado() {
        String[] letras = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                           "A", "S", "D", "F", "G", "H", "J", "K", "L", ";",
                           "Z", "X", "C", "V", "B", "N", "M", "Ñ", ",", ".", "/",
                           "Caps", "Espacio", "Intro", "Borrar"};

        botonesTeclado = new JButton[letras.length];

        for (int i = 0; i < letras.length; i++) {
            botonesTeclado[i] = new JButton(letras[i]);
            final int index = i;

            personalizarBoton(botonesTeclado[i], letras[index]);

            botonesTeclado[i].addActionListener(e -> {
                String tecla = letras[index];
                procesarTecla(tecla);
            });
            add(botonesTeclado[i]);
        }
    }

    private void personalizarBoton(JButton boton, String letra) {
        Color colorFondo = new Color(210, 210, 210); // Color de fondo claro
        Color colorTexto = Color.DARK_GRAY; // Color de texto oscuro
        Color colorFondoEspecial = new Color(180, 180, 180); // Color de fondo para teclas especiales

        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);

        if (letra.equals("Caps") || letra.equals("Espacio") || letra.equals("Intro") || letra.equals("Borrar")) {
            boton.setBackground(colorFondoEspecial);
            boton.setForeground(Color.BLACK);
        }

        boton.setPreferredSize(new Dimension(70, 40)); // Tamaño más grande de los botones
        boton.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente más grande
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    private void procesarTecla(String tecla) {
        if (tecla.equals("Caps")) {
            mayusculasActivadas = !mayusculasActivadas;
            actualizarEstadoTeclado();
        } else if (tecla.equals("Espacio")) {
            ventana.procesarTecla(" ");
        } else {
            if (!primeraTeclaPresionada) {
                ocultarLetrasTeclado();
                primeraTeclaPresionada = true;
            }
            ventana.procesarTecla(mayusculasActivadas ? tecla.toUpperCase() : tecla.toLowerCase());
        }
    }

    private void ocultarLetrasTeclado() {
        for (JButton boton : botonesTeclado) {
            boton.setForeground(boton.getBackground());
        }
    }

    private void actualizarEstadoTeclado() {
        for (JButton boton : botonesTeclado) {
            String texto = boton.getText();
            if (!texto.equals("Caps") && !texto.equals("Espacio") && !texto.equals("Intro") && !texto.equals("Borrar")) {
                boton.setText(mayusculasActivadas ? texto.toUpperCase() : texto.toLowerCase());
            }
        }
    }
}
