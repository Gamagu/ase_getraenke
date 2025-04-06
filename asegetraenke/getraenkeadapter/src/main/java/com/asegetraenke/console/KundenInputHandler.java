package com.asegetraenke.console;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.asegetraenke.IKundenUsecases;
import com.asegetraenke.console.Utils.IConsoleUtils;
import com.asegetraenke.console.consolefunctionmapping.ICommand;
import com.asegetraenke.console.consolefunctionmapping.ICommandRegistrar;
import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;

public class KundenInputHandler {
    private final IKundenUsecases kundeUseCases;
    private final IConsoleUtils consoleUtils;
    
    public KundenInputHandler(IKundenUsecases kundeUseCases, IConsoleUtils consoleUtils, ICommandRegistrar registrar) {
        this.kundeUseCases = kundeUseCases;
        this.consoleUtils = consoleUtils;
        registrar.registerCommands(this);
    }

    @ICommand(value = "createkunde", category = "kunde")
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

    @ICommand(value = "getallkunden", category = "kunde")
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

    @ICommand(value = "setname", category = "kunde")
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


    @ICommand(value = "getkunde", category = "kunde")
    public void handleGetKundeInput() {
        String eMail = consoleUtils.readStringInputWithPrompt("E-Mail: ");
        Optional<Kunde> kunde = this.kundeUseCases.getKunde(eMail);
        if(kunde.isPresent()){
            return;
        }
        System.out.println("No Customer with the Mail: " + eMail + " was found.");
    }
    
    @ICommand(value = "getkundenbalance", category = "kunde")
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

    @ICommand(value = "getallbestellungen", category = "kunde")
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
}
