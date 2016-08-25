package Controlador;

import Modelo.Filtros;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author miguelita
 */
public class Controlador {

    /**
     *
     * @param filtro
     * @param img
     * @return
     * @throws IOException
     */
    public static BufferedImage aplicaFiltro(String filtro, File img) throws IOException {
        switch (filtro) {
            case "Tonos de grises por promedio":
                return Filtros.tonosDeGrisesPorPromedio(img);
            case "Convertir a grises 1":
                return null;
            case "Convertir a grises 2":
                return null;
            case "Mosaico":
                return null;
            case "Red, Green or Blue":
                return null;
            case "Micas":
                return null;
        }
        return null;
    }

    /**
     * 
     * @param bi
     * @return 
     */
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
}
