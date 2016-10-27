package Modelo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javafx.concurrent.Task;

/**
 *
 * @author miguelita
 */
public class FiltrosSemitonosOleosSepia {

    /**
     * Filtro sepia.
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task sepia(BufferedImage original, BufferedImage procesada) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int progresoTotal = original.getHeight() * original.getWidth(), progreso = 0, r, g, b;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        Color color = new Color(original.getRGB(j, i));
                        r = (int) ((color.getRed() * .393) + (color.getGreen() * .769) + (color.getBlue() * .189));
                        g = (int) ((color.getRed() * .349) + (color.getGreen() * .686) + (color.getBlue() * .168));
                        b = (int) ((color.getRed() * .272) + (color.getGreen() * .534) + (color.getBlue() * .131));
                        Color rgbSepia = new Color(r > 255 ? 255 : r, g > 255 ? 255 : g, b > 255 ? 255 : b);
                        procesada.setRGB(j, i, rgbSepia.getRGB());
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }
        };
    }

    /**
     * Algoritmo filtro oleo.
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task oleo(BufferedImage original, BufferedImage procesada) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int progresoTotal = original.getWidth() * original.getHeight(), progreso = 0;
                HashMap<Integer, Integer> colores = new HashMap<>();
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        double r = 0, g = 0, b = 0;
                        colores.clear();
                        for (int k = 0; k < 5   ; k++) {//altura
                            for (int l = 0; l < 5; l++) {    //anchura                    
                                Color color = new Color(original.getRGB((j - 5 / 2 + l + original.getWidth()) % original.getWidth(), (i - 5 / 2 + k + original.getHeight()) % original.getHeight()));
                                if (!colores.containsKey(color.getRGB())) {
                                    colores.put(color.getRGB(), 0);
                                } else {
                                    int repeticiones = colores.get(color.getRGB());
                                    colores.replace(color.getRGB(), repeticiones++);
                                }
                            }
                        }
                        int mayor = 0;
                        int color = -1;
                        for (Integer key : colores.keySet()) {
                            if (colores.get(key) >= mayor) {
                                color = key;
                                mayor = colores.get(key);
                            }
                        }
                        procesada.setRGB(j, i, color);
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }

        };
    }

    /**
     * Algoritmo filtro MAX.
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task MAX(BufferedImage original, BufferedImage procesada) {
        return new Task() {
            @Override
            protected Object call() throws Exception {                
                int progresoTotal = original.getWidth() * original.getHeight(), progreso = 0;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        double r = 0, g = 0, b = 0;
                        int colorMayor = Integer.MIN_VALUE;
                        for (int k = 0; k < 5   ; k++) {//altura
                            for (int l = 0; l < 5; l++) {    //anchura                    
                                Color color = new Color(original.getRGB((j - 5 / 2 + l + original.getWidth()) % original.getWidth(), (i - 5 / 2 + k + original.getHeight()) % original.getHeight()));
                                if(colorMayor <= color.getRGB()){
                                    colorMayor = color.getRGB();                                    
                                }
                            }
                        }                     
                        procesada.setRGB(j, i, colorMayor);
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }

        };
    }
    
    /**
     * Algoritmo filtro MIN.
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task MIN(BufferedImage original, BufferedImage procesada) {
        return new Task() {
            @Override
            protected Object call() throws Exception {                
                int progresoTotal = original.getWidth() * original.getHeight(), progreso = 0;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        double r = 0, g = 0, b = 0;
                        int colorMenor = Integer.MAX_VALUE;
                        for (int k = 0; k < 5   ; k++) {//altura
                            for (int l = 0; l < 5; l++) {    //anchura                    
                                Color color = new Color(original.getRGB((j - 5 / 2 + l + original.getWidth()) % original.getWidth(), (i - 5 / 2 + k + original.getHeight()) % original.getHeight()));
                                if(colorMenor >= color.getRGB()){
                                    colorMenor = color.getRGB();                                    
                                }
                            }
                        }                     
                        procesada.setRGB(j, i, colorMenor);
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }

        };
    }
}
