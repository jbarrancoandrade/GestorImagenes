/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.awt.Image;

/**
 *
 * @author Jhonbarranco
 */
public class Alm_ImgInsumos {

    Image Graficos;
    String Grafico;
    String Codins;

    public Alm_ImgInsumos() {
    }

    public Alm_ImgInsumos(Image Graficos, String Grafico, String Codins) {
        this.Graficos = Graficos;
        this.Grafico = Grafico;
        this.Codins = Codins;
    }

    public Image getGraficos() {
        return Graficos;
    }

    public void setGraficos(Image Graficos) {
        this.Graficos = Graficos;
    }

    public String getGrafico() {
        return Grafico;
    }

    public void setGrafico(String Grafico) {
        this.Grafico = Grafico;
    }

    public String getCodins() {
        return Codins;
    }

    public void setCodins(String Codins) {
        this.Codins = Codins;
    }

}
