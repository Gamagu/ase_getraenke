package com.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.entities.Bestellung;
import com.example.entities.Kunde;
import com.example.entities.Lieferung;
import com.example.entities.Produkt;
import com.example.repositories.GetraenkeRepository;
import com.example.repositories.UserRepository;
import com.example.util.Pair;
import com.example.util.Triple;
import com.example.valueobjects.BestellungProdukt;
import com.example.valueobjects.Pfandwert;
import com.example.valueobjects.Preis;

class GetraenkeUsecases {
    final private GetraenkeRepository gRepo;
    //final private UserRepository uRepo;

    GetraenkeUsecases(GetraenkeRepository grepo, UserRepository urepo) {
        //this.uRepo =urepo;
        this.gRepo = grepo;
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
                gRepo.addLieferung(new Lieferung(prod.key(), prod.value(), lieferId));
            }
    }

    // soll npublic euen Pfandwert anlegen
     public void addPfandWert(String beschreibung, double wert) throws Exception{
        Pfandwert pfandwert = new Pfandwert(wert, beschreibung);
        gRepo.addPfandwert(pfandwert);
    }

    // neuen public Preis für Pfand setzen
     public Pfandwert setPfandwert(Pfandwert pfandwert, double newValue) throws Exception{
        Pfandwert newPfandwert = new Pfandwert(newValue, pfandwert.beschreibung);
        gRepo.addPfandwert(newPfandwert);
        return newPfandwert;
    }

    // setzt public Pfandwert für ein Produkt. UUIDs sind die IDs der jeweiligen
    // Produkte und der value des pairs, ist die anzahl des Pfandwertes.
     public void setPfandwertProdukt(Produkt produkt, Iterable<Pfandwert> pfandliste) throws Exception{
        produkt.setPfandAssignment(pfandliste, gRepo);
    }

    // for thpublic e next functions, the of pair is the identifier and the second
    // is a decription. UUID could be mapped to incrementing numbers.
    public Iterable<Pair<Pfandwert, String>> getAllPfandwerte() {
        return StreamSupport.stream(gRepo.getPfandwerte().spliterator(), false)
                .map(wert -> new Pair<>(wert, wert.toString())).collect(Collectors.toList());
    }

    public Pfandwert getPfandWert(UUID id) {
        return gRepo.getPfandwert(id);
    }

    public Iterable<Pair<Produkt, String>> getAllProducts() {
        return StreamSupport.stream(gRepo.getProdukte().spliterator(), false)
                .map(wert -> new Pair<>(wert, wert.toString())).collect(Collectors.toList());
    }

    public Preis getPriceForProdukt(Produkt product) {
        return product.getCurrentPreis();
    }

    public Iterable<Preis> getPriceHistoryForProdukt(Produkt product) {
        return gRepo.getPreisForProdukt(product);
    }

    public double setPriceForProdukt(Produkt product, double preis) {
        Preis p = new Preis(preis, product);
        product.setPreis(p, gRepo);
        return p.getPrice();
    }

    public int getStockAmountForProdukt(Produkt product) {
        return getAmountLieferungenForProdukt(product) - getOrderedAmountForProdukt(product);
    }

    public int getAmountLieferungenForProdukt(Produkt product) {
        return StreamSupport.stream(gRepo.getLieferungen(product).spliterator(), false)
                .mapToInt(lieferung -> lieferung.getMenge()).sum();
    }

    public int getOrderedAmountForProdukt(Produkt product) {
        return StreamSupport.stream(gRepo.getBestellungen().spliterator(), false).mapToInt(lieferung -> lieferung
                .getProdukte().filter(p -> p.getProdukt().equals(product)).mapToInt(p -> p.getMenge()).sum()).sum();
    }

     public void addProdukt(String name, String beschreibung, String kategorie, double preis) throws Exception{
         Produkt produkt = new Produkt(name, beschreibung, kategorie);
        Preis p = new Preis(preis, produkt);
        produkt.setPreis(p, gRepo);
    }

     public Produkt getProduct(UUID produktId) throws Exception{
        return gRepo.getProdukt(produktId);
    }

     public Bestellung addBestellung(Kunde kunde, Iterable<Triple<Produkt, Integer, Double>> produkte) throws Exception{
        ArrayList<BestellungProdukt> prodList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for(Triple<Produkt,Integer, Double> prod : produkte){
            Preis preis = gRepo.getPreis(prod.first(), prod.value(), now).orElse(null);
            if(preis == null){
                preis = new Preis(prod.number(), prod.first());
            gRepo.addPrice(preis);
            }
            prodList.add(new BestellungProdukt(prod.first(), preis, prod.value()));
        }
        return new Bestellung(kunde, now, prodList);
    }

}