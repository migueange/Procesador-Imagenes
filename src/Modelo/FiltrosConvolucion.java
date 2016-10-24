package Modelo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javafx.concurrent.Task;

/**
 *
 * @author miguelita
 */
public class FiltrosConvolucion {

    /**
     * Aplica un blur a través de una convolución, utiliza un filtro de 5x5.
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task blur(BufferedImage original, BufferedImage procesada) {
        double[][] filtro = new double[][]{
            {0, 0, 1, 0, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {0, 0, 1, 0, 0}
        };
        return aplicaConvolucion(original, procesada, filtro, 1.0 / 13.0, 0);
    }

    /**
     * Aplica un motion blur a través de una convolución, utiliza un filtro de
     * 9x9.
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task motionBlur(BufferedImage original, BufferedImage procesada) {
        double[][] filtro = new double[][]{
            {1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1}
        };
        return aplicaConvolucion(original, procesada, filtro, 1.0 / 9.0, 0);
    }

    /**
     * Encuentra bordes verticales
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task encontrarBordesVerticales(BufferedImage original, BufferedImage procesada) {
        double[][] filtro = new double[][]{
            {0, 0, -1, 0, 0},
            {0, 0, -1, 0, 0},
            {0, 0, 4, 0, 0},
            {0, 0, -1, 0, 0},
            {0, 0, -1, 0, 0}
        };
        return aplicaConvolucion(original, procesada, filtro, 1.0, 0);
    }

    /**
     * Encuentra bordes horizontales
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task encontrarBordesHorizontales(BufferedImage original, BufferedImage procesada) {
        double[][] filtro = new double[][]{
            {0, 0, -1, 0, 0},
            {0, 0, -1, 0, 0},
            {0, 0, 2, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
        };
        return aplicaConvolucion(original, procesada, filtro, 1.0, 0);
    }

    /**
     * Encuentra bordes diagonales.
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task encontrarBordesDiagonales(BufferedImage original, BufferedImage procesada) {
        double[][] filtro = new double[][]{
            {-1, 0, 0, 0, 0},
            {0, -2, 0, 0, 0},
            {0, 0, 6, 0, 0},
            {0, 0, 0, -2, 0},
            {0, 0, 0, 0, -1}
        };
        return aplicaConvolucion(original, procesada, filtro, 1.0, 0);
    }

    /**
     * Encuentra bordes en todas las direcciones (Verticales, horizontales y
     * diagonales).
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task encontrarBordesTodasDirecciones(BufferedImage original, BufferedImage procesada) {
        double[][] filtro = new double[][]{
            {-1, -1, -1},
            {-1, 8, -1},
            {-1, -1, -1}
        };
        return aplicaConvolucion(original, procesada, filtro, 1.0, 0);
    }

    /**
     * Filtro sharpen mostrando bordes excesivos.
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task sharpen(BufferedImage original, BufferedImage procesada) {
        double[][] filtro = new double[][]{
            {-1, -1, -1},
            {-1, 9, -1},
            {-1, -1, -1}
        };
        return aplicaConvolucion(original, procesada, filtro, 1.0, 0);
    }

    /**
     * Realza el relieve de una imagen.
     *
     * @param original
     * @param procesada
     * @return
     */
    public static Task emboss(BufferedImage original, BufferedImage procesada) {
        double[][] filtro = new double[][]{
            {-1, -1, 0},
            {-1, 0, 1},
            {0, 1, 1}
        };
        return aplicaConvolucion(original, procesada, filtro, 1.0, 128);
    }

