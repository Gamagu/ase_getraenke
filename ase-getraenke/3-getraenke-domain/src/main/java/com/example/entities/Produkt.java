package com.example.entities;
import com.example.repositories.GetraenkeRepository;
import com.example.util.EntityWrapper;
import com.example.valueobjects.Preis;
public class Produkt extends EntityWrapper<Produkt> implements Preis.Priced{
    private String name;
    private String beschreibung;
    private String kategorie;
    private Preis preis;

    Iterable<Pfandwert> pfand;
    public Preis getCurrentPreis(){
        return preis;
    }

    public void setPreis(Preis preis, GetraenkeRepository repo){
        this.preis = preis;
        repo.addPrice(preis);
    }
}
