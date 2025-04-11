package de.nyg.application.asegetraenke.aggregate;

import java.util.ArrayList;

import de.nyg.application.asegetraenke.KundenUsecases;
import de.nyg.domain.asegetraenke.entities.Bestellung;
import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Zahlungsvorgang;

public class CustomerDashboard {
    public final Kunde kunde;
    public final ArrayList<Bestellung> bestellungen;
    public  final double gezahlt;
    public final double warenwert;
    public final ArrayList<Zahlungsvorgang> zahlungsvorgaenge;

    public CustomerDashboard(Kunde kunde, KundenUsecases kusecases){
        this.kunde = kunde;
        this.bestellungen = new ArrayList<Bestellung>();
        for(Bestellung b : kusecases.getAllBestellungen(kunde)){
            this.bestellungen.add(b);
        }
        zahlungsvorgaenge = new ArrayList<Zahlungsvorgang>();
        for(Zahlungsvorgang z : kusecases.getAllZahlungen(kunde)){
            this.zahlungsvorgaenge.add(z);
        }
        this.gezahlt = kusecases.getKundenZahlungenSum(kunde);
        this.warenwert = kusecases.getKundenOrderSum(kunde);
    }
    
}
