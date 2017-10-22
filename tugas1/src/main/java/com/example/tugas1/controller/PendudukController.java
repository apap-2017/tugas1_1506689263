package com.example.tugas1.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.service.KeluargaService;
import com.example.tugas1.service.LokasiService;
import com.example.tugas1.service.PendudukService;

@Controller
public class PendudukController {
	
	@Autowired
	PendudukService pendudukDAO;
	
	@Autowired
	KeluargaService keluargaDAO;
	
	@Autowired
	LokasiService lokasiDAO;
	
	@RequestMapping("/")
    public String index()
    {
        return "index";
    }
	
	//mau nambahin umur
	@RequestMapping(value = "/penduduk", method = RequestMethod.GET)
	public String viewPendudukByNIK(Model model, @RequestParam (value = "nik", required = false) String nik){
		if(nik == ""){
			return "null";
		}
		else{
			PendudukModel penduduk = pendudukDAO.viewPendudukByNIK(nik);
			
			if(penduduk != null){
				model.addAttribute("penduduk", penduduk);
	            return "result_nik";
			}
			else{
				model.addAttribute("nik", nik);
	            return "not_found_nik";
			}
		}
		
	}
	
	@RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
	public String mematikanPenduduk(Model model, @ModelAttribute PendudukModel pendudukModel){
		PendudukModel penduduk = pendudukDAO.dataPenduduk(pendudukModel.getNik());
		pendudukDAO.changeStatusKematian(penduduk.getNik());
		List<PendudukModel> jml_keluarga = pendudukDAO.sisa_anggotaKeluarga(penduduk.getId_keluarga());
		if(jml_keluarga.isEmpty()){
			keluargaDAO.changeValidNKK(penduduk.getId_keluarga());
		}
		model.addAttribute("nik", penduduk.getNik());
		return "result_ubah_kematian";
	}
	
	
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.GET)
	public String tambahPenduduk(Model model, @ModelAttribute("nik") String nik){
		model.addAttribute("penduduk", new PendudukModel());
		model.addAttribute("nik", nik);
		return "tambah_penduduk";
	
	}
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
	public String tambahPenduduk(@ModelAttribute PendudukModel penduduk, Model model){
		String kode = pendudukDAO.generateNIK(penduduk.getId_keluarga());
		DateFormat sdfr = new SimpleDateFormat("ddMMyy");
		String tanggal = sdfr.format(penduduk.getTanggal_lahir());
		if(penduduk.getJenis_kelamin() == 1){
			int int_tanggal = Integer.parseInt(tanggal) + 400000;
			tanggal = Integer.toString(int_tanggal);
		}
		List<PendudukModel> check = pendudukDAO.check_nik(penduduk.getId_keluarga(), penduduk.getTanggal_lahir(), penduduk.getJenis_kelamin());
		if(check.isEmpty()){
			penduduk.setNik(kode + tanggal + "0001");
		}
		if(!check.isEmpty()){
			int id = check.size() + 1;
			String index = String.format("%04d", id);
			penduduk.setNik(kode + tanggal + index);
		}
		pendudukDAO.addPenduduk(penduduk);
		model.addAttribute("penduduk", penduduk);
		return "result_tambah_penduduk";
	}
	
	@RequestMapping("/penduduk/ubah/{nik}")
	public String ubahPenduduk(Model model, @PathVariable(value = "nik") String nik){
		PendudukModel penduduk = pendudukDAO.dataPenduduk(nik);
		if(penduduk != null){
			model.addAttribute("penduduk", penduduk);
			return "ubah_penduduk";
		}
		else{
			model.addAttribute("nik", nik);
			return "not_found_nik";
		}
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
	public String ubahPenduduk(Model model, @ModelAttribute PendudukModel penduduk){
		String old_nik = penduduk.getNik();
		model.addAttribute("old_nik", old_nik);
		int old_gender = pendudukDAO.old_gender(penduduk.getNik());
		Date old_birthday = pendudukDAO.old_birthday(penduduk.getNik());
		int old_idFamily = pendudukDAO.old_idFamily(penduduk.getNik());
		pendudukDAO.updatePenduduk(penduduk);
		if(penduduk.getJenis_kelamin() != old_gender || !penduduk.getTanggal_lahir().equals(old_birthday) || penduduk.getId_keluarga() != old_idFamily){
			String kode = pendudukDAO.generateNIK(penduduk.getId_keluarga());
			DateFormat sdfr = new SimpleDateFormat("ddMMyy");
			String tanggal = sdfr.format(penduduk.getTanggal_lahir());
			
			if(old_gender != penduduk.getJenis_kelamin() && old_gender == 0){
				int int_tanggal = Integer.parseInt(tanggal) + 400000;
				tanggal = Integer.toString(int_tanggal);
			}
			
			if(old_gender != penduduk.getJenis_kelamin() && old_gender == 1){
				int int_tanggal = Integer.parseInt(tanggal) - 400000;
				tanggal = Integer.toString(int_tanggal);
			}
			
			List<PendudukModel> check = pendudukDAO.check_nik(penduduk.getId_keluarga(), penduduk.getTanggal_lahir(), penduduk.getJenis_kelamin());
			
			if(check.isEmpty()){
				penduduk.setNik(kode + tanggal + "0001");
			}
			
			if(!check.isEmpty()){
				int id = check.size() + 1;
				String index = String.format("%04d", id);
				penduduk.setNik(kode + tanggal + index);
			}
			pendudukDAO.changeNIK(old_nik, penduduk.getNik());	
		}
		return "result_ubah_penduduk";
	}
	

	@RequestMapping(value = "/penduduk/cari", method = RequestMethod.GET)
	public String cariPenduduk(Model model, @RequestParam(value = "kt", required = false) Integer kt,
			@RequestParam(value = "kc", required = false) Integer kc,
			@RequestParam(value = "kl", required = false) Integer kl){
		if(kt == null){
			if(kc == null){
				if(kl == null){
					List<KotaModel> kota = lokasiDAO.listKota();
					model.addAttribute("kota", kota);
					return "cari_kota";
				}
				else{
					return "error_kt_kc_kl";
				}
			}
			else{
				if(kl == null){
					return "error_kt_kc_kl";
				}
				else{
					return "error_kt_kc_kl";
				}
			}
		}
		else{
			if(kc == null){
				if(kl == null){
					String namaKota = lokasiDAO.namaKota(kt);
					model.addAttribute("namaKota", namaKota);
					model.addAttribute("idKota", kt);
					List<KecamatanModel> kecamatan = lokasiDAO.listKecamatan(kt);
					model.addAttribute("kecamatan", kecamatan);
					return "cari_kecamatan";
				}
				else{
					return "error_kt_kc_kl";
				}
			}
			else{
				if(kl == null){
					String namaKota = lokasiDAO.namaKota(kt);
					String namaKecamatan = lokasiDAO.namaKecamatan(kc);
					model.addAttribute("idKota", kt);
					model.addAttribute("idKecamatan", kc);
					model.addAttribute("namaKota", namaKota);
					model.addAttribute("namaKecamatan", namaKecamatan);
					List<KelurahanModel> kelurahan = lokasiDAO.listKelurahan(kc);
					model.addAttribute("kelurahan", kelurahan);
					return "cari_kelurahan";
				}
				else{
					String namaKota = lokasiDAO.namaKota(kt);
					String namaKecamatan = lokasiDAO.namaKecamatan(kc);
					String namaKelurahan = lokasiDAO.namaKelurahan(kl);
					model.addAttribute("namaKota", namaKota);
					model.addAttribute("namaKecamatan", namaKecamatan);
					model.addAttribute("namaKelurahan", namaKelurahan);
					List<PendudukModel> penduduk = pendudukDAO.cariPenduduk(kl);
					model.addAttribute("penduduk", penduduk);
					return "cari_penduduk";
				}	
			}		
		}
	}
	
}
