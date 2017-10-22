package com.example.tugas1.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	@Select("SELECT P.nik, P.nama, P.tempat_lahir, P.tanggal_lahir, KG.alamat, KG.RT, KG.RW, KL.nama_kelurahan, KC.nama_kecamatan, KO.nama_kota, P.golongan_darah, P.agama, P.status_perkawinan, P.pekerjaan, P.is_wni, P.is_wafat "
			+ "FROM penduduk P, keluarga KG, kelurahan KL, kecamatan KC, kota KO "
			+ "WHERE P.nik = #{nik} and P.id_keluarga = KG.id and KG.id_kelurahan = KL.id and KL.id_kecamatan = KC.id and KC.id_kota = KO.id")
	@Results(value = {
			@Result(property="nik", column="nik"),
			@Result(property="nama", column="nama"),
			@Result(property="tempat_lahir", column="tempat_lahir"),
			@Result(property="tanggal_lahir", column="tanggal_lahir"),
			@Result(property="alamat", column="alamat"),
			@Result(property="RT", column="RT"),
			@Result(property="RW", column="RW"),
			@Result(property="nama_kelurahan", column="nama_kelurahan"),
			@Result(property="nama_kecamatan", column="nama_kecamatan"),
			@Result(property="nama_kota", column="nama_kota"),
			@Result(property="golongan_darah", column="golongan_darah"),
			@Result(property="agama", column="agama"),
			@Result(property="status_perkawinan", column="status_perkawinan"),
			@Result(property="pekerjaan", column="pekerjaan"),
			@Result(property="is_wni", column="is_wni"),
			@Result(property="is_wafat", column="is_wafat")
	})
	PendudukModel viewPendudukByNIK(@Param("nik") String nik);
	
	@Insert("INSERT INTO penduduk VALUES (default, #{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, 0)")
	void addPenduduk(PendudukModel penduduk);
	
	@Select("SELECT SUBSTRING(KC.kode_kecamatan, 1, 6) FROM keluarga KG, kelurahan KL, kecamatan KC WHERE KG.id = #{id_keluarga} and KG.id_kelurahan = KL.id and KL.id_kecamatan = KC.id")
	String generateNIK(int id_keluarga);
	
	@Select("SELECT id_keluarga, tanggal_lahir, jenis_kelamin FROM penduduk WHERE id_keluarga = #{id_keluarga} and tanggal_lahir = #{tanggal_lahir} and jenis_kelamin = #{jenis_kelamin}")
	List<PendudukModel> check_nik(@Param("id_keluarga") int id_keluarga, @Param("tanggal_lahir") Date tanggal_lahir, @Param("jenis_kelamin") int jenis_kelamin);

	@Select("SELECT * FROM penduduk WHERE nik = #{nik}")
	@Results(value = {
			@Result(property="nama", column="nama"),
			@Result(property="tempat_lahir", column="tempat_lahir"),
			@Result(property="tanggal_lahir", column="tanggal_lahir"),
			@Result(property="jenis_kelamin", column="jenis_kelamin"),
			@Result(property="is_wni", column="is_wni"),
			@Result(property="id_keluarga", column="id_keluarga"),
			@Result(property="agama", column="agama"),
			@Result(property="pekerjaan", column="pekerjaan"),
			@Result(property="status_perkawinan", column="status_perkawinan"),
			@Result(property="status_dalam_keluarga", column="status_dalam_keluarga"),
			@Result(property="golongan_darah", column="golongan_darah"),
			@Result(property="is_wafat", column="is_wafat")
	})
	PendudukModel dataPenduduk(@Param("nik") String nik);
	
	@Update("UPDATE penduduk SET nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, "
			+ "jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, id_keluarga = #{id_keluarga}, agama = #{agama}, "
			+ "pekerjaan = #{pekerjaan}, status_perkawinan = #{status_perkawinan}, status_dalam_keluarga = #{status_dalam_keluarga}, "
			+ "golongan_darah = #{golongan_darah} WHERE nik = #{nik}")
	void updatePenduduk(PendudukModel penduduk);
	
	@Select("SELECT jenis_kelamin FROM penduduk WHERE nik = #{nik}")
	int old_gender(@Param("nik") String nik);
	
	@Select("SELECT tanggal_lahir FROM penduduk WHERE nik = #{nik}")
	Date old_birthday(@Param("nik") String nik);
	
	@Select("SELECT id_keluarga FROM penduduk WHERE nik = #{nik}")
	int old_idFamily(@Param("nik") String nik);
	
	@Update("UPDATE penduduk SET nik = #{new_nik} WHERE nik = #{old_nik}")
	void changeNIK(@Param("old_nik") String old_nik, @Param("new_nik") String new_nik);
	
	@Update("UPDATE penduduk SET is_wafat = 1 WHERE nik = #{nik}")
	void changeStatusKematian(String nik);
	
	@Select("SELECT nik FROM penduduk WHERE id_keluarga = #{id_keluarga} and is_wafat = 0")
	List<PendudukModel> sisa_anggotaKeluarga(int id_keluarga);
	
	@Select("SELECT p.nik, p.nama, p.tempat_lahir, p.tanggal_lahir, p.jenis_kelamin, p.agama, p.pekerjaan, p.status_perkawinan, p.status_dalam_keluarga, p.is_wni FROM penduduk P, keluarga KG WHERE p.id_keluarga = KG.id and KG.id_kelurahan = #{kl}")
	List<PendudukModel> cariPenduduk(@Param("kl") Integer kl);
	
}
