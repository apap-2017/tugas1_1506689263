package com.example.tugas1.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.service.KeluargaService;
import com.example.tugas1.service.LokasiService;

@Controller
public class KeluargaController {
	
	@Autowired
	KeluargaService keluargaDAO;
	
	@Autowired
	LokasiService lokasiDAO;
	
	@RequestMapping(value = "/keluarga", method = RequestMethod.GET)
	public String viewKeluargaByNKK(Model model, String nkk){
		if(nkk == ""){
			return "null";
		}
		KeluargaModel keluarga = keluargaDAO.viewKeluargaByNKK(nkk);
		
		if(keluarga != null){
			model.addAttribute("keluarga", keluarga);
            return "result_nkk";
		}
		else{
			model.addAttribute("nkk", nkk);
            return "not_found_nkk";
		}
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.GET)
	public String tambahKeluarga(Model model){
		model.addAttribute("keluarga", new KeluargaModel());
		return "tambah_keluarga";
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
	public String tambahKeluarga(@ModelAttribute KeluargaModel keluarga, Model model){
		String kode = keluargaDAO.generateNKK(keluarga.getNama_kecamatan());
		DateFormat sdfr = new SimpleDateFormat("ddMMyy");
	    Date dateobj = new Date();
	    String tanggal = sdfr.format(dateobj);
	    int id_kelurahan = lokasiDAO.idKelurahan(keluarga.getNama_kelurahan());
	    keluarga.setId_kelurahan(id_kelurahan);
	    String nkk_data = kode + tanggal;
	    List<KeluargaModel> check = keluargaDAO.check_nkk(nkk_data);
	    if(check.isEmpty()){
			keluarga.setNomor_kk(nkk_data + "0001");
		}
		if(!check.isEmpty()){
			int id = check.size() + 1;
			String index = String.format("%04d", id);
			keluarga.setNomor_kk(nkk_data + index);
		}
		keluargaDAO.addKeluarga(keluarga);
		model.addAttribute("keluarga", keluarga);
		return "result_tambah_keluarga";
	}
	
	@RequestMapping("/keluarga/ubah/{nkk}")
	public String ubahKeluarga(Model model, @PathVariable(value = "nkk") String nkk){
		KeluargaModel keluarga = keluargaDAO.dataKeluarga(nkk);
		if(keluarga != null){
			model.addAttribute("keluarga", keluarga);
			return "ubah_keluarga";
		}
		else{
			model.addAttribute("nkk", nkk);
			return "not_found_nkk";
		}
	}
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	public String ubahKeluarga(Model model, @ModelAttribute KeluargaModel keluarga){
		String old_nkk = keluarga.getNomor_kk();
		model.addAttribute("old_nkk", old_nkk);
		String kode = old_nkk.substring(0, 6);
		String old_namaKecamatan = lokasiDAO.old_namaKecamatan(kode+"0");
		int old_idKelurahan = lokasiDAO.old_idKelurahan(old_nkk);
		String old_namaKelurahan = lokasiDAO.old_namaKelurahan(old_idKelurahan);
		DateFormat sdfr = new SimpleDateFormat("ddMMyy");
	    Date dateobj = new Date();
	    String tanggal = sdfr.format(dateobj);
	    
	    if(!keluarga.getNama_kelurahan().equals(old_namaKelurahan)){
		    int new_idKelurahan = lokasiDAO.idKelurahan(keluarga.getNama_kelurahan());
		    keluarga.setId_kelurahan(new_idKelurahan);
	    }
	    else{
	    	keluarga.setId_kelurahan(old_idKelurahan);
	    }
	    
	    keluargaDAO.updateKeluarga(keluarga);
	    
	    if(!keluarga.getNama_kecamatan().equals(old_namaKecamatan)){
	    	kode = keluargaDAO.generateNKK(keluarga.getNama_kecamatan());
	    }
	    
	    String nkk_data = kode + tanggal;
	    List<KeluargaModel> check = keluargaDAO.check_nkk(nkk_data);
	    if(check.isEmpty()){
			keluarga.setNomor_kk(nkk_data + "0001");
		}
		if(!check.isEmpty()){
			int id = check.size() + 1;
			String index = String.format("%04d", id);
			keluarga.setNomor_kk(nkk_data + index);
		}
		keluargaDAO.changeNKK(old_nkk, keluarga.getNomor_kk());
	    return "result_ubah_keluarga";
	}
	
	
}
