/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myBank.controller;

import myBank.dao.loginDao;
import myBank.model.userEntity;
import myBank.view.login.loginFrame;

/**
 *
 * @author Fauzi
 */
public class loginController {
    loginDao dao = new loginDao();
    public void login(loginFrame view){
        String idUser = view.getIdUser().getText();
        String pass = String.valueOf(view.getPassword().getText());
        if(!idUser.equals("") || !pass.equals("")){
            if(idUser.equals("")){
                view.setMessageValidasi("Isi ID user", true);
            }else if (pass.equals("")){
                view.setMessageValidasi("Isi Password user", true);
            }
            else{
                System.out.println("id "+ idUser + " pass "+ pass);
                if(dao.cekLogin(idUser, pass) == true){
                    userEntity user = new userEntity();
                    System.out.println("id : "+ user.getId_user());
                    System.out.println("pass : "+ user.getPass());
                    System.out.println("ket : "+ user.getKeterangan());
                }
                else{
                    view.setMessageValidasi("Gagal login. Cek ID atau Password", true);
                }
        }
        }
    }
}
