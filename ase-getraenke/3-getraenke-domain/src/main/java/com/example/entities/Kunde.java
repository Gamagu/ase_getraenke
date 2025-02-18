package com.example.entities;

import java.util.UUID;

import com.example.util.EntityWrapper;

public class Kunde extends EntityWrapper<Kunde>{
    UUID id;
    String name;
    String nachname;
    String mail;
}
