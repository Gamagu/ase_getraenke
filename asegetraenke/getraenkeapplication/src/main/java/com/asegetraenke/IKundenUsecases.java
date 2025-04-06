package com.asegetraenke;
import java.time.LocalDateTime;
import java.util.Optional;

import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Zahlungsvorgang;

public interface IKundenUsecases {

Kunde createKunde(String name, String nachname, String eMail);

Iterable<Kunde> getAllKunden();

void setName(Kunde kunde, String name, String nachname);

Optional<Kunde> getKunde(String eMail);

double getKundenBalance(Kunde kunde);

double getKundenOrderSum(Kunde kunde);

double getKundenZahlungenSum(Kunde kunde);

Iterable<Zahlungsvorgang> getAllZahlungen(Kunde kunde);

Iterable<Bestellung> getAllBestellungen(Kunde kunde);

Zahlungsvorgang addZahlungsvorgang(Kunde kunde, String zahlungsweg, double betrag, LocalDateTime date);
}
