package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KeluargaMapper;
import com.example.tugas1.model.KeluargaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService{
	
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Override
	public KeluargaModel viewKeluargaByNKK(String nkk){
		return keluargaMapper.viewKeluargaByNKK(nkk);
	}
	
	@Override
	public String generateNKK(String nama_kecamatan){
		return keluargaMapper.generateNKK(nama_kecamatan);
	}
	
	@Override
	public List<KeluargaModel> check_nkk(String kode){
		return keluargaMapper.check_nkk(kode);
	}
	
	@Override
	public void addKeluarga(KeluargaModel keluarga){
		keluargaMapper.addKeluarga(keluarga);
	}
	
	@Override
	public KeluargaModel dataKeluarga(String nkk){
		return keluargaMapper.dataKeluarga(nkk);
	}
	
	@Override
	public void updateKeluarga(KeluargaModel keluarga){
		keluargaMapper.updateKeluarga(keluarga);
	}
	
	@Override
	public void changeNKK(String old_nkk, String new_nkk){
		keluargaMapper.changeNKK(old_nkk, new_nkk);
	}
	
	@Override
	public void changeValidNKK(int id){
		keluargaMapper.changeValidNKK(id);
	}
}
