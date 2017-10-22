package com.example.tugas1.model;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel {
	@NotNull
	private long id;
	@NotNull
	private String nik;
	@NotNull
	private String nama;
	@NotNull
	private String tempat_lahir;
	@NotNull
	private Date tanggal_lahir;
	@NotNull
	private String alamat;
	@NotNull
	private String RT;
	@NotNull
	private String RW;
	@NotNull
	private String nama_kelurahan;
	@NotNull
	private String nama_kecamatan;
	@NotNull
	private String nama_kota;
	@NotNull
	private int jenis_kelamin;
	@NotNull
	private int is_wni;
	@NotNull
	private int id_keluarga;
	@NotNull
	private String agama;
	@NotNull
	private String pekerjaan;
	@NotNull
	private String status_perkawinan;
	@NotNull
	private String status_dalam_keluarga;
	@NotNull
	private String golongan_darah;
	@NotNull
	private int is_wafat;
}
