package de.nyg.application.asegetraenke;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import de.nyg.domain.asegetraenke.entities.Bestellung;
import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Lieferung;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.repositories.CustomerRepository;
import de.nyg.domain.asegetraenke.repositories.GetraenkeRepository;
import de.nyg.domain.asegetraenke.util.Pair;
import de.nyg.domain.asegetraenke.util.Triple;
import de.nyg.domain.asegetraenke.valueobjects.BestellungProdukt;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;
import de.nyg.domain.asegetraenke.valueobjects.Preis;


public class GetraenkeUsecases{
    final private GetraenkeRepository grepo;
    //final private CustomerRepository crepo;
    public GetraenkeUsecases(GetraenkeRepository grepo, CustomerRepository crepo){
        this.grepo = grepo;
        //this.crepo = crepo;
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
                grepo.addLieferung(new Lieferung(prod.key(), prod.value(), lieferId));
            }
    }

    // soll npublic euen Pfandwert anlegen
     public void addPfandWert(String beschreibung, double wert) throws Exception{
        Pfandwert pfandwert = new Pfandwert(wert, beschreibung);
        grepo.addPfandwert(pfandwert);
    }

    // neuen public Preis für Pfand setzen
     public Pfandwert setPfandwert(Pfandwert pfandwert, double newValue) throws Exception{
        Pfandwert newPfandwert = new Pfandwert(newValue, pfandwert.beschreibung);
        grepo.addPfandwert(newPfandwert);
        for (Produkt p : grepo.getProdukte()){
            Iterable<Pfandwert> pfandwerte = p.getPfandwert();
            if(StreamSupport.stream((pfandwerte.spliterator()),false).filter(x -> x.getId() == pfandwert.getId()).count() > 0){
                ArrayList<Pfandwert> newPfandwerte = new ArrayList<>();
                for(Pfandwert pf: pfandwerte){
                    if(pf.getId() == pfandwert.getId()){
                        newPfandwerte.add(newPfandwert);
                    }else {
                        newPfandwerte.add(pf);
                    }
                }
                p.setPfandAssignment(newPfandwerte, grepo);
            }
        }
        return newPfandwert;
    }

    // setzt public Pfandwert für ein Produkt. UUIDs sind die IDs der jeweiligen
    // Produkte und der value des pairs, ist die anzahl des Pfandwertes.
     public void setPfandwertProdukt(Produkt produkt, Iterable<Pfandwert> pfandliste) throws Exception{
        produkt.setPfandAssignment(pfandliste, grepo);
    }

    // for thpublic e next functions, the of pair is the identifier and the second
    // is a decription. UUID could be mapped to incrementing numbers.
    public Iterable<Pfandwert> getAllPfandwerte() {
        return StreamSupport.stream(grepo.getPfandwerte().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Pfandwert> getPfandWert(UUID id) {
        return grepo.getPfandwert(id);
    }

    public Iterable<Produkt> getAllProducts() {
        return StreamSupport.stream(grepo.getProdukte().spliterator(), false)
               .collect(Collectors.toList());
    }

    public Preis getPriceForProdukt(Produkt product) {
        return product.getCurrentPreis();
    }

    public Iterable<Preis> getPriceHistoryForProdukt(Produkt product) {
        return grepo.getPreisForProdukt(product);
    }

    public double setPriceForProdukt(Produkt product, double preis) {
        Preis p = new Preis(preis, product);
        grepo.addPrice(p);
        product.setPreis(p, grepo);
        return p.getPrice();
    }

    public int getStockAmountForProdukt(Produkt product) {
        return getAmountLieferungenForProdukt(product) - getOrderedAmountForProdukt(product);
    }

    public int getAmountLieferungenForProdukt(Produkt product) {
        return StreamSupport.stream(grepo.getLieferungen(product).spliterator(), false)
                .mapToInt(lieferung -> lieferung.getMenge()).sum();
    }

    public int getOrderedAmountForProdukt(Produkt product) {
        return StreamSupport.stream(grepo.getBestellungen().spliterator(), false).mapToInt(lieferung -> lieferung
                .getProdukte().filter(p -> p.getProdukt().equals(product)).mapToInt(p -> p.getMenge()).sum()).sum();
    }

    public void addProdukt(Produkt produkt, double preis) throws Exception{
        Preis p = new Preis(preis, produkt);
        grepo.addProdukt(produkt);
        grepo.addPrice(p);
        produkt.setPreis(p, grepo);
    }

     public Optional<Produkt> getProduct(UUID produktId) throws Exception{
        return grepo.getProdukt(produktId);
    }

     public Bestellung addBestellung(Kunde kunde, Iterable<Triple<Produkt, Integer, Double>> produkte) throws Exception{
         LocalDateTime now = LocalDateTime.now();
        ArrayList<BestellungProdukt> prodList =parseBestellungsProdukte(produkte, now);
        Bestellung b = new Bestellung(kunde, now, prodList);
        grepo.addBestellung(b);
        return b;
    }

    private ArrayList<BestellungProdukt> parseBestellungsProdukte(Iterable<Triple<Produkt, Integer, Double>> produkte, LocalDateTime timestamp) {
        ArrayList<BestellungProdukt> prodList = new ArrayList<>();
        for(Triple<Produkt,Integer, Double> prod : produkte){
            Preis preis = grepo.getPreis(prod.first(), prod.value(), timestamp).orElse(null);
            if(preis == null){
                preis = new Preis(prod.number(), prod.first());
                grepo.addPrice(preis);
            }
            prodList.add(new BestellungProdukt(prod.first(), preis, prod.value()));
        }
 
        return prodList;
    }
}
