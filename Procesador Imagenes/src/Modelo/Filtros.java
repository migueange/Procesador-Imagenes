package Modelo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author miguelita
 */
public class Filtros {

    /**
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
                System.out.println(rgbGris.getRed()+"|"+rgbGris.getGreen()+"|"+rgbGris.getBlue());
                procesada.setRGB(j, i, rgbGris.getRGB());
            }
        }     
        return procesada;
    }

}
