package Modelo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Contiene filtros para las imágenes.
 *
 * @author miguelita
 */
public class Filtros {

    /**
     * Convierte la imagen a tonos de grises con la siguiente fórmula. gris =
     * (R+B+G)/3
     *
     * @param img
     * @return
     * @throws java.io.IOException
     */
    public static BufferedImage tonosDeGrisesPorPromedio(File img) throws IOException {
        BufferedImage original = ImageIO.read(img);
        BufferedImage procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < original.getHeight(); i++) {
            for (int j = 0; j < original.getWidth(); j++) {
                Color color = new Color(original.getRGB(j, i));
                int gris = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color rgbGris = new Color(gris, gris, gris);
                procesada.setRGB(j, i, rgbGris.getRGB());
            }
        }
        return procesada;
    }

    /**
     * Convierte la imagen a tonos de grises con la siguiente fórmula. gris =
     * (R*0.3)+(G*0.59)+(B*0.11)
     *
     * @param img
     * @return
     * @throws IOException
     */
    public static BufferedImage tonosDeGrisesPorPorcentaje(File img) throws IOException {
        BufferedImage original = ImageIO.read(img);
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
     * @param img
     * @param colorS
     * @return
     * @throws IOException
     */
    public static BufferedImage tonosDeGrisesPorColor(File img, String colorS) throws IOException {
        BufferedImage original = ImageIO.read(img);
        BufferedImage procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < original.getHeight(); i++) {
            for (int j = 0; j < original.getWidth(); j++) {
                Color color = new Color(original.getRGB(j, i));
                int gris = (colorS.equals("Red")) ? color.getRed() : (colorS.equals("Green")) ? color.getGreen() : color.getBlue();
                Color rgbGris = new Color(gris, gris, gris);
                procesada.setRGB(j, i, rgbGris.getRGB());
            }
        }
        return procesada;
    }

    /**
     * Toma un color y lo fija, mientras que los otros dos los fija en cero.
     *
     * @param img
     * @param colorD
     * @return
     * @throws IOException
     */
    public static BufferedImage colorDominante(File img, String colorD) throws IOException {
        BufferedImage original = ImageIO.read(img);
        BufferedImage procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < original.getHeight(); i++) {
            for (int j = 0; j < original.getWidth(); j++) {
                Color color = new Color(original.getRGB(j, i));
                Color colorDominante;
                if (colorD.equals("Red")) {
                    colorDominante = new Color(color.getRed(), 0, 0);
                } else if (colorD.equals("Green")) {
                    colorDominante = new Color(0, color.getGreen(), 0);
                } else {
                    colorDominante = new Color(0, 0, color.getBlue());
                }
                procesada.setRGB(j, i, colorDominante.getRGB());
            }
        }
        return procesada;
    }

    /**
     * Crea una "mica" del color que inidiquen los valores rgb dados.
     *
     * @param img
     * @param r
     * @param g
     * @param b
     * @return
     * @throws IOException
     */
    public static BufferedImage micas(File img, int r, int g, int b) throws IOException {
        BufferedImage original = ImageIO.read(img);
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
     * @param img
     * @param n
     * @param m
     * @return
     * @throws java.io.IOException
     */
    public static BufferedImage mosaico(File img, int n, int m) throws IOException {
        BufferedImage original = ImageIO.read(img);
        BufferedImage procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        int r, g, b;
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
            }
        }
        return procesada;
    }

    /**
     *
     * @param img
     * @param filtro
     * @param factor
     * @param bias
     * @return
     */
    private BufferedImage convolucion(File img, double[][] filtro, double factor, double bias) throws IOException {
        BufferedImage original = ImageIO.read(img);
        BufferedImage procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < original.getHeight(); i++) {
            for (int j = 0; j < original.getWidth(); j++) {
                double r, g, b;
                for (int k = 0; k < filtro.length; k++) {
                    for (int l = 0; l < filtro.length; l++) {
                       
                    }
                }

            }
        }

        return procesada;
    }

}
