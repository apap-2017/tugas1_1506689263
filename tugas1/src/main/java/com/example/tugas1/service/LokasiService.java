package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;

public interface LokasiService {

	int idKelurahan(String nama_kelurahan);
	
	String old_namaKecamatan(String kode);
	
	int old_idKelurahan(String nkk);
	
	String old_namaKelurahan(int id);
	
	List<KotaModel> listKota();
	
	String namaKota(Integer kt);
	
	List<KecamatanModel> listKecamatan(Integer kt);
	
	String namaKecamatan(Integer kc);
	
	String namaKelurahan(Integer kl);
	
	List<KelurahanModel> listKelurahan(Integer kc);
}
