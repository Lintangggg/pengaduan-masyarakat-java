package com.smk.bi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smk.bi.model.Tanggapan;

public interface TanggapanRepository extends JpaRepository<Tanggapan, Long> {
    // You can add custom queries if needed
    List<Tanggapan> findByPengaduan_Id(Long idPengaduan);
}
