package com.example.tugas1.service;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;

public interface PendudukService {
	
	PendudukModel viewPendudukByNIK(String nik);
	
	void addPenduduk(PendudukModel penduduk);
	
	String generateNIK(int id_keluarga);
	
	List<PendudukModel> check_nik(int id_keluarga, Date tanggal_lahir, int jenis_kelamin);
	
	PendudukModel dataPenduduk(String nik);
	
	void updatePenduduk(PendudukModel penduduk);
	
	int old_gender(String nik);
	
	Date old_birthday(String nik);
	
	int old_idFamily(String nik);
	
	void changeNIK(String old_nik, String new_nik);
	
	void changeStatusKematian(String nik);
	
	List<PendudukModel> sisa_anggotaKeluarga(int id_keluarga);
	
	List<PendudukModel> cariPenduduk(Integer kl);
}
