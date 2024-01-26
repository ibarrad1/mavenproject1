import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GestorTeclado {
    private List<String> pangramas;
    private Random random;
    private String pangramaActual;
    private String pangramaOriginal;
    private Map<Character, Integer> contadorErrores;
    private int totalAciertos;
    private int totalErrores;

    public GestorTeclado() {
        random = new Random();
        pangramas = new ArrayList<>();
        cargarPangramasDesdeArchivo();
        contadorErrores = new HashMap<>();
        totalAciertos = 0;
        totalErrores = 0;
    }

    private void cargarPangramasDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("pangramas.txt"), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    pangramas.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String nuevoPangrama() {
        if (pangramas.isEmpty()) {
            return "No hay pangramas disponibles.";
        }
        pangramaActual = pangramas.get(random.nextInt(pangramas.size()));
        pangramaOriginal = pangramaActual;
        return pangramaActual;
    }

    public boolean verificarPangrama(String intentoUsuario) {
        boolean esCorrecto = intentoUsuario.equalsIgnoreCase(pangramaOriginal);
        if (esCorrecto) {
            totalAciertos += pangramaOriginal.length();
        } else {
            for (char c : intentoUsuario.toCharArray()) {
                contadorErrores.put(c, contadorErrores.getOrDefault(c, 0) + 1);
            }
            totalErrores += intentoUsuario.length();
        }
        return esCorrecto;
    }

    public void procesarTecla(char teclaPresionada) {
        if (pangramaActual != null && !pangramaActual.isEmpty()) {
            char teclaCorrecta = pangramaActual.charAt(0);
            if (teclaPresionada == teclaCorrecta) {
                totalAciertos++;
                pangramaActual = pangramaActual.substring(1);
            } else {
                totalErrores++;
                contadorErrores.put(teclaPresionada, contadorErrores.getOrDefault(teclaPresionada, 0) + 1);
            }
        }
    }

    public void retrocederPosicion() {
        if (pangramaActual.length() > 0) {
            pangramaActual = pangramaOriginal.substring(0, pangramaOriginal.length() - pangramaActual.length() + 1);
        }
    }

    public String getPangramaActual() {
        return pangramaActual;
    }

    public String generarInforme() {
        StringBuilder informe = new StringBuilder();
        informe.append("Informe de Simulación de Teclado\n");
        informe.append("Total Aciertos: ").append(totalAciertos).append("\n");
        informe.append("Total Errores: ").append(totalErrores).append("\n");
        informe.append("Teclas con más errores:\n");
        for (Map.Entry<Character, Integer> entry : contadorErrores.entrySet()) {
            informe.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return informe.toString();
    }
}

