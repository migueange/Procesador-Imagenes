package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

    /*Aplica una mica a una imagen*/
    private static BufferedImage micas(BufferedImage original, int r, int g, int b) throws IOException {
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

    //******************************************************* Convolucion****************************************************************
    /**
     * Aplica un blur a través de una convolución, utiliza un filtro de 5x5.
     *
     * @param img
     * @return
     * @throws IOException
     */
    public static BufferedImage blur(File img) throws IOException {
        double[][] filtro = new double[][]{
            {0, 0, 1, 0, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {0, 0, 1, 0, 0}
        };
        return aplicaConvolucion(img, filtro, 1.0 / 13.0, 0);
    }

    /**
     * Aplica un motion blur a través de una convolución, utiliza un filtro de
     * 9x9.
     *
     * @param img
     * @return
     * @throws IOException
     */
    public static BufferedImage motionBlur(File img) throws IOException {
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
        return aplicaConvolucion(img, filtro, 1.0 / 9.0, 0);
    }

    /**
     * Encuentra bordes verticales
     *
     * @param img
     * @return
     * @throws IOException
     */
    public static BufferedImage encontrarBordesVerticales(File img) throws IOException {
        double[][] filtro = new double[][]{
            {0, 0, -1, 0, 0},
            {0, 0, -1, 0, 0},
            {0, 0, 4, 0, 0},
            {0, 0, -1, 0, 0},
            {0, 0, -1, 0, 0}
        };
        return aplicaConvolucion(img, filtro, 1.0, 0);
    }

    /**
     * Encuentra bordes horizontales
     *
     * @param img
     * @return
     * @throws IOException
     */
    public static BufferedImage encontrarBordesHorizontales(File img) throws IOException {
        double[][] filtro = new double[][]{
            {0, 0, -1, 0, 0},
            {0, 0, -1, 0, 0},
            {0, 0, 2, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
        };
        return aplicaConvolucion(img, filtro, 1.0, 0);
    }

    /**
     * Encuentra bordes diagonales.
     *
     * @param img
     * @return
     * @throws IOException
     */
    public static BufferedImage encontrarBordesDiagonales(File img) throws IOException {
        double[][] filtro = new double[][]{
            {-1, 0, 0, 0, 0},
            {0, -2, 0, 0, 0},
            {0, 0, 6, 0, 0},
            {0, 0, 0, -2, 0},
            {0, 0, 0, 0, -1}
        };
        return aplicaConvolucion(img, filtro, 1.0, 0);
    }

    /**
     * Encuentra bordes en todas las direcciones (Verticales, horizontales y
     * diagonales).
     *
     * @param img
     * @return
     * @throws IOException
     */
    public static BufferedImage encontrarBordesTodasDirecciones(File img) throws IOException {
        double[][] filtro = new double[][]{
            {-1, -1, -1},
            {-1, 8, -1},
            {-1, -1, -1}
        };
        return aplicaConvolucion(img, filtro, 1.0, 0);
    }

    /**
     * Filtro sharpen mostrando bordes excesivos.
     *
     * @param img
     * @return
     * @throws IOException
     */
    public static BufferedImage sharpen(File img) throws IOException {
        double[][] filtro = new double[][]{
            {-1, -1, -1},
            {-1, 9, -1},
            {-1, -1, -1}
        };
        return aplicaConvolucion(img, filtro, 1.0, 0);
    }

    /**
     * Realza el relieve de una imagen.
     *
     * @param img
     * @return
     * @throws IOException
     */
    public static BufferedImage emboss(File img) throws IOException {
        double[][] filtro = new double[][]{
            {-1, -1, 0},
            {-1, 0, 1},
            {0, 1, 1}
        };
        return aplicaConvolucion(img, filtro, 1.0, 128);
    }

    /**
     * Aumenta o disminuye el brillo de una imagen.
     *
     * @param img
     * @param brillo
     * @return
     * @throws IOException
     */
    public static BufferedImage brillo(File img, int brillo) throws IOException {
        BufferedImage original = ImageIO.read(img);
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

    /* Da el brillo a una imagen */
    private static BufferedImage brillo(BufferedImage original, int brillo) throws IOException {
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
     * @param img
     * @return
     * @throws IOException
     */
    public static BufferedImage altoContraste(File img) throws IOException {
        BufferedImage original = tonosDeGrisesPorPorcentaje(img);
        BufferedImage procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
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
            }
        }
        return procesada;
    }

    /**
     *
     * @param img
     * @return
     * @throws IOException
     */
    public static BufferedImage inverso(File img) throws IOException {
        BufferedImage original = tonosDeGrisesPorPorcentaje(img);
        BufferedImage procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
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
            }
        }
        return procesada;
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
    private static BufferedImage aplicaConvolucion(File img, double[][] filtro, double factor, double bias) throws IOException {
        BufferedImage original = ImageIO.read(img);
        BufferedImage procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
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
            }
        }
        return procesada;
    }

    //******************************************************* Imágenes recursivas ****************************************************************
    /**
     * Genera una imagen hecha de la misma imagen con tonos reales. Este método
     * no crea imágenes en disco duro y solo usa las imágenes necesarias sin
     * repetirlas.
     *
     * @param img
     * @param n
     * @param m
     * @return
     * @throws IOException
     */
    public static BufferedImage imagenesRecursivasColorReal(BufferedImage original,BufferedImage procesada, int n, int m) throws IOException {
        /*La imagen original*/
        //BufferedImage original = ImageIO.read(img);
        /*Imagen que contendrá el mosaico recursivo*/
        
        /*Valores para el promedio por región*/
        int r, g, b;
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
            }
        }
        return procesada;
    }

    /*Obtiene una imagen igual a la original pero más pequeña en colores reales.*/
    private static BufferedImage getSubImagenColorReal(BufferedImage original, int r, int g, int b, int n, int m) throws IOException {
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
     * @param img
     * @param n
     * @param m
     * @return
     * @throws IOException
     */
    public static BufferedImage imagenesRecursivasTonosGris(File img, int n, int m) throws IOException {
        /*La imagen original*/
        BufferedImage original = tonosDeGrisesPorPorcentaje(img);
        /*Imagen que contendrá el mosaico recursivo*/
        BufferedImage procesada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        /*Valores para el promedio por región*/
        int r, g, b;
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
            }
        }
        return procesada;
    }

    /*Obtiene una imagen igual a la original pero más pequeña en tonos de grises.*/
    private static BufferedImage getSubImagenTonosGrises(BufferedImage original, int brillo, int n, int m) throws IOException {
        BufferedImage subImagen = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = subImagen.createGraphics();
        graphics.drawImage(original, 0, 0, n, m, null);
        graphics.dispose();
        return brillo(subImagen, brillo);
    }

}
