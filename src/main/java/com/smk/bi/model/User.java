package com.smk.bi.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_petugas", nullable = false)
    private String namaPetugas;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "telp", nullable = false)
    private String telp;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private UserRole level = UserRole.petugas; // Default role is petugas
}
