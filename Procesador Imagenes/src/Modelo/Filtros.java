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
                if(colorD.equals("Red")){
                    colorDominante = new Color(color.getRed(),0,0);
                }else if (colorD.equals("Green")){
                    colorDominante = new Color(0,color.getGreen(),0);
                }else{
                    colorDominante = new Color(0,0,color.getBlue());
                }
                procesada.setRGB(j, i, colorDominante.getRGB());
            }
        }
        return procesada;
    }
    
    /**
     * 
     * @param img
     * @param r
     * @param g
     * @param b
     * @return
     * @throws IOException 
     */
    public static BufferedImage micas(File img,int r,int g,int b) throws IOException{
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
}
