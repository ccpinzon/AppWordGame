package edu.uptc.appwordgame;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.uptc.appwordgame.Logic.HaldingUsers;
import edu.uptc.appwordgame.Logic.User;
import edu.uptc.appwordgame.Persistence.ConnectionCloud;
import edu.uptc.appwordgame.Persistence.DatabaseAccess;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
       // listarUsers();
        //probarConexion();
      //  probarConexionSQlite();
    }



    private void listarUsers() throws SQLException {
        try {
            HaldingUsers haldingUsers =  new HaldingUsers();

            ArrayList<User> users = haldingUsers.getUsers();
            System.out.println(haldingUsers.findUser("tolo"));
            System.out.println(haldingUsers.findUser("kaksdkd"));
            for (User u: users) {
                System.out.println("Usuario"+"("+u.getUserId()+") = "+u.getName()+", "+u.getNickName()+", "+u.getPassword());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
            ConnectionCloud.cerrar();
    }

    public void probarConexion() throws  Exception{
        ConnectionCloud c = new ConnectionCloud();

        Connection con = c.obtener();

        if (con!= null){
            System.out.println("Conexion esablecida");
        }else{
            System.out.println("Conexion erronea");
        }

        PreparedStatement st = con.prepareStatement("SELECT * FROM USER");

        ResultSet rs = st.executeQuery();
        String name = "";
        while(rs.next()){
            name = rs.getString("password");
            System.out.println(name);
        }

    }
}