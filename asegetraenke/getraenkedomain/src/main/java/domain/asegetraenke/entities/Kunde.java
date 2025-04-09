package domain.asegetraenke.entities;

import domain.asegetraenke.util.EntityWrapper;

public class Kunde extends EntityWrapper<Kunde>{
    String name;
    String nachname;
    String mail;

    public Kunde(String name, String nachname, String mail){
        super();
        this.name = name;
        this.nachname = nachname;
        this.mail = mail;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s",this.name, this.nachname, this.mail);
    }
    
    public void setName(String name, String nachname){
        this.name =name;
        this.nachname = nachname;
    }

    public String getMail(){
        return mail;
    }
}
