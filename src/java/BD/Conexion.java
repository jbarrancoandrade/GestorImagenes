/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Jhonbarranco
 */
public class Conexion {
    
      public static Connection GetConnection() {
        Connection conexion = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//BD_COMERCIAL
            String url = "jdbc:sqlserver://localhost;databaseName=BD_SEGURIDAD_KANTICAS;user=sa;password=admin1;";
            conexion = DriverManager.getConnection(url);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error1 en la Conexión con la BD " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error2 en la Conexión con la BD " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error3 en la Conexión con la BD " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } finally {
            return conexion;
        }
    }
    
}
