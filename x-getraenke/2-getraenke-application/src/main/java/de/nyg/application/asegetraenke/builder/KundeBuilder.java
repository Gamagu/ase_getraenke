package de.nyg.application.asegetraenke.builder;

import de.nyg.domain.asegetraenke.entities.Kunde;

public class KundeBuilder {
    private String name;
    private String nachname;
    private String email;

    public KundeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public KundeBuilder withNachname(String nachname) {
        this.nachname = nachname;
        return this;
    }

    public KundeBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public Kunde build() {
        if (name == null || nachname == null || email == null) {
            throw new IllegalStateException("Name, Nachname und E-Mail dürfen nicht null sein");
        }
        return new Kunde(name, nachname, email);
    }
}

