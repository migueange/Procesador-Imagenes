package Modelo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javafx.concurrent.Task;

/**
 *
 * @author miguelita
 */
public class FiltrosColores {

    /**
     * Convierte la imagen a tonos de grises con la siguiente fórmula. gris =
     * (R+B+G)/3
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task tonosDeGrisesPorPromedio(BufferedImage original, BufferedImage procesada) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int progresoTotal = original.getHeight() * original.getWidth(), progreso = 0;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        Color color = new Color(original.getRGB(j, i));
                        int gris = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                        Color rgbGris = new Color(gris, gris, gris);
                        procesada.setRGB(j, i, rgbGris.getRGB());
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }
        };

    }

    /**
     * Convierte la imagen a tonos de grises con la siguiente fórmula. gris =
     * (R*0.3)+(G*0.59)+(B*0.11)
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task tonosDeGrisesPorPorcentaje(BufferedImage original, BufferedImage procesada) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int progresoTotal = original.getHeight() * original.getWidth(), progreso = 0;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        Color color = new Color(original.getRGB(j, i));
                        int gris = (int) ((color.getRed() * 0.3) + (color.getGreen() * .59) + color.getBlue() * .11);
                        Color rgbGris = new Color(gris, gris, gris);
                        procesada.setRGB(j, i, rgbGris.getRGB());
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }
        };
    }

    /**/

    /**
     *
     * @param original
     * @return 
     */
    public static BufferedImage tonosDeGrisesPorPorcentaje(BufferedImage original) {
        BufferedImage procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < original.getHeight(); i++) {
            for (int j = 0; j < original.getWidth(); j++) {
                Color color = new Color(original.getRGB(j, i));
                int gris = (int) ((color.getRed() * 0.3) + (color.getGreen() * .59) + color.getBlue() * .11);
                Color rgbGris = new Color(gris, gris, gris);
                procesada.setRGB(j, i, rgbGris.getRGB());
            }
        }
        return procesada;
    }

    /**
     * Convierte la imagen tomando el valor del color seleccionado.
     *
     * @param original
     * @param procesada
     * @param colorS
     * @return
     */
    public static Task tonosDeGrisesPorColor(BufferedImage original, BufferedImage procesada, String colorS) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int progresoTotal = original.getHeight() * original.getWidth(), progreso = 0;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        Color color = new Color(original.getRGB(j, i));
                        int gris = (colorS.equals("Red")) ? color.getRed() : (colorS.equals("Green")) ? color.getGreen() : color.getBlue();
                        Color rgbGris = new Color(gris, gris, gris);
                        procesada.setRGB(j, i, rgbGris.getRGB());
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }
        };
    }

    /**
     * Toma un color y lo fija, mientras que los otros dos los fija en cero.
     *
     * @param original
     * @param procesada
     * @param colorD
     * @return
     * @
     */
    public static Task colorDominante(BufferedImage original, BufferedImage procesada, String colorD) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int progresoTotal = original.getHeight() * original.getWidth(), progreso = 0;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        Color color = new Color(original.getRGB(j, i));
                        Color colorDominante;
                        switch (colorD) {
                            case "Red":
                                colorDominante = new Color(color.getRed(), 0, 0);
                                break;
                            case "Green":
                                colorDominante = new Color(0, color.getGreen(), 0);
                                break;
                            default:
                                colorDominante = new Color(0, 0, color.getBlue());
                                break;
                        }
                        procesada.setRGB(j, i, colorDominante.getRGB());
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }
        };
    }

    /**
     * Crea una "mica" del color que inidiquen los valores rgb dados.
     *
     * @param original
     * @param procesada
     * @param r
     * @param g
     * @param b
     * @return
     */
    public static Task micas(BufferedImage original, BufferedImage procesada, int r, int g, int b) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int progresoTotal = original.getHeight() * original.getWidth(), progreso = 0;
                Color colorMica = new Color(r, g, b);
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        Color color = new Color(original.getRGB(j, i));
                        procesada.setRGB(j, i, colorMica.getRGB() & color.getRGB());
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }
        };
    }

    /**
     * 
     * @param original
     * @param r
     * @param g
     * @param b
     * @return 
     */
    public static BufferedImage micas(BufferedImage original, int r, int g, int b) {
        BufferedImage procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        Color colorMica = new Color(r, g, b);
        for (int i = 0; i < original.getHeight(); i++) {
            for (int j = 0; j < original.getWidth(); j++) {
                Color color = new Color(original.getRGB(j, i));
                procesada.setRGB(j, i, colorMica.getRGB() & color.getRGB());
            }
        }
        return procesada;
    }

    /**
     * Filtro mosaico, toma submatrices de nxm y colorea esa región de la imagen
     * con el promedio de los colores de cada submatriz.
     *
     * @param original
     * @param procesada
     * @param n
     * @param m
     * @return
     */
    public static Task mosaico(BufferedImage original, BufferedImage procesada, int n, int m) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int r, g, b, progresoTotal = (original.getWidth() * original.getHeight()) / (n * m), progreso = 0;
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
                        /*Pintar bloque con el promedio*/
                        for (int k = i; k < ((i + m < original.getHeight()) ? i + m : original.getHeight()); k++) {
                            for (int l = j; l < ((j + n < original.getWidth()) ? j + n : original.getWidth()); l++) {
                                procesada.setRGB(l, k, new Color(r / (n * m), g / (n * m), b / (n * m)).getRGB());
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
}
