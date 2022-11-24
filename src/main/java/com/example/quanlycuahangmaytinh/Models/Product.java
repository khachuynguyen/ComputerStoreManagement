package com.example.quanlycuahangmaytinh.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private long price;
    private String cpu;
    private String ram;
    @Column(name = "hard_drive")
    private String hardDrive;
    private String screen;
    @Column(name = "graphic_card")
    private String graphicCard;
    private String connector;
    private String design;
    @Column(name = "release_time")
    private String releaseTime;
    @Column(name="avatar")
    private String avatar;
}
