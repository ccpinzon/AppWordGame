package edu.uptc.appwordgame.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by cristhian on 7/11/16.
 */

public class ConnectionCloud {
    private static final String url = "jdbc:mysql://192.168.0.23/wordgame";
    private static final String user = "root";
    private static final String password = "2010";
    private static Connection cnx = null;
    public static Connection obtener() throws SQLException, ClassNotFoundException {
        Properties info =  new Properties();
        info.setProperty("user",user);
        info.setProperty("password",password);
        info.setProperty("useSSL","true");
        info.setProperty("serverSslCert", "classpath:server.crt");
        if (cnx == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                cnx = DriverManager.getConnection(url,info);
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
