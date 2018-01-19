/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.controller;

import com.sun.org.apache.bcel.internal.generic.Select;
import java.util.List;
import javax.swing.JOptionPane;
import myBank.dao.loginDao;
import myBank.model.userEntity;
import myBank.view.customerService.customerServiceFrame;
import myBank.view.login.loginFrame;
import myBank.view.teller.tellerFrame;

/**
 *
 * @author Fauzi
 */
public class loginController {

    loginDao dao = new loginDao();

    public void login(loginFrame view) {
        String idUser = view.getIdUser().getText();
        String pass = String.valueOf(view.getPassword().getText());
        String keterangan = null ;
        if (!idUser.equals("") || !pass.equals("")) {
            if (idUser.equals("")) {
                view.setMessageValidasi("Isi ID user", true);
            } else if (pass.equals("")) {
                view.setMessageValidasi("Isi Password user", true);
            } else {
                System.out.println("id " + idUser + " pass " + pass);
                if (dao.cekLogin(idUser, pass) == true) {
                    List<userEntity> listUser = dao.loadUser();
                    for (userEntity user : listUser) {                     
                    keterangan = user.getKeterangan();
                    }
                    switch(keterangan){
                        case "TELLER":
                            tellerFrame teller = new tellerFrame();
                            teller.setVisible(true);
                            view.dispose();
                            break;
                        case "HRD" :
                            break;
                        case "MANAGER" :
                            break;
                        case "CS" :
                            customerServiceFrame cs = new customerServiceFrame();
                            cs.setVisible(true);
                            view.dispose();
                            break;
                           default: JOptionPane.showMessageDialog(null, "terjadi kesalahan");
                }
                            
                } else {
                    view.setMessageValidasi("Gagal login. Cek ID atau Password", true);
                }
            }
            
        }
    }
}
