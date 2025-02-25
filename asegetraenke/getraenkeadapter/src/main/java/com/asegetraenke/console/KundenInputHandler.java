package com.asegetraenke.console;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.asegetraenke.kundeusecases;
import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;

public class KundenInputHandler {
    private final Map<String, Runnable> kundeCommandMap;
    private final kundeusecases kundeUseCases;
    private final ConsoleUtils consoleUtils;
    
    public KundenInputHandler(kundeusecases kundeUseCases, ConsoleUtils consoleUtils) {
        this.kundeCommandMap = initializeCommandMapKunde();
        this.kundeUseCases = kundeUseCases;
        this.consoleUtils = consoleUtils;
    }

    private Map<String, Runnable> initializeCommandMapKunde() {
        Map<String, Runnable> map = new HashMap<>();
        map.put("createkunde", () -> handleCreateKundeInput());
        map.put("getallkunden", () -> handleGetAllKundenInput());
        map.put("setname", () -> handleSetNameInput());
        map.put("getkunde", () -> handleGetKundeInput());
        map.put("getkundenbalance", () -> handleGetKundenBalanceInput());
        map.put("getallbestellungen", () -> handleGetAllBestellungenInput());
        return map;
    }

    public void handleCreateKundeInput() {
        System.out.println("Create Customer: ");
        String name = consoleUtils.readStringInputWithPrompt("Name: ");
        String nachname = consoleUtils.readStringInputWithPrompt("Nachname: ");    
        String eMail = consoleUtils.readStringInputWithPrompt("E-Mail: ");
        if(consoleUtils.acceptInput()){
            kundeUseCases.createKunde(name, nachname, eMail);
            System.out.println("Kunde was created succesfully");
        }
    }

    public void handleGetAllKundenInput() {
        Iterable<Kunde> kundenVec = this.kundeUseCases.getAllKunden();
        List<Kunde> kundenList = StreamSupport.stream(kundenVec.spliterator(), false)
                                 .collect(Collectors.toList());
        
        if(kundenList.isEmpty()){
            consoleUtils.errorNoKunden();
            return;
        }
        int count = 1;
        for(Kunde kunde : kundenList){
            consoleUtils.printKundeWithNumber(kunde,count);
            count++;
        }
    }

    public void handleSetNameInput() {
        Optional<Kunde> kundeOptional = consoleUtils.pickOneUserFromAllUsers();
        if(kundeOptional.isEmpty()){
            consoleUtils.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        System.out.println(kunde.toString());
        String newFirstName = consoleUtils.readStringInputWithPrompt("Name: ");
        String newSecondName = consoleUtils.readStringInputWithPrompt("Nachname: ");
        if(!consoleUtils.acceptInput()){
            return;
        }
        this.kundeUseCases.setName(kunde, newFirstName, newSecondName);
        System.out.println("Name was succesfully changed");
    }

    //TODO Keine Leave Kondition
    public void handleGetKundeInput() {
        while(true){
            String eMail = consoleUtils.readStringInputWithPrompt("E-Mail: ");
            Optional<Kunde> kunde = this.kundeUseCases.getKunde(eMail);
            if(kunde.isPresent()){
                break;
            }
            System.out.println("No Customer with the Mail: " + eMail + " was found.");
        }
    }

    public void handleGetKundenBalanceInput() {
        Optional<Kunde> kundeOptional = consoleUtils.pickOneUserFromAllUsers();
        if(kundeOptional.isEmpty()){
            consoleUtils.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        double dBalance = this.kundeUseCases.getKundenBalance(kunde);
        System.out.println( kunde.toString()+ "\n"+ dBalance);
    }

    public void handleGetAllBestellungenInput() {
        Optional<Kunde> kundeOptional = consoleUtils.pickOneUserFromAllUsers();
        if(kundeOptional.isEmpty()){
            consoleUtils.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        Iterable<Bestellung> bestellungsVec = this.kundeUseCases.getAllBestellungen(kunde);
        for(Bestellung bestellung : bestellungsVec){
            System.out.println(bestellung.toString());
        }
    }
    public Map<String, Runnable> getKundeCommandMap() {
        return kundeCommandMap;
    }
}
