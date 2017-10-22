package com.example.tugas1.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.PendudukMapper;
import com.example.tugas1.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService{
	
	@Autowired
	private PendudukMapper pendudukMapper;
	
	@Override
	public PendudukModel viewPendudukByNIK(String nik){
		log.info("mencari penduduk berdasarkan nik {}", nik);
		return pendudukMapper.viewPendudukByNIK(nik);
	}
	
	@Override
	public void addPenduduk(PendudukModel penduduk){
		pendudukMapper.addPenduduk(penduduk);
	}
	
	
	@Override
	public String generateNIK(int id_keluarga){
		return pendudukMapper.generateNIK(id_keluarga);
	}
	
	@Override
	public List<PendudukModel> check_nik(int id_keluarga, Date tanggal_lahir, int jenis_kelamin){
		return pendudukMapper.check_nik(id_keluarga, tanggal_lahir, jenis_kelamin);
	}
	
	@Override
	public PendudukModel dataPenduduk(String nik){
		return pendudukMapper.dataPenduduk(nik);
	}
	
	@Override
	public void updatePenduduk(PendudukModel penduduk){
		pendudukMapper.updatePenduduk(penduduk);
	}
	
	@Override
	public int old_gender(String nik){
		return pendudukMapper.old_gender(nik);
	}
	
	@Override
	public Date old_birthday(String nik){
		return pendudukMapper.old_birthday(nik);
	}
	
	@Override
	public int old_idFamily(String nik){
		return pendudukMapper.old_idFamily(nik);
	}
	
	@Override
	public void changeNIK(String old_nik, String new_nik){
		pendudukMapper.changeNIK(old_nik, new_nik);
	}
	
	@Override
	public void changeStatusKematian(String nik){
		pendudukMapper.changeStatusKematian(nik);
	}
	
	@Override
	public List<PendudukModel> sisa_anggotaKeluarga(int id_keluarga){
		return pendudukMapper.sisa_anggotaKeluarga(id_keluarga);
	}
	
	@Override
	public List<PendudukModel> cariPenduduk(Integer kl){
		return pendudukMapper.cariPenduduk(kl);
	}
}
