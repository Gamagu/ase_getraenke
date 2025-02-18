package com.example.entities;

import java.time.LocalDateTime;

import com.example.util.EntityWrapper;

public class Zahlungsvorgang extends EntityWrapper<Zahlungsvorgang>{
    Kunde kunde;
    String zahlungsweg;
    double betrag;
    LocalDateTime date;
}
