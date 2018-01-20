/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.controller;

import java.util.Date;
//import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import myBank.dao.TabunganDao;
import myBank.dao.nasabahDao;
import myBank.model.TabunganEntity;
import myBank.model.NasabahEntity;
import myBank.view.customerService.nasabah.nasabahNew;
import myBank.view.customerService.nasabah.nasabahPanel;

/**
 *
 * @author Fauzi
 */
public class nasabahController {

    nasabahDao nasabahDao = new nasabahDao();
    TabunganDao tabunganDao = new TabunganDao();
    Date date = new Date();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String tglSekarang = formatter.format(date);

    private String generateNoRek(String noKTP) {
        String rek = noKTP.substring(0, 6) + noKTP.substring(noKTP.length() - 4, noKTP.length()) + nasabahDao.noUrutNasabah();
        return rek;
    }

    private String generateIdNasabah(String tgl) {
        String id;
        tgl =tgl.replace("-", "");
        tgl = tgl.substring(2, tgl.length());
        id = "E" + nasabahDao.noUrutNasabah() + tgl ;
        return id;
    }

    private String generatePassword() {
        Random random = new Random();
        String pass = "";
        for (int i = 0; i < 6; i++) {
            pass += String.valueOf(random.nextInt(10));
        }
        return pass;
    }

    public List<NasabahEntity> loadData() {
        return nasabahDao.selectNasabah();
    }

    public void addNasabah(nasabahNew view) {
        String message = "";
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
        String photo = view.getPathPhoto();
        if (photo.equals("")) {
            photo = "null";
        }
        if ("".equals(noKtp) || "".equals(nama) || "".equals(jenisKelamin) || "".equals(tempatLahir) || "".equals(tanggalLahir) || "".equals(noTlp) || "".equals(alamat)) {
            message = "* Data nasabah harus lengkap" + "<br>" + message;
            view.setValidasi(message, true);
        } else if (true == nasabahDao.cekNoKTP(noKtp)) {
            message = "* Maaf No KTP sudah digunakan";
            view.setValidasi(message, true);
        } else if (Integer.parseInt(saldo) < 100000) {
            message = "* Saldo awal minimal Rp 100.000";
            view.setValidasi(message, true);
        } else {
            String noRek = generateNoRek(noKtp);
            String idNasabah = generateIdNasabah(tanggalLahir);
            String tglDaftar = tglSekarang;
            String tglUpdate = tglSekarang;
            TabunganEntity tabungan = new TabunganEntity();
            NasabahEntity nasabah = new NasabahEntity();
            nasabah.setIdNasabah(idNasabah);
            nasabah.setNoKtp(noKtp);
            nasabah.setNama(nama);
            nasabah.setJenisKelamin(jenisKelamin);
            nasabah.setTempatLahir(tempatLahir);
            nasabah.setTanggalLahir(java.sql.Date.valueOf(tanggalLahir));
            nasabah.setAlamat(alamat);
            nasabah.setNoTlp(noTlp);
            nasabah.setPoto(photo);
            nasabah.setPassword(generatePassword());
            nasabah.setTglDaftar(java.sql.Date.valueOf(tglDaftar));
            nasabah.setTglUpdate(java.sql.Date.valueOf(tglUpdate));
            tabungan.setIdNasabah(idNasabah);
            tabungan.setNo_rek(noRek);
            tabungan.setSaldo(Double.valueOf(saldo));
            if ((nasabahDao.insertNasabah(nasabah) == true) && (tabunganDao.insertTabungan(tabungan)== true)) {
                JOptionPane.showMessageDialog(null, "Data nasabah berhasil di simpan");
                TabunganEntity dataNasabah = tabunganDao.selectNasabahBaru(idNasabah);
                view.setDataNasabah(dataNasabah.getNo_rek(), dataNasabah.getNoKtp(), dataNasabah.getNama(), dataNasabah.getJenisKelamin(), dataNasabah.getTempatLahir() +", " + dataNasabah.getTanggalLahir(), dataNasabah.getAlamat(), dataNasabah.getSaldo(), dataNasabah.getIdNasabah(), dataNasabah.getPassword());
                view.setSlide2();
            } else {
                JOptionPane.showMessageDialog(null, "Data nasabah gagal di simpan");
            }
        }
    }

    public List<NasabahEntity> caridata(nasabahPanel view) {
        String keyWord = view.getCari().getText();
        List<NasabahEntity> res = new ArrayList<>();
        if (!"".equals(keyWord)) {
            res = nasabahDao.cariNasabah(keyWord);
        }
        return res;
    }
}
