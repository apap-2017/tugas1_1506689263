package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.KeluargaModel;

public interface KeluargaService {
	
	KeluargaModel viewKeluargaByNKK(String nkk);
	
	String generateNKK(String nama_kecamatan);
	
	List<KeluargaModel> check_nkk(String kode);
	
	void addKeluarga(KeluargaModel keluarga);
	
	KeluargaModel dataKeluarga(String nkk);
	
	void updateKeluarga(KeluargaModel keluarga);
	
	void changeNKK(String old_nkk, String new_nkk);
	
	void changeValidNKK(int id);
}
