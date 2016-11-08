package edu.uptc.appwordgame.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by cristhian on 7/11/16.
 */

public class ConnectionCloud {
    public static final String url = "jdbc:mysql://db4free.net:3306/wordgame";
    public static final String user = "worduptc";
    public static final String pass = "worduptc123";
    private static Connection cnx = null;
    public static Connection obtener() throws SQLException, ClassNotFoundException {
        if (cnx == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                cnx = DriverManager.getConnection(url, user, pass);
            } catch (SQLException ex) {
                throw new SQLException(ex);
            } catch (ClassNotFoundException ex) {
                throw new ClassCastException(ex.getMessage());
            }
        }
        return cnx;
    }
    public static void cerrar() throws SQLException {
        if (cnx != null) {
            cnx.close();
        }
    }

}
