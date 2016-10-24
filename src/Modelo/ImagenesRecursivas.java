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
public class ImagenesRecursivas {
    /**
     * Genera una imagen hecha de la misma imagen con tonos reales. Este método
     * no crea imágenes en disco duro y solo usa las imágenes necesarias sin
     * repetirlas.
     *
     * @param original
     * @param procesada
     * @param n
     * @param m
     * @return
     */
    public static Task imagenesRecursivasColorReal(BufferedImage original, BufferedImage procesada, int n, int m) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                /*Valores para el promedio de color por región y para el proceso*/
                int r, g, b, progresoTotal = (original.getWidth() * original.getHeight()) / (n * m), progreso = 0;
                /*Un Diccionario que nos ayudará a hacer más eficiente el proceso, reutilizando subimagenes.
                Su llave será el entero RGB que representa a cada color, por lo cual es único, su valor es una
                imagen mas pequeña y única.*/
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
                        /*Pintar cada subimagen dado el promedio de la región, hacemos esto aplicando una mica a cada subimagen*/
                        Color colorMica = new Color(r / (n * m), g / (n * m), b / (n * m));
                        /*Si no existe la imagen con la mica dado el promedio de color de la región, se crea.*/
                        if (!subImagenes.containsKey(colorMica.getRGB())) {
                            subImagenes.put(colorMica.getRGB(), getSubImagenColorReal(original, colorMica.getRed(), colorMica.getGreen(), colorMica.getBlue(), n, m));
                        }
                        /*Una vez obtenida la subimagen con la mica especifica, pintamos esa región de la original con la subimagen.*/
                        BufferedImage subImagen = subImagenes.get(colorMica.getRGB());
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

    /*Obtiene una imagen igual a la original pero más pequeña en colores reales.*/
    private static BufferedImage getSubImagenColorReal(BufferedImage original, int r, int g, int b, int n, int m) {
        BufferedImage subImagen = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = subImagen.createGraphics();
        graphics.drawImage(original, 0, 0, n, m, null);
        graphics.dispose();
        return FiltrosColores.micas(subImagen, r, g, b);
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
    public static Task imagenesRecursivasTonosGris(BufferedImage imagenOriginal, BufferedImage procesada, int n, int m) {
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
                        /*Pintar cada subimagen dado el promedio de la región, hacemos esto aplicando una mica a cada subimagen*/
                        Color colorPromedio = new Color(r / (n * m), g / (n * m), b / (n * m));
                        /*Calculamos el promedio de todos los colores para obtener una constante que será el brillo */
                        int brillo = ((r + g + b) / (n * m)) / 3;
                        /*Si no existe la imagen con la mica dado el promedio de color de la región, se crea.*/
                        if (!subImagenes.containsKey(colorPromedio.getRGB())) {
                            subImagenes.put(colorPromedio.getRGB(), getSubImagenTonosGrises(original, brillo, n, m));
                        }
                        /*Una vez obtenida la subimagen con la mica especifica, pintamos esa región de la original con la subimagen.*/
                        BufferedImage subImagen = subImagenes.get(colorPromedio.getRGB());
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
    private static BufferedImage getSubImagenTonosGrises(BufferedImage original, int brillo, int n, int m) {
        BufferedImage subImagen = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = subImagen.createGraphics();
        graphics.drawImage(original, 0, 0, n, m, null);
        graphics.dispose();
        return FiltrosConvolucion.brillo(subImagen, brillo);
    }
}
