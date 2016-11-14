package Modelo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javafx.concurrent.Task;

/**
 *
 * @author miguelita
 */
public class FiltrosBlendingMarcaIconosLuzNegra {

    /**
     * Filtro Blending.
     *
     * @param original
     * @param imagenMezcla
     * @param procesada
     * @param alpha
     * @return
     */
    public static Task blending(BufferedImage original, BufferedImage imagenMezcla, BufferedImage procesada, double alpha) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int progresoTotal = original.getHeight() * original.getWidth(), progreso = 0, r, g, b;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        Color colorOriginal = new Color(original.getRGB(j, i));
                        if (i < imagenMezcla.getHeight() && j < imagenMezcla.getWidth()) {
                            Color colorImgMezcla = new Color(imagenMezcla.getRGB(j, i));
                            r = (int) ((colorOriginal.getRed() * alpha) + (colorImgMezcla.getRed() * (1.0 - alpha)));
                            g = (int) ((colorOriginal.getGreen() * alpha) + (colorImgMezcla.getGreen() * (1.0 - alpha)));
                            b = (int) ((colorOriginal.getBlue() * alpha) + (colorImgMezcla.getBlue() * (1.0 - alpha)));
                            procesada.setRGB(j, i, new Color(r, g, b).getRGB());
                        } else {
                            procesada.setRGB(j, i, colorOriginal.getRGB());
                        }
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }
        };
    }

    /**
     * Filtro Luz Negra
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task luzNegra(BufferedImage original, BufferedImage procesada) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int progresoTotal = original.getHeight() * original.getWidth(), progreso = 0, r, g, b, l;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        Color color = new Color(original.getRGB(j, i));
                        l = ((222 * color.getRed()) + (707 * color.getGreen()) + (71 * color.getBlue())) / 1000;
                        r = Math.abs(color.getRed() - l) * 2;
                        g = Math.abs(color.getGreen() - l) * 2;
                        b = Math.abs(color.getBlue() - l) * 2;
                        procesada.setRGB(j, i, new Color(Math.min(r, 255), Math.min(g, 255), Math.min(b, 255)).getRGB());
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }
        };
    }

    /**
     * Filtro iconos
     *
     * @param original
     * @param procesada
     * @param tamanioIcono
     * @return
     */
    public static Task icono(BufferedImage original, BufferedImage procesada, int tamanioIcono) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int n = original.getWidth() / tamanioIcono, m = original.getHeight() / tamanioIcono, r, g, b, progresoTotal = (original.getWidth() * original.getHeight()) / (n * m), progreso = 0;
                /* Recorrer bloques de nxm */
                for (int i = 0, y = 0; i < original.getHeight() && y < tamanioIcono; i += m, y++) {
                    for (int j = 0, x = 0; j < original.getWidth() && x < tamanioIcono; j += n, x++) {
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
                        procesada.setRGB(x, y, new Color(r / (n * m), g / (n * m), b / (n * m)).getRGB());
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }
        };
    }

    /**
     * Filtro quitar marca de agua
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task quitaMarcaDeAgua(BufferedImage original, BufferedImage procesada) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int progresoTotal = original.getHeight() * original.getWidth(), progreso = 0, r, g, b;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        Color colorOriginal = new Color(original.getRGB(j, i));
                        r = colorOriginal.getRed();
                        g = colorOriginal.getGreen();
                        b = colorOriginal.getBlue();
                        int color = 0;
                        if (Math.abs(r - g) <= 5 && Math.abs(r - b) <= 5 && Math.abs(g - b) <= 5) {                                              
                            procesada.setRGB(j, i, colorOriginal.getRGB());
                        } else {
                            procesada.setRGB(j, i, new Color(Math.min ((r+g+b)/3 + 40,255),Math.min((r+g+b)/3 + 40,255),Math.min((r+g+b)/3+40,255)).getRGB());
                        }
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }
        };
    }
}
