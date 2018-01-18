/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.controller;

import java.util.Date;
//import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import myBank.dao.nasabahDao;
import myBank.model.nasabahEntity;
import myBank.view.customerService.nasabah.nasabahNew;

/**
 *
 * @author Fauzi
 */
public class nasabahController {
    nasabahDao dao = new nasabahDao();
    Date date = new Date();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String tglSekarang = formatter.format(date);
    private String idNasabah(String noKTP){
        String id;
        id ="15"+ String.valueOf(noKTP.substring(noKTP.length() - 4 , noKTP.length())) + dao.noUrutNasabah();
        return id;
    }
    public void addNasabah(nasabahNew view) {
        String message ="";
        String idNasabah;
        String noKtp = view.getNoKTP().getText();
        String nama = view.getNama().getText();
        String jenisKelamin;
        if (view.getJkLaki().isSelected() == true) {
            jenisKelamin = "Laki - laki";
        } else {
            jenisKelamin = "Perempuan";
        }
        String tempatLahir = view.getTempatLahir().getText();
        String tanggalLahir = view.getTanggalLahir();
        String noTlp = view.getNoTlp().getText();
        String alamat = view.getAlamat().getText();
        String saldo = view.getSaldo().getText();
        String status = "Aktif";
        String photo = view.getPathPhoto();
        String tglDaftar = tglSekarang;
        String tglUpdate = tglSekarang;
        if ("".equals(noKtp) || "".equals(nama) || "".equals(jenisKelamin) || "".equals(tempatLahir) || "".equals(tanggalLahir) || "".equals(noTlp) || "".equals(alamat)) {
            message ="* Data nasabah harus lengkap"+"<br>" + message;
            view.setValidasi(message, true);
        } else if (true == dao.cekNoKTP(noKtp)){
            message ="* Maaf No KTP sudah digunakan " +"<br>"+ message;
            view.setValidasi(message, true);
        } else if(Integer.parseInt(saldo) < 100000){
            message ="* Saldo awal minimal Rp 1.00.000 " +"<br>"+ message;
            view.setValidasi(message, true);
        }
        else {
            nasabahEntity nasabah = new nasabahEntity();
            nasabah.setIdNasabah(idNasabah(noKtp));
            nasabah.setNoKtp(noKtp);
            nasabah.setNama(nama);
            nasabah.setJenisKelamin(jenisKelamin);
            nasabah.setTempatLahir(tempatLahir);
            nasabah.setTanggalLahir(java.sql.Date.valueOf(tanggalLahir));
            nasabah.setAlamat(alamat);
            nasabah.setNoTlp(noTlp);
            nasabah.setStatus("aktif");
            nasabah.setPoto(photo);
            nasabah.setTglDaftar(java.sql.Date.valueOf(tglDaftar));
            nasabah.setTglUpdate(java.sql.Date.valueOf(tglUpdate));
            dao.insertNasabah(nasabah);
        }
    }

    public void ceka() {
        System.out.println("masuk");
    }
}
