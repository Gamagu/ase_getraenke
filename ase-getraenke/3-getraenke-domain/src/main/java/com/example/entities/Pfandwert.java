package com.example.entities;

import java.util.UUID;

import com.example.util.EntityWrapper;

public class Pfandwert extends EntityWrapper<Pfandwert> {
    UUID id;
    double wert;
    String beschreibung;
}
