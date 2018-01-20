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
import myBank.model.NasabahEntity;

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

    public String noUrutNasabah() {
        int noUrut = 0;
        String noUrutSQL;
        noUrutSQL = "SELECT COUNT(no_ktp) FROM tbnasabah";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(noUrutSQL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                noUrut = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("Terjadi erorr" + ex.getMessage());
        } finally {
            conn.closeConnection();
        }
        return String.valueOf(noUrut);
    }

    public List<NasabahEntity> selectNasabah() {
        List<NasabahEntity> listNasabah = new ArrayList<>();
        String selectSQL = "SELECT id_ns,no_ktp,nama_ns,jk_ns FROM tbnasabah";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(selectSQL);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                NasabahEntity nasabah = new NasabahEntity();
                nasabah.setIdNasabah(rs.getString("id_ns"));
                nasabah.setNoKtp(rs.getString("no_ktp"));
                nasabah.setNama(rs.getString("nama_ns"));
                nasabah.setJenisKelamin(rs.getString("jk_ns"));                
                listNasabah.add(nasabah);
            }
            statement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil seluruh data nasaabah : " + ex.getMessage());
        } finally {
            conn.closeConnection();
        }
        return listNasabah;
    }

    public List<NasabahEntity> cariNasabah(String keyWord) {
        List<NasabahEntity> listNasabah = new ArrayList<>();
        String selectSQL = "SELECT id_ns,no_ktp,nama_ns,jk_ns,stts_ns FROM tbnasabah WHERE id_ns = ? OR no_ktp = ? OR nama_ns LIKE ?";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(selectSQL);
            statement.setString(1, keyWord);
            statement.setString(2, keyWord);
            statement.setString(3, "%" + keyWord + "%");
            ResultSet rs = statement.executeQuery();
            rs.last();
            if (rs.getRow() == 0) {
                JOptionPane.showMessageDialog(null, "Data nasabah tidak ditemukan");
            } else {
                rs.first();
                while (rs.next()) {
                    NasabahEntity nasabah = new NasabahEntity();
                    nasabah.setIdNasabah(rs.getString("id_ns"));
                    nasabah.setNoKtp(rs.getString("no_ktp"));
                    nasabah.setNama(rs.getString("nama_ns"));
                    nasabah.setJenisKelamin(rs.getString("jk_ns"));
                    listNasabah.add(nasabah);
                }
            }
            statement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil seluruh data nasaabah : " + ex.getMessage());
        } finally {
            conn.closeConnection();
        }
        return listNasabah;
    }

    public boolean insertNasabah(NasabahEntity nasabah) {
        boolean result = false;
        final String insertNasabahSQL = "INSERT INTO tbnasabah(id_ns,no_ktp,nama_ns,tempat_lahir_ns,tanggal_lahir_ns,alamat_ns,tlp_ns,jk_ns,poto,password,tgl_daftar,tgl_update)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = conn.openConnection().prepareStatement(insertNasabahSQL);
            statement.setString(1, nasabah.getIdNasabah());
            statement.setString(2, nasabah.getNoKtp());
            statement.setString(3, nasabah.getNama());
            statement.setString(4, nasabah.getTempatLahir());
            statement.setDate(5, nasabah.getTanggalLahir());
            statement.setString(6, nasabah.getAlamat());
            statement.setString(7, nasabah.getNoTlp());
            statement.setString(8, nasabah.getJenisKelamin());     
            statement.setString(9, nasabah.getPoto());
            statement.setString(10, nasabah.getPassword());
            statement.setDate(11, nasabah.getTglDaftar());
            statement.setDate(12, nasabah.getTglUpdate());
            statement.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saat menyimpan data nasabah ke database : " + ex.getMessage());
            result = false;
        } finally {
            conn.closeConnection();
        }
        return result;
    }

}
