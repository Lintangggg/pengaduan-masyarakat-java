package com.smk.bi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smk.bi.model.Pengaduan;
import com.smk.bi.repository.PengaduanRepository;

import java.util.List;

@Service
public class PengaduanService {

    private final PengaduanRepository pengaduanRepository;

    @Autowired
    public PengaduanService(PengaduanRepository pengaduanRepository) {
        this.pengaduanRepository = pengaduanRepository;
    }

    public List<Pengaduan> getAllPengaduan() {
        return pengaduanRepository.findAll();
    }

    public Pengaduan getPengaduanById(Long id) {
        return pengaduanRepository.findById(id).orElse(null);
    }

    public Pengaduan savePengaduan(Pengaduan pengaduan) {
        return pengaduanRepository.save(pengaduan);
    }

    public void deletePengaduan(Long id) {
        pengaduanRepository.deleteById(id);
    }
    
    public List<Pengaduan> getPengaduanByMasyarakat(String nik) {
        return pengaduanRepository.findByNik(nik);
    }
}
