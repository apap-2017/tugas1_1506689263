package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
	
	@Select("SELECT KG.id, KG.nomor_kk, KG.alamat, KG.RT, KG.RW, KL.nama_kelurahan, KC.nama_kecamatan, KO.nama_kota "
			+ "FROM keluarga KG, kelurahan KL, kecamatan KC, kota KO "
			+ "WHERE KG.nomor_kk = #{nkk} and KG.id_kelurahan = KL.id and KL.id_kecamatan = KC.id and KC.id_kota = KO.id")
	@Results(value = {
			@Result(property="nomor_kk", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="RT", column="RT"),
			@Result(property="RW", column="RW"),
			@Result(property="nama_kelurahan", column="nama_kelurahan"),
			@Result(property="nama_kecamatan", column="nama_kecamatan"),
			@Result(property="nama_kota", column="nama_kota"),
			@Result(property="daftarAnggota", column="id",
			javaType = List.class,
			many = @Many(select="selectAnggotaKeluarga"))
	})
	KeluargaModel viewKeluargaByNKK(@Param("nkk") String nkk);
	
	@Select("SELECT nama, nik, jenis_kelamin, tempat_lahir, tanggal_lahir, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, is_wni "
			+ "FROM penduduk WHERE penduduk.id_keluarga = #{id}")
	List<PendudukModel> selectAnggotaKeluarga();
	
	@Select("SELECT SUBSTRING(kode_kecamatan, 1, 6) FROM kecamatan WHERE nama_kecamatan = #{nama_kecamatan}")
	String generateNKK(String nama_kecamatan);
	
	@Select("SELECT SUBSTRING(nomor_kk, 1, 12) FROM keluarga WHERE SUBSTRING(nomor_kk, 1, 12) = #{kode}")
	List<KeluargaModel> check_nkk(String kode);
	
	@Insert("INSERT INTO keluarga VALUES (default, #{nomor_kk}, #{alamat}, #{RT}, #{RW}, #{id_kelurahan}, 1)")
	void addKeluarga(KeluargaModel keluarga);
	
	@Select("SELECT KG.nomor_kk, KG.alamat, KG.RT, KG.RW, KG.is_tidak_berlaku, KL.nama_kelurahan, KC.nama_kecamatan, KO.nama_kota "
			+ "FROM keluarga KG, kelurahan KL, kecamatan KC, kota KO "
			+ "WHERE KG.nomor_kk = #{nkk} and KG.id_kelurahan = KL.id and KL.id_kecamatan = KC.id "
			+ "and KC.id_kota = KO.id")
	@Results(value = {
			@Result(property="nomor_kk", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="RT", column="RT"),
			@Result(property="RW", column="RW"),
			@Result(property="nama_kelurahan", column="nama_kelurahan"),
			@Result(property="nama_kecamatan", column="nama_kecamatan"),
			@Result(property="nama_kota", column="nama_kota")
	})
	KeluargaModel dataKeluarga(@Param("nkk") String nkk);
	
	@Update("UPDATE keluarga SET alamat = #{alamat}, RT = #{RT}, RW = #{RW}, id_kelurahan = #{id_kelurahan} WHERE nomor_kk = #{nomor_kk}")
	void updateKeluarga(KeluargaModel keluarga);
	
	@Update("UPDATE keluarga SET nomor_kk = #{new_nkk} WHERE nomor_kk = #{old_nkk}")
	void changeNKK(@Param("old_nkk") String old_nkk, @Param("new_nkk") String new_nkk);
	
	@Update("UPDATE keluarga SET is_tidak_berlaku = 1 WHERE id = #{id}")
	void changeValidNKK(int id);
	
}
