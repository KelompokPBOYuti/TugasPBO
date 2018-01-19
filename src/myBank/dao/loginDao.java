/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myBank.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import myBank.database.connectionDatabase;
import myBank.model.userEntity;
import myBank.view.teller.tellerFrame;

/**
 *
 * @author Fauzi
 */
public class loginDao {
    connectionDatabase conn = new connectionDatabase();
    
    public boolean cekLogin(String idUser ,String password){
        List<userEntity> listUser = new ArrayList<>();
        boolean result = false;
        String loginSQL;
        String ketrangan;
        loginSQL = "SELECT * FROM tblogin WHERE id_user = ? AND pass = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(loginSQL);
            statement.setString(1, idUser);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                userEntity user = new userEntity();
                user.setId_user(rs.getString("id_user"));
                user.setPass(rs.getString("pass"));
                user.setKeterangan(rs.getString("ket"));
                listUser.add(user);
                result =true;
            } else {
                result = false;
            }
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi Erorr saat login "+ e.getMessage());
        } finally {
            conn.closeConnection();
        }
        return result;
    }
}
