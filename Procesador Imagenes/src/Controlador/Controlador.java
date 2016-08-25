package Controlador;

import Modelo.Filtros;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
            case "Tonos de grises por porcentaje":
                return Filtros.tonosDeGrisesPorPorcentaje(img);
            case "Mosaico":
                return null;
            case "Red, Green or Blue":
                return null;
            case "Micas":
                return null;
        }
        return null;
    }
    
}
