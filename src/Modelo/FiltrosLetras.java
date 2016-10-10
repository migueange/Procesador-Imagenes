package Modelo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javafx.concurrent.Task;

/**
 *
 * @author miguelita
 */
public class FiltrosLetras {

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
                        /*Color promedio*/
                        Color colorPromedio = new Color(r / (n * m), g / (n * m), b / (n * m));
                        /*Calculamos el promedio de todos los colores para obtener una constante que será el brillo */
                        int brillo = ((r + g + b) / (n * m)) / 3;
                        /*Si no existe la imagen con ese brillo, se crea.*/
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
     * Genera una imagen hecha de una letra en tonos de gris pero con letras
     * predeterminadas.
     *
     * @param imagenOriginal
     * @param procesada
     * @param n
     * @param m
     * @return
     */
    public static Task letrasBlancoYNegro(BufferedImage imagenOriginal, BufferedImage procesada, int n, int m) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                /*La imagen original en tonos de grises*/
                BufferedImage original = FiltrosColores.tonosDeGrisesPorPorcentaje(imagenOriginal);
                /*Valores para el promedio de color por región y para el proceso*/
                int r, g, b, progresoTotal = (original.getWidth() * original.getHeight()) / (n * m), progreso = 0;
                /*Un Diccionario que nos ayudará a hacer más eficiente el proceso, reutilizando subimagenes.
                  Su llave será la letra que representa a el brillo de cada letra, por lo cual es único, su valor es una
                  imagen mas pequeña que la origina y única.*/
                HashMap<String, BufferedImage> subImagenes = new HashMap<>();
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
                        /*Calculamos el promedio de todos los colores para obtener una constante que será el brillo */
                        int brillo = ((r + g + b) / (n * m)) / 3;
                        /*Obtenemos el brillo para saber que letra poner*/
                        String letra = "";
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
                        /*Si no existe la imagen con la letra, se crea.*/
                        if (!subImagenes.containsKey(letra)) {
                            subImagenes.put(letra, getSubImagenLetrasBYN(letra, n, m));
                        }
                        /*Una vez obtenida la subimagen con la mica especifica, pintamos esa región de la original con la subimagen.*/
                        BufferedImage subImagen = subImagenes.get(letra);
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

