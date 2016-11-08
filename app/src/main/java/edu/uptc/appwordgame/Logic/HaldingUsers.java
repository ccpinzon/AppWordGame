package edu.uptc.appwordgame.Logic;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.uptc.appwordgame.Persistence.ConnectionCloud;

/**
 * Created by cristhian on 7/11/16.
 */

public class HaldingUsers {
    private ArrayList<User> users;
    private ConnectionCloud connectionCloud;
    private Connection conn;


    public HaldingUsers() throws SQLException, ClassNotFoundException {
        users = new ArrayList<User>();
        connectionCloud = new ConnectionCloud();
        conn = connectionCloud.obtener();
        loadUsers();
    }

    private void loadUsers() throws SQLException {
        String sql = "SELECT * FROM USER";
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        while (rs.next()){
            String userid = rs.getString("userid");
            String name = rs.getString("name");
            String user = rs.getString("nickname");
            String pass = rs.getString("password");
            String email = rs.getString("email");

            users.add(new User(userid,name,user,pass,email));
        }
    }

    public User findUser(String nickname){
        for (User user:users) {
            if (user.getNickName().equals(nickname))
                return user;
        }
        return null;
    }
    public boolean addUser(User user) throws SQLException {
        if(findUser(user.getNickName())==null) {
            String sql = "INSERT INTO USER (name,nickname,password,email)" + "VALUES (?,?,MD5(?),?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, user.getName());
            st.setString(2, user.getNickName());
            // TODO: MD5 ENCRIPTION
            st.setString(3, user.getPassword());
            st.setString(4, user.getEmail());
            st.execute();
            conn.close();
            users.add(user);
            return true;
        }else{
            return false;
        }
    }


    public ArrayList<User> getUsers() {
        return (ArrayList<User>) users.clone();
    }
}
