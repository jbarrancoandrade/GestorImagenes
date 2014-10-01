/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import BD.Alm_ImgInsumos;
import BD.Conexion;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Jhonbarranco
 */
@ManagedBean
public class fileUploadView {

    Connection miConexion;
    private ArrayList<Alm_ImgInsumos> imagenes;
    private StreamedContent chart;

    public fileUploadView() throws IOException {
        miConexion = Conexion.GetConnection();
        Obmg();
    }

    /**
     * Guardar Imagenes EN La BD
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) throws IOException {

        FacesMessage message = new FacesMessage("Exitosa", event.getFile().getFileName() + " Esta Cargado");
        FacesContext.getCurrentInstance().addMessage(null, message);

        String nombreCompleto = event.getFile().getFileName();
        int ubic = nombreCompleto.indexOf(".");
        String noombre = nombreCompleto.substring(0, ubic);
        try {

            UpdateImg(noombre, event.getFile().getInputstream());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error! :\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param Codins
     * @param in
     * @throws FileNotFoundException
     */
    public void UpdateImg(String Codins, InputStream in) throws FileNotFoundException {
        Connection conn = (Connection) Conexion.GetConnection();
        FileInputStream fis = null;
        //"UPDATE Alm_ImgInsumos SET Graficos=? WHERE Codins=?"
        try {//INSERT INTO Alm_ImgInsumos (Codins,  Graficos) VALUES (?,  ?)
            String sql = "INSERT INTO Alm_ImgInsumos (Codins,  Graficos) VALUES (?,  ?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setBinaryStream(2, in, (int) in.available());
            statement.setString(1, Codins);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Correcto");

            }

        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);

            JOptionPane.showMessageDialog(null, "Error! :\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(fileUploadView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @return @throws SQLException
     */
    ArrayList<Alm_ImgInsumos> getImagenes() throws SQLException {
        ArrayList<Alm_ImgInsumos> lista = new ArrayList();
        Connection conn = (Connection) Conexion.GetConnection();
        Statement stmt = null;

        try {

            String query = "SELECT Graficos,Codins FROM Alm_ImgInsumos";

            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Alm_ImgInsumos imagen = new Alm_ImgInsumos();
                    Blob blob = rs.getBlob("Graficos");
                    String nombre = rs.getObject("Codins").toString();
                    byte[] data = blob.getBytes(1, (int) blob.length());
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(new ByteArrayInputStream(data));
                    } catch (IOException ex) {
                        Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    imagen.setGraficos(img);
                    imagen.setCodins(nombre);
                    lista.add(imagen);
                }
                rs.close();
            } catch (SQLException e) {

            }

        } catch (Exception e) {
            if (stmt != null) {
                stmt.close();
            }
        }

        return lista;
    }

    /**
     *
     */
    public void ObtenerImagenes() {
        try {
            imagenes = getImagenes();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error! :\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @return
     */
    public void Obmg() throws IOException {
        ObtenerImagenes();
        String codIns = "I0201014";

        Image a = null;

        for (int i = 0; i < imagenes.size(); i++) {
            if (imagenes.get(i).getCodins().equalsIgnoreCase(codIns)) {
                a = (Image) (RenderedImage) imagenes.get(i).getGraficos();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write((RenderedImage) a, "png", os);
                chart = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");
            }
        }

    }

    public StreamedContent getChart() {
        return chart;
    }

    public void setChart(StreamedContent chart) {
        this.chart = chart;
    }

}
