import javax.swing.SwingUtilities;

public class SimuladorTecladoApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaSimuladorTeclado().setVisible(true);
            }
        });
    }
}

