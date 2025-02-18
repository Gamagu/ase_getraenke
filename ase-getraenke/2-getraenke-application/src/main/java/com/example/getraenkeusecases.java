package com.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.entities.Bestellung;
import com.example.entities.Kunde;
import com.example.entities.Lieferung;
import com.example.entities.Produkt;
import com.example.entities.Zahlungsvorgang;
import com.example.repositories.GetraenkeRepository;
import com.example.util.Pair;
import com.example.util.Triple;
import com.example.valueobjects.BestellungProdukt;
import com.example.valueobjects.Pfandwert;
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
            int lieferId = Lieferung.counter.incrementAndGet();
            for(Pair<Produkt,Integer> prod : produkte){
                repo.addLieferung(new Lieferung(prod.key(), prod.value(), lieferId));
            }
    }

    // soll npublic euen Pfandwert anlegen
     public void addPfandWert(String beschreibung, double wert) throws Exception{
        Pfandwert pfandwert = new Pfandwert(wert, beschreibung);
        repo.addPfandwert(pfandwert);
    }

    // neuen public Preis für Pfand setzen
     public Pfandwert setPfandwert(Pfandwert pfandwert, double newValue) throws Exception{
        Pfandwert newPfandwert = new Pfandwert(newValue, pfandwert.beschreibung);
        repo.addPfandwert(newPfandwert);
        return newPfandwert;
    }

    // setzt public Pfandwert für ein Produkt. UUIDs sind die IDs der jeweiligen
    // Produkte und der value des pairs, ist die anzahl des Pfandwertes.
     public void setPfandwertProdukt(Produkt produkt, Iterable<Pfandwert> pfandliste) throws Exception{
        produkt.setPfandAssignment(pfandliste, repo);
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

     public void addProdukt(String name, String beschreibung, String kategorie, double preis) throws Exception{
         Produkt produkt = new Produkt(name, beschreibung, kategorie);
        Preis p = new Preis(preis, produkt);
        produkt.setPreis(p, repo);
    }

     public Produkt getProduct(UUID produktId) throws Exception{
        return repo.getProdukt(produktId);
    }

     public Bestellung addBestellung(Kunde kunde, Iterable<Triple<Produkt, Integer, Double>> produkte) throws Exception{
        ArrayList<BestellungProdukt> prodList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for(Triple<Produkt,Integer, Double> prod : produkte){
            Preis preis = repo.getPreis(prod.first(), prod.value(), now).orElse(null);
            if(preis == null){
                preis = new Preis(prod.number(), prod.first());
                repo.addPrice(preis);
            }
            prodList.add(new BestellungProdukt(prod.first(), preis, prod.value()));
        }
        return new Bestellung(kunde, now, prodList);
    }

     public Zahlungsvorgang addZahlungsvorgang(Kunde kunde, String zahlungsweg, double betrag,
            LocalDateTime date) throws Exception{
                Zahlungsvorgang z = new Zahlungsvorgang(kunde, zahlungsweg,betrag, date);
                repo.addZahlungsVorgang(z);
                return z;
            }

}