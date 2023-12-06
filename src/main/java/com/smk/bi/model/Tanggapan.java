package com.smk.bi.model;

import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tanggapan")
@Data
public class Tanggapan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pengaduan", nullable = false)
    private Pengaduan pengaduan;

    @Column(name = "tgl_tanggapan", nullable = false, columnDefinition = "timestamp default current_timestamp()")
    private Timestamp tglTanggapan;

    @Column(name = "tanggapan", nullable = false, columnDefinition = "text")
    private String tanggapan;

    @ManyToOne
    @JoinColumn(name = "id_petugas", nullable = false)
    private User petugas;
}
