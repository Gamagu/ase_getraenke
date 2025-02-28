package com.asegetraenke.aggregate;

import java.util.ArrayList;

import com.asegetraenke.KundenUsecases;
import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Zahlungsvorgang;

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
