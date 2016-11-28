package Modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javafx.concurrent.Task;
import javax.imageio.ImageIO;

/**
 *
 * @author miguelita
 */
public class FotoMosaico {

    public static Task creaFotoMosaico(BufferedImage original, BufferedImage procesada, String rutaBiblioteca, int n, int m) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println(rutaBiblioteca);
                HashMap<File, Componentes> biblioteca = procesaBiblioteca(rutaBiblioteca);
                System.out.println(biblioteca.size());
                HashMap<File, BufferedImage> imagenesUsadas = new HashMap<>();
                int r, g, b, progresoTotal = (original.getWidth() * original.getHeight()) / (n * m), progreso = 0;
                for (int i = 0; i < original.getHeight(); i += m) {
                    for (int j = 0; j < original.getWidth(); j += n) {
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
                        Color color = new Color(r / (n * m), g / (n * m), b / (n * m));
                        double distanciaMenor = Double.MAX_VALUE;
                        File imagenActual = null;
                        for (File imagen : biblioteca.keySet()) {
                            double distanciaEuclidiana = calculaDistancia(biblioteca.get(imagen), color);
                            if (distanciaEuclidiana < distanciaMenor) {
                                distanciaMenor = distanciaEuclidiana;
                                imagenActual = imagen;
                            }

                        }
                        if (!imagenesUsadas.containsKey(imagenActual)) {
                            imagenesUsadas.put(imagenActual, getSubImagen(ImageIO.read(imagenActual), n, m));
                        }
                        /*Una vez obtenida la subimagen con la mica especifica, pintamos esa región de la original con la subimagen.*/
                        BufferedImage subImagen = imagenesUsadas.get(imagenActual);
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
    private static BufferedImage getSubImagen(BufferedImage imagen, int n, int m) {
        BufferedImage subImagen = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = subImagen.createGraphics();
        graphics.drawImage(imagen, 0, 0, n, m, null);
        graphics.dispose();
        return subImagen;
    }


    /**/
    private static double calculaDistancia(Componentes comp, Color colorRegion) {
        return Math.sqrt(Math.pow(colorRegion.getRed() - comp.R, 2) + Math.pow(colorRegion.getGreen() - comp.G, 2) + Math.pow(colorRegion.getBlue() - comp.B, 2));
    }

    /**
     *
     * @param rutaBiblioteca
     * @return
     */
    private static HashMap<File, Componentes> procesaBiblioteca(String rutaBiblioteca) throws IOException {
        HashMap<File, Componentes> biblioteca = new HashMap<>();
        File ruta = new File(rutaBiblioteca);
        File[] archivos = ruta.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.listFiles() != null) {
                    procesaDirectorio(biblioteca, archivo);
                } else {
                    procesaArchivo(biblioteca, archivo);
                }
            }
        } else {
            procesaArchivo(biblioteca, ruta);
        }
        return biblioteca;
    }

    /**/
    private static void procesaArchivo(HashMap<File, Componentes> biblioteca, File archivo) throws IOException {
        String extension = archivo.getName().substring(archivo.getName().lastIndexOf(".") + 1).toLowerCase();
        if (!extension.equals("jpg") && !extension.equals("png")) {
            return;
        }
        try {
            BufferedImage imagen = ImageIO.read(archivo);
            int r = 0, g = 0, b = 0;
            for (int i = 0; i < imagen.getHeight(); i++) {
                for (int j = 0; j < imagen.getWidth(); j++) {
                    Color color = new Color(imagen.getRGB(j, i));
                    r += color.getRed();
                    g += color.getGreen();
                    b += color.getBlue();
                }
            }
            Color color = new Color(r / (imagen.getHeight() * imagen.getWidth()), g / (imagen.getHeight() * imagen.getWidth()), b / (imagen.getHeight() * imagen.getWidth()));
            biblioteca.put(archivo, new Componentes(color.getRed(), color.getGreen(), color.getBlue()));
        } catch (Exception e) {
            System.err.println("Error en " + archivo.getName());
        }

    }

    /**/
    private static void procesaDirectorio(HashMap<File, Componentes> biblioteca, File directorio) throws IOException {
        File[] archivos = directorio.listFiles();
        for (File archivo : archivos) {
            if (archivo.listFiles() != null) {
                procesaDirectorio(biblioteca, archivo);
            } else {
                procesaArchivo(biblioteca, archivo);
            }
        }
    }

    /**
     * Guarda los colores de cada imagen en la biblioteca
     */
    private static class Componentes {

        public int R;
        public int G;
        public int B;

        /**
         * Constructor
         */
        public Componentes(int r, int g, int b) {
            this.R = r;
            this.G = g;
            this.B = b;
        }
    }

}
