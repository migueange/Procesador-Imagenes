package Modelo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javafx.concurrent.Task;

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
    private static BufferedImage tonosDeGrisesPorPorcentaje(BufferedImage original) {
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

    /*Aplica una mica a una imagen*/
    private static BufferedImage micas(BufferedImage original, int r, int g, int b) {
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

    //******************************************************* Convolucion****************************************************************
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

    /* Da el brillo a una imagen */
    private static BufferedImage brillo(BufferedImage original, int brillo) {
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
                BufferedImage original = tonosDeGrisesPorPorcentaje(imagenOriginal);
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
                BufferedImage original = tonosDeGrisesPorPorcentaje(imagenOriginal);
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

    //******************************************************* Imágenes recursivas ****************************************************************
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
        return micas(subImagen, r, g, b);
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
                BufferedImage original = tonosDeGrisesPorPorcentaje(imagenOriginal);
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
        return brillo(subImagen, brillo);
    }

//**************************************************Sopa de Letras***********************************************************
    /**
     * Crea una imagen hecha con una letra con color.
     *
     * @param original
     * @param procesada
     * @param letra
     * @param n
     * @param m
     * @return
     */
    public static Task unaLetraColores(BufferedImage original, BufferedImage procesada, String letra, int n, int m) {
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
                        Color colorMica = new Color(r / (n * m), g / (n * m), b / (n * m));
                        /*Si no existe la imagen con la mica dado el promedio de color de la región, se crea.*/
                        if (!subImagenes.containsKey(colorMica.getRGB())) {
                            subImagenes.put(colorMica.getRGB(), getSubImagenLetra(letra, colorMica.getRed(), colorMica.getGreen(), colorMica.getBlue(), n, m));
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

    private static BufferedImage getSubImagenLetra(String letra, int r, int g, int b, int n, int m) {
        BufferedImage subImagen = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        Graphics gr = subImagen.getGraphics();
        gr.setColor(new Color(r, g, b));
        gr.setFont(new Font("Arial", Font.PLAIN, n));
        gr.drawString(letra, 0, (int) Math.floor((n * 7) / 10));
        gr.dispose();
        return subImagen;
    }

    /**
     * Genera una imagen hecha de una letra en tonos de gris.
     *
     * @param imagenOriginal
     * @param procesada
     * @param letra
     * @param n
     * @param m
     * @return
     */
    public static Task unaLetraTonosGrises(BufferedImage imagenOriginal, BufferedImage procesada, String letra, int n, int m) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                /*La imagen original en tonos de grises*/
                BufferedImage original = tonosDeGrisesPorPorcentaje(imagenOriginal);
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
                            subImagenes.put(colorPromedio.getRGB(), getSubImagenLetraTonosGrises(letra, brillo, n, m));
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

    private static BufferedImage getSubImagenLetraTonosGrises(String letra, int brillo, int n, int m) {
        BufferedImage subImagen = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        Graphics gr = subImagen.getGraphics();
        int gris = brillo > 255 ? 255 : brillo;
        gr.setColor(new Color(gris, gris, gris));
        gr.setFont(new Font("Arial", Font.PLAIN, n));
        gr.drawString(letra, 0, (int) Math.floor((n * 7) / 10));
        gr.dispose();
        return subImagen;
    }

    /**
     * Genera una imagen hecha de una letra en tonos de gris pero con letras predeterminadas.
     *
     * @param imagenOriginal
     * @param procesada
     * @param letra
     * @param n
     * @param m
     * @return
     */
    public static Task letrasBlancoYNegro(BufferedImage imagenOriginal, BufferedImage procesada, int n, int m) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                /*La imagen original en tonos de grises*/
                BufferedImage original = tonosDeGrisesPorPorcentaje(imagenOriginal);
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
                            subImagenes.put(colorPromedio.getRGB(), getSubImagenLetrasBYN(brillo, n, m));
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

    private static BufferedImage getSubImagenLetrasBYN(int brillo, int n, int m) {
        BufferedImage subImagen = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        Graphics gr = subImagen.getGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, n, m);
        int gris = brillo > 255 ? 255 : brillo;
        String[] letrasBYN = new String[255];
        String letra="";
        if (brillo >= 0 && brillo < 16) {
            letra = "M";
        } else if (brillo >= 16 && brillo < 32) {
            letra = "N";
        } else if (brillo >= 32 && brillo < 48) {
            letra = "H";
        } else if (brillo >= 48 && brillo < 64) {
            letra = "#";
        } else if (brillo >= 64 && brillo < 80) {
            letra = "Q";
        } else if (brillo >= 80 && brillo < 96) {
            letra = "U";
        } else if (brillo >= 96 && brillo < 112) {
            letra = "A";
        } else if (brillo >= 112 && brillo < 128) {
            letra = "D";
        } else if (brillo >= 128 && brillo < 144) {
            letra = "O";
        } else if (brillo >= 144 && brillo < 160) {
            letra = "Y";
        } else if (brillo >= 160 && brillo < 176) {
            letra = "2";
        } else if (brillo >= 176 && brillo < 192) {
            letra = "$";
        } else if (brillo >= 192 && brillo < 208) {
            letra = "%";
        } else if (brillo >= 208 && brillo < 224) {
            letra = "+";
        } else if (brillo >= 224 && brillo < 240) {
            letra = "_";
        } else if (brillo >= 240 && brillo < 255) {
            letra = " ";
        }
        gr.setColor(Color.BLACK);
        gr.setFont(new Font("Arial", Font.PLAIN, n));
        gr.drawString(letra, 0, (int) Math.floor((n * 7) / 10));
        gr.dispose();
        return subImagen;
    }
}
