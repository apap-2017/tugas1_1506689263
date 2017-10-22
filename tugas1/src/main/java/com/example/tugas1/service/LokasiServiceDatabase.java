package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.LokasiMapper;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LokasiServiceDatabase implements LokasiService{
	
	@Autowired
	private LokasiMapper lokasiMapper;
	
	public int idKelurahan(String nama_kelurahan){
		return lokasiMapper.idKelurahan(nama_kelurahan);
	}
	
	@Override
	public String old_namaKecamatan(String kode){
		return lokasiMapper.old_namaKecamatan(kode);
	}
	
	@Override
	public int old_idKelurahan(String nkk){
		return lokasiMapper.old_idKelurahan(nkk);
	}
	
	@Override
	public String old_namaKelurahan(int id){
		return lokasiMapper.old_namaKelurahan(id);
	}
	
	@Override
	public List<KotaModel> listKota(){
		return lokasiMapper.listKota();
	}
	
	@Override
	public String namaKota(Integer kt){
		return lokasiMapper.namaKota(kt);
	}
	
	@Override
	public List<KecamatanModel> listKecamatan(@Param("kt") Integer kt){
		return lokasiMapper.listKecamatan(kt);
	}
	
	@Override
	public String namaKecamatan(Integer kc){
		return lokasiMapper.namaKecamatan(kc);
	}
	
	@Override
	public String namaKelurahan(Integer kl){
		return lokasiMapper.namaKelurahan(kl);
	}
	
	@Override
	public List<KelurahanModel> listKelurahan(@Param("kc") Integer kc){
		return lokasiMapper.listKelurahan(kc);
	}
}
