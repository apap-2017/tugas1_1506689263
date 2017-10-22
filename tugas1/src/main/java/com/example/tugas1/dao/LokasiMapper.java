package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;

@Mapper
public interface LokasiMapper {

	@Select("SELECT id FROM kelurahan WHERE nama_kelurahan = #{nama_kelurahan}")
	int idKelurahan(String nama_kelurahan);
	
	@Select("SELECT nama_kecamatan FROM kecamatan WHERE kode_kecamatan = #{kode}")
	String old_namaKecamatan(String kode);
	
	@Select("SELECT id_kelurahan FROM keluarga WHERE nomor_kk = #{nkk}")
	int old_idKelurahan(String nkk);
	
	@Select("SELECT nama_kelurahan FROM kelurahan WHERE id = #{id}")
	String old_namaKelurahan(int id);
	
	@Select("SELECT * FROM kota")
	List<KotaModel> listKota();
	
	@Select("SELECT nama_kota FROM kota WHERE id = #{kt}")
	String namaKota(@Param("kt") Integer kt);
	
	@Select("SELECT * FROM kecamatan WHERE id_kota = #{kt}")
	List<KecamatanModel> listKecamatan(@Param("kt") Integer kt);
	
	@Select("SELECT nama_kecamatan FROM kecamatan WHERE id = #{kc}")
	String namaKecamatan(@Param("kc") Integer kc);
	
	@Select("SELECT * FROM kelurahan WHERE id_kecamatan = #{kc}")
	List<KelurahanModel> listKelurahan(@Param("kc") Integer kc);
	
	@Select("SELECT nama_kelurahan FROM kelurahan WHERE id = #{kl}")
	String namaKelurahan(@Param("kl") Integer kl);
	
}
