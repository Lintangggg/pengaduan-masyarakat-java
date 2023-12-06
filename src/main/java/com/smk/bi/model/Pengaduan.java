package com.smk.bi.model;

import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "pengaduan")
@Data
public class Pengaduan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tgl_pengaduan", nullable = false, columnDefinition = "timestamp default current_timestamp()")
    private Timestamp tglPengaduan;

    @Column(name = "nik", nullable = false)
    private String nik;

    @Column(name = "judul", nullable = false)
    private String judul;

    @Column(name = "isi_laporan", nullable = false, columnDefinition = "text")
    private String isiLaporan;

    @Lob
    @Column(name = "foto", nullable = false, columnDefinition = "longblob")
    private byte[] foto;

    @Column(name = "status", nullable = false, columnDefinition = "tinyint default 0")
    private int status;
}
