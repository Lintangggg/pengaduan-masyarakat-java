package com.smk.bi.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "masyarakat")
@Data
public class Masyarakat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik", nullable = false, unique = true)
    private String nik;

    @Column(name = "nama", nullable = false)
    private String nama;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "telp", nullable = false)
    private String telp;
}

