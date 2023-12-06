package com.smk.bi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smk.bi.model.Tanggapan;
import com.smk.bi.repository.TanggapanRepository;

import java.util.List;

@Service
public class TanggapanService {

    private final TanggapanRepository tanggapanRepository;

    @Autowired
    public TanggapanService(TanggapanRepository tanggapanRepository) {
        this.tanggapanRepository = tanggapanRepository;
    }

    public List<Tanggapan> getAllTanggapan() {
        return tanggapanRepository.findAll();
    }

    public Tanggapan getTanggapanById(Long id) {
        return tanggapanRepository.findById(id).orElse(null);
    }

    public Tanggapan saveTanggapan(Tanggapan tanggapan) {
        return tanggapanRepository.save(tanggapan);
    }

    public void deleteTanggapan(Long id) {
        tanggapanRepository.deleteById(id);
    }
    
    public List<Tanggapan> getTanggapanByPengaduan(Long idPengaduan) {
        return tanggapanRepository.findByPengaduan_Id(idPengaduan);
    }
}
