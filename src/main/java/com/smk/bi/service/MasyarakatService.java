package com.smk.bi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smk.bi.model.Masyarakat;
import com.smk.bi.repository.MasyarakatRepository;

import java.util.List;

@Service
public class MasyarakatService {

    private final MasyarakatRepository masyarakatRepository;

    @Autowired
    public MasyarakatService(MasyarakatRepository masyarakatRepository) {
        this.masyarakatRepository = masyarakatRepository;
    }

    public List<Masyarakat> getAllMasyarakat() {
        return masyarakatRepository.findAll();
    }

    public Masyarakat getMasyarakatById(Long id) {
        return masyarakatRepository.findById(id).orElse(null);
    }

    public Masyarakat getMasyarakatByUsername(String username) {
        return masyarakatRepository.findByUsername(username);
    }

    public Masyarakat getMasyarakatByNik(String nik) {
        return masyarakatRepository.findByNik(nik);
    }

    public Masyarakat saveMasyarakat(Masyarakat masyarakat) {
        return masyarakatRepository.save(masyarakat);
    }

    public void deleteMasyarakat(Long id) {
        masyarakatRepository.deleteById(id);
    }
}
