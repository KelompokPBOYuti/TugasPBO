/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import myBank.database.connectionDatabase;
import myBank.model.nasabahEntity;

/**
 *
 * @author Fauzi
 */
public class nasabahDao {

    connectionDatabase conn = new connectionDatabase();

    public boolean cekNoKTP(String noKTP) {
        boolean result = false;
        String noKtpSQL;
        noKtpSQL = "SELECT no_ktp FROM tbnasabah WHERE no_ktp= ? ";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(noKtpSQL);
            statement.setString(1, noKTP);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException ex) {
            System.out.println("Terjadi erorr" + ex.getMessage());
        } finally {
            conn.closeConnection();
        }
        return result;
    }
    public String noUrutNasabah(){
        int noUrut = 0;
        String noUrutSQL;
        noUrutSQL = "SELECT COUNT(no_ktp) FROM tbnasabah";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(noUrutSQL);            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                noUrut = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("Terjadi erorr" + ex.getMessage());
        } finally {
            conn.closeConnection();
        }        
        return String.valueOf(noUrut);
    }
    public void insertNasabah(nasabahEntity nasabah){
        String insertSQL = "INSERT INTO tbnasabah(id_ns,no_ktp,nama_ns,tempat_lahir_ns,tanggal_lahir_ns,alamat_ns,tlp_ns,jk_ns,stts_ns,poto,tgl_daftar,tgl_update)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {    
            PreparedStatement statement = conn.openConnection().prepareStatement(insertSQL);
            statement.setString(1, nasabah.getIdNasabah());
            statement.setString(2, nasabah.getNoKtp());
            statement.setString(3, nasabah.getNama());
            statement.setString(4, nasabah.getTempatLahir());
            statement.setDate(5, nasabah.getTanggalLahir());
            statement.setString(6, nasabah.getAlamat());
            statement.setString(7, nasabah.getNoTlp());
            statement.setString(8, nasabah.getJenisKelamin());
            statement.setString(9, nasabah.getStatus());
            statement.setString(10, nasabah.getPoto());
            statement.setDate(11, nasabah.getTglDaftar());
            statement.setDate(12, nasabah.getTglUpdate());
            statement.executeUpdate();
            //fireOnInsert(nasabah);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saat menyimpan data ke database : "+ex.getMessage());
        }finally{
            conn.closeConnection();
        }
    }
}