    /**
     * Aumenta o disminuye el brillo de una imagen.
     *
     * @param original
     * @param procesada
     * @param brillo
     * @return
     */
    public static Task brillo(BufferedImage original, BufferedImage procesada, int brillo) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int progresoTotal = original.getHeight() * original.getWidth(), progreso = 0;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        Color color = new Color(original.getRGB(j, i));
                        int r = ((color.getRed() + brillo) > 255) ? 255 : ((color.getRed() + brillo) < 0) ? 0 : color.getRed() + brillo;
                        int g = ((color.getGreen() + brillo) > 255) ? 255 : ((color.getGreen() + brillo) < 0) ? 0 : color.getGreen() + brillo;
                        int b = ((color.getBlue() + brillo) > 255) ? 255 : ((color.getBlue() + brillo) < 0) ? 0 : color.getBlue() + brillo;
                        procesada.setRGB(j, i, new Color(r, g, b).getRGB());
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
     * @param brillo
     * @return 
     */
    public static BufferedImage brillo(BufferedImage original, int brillo) {
        BufferedImage procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < original.getHeight(); i++) {
            for (int j = 0; j < original.getWidth(); j++) {
                Color color = new Color(original.getRGB(j, i));
                int r = ((color.getRed() + brillo) > 255) ? 255 : ((color.getRed() + brillo) < 0) ? 0 : color.getRed() + brillo;
                int g = ((color.getGreen() + brillo) > 255) ? 255 : ((color.getGreen() + brillo) < 0) ? 0 : color.getGreen() + brillo;
                int b = ((color.getBlue() + brillo) > 255) ? 255 : ((color.getBlue() + brillo) < 0) ? 0 : color.getBlue() + brillo;
                procesada.setRGB(j, i, new Color(r, g, b).getRGB());
            }
        }
        return procesada;
    }

    /**
     * Filtro de alto contraste.
     *
     * @param imagenOriginal
     * @param procesada
     * @return
     */
    public static Task altoContraste(BufferedImage imagenOriginal, BufferedImage procesada) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                BufferedImage original = FiltrosColores.tonosDeGrisesPorPorcentaje(imagenOriginal);
                int progresoTotal = original.getHeight() * original.getWidth(), progreso = 0;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        Color color = new Color(original.getRGB(j, i));
                        int r = (int) (color.getRed() * 255);
                        int g = (int) (color.getGreen() * 255);
                        int b = (int) (color.getBlue() * 255);
                        if (((r + g + b) / 3) > 127) {
                            procesada.setRGB(j, i, new Color(255, 255, 255).getRGB());
                        } else {
                            procesada.setRGB(j, i, new Color(0, 0, 0).getRGB());
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
     * Es el inverso del filtro alto contraste.
     *
     * @param imagenOriginal
     * @param procesada
     * @return
     */
    public static Task inverso(BufferedImage imagenOriginal, BufferedImage procesada) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                BufferedImage original = FiltrosColores.tonosDeGrisesPorPorcentaje(imagenOriginal);
                int progresoTotal = original.getHeight() * original.getWidth(), progreso = 0;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        Color color = new Color(original.getRGB(j, i));
                        int r = (int) (color.getRed() * 255);
                        int g = (int) (color.getGreen() * 255);
                        int b = (int) (color.getBlue() * 255);
                        if (((r + g + b) / 3) <= 127) {
                            procesada.setRGB(j, i, new Color(255, 255, 255).getRGB());
                        } else {
                            procesada.setRGB(j, i, new Color(0, 0, 0).getRGB());
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
     * Algoritmo para convolución, aplica cualquier filtro de nxn.
     *
     * @param img
     * @param filtro
     * @param factor
     * @param bias
     * @return
     */
    private static Task aplicaConvolucion(BufferedImage original, BufferedImage procesada, double[][] filtro, double factor, double bias) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int progresoTotal = original.getWidth() * original.getHeight(), progreso = 0;
                for (int i = 0; i < original.getHeight(); i++) {
                    for (int j = 0; j < original.getWidth(); j++) {
                        double r = 0, g = 0, b = 0;
                        for (int k = 0; k < filtro.length; k++) {//altura
                            for (int l = 0; l < filtro.length; l++) {    //anchura                    
                                Color color = new Color(original.getRGB((j - filtro.length / 2 + l + original.getWidth()) % original.getWidth(), (i - filtro.length / 2 + k + original.getHeight()) % original.getHeight()));
                                r += color.getRed() * filtro[k][l];
                                g += color.getGreen() * filtro[k][l];
                                b += color.getBlue() * filtro[k][l];
                            }
                        }
                        r = Math.min(Math.max(factor * r + bias, 0), 255);
                        g = Math.min(Math.max(factor * g + bias, 0), 255);
                        b = Math.min(Math.max(factor * b + bias, 0), 255);
                        procesada.setRGB(j, i, new Color((int) r, (int) g, (int) b).getRGB());
                        updateProgress(progreso++, progresoTotal);
                    }
                }
                updateProgress(progresoTotal, progresoTotal);
                return true;
            }

        };
    }
}
