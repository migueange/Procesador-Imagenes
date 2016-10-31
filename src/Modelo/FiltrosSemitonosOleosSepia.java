package Modelo;

import java.awt.Color;
import java.awt.Graphics;
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
                        for (int k = 0; k < 7; k++) {//altura
                            for (int l = 0; l < 7; l++) {    //anchura                    
                                Color color = new Color(original.getRGB((j - 7 / 2 + l + original.getWidth()) % original.getWidth(), (i - 7 / 2 + k + original.getHeight()) % original.getHeight()));
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
                        for (int k = 0; k < 7; k++) {//altura
                            for (int l = 0; l < 7; l++) {    //anchura                    
                                Color color = new Color(original.getRGB((j - 7 / 2 + l + original.getWidth()) % original.getWidth(), (i - 7 / 2 + k + original.getHeight()) % original.getHeight()));
                                if (colorMayor <= color.getRGB()) {
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
                        for (int k = 0; k < 7; k++) {//altura
                            for (int l = 0; l < 7; l++) {    //anchura                    
                                Color color = new Color(original.getRGB((j - 7 / 2 + l + original.getWidth()) % original.getWidth(), (i - 7 / 2 + k + original.getHeight()) % original.getHeight()));
                                if (colorMenor >= color.getRGB()) {
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

    /**
     * Genera una imagen hecha de la misma imagen en tonos de grises. Este
     * método no crea imágenes en disco duro y solo usa las imágenes necesarias
     * sin repetirlas.
     *
     * @param imagenOriginal
     * @param procesada
     * @param n
     * @param m
     * @return
     */
    public static Task semiTonos(BufferedImage imagenOriginal, BufferedImage procesada, int n, int m, BufferedImage[] tonos) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                /*La imagen original en tonos de grises*/
                BufferedImage original = FiltrosColores.tonosDeGrisesPorPorcentaje(imagenOriginal);
                /*Valores para el promedio de color por región y para el proceso*/
                int r, g, b, progresoTotal = (original.getWidth() * original.getHeight()) / (n * m), progreso = 0;
                /*Un Diccionario que nos ayudará a hacer más eficiente el proceso, reutilizando subimagenes.
                  Su llave será el entero RGB que representa a cada color, por lo cual es único, su valor es una
                  imagen mas pequeña que la origina y única.*/
                HashMap<Integer, BufferedImage> subImagenes = new HashMap<>();
                /* Recorrer bloques de nxm */
                for (int i = 0; i < original.getHeight(); i += m) {
                    for (int j = 0; j < original.getWidth(); j += n) {
                        /*Promedio por bloque*/
                        r = g = b = 0;
                        for (int k = i; k < ((i + m < original.getHeight()) ? i + m : original.getHeight()); k++) {
                            for (int l = j; l < ((j + n < original.getWidth()) ? j + n : original.getWidth()); l++) {
                                Color color = new Color(original.getRGB(l, k));
                                r += color.getRed();
                                g += color.getGreen();
                                b += color.getBlue();
                            }
                        }
                        Color colorPromedio = new Color(r / (n * m), g / (n * m), b / (n * m));
                        int tono = 0;
                        r = colorPromedio.getRed();
                        /*Checar que color pintar*/
                        if (r >= 0 && r < 25) {
                            tono = 10;
                        } else if (r >= 25 && r < 50) {
                            tono = 9;
                        } else if (r >= 50 && r < 75) {
                            tono = 8;
                        } else if (r >= 75 && r < 100) {
                            tono = 7;
                        } else if (r >= 100 && r < 125) {
                            tono = 6;
                        } else if (r >= 125 && r < 150) {
                            tono = 5;
                        } else if (r >= 150 && r < 175) {
                            tono = 4;
                        } else if (r >= 175 && r < 200) {
                            tono = 3;
                        } else if (r >= 200 && r < 225) {
                            tono = 2;
                        } else {
                            tono = 1;
                        }
                        /*Si no existe la imagen con la mica dado el promedio de color de la región, se crea.*/
                        if (!subImagenes.containsKey(tono)) {
                            subImagenes.put(tono, getSubImagenTonosGrises(tonos[tono - 1], n, m));
                        }
                        /*Una vez obtenida la subimagen con la mica especifica, pintamos esa región de la original con la subimagen.*/
                        BufferedImage subImagen = subImagenes.get(tono);
                        for (int k = i, i1 = 0; k < ((i + m < original.getHeight()) ? i + m : original.getHeight()); k++, i1++) {
                            for (int l = j, j1 = 0; l < ((j + n < original.getWidth()) ? j + n : original.getWidth()); l++, j1++) {
                                procesada.setRGB(l, k, subImagen.getRGB(j1, i1));
                            }
                        }
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }
        };
    }

    /*Obtiene una imagen igual a la original pero más pequeña en tonos de grises.*/
    private static BufferedImage getSubImagenTonosGrises(BufferedImage original, int n, int m) {
        BufferedImage subImagen = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = subImagen.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, n, m);
        graphics.drawImage(original, 0, 0, n, m, null);
        graphics.dispose();
        return subImagen;
    }

}
