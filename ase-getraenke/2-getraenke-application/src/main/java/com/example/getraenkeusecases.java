package com.example;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.entities.Bestellung;
import com.example.entities.Kunde;
import com.example.entities.Pfandwert;
import com.example.entities.Produkt;
import com.example.entities.Zahlungsvorgang;
import com.example.repositories.GetraenkeRepository;
import com.example.util.Pair;
import com.example.valueobjects.BestellungProdukt;
import com.example.valueobjects.Preis;

class GetraenkeUsecases {
    private final GetraenkeRepository repo;

    GetraenkeUsecases(GetraenkeRepository repo) {
        this.repo = repo;
    }

    /**
     * 
     * @param produkte Liste der Produkt Ids und deren Mengen
     * @param date     Zeitpunkt der Lieferung
          * @throws Exception 
          */
         public void acceptLieferung(Iterable<Pair<Produkt, Integer>> produkte, LocalDateTime date) throws Exception{
        throw new Exception("Not Implemented");
    }

    // soll npublic euen Pfandwert anlegen
     public void addPfandWert(String beschreibung, double wert) throws Exception{
        throw new Exception("Not Implemented");
    }

    // neuen public Preis für Pfand setzen
     public void setPfandwert(Pfandwert pfandwert) throws Exception{
        throw new Exception("Not Implemented");
    }

    // setzt public Pfandwert für ein Produkt. UUIDs sind die IDs der jeweiligen
    // Produkte und der value des pairs, ist die anzahl des Pfandwertes.
     public void setPfandwertProdukt(Produkt produkt, Iterable<Pair<Pfandwert, Integer>> pfandliste) throws Exception{
        throw new Exception("Not Implemented");
    }

    // for thpublic e next functions, the of pair is the identifier and the second
    // is a decription. UUID could be mapped to incrementing numbers.
    public Iterable<Pair<Pfandwert, String>> getAllPfandwerte() {
        return StreamSupport.stream(repo.getPfandwerte().spliterator(), false)
                .map(wert -> new Pair<>(wert, wert.toString())).collect(Collectors.toList());
    }

    public Pfandwert getPfandWert(UUID id) {
        return repo.getPfandwert(id);
    }

    public Iterable<Pair<Produkt, String>> getAllProducts() {
        return StreamSupport.stream(repo.getProdukte().spliterator(), false)
                .map(wert -> new Pair<>(wert, wert.toString())).collect(Collectors.toList());
    }

    public Preis getPriceForProdukt(Produkt product) {
        return product.getCurrentPreis();
    }

    public Iterable<Preis> getPriceHistoryForProdukt(Produkt product) {
        return repo.getPreisForProdukt(product);
    }

    public double setPriceForProdukt(Produkt product, double preis) {
        Preis p = new Preis(preis, product);
        product.setPreis(p, repo);
        return p.getPrice();
    }

    public int getStockAmountForProdukt(Produkt product) {
        return getAmountLieferungenForProdukt(product) - getOrderedAmountForProdukt(product);
    }

    public int getAmountLieferungenForProdukt(Produkt product) {
        return StreamSupport.stream(repo.getLieferungen(product).spliterator(), false)
                .mapToInt(lieferung -> lieferung.getMenge()).sum();
    }

    public int getOrderedAmountForProdukt(Produkt product) {
        return StreamSupport.stream(repo.getBestellungen().spliterator(), false).mapToInt(lieferung -> lieferung
                .getProdukte().filter(p -> p.getProdukt().equals(product)).mapToInt(p -> p.getMenge()).sum()).sum();
    }

     public void addProdukt(String Name, String beschreibung, String kategorie, double preis) throws Exception{
        throw new Exception("Not Implemented");
    }

     public Produkt getProduct(UUID produktId) throws Exception{
        throw new Exception("Not Implemented");
    }

     public Bestellung addBestellung(Kunde kunde, Iterable<Pair<Produkt, Integer>> produkte) throws Exception{
        throw new Exception("Not Implemented");
    }

     public Zahlungsvorgang addZahlungsvorgang(Kunde kunde, String zahlungsweg, double betrag,
            LocalDateTime date) throws Exception{
                throw new Exception("Not Implemented");
            }

}