    private static BufferedImage getSubImagenLetrasBYN(String letra, int n, int m) {
        BufferedImage subImagen = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        Graphics gr = subImagen.getGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, n, m);
        gr.setColor(Color.BLACK);
        gr.setFont(new Font("Arial", Font.PLAIN, n));
        gr.drawString(letra, 0, (int) Math.floor((n * 7) / 10));
        gr.dispose();
        return subImagen;
    }

    /**
     * Crea una imagen hecha con las letras de un texto.
     *
     * @param original
     * @param procesada
     * @param letrero
     * @param n
     * @param m
     * @return
     */
    public static Task coloresConTexto(BufferedImage original, BufferedImage procesada, String letrero, int n, int m) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                /*Valores para el promedio de color por región y para el proceso*/
                int r, g, b, progresoTotal = (original.getWidth() * original.getHeight()) / (n * m), progreso = 0, posicion = 0;
                /*Un Diccionario que nos ayudará a hacer más eficiente el proceso, reutilizando subimagenes.
                  Su llave será la letra que representa a el brillo de cada letra, por lo cual es único, su valor es una
                  imagen mas pequeña que la origina y única.*/
                HashMap<String, BufferedImage> subImagenes = new HashMap<>();
                /* Recorrer bloques de nxm */
                for (int i = 0; i < original.getHeight(); i += m) {
                    for (int j = 0; j < original.getWidth(); j += n) {
                        String letra = letrero.charAt(posicion++ % letrero.length()) + "";
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
                        /*Color promedio de la región*/
                        Color colorPromedio = new Color(r / (n * m), g / (n * m), b / (n * m));
                        /*Si no existe la imagen con la letra y ese color, se crea.*/
                        if (!subImagenes.containsKey(colorPromedio.getRGB() + letra)) {
                            subImagenes.put(colorPromedio.getRGB() + letra, getSubImagenLetrero(letra, colorPromedio.getRed(), colorPromedio.getGreen(), colorPromedio.getBlue(), n, m));
                        }
                        /*Una vez obtenida la subimagen con la mica especifica, pintamos esa región de la original con la subimagen.*/
                        BufferedImage subImagen = subImagenes.get(colorPromedio.getRGB() + letra);
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

    private static BufferedImage getSubImagenLetrero(String letra, int r, int g, int b, int n, int m) {
        BufferedImage subImagen = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        Graphics gr = subImagen.getGraphics();
        gr.setColor(new Color(r, g, b));
        gr.setFont(new Font("Arial", Font.PLAIN, n));
        gr.drawString(letra, 0, ((int) Math.floor((n * 7) / 10)));
        gr.dispose();
        return subImagen;
    }

    /**
     * Genera una imagen hecha de una letra en tonos de gris pero con letras de
     * dominó.
     *
     * @param imagenOriginal
     * @param procesada
     * @param n
     * @param m
     * @return
     */
    public static Task letrasDomino(BufferedImage imagenOriginal, BufferedImage procesada, int n, int m) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                /*La imagen original en tonos de grises*/
                BufferedImage original = FiltrosColores.tonosDeGrisesPorPorcentaje(imagenOriginal);
                /*Valores para el promedio de color por región y para el proceso*/
                int r, g, b, progresoTotal = (original.getWidth() * original.getHeight()) / (n * m), progreso = 0;
                /*Un Diccionario que nos ayudará a hacer más eficiente el proceso, reutilizando subimagenes.
                  Su llave será la letra que representa a el brillo de cada letra, por lo cual es único, su valor es una
                  imagen mas pequeña que la origina y única.*/
                HashMap<String, BufferedImage> subImagenes = new HashMap<>();
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
                        /*Calculamos el promedio de todos los colores para obtener una constante que será el brillo */
                        int brillo = ((r + g + b) / (n * m)) / 3;
                        /*Obtenemos el brillo para saber que letra poner*/
                        String letra;
                        if (brillo >= 0 && brillo < 19) {
                            letra = "w";
                        } else if (brillo >= 19 && brillo < 38) {
                            letra = "p";
                        } else if (brillo >= 38 && brillo < 57) {
                            letra = "o";
                        } else if (brillo >= 57 && brillo < 76) {
                            letra = "h";
                        } else if (brillo >= 76 && brillo < 95) {
                            letra = "g";
                        } else if (brillo >= 95 && brillo < 114) {
                            letra = "Z";
                        } else if (brillo >= 114 && brillo < 133) {
                            letra = "Y";
                        } else if (brillo >= 133 && brillo < 152) {
                            letra = "X";
                        } else if (brillo >= 152 && brillo < 171) {
                            letra = "Q";
                        } else if (brillo >= 171 && brillo < 190) {
                            letra = "J";
                        } else if (brillo >= 190 && brillo < 209) {
                            letra = "I";
                        } else if (brillo >= 209 && brillo < 228) {
                            letra = "B";
                        } else {
                            letra = "A";
                        }
                        /*Si no existe la imagen con la letra, se crea.*/
                        if (!subImagenes.containsKey(letra)) {
                            subImagenes.put(letra, getSubImagenLetrasDomino(letra, n, m));
                        }
                        /*Una vez obtenida la subimagen con la mica especifica, pintamos esa región de la original con la subimagen.*/
                        BufferedImage subImagen = subImagenes.get(letra);
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

    private static BufferedImage getSubImagenLetrasDomino(String letra, int n, int m) {
        BufferedImage subImagen = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        Graphics gr = subImagen.getGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, n, m);
        gr.setColor(Color.BLACK);
        gr.setFont(new Font("WhiteDominoes", Font.BOLD, n));
        gr.drawString(letra, (-5 * n) / 40, n / 2);
        gr.dispose();
        return subImagen;
    }

    /**
     * Genera una imagen hecha de una letra en tonos de gris pero con letras de
     * naipes.
     *
     * @param imagenOriginal
     * @param procesada
     * @param n
     * @param m
     * @return
     */
    public static Task letrasNaipes(BufferedImage imagenOriginal, BufferedImage procesada, int n, int m) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                /*La imagen original en tonos de grises*/
                BufferedImage original = FiltrosColores.tonosDeGrisesPorPorcentaje(imagenOriginal);
                /*Valores para el promedio de color por región y para el proceso*/
                int r, g, b, progresoTotal = (original.getWidth() * original.getHeight()) / (n * m), progreso = 0;
                /*Un Diccionario que nos ayudará a hacer más eficiente el proceso, reutilizando subimagenes.
                  Su llave será la letra que representa a el brillo de cada letra, por lo cual es único, su valor es una
                  imagen mas pequeña que la origina y única.*/
                HashMap<String, BufferedImage> subImagenes = new HashMap<>();
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
                        /*Calculamos el promedio de todos los colores para obtener una constante que será el brillo */
                        int brillo = ((r + g + b) / (n * m)) / 3;
                        /*Obtenemos el brillo para saber que letra poner*/
                        String letra;
                        if (brillo >= 0 && brillo < 19) {
                            letra = "W";
                        } else if (brillo >= 19 && brillo < 38) {
                            letra = "H";
                        } else if (brillo >= 38 && brillo < 57) {
                            letra = "T";
                        } else if (brillo >= 57 && brillo < 76) {
                            letra = "G";
                        } else if (brillo >= 76 && brillo < 95) {
                            letra = "S";
                        } else if (brillo >= 95 && brillo < 114) {
                            letra = "F";
                        } else if (brillo >= 114 && brillo < 133) {
                            letra = "R";
                        } else if (brillo >= 133 && brillo < 152) {
                            letra = "E";
                        } else if (brillo >= 152 && brillo < 171) {
                            letra = "D";
                        } else if (brillo >= 171 && brillo < 190) {
                            letra = "P";
                        } else if (brillo >= 190 && brillo < 209) {
                            letra = "C";
                        } else if (brillo >= 209 && brillo < 228) {
                            letra = "B";
                        } else {
                            letra = "A";
                        }
                        /*Si no existe la imagen con la letra, se crea.*/
                        if (!subImagenes.containsKey(letra)) {
                            subImagenes.put(letra, getSubImagenLetrasNaipes(letra, n, m));
                        }
                        /*Una vez obtenida la subimagen con la mica especifica, pintamos esa región de la original con la subimagen.*/
                        BufferedImage subImagen = subImagenes.get(letra);
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

    private static BufferedImage getSubImagenLetrasNaipes(String letra, int n, int m) {
        BufferedImage subImagen = new BufferedImage(n, m, BufferedImage.TYPE_INT_RGB);
        Graphics gr = subImagen.getGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, n, m);
        gr.setColor(Color.BLACK);
        gr.setFont(new Font("Playing Cards", Font.BOLD, n));
        gr.drawString(letra, (-5 * n) / 40, (65 * n) / 50);
        gr.dispose();
        return subImagen;
    }
}
