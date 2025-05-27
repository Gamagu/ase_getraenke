package de.nyg.adapters.asegetraenke.console;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import de.nyg.application.asegetraenke.KundenUsecases;
import de.nyg.adapters.asegetraenke.console.consolefunctionmapping.CommandRegistrar;
import de.nyg.adapters.asegetraenke.console.consolefunctionmapping.ICommand;
import de.nyg.adapters.asegetraenke.console.utils.ConsoleError;
import de.nyg.adapters.asegetraenke.console.utils.ConsolePrinter;
import de.nyg.adapters.asegetraenke.console.utils.ConsoleReader;
import de.nyg.adapters.asegetraenke.console.utils.EntityPicker;
import de.nyg.domain.asegetraenke.entities.Bestellung;
import de.nyg.domain.asegetraenke.entities.Kunde;

public class KundenInputHandler {
    private final KundenUsecases kundeUseCases;
    private final ConsoleError consoleError;
    private final ConsoleReader consoleReader;
    private final ConsolePrinter consolePrinter;
    private final EntityPicker<Kunde> kundenPicker;
    
    public KundenInputHandler(KundenUsecases kundeUseCases, ConsoleError consoleError, CommandRegistrar registrar, EntityPicker<Kunde> kundenPicker, ConsoleReader consoleReader, ConsolePrinter consolePrinter) {
    this.kundeUseCases = kundeUseCases;
        this.consoleError = consoleError;
        this.kundenPicker = kundenPicker;
        this.consoleReader = consoleReader;
        this.consolePrinter = consolePrinter;
        registrar.registerCommands(this);
    }

    @ICommand(value = "createkunde", category = "kunde")
    public void handleCreateKundeInput() {
        System.out.println("Create Customer: ");
        String name = consoleReader.readStringInputWithPrompt("Name: ");
        String nachname = consoleReader.readStringInputWithPrompt("Nachname: ");    
        String eMail = consoleReader.readStringInputWithPrompt("E-Mail: ");
        if(consoleReader.acceptInput()){
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
            consoleError.errorNoKunden();
            return;
        }
        int count = 1;
        for(Kunde kunde : kundenList){
            consolePrinter.printKundeWithNumber(kunde,count);
            count++;
        }
    }

    @ICommand(value = "setname", category = "kunde")
    public void handleSetNameInput() {
        Iterable<Kunde> iterable = kundeUseCases.getAllKunden();
        List<Kunde> kundenListe = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
        Optional<Kunde> kundeOptional = kundenPicker.pickOneFromList(kundenListe, Kunde::toString);
        if(kundeOptional.isEmpty()){
            consoleError.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        System.out.println(kunde.toString());
        String newFirstName = consoleReader.readStringInputWithPrompt("Name: ");
        String newSecondName = consoleReader.readStringInputWithPrompt("Nachname: ");
        if(!consoleReader.acceptInput()){
            return;
        }
        this.kundeUseCases.setName(kunde, newFirstName, newSecondName);
        System.out.println("Name was succesfully changed");
    }

    @ICommand(value = "getkunde", category = "kunde")
    public void handleGetKundeInput() {
        String eMail = consoleReader.readStringInputWithPrompt("E-Mail: ");
        Optional<Kunde> kunde = this.kundeUseCases.getKunde(eMail);
        if(kunde.isPresent()){
            return;
        }
        System.out.println("No Customer with the Mail: " + eMail + " was found.");
    }
    
    @ICommand(value = "getkundenbalance", category = "kunde")
    public void handleGetKundenBalanceInput() {
        Iterable<Kunde> iterable = kundeUseCases.getAllKunden();
        List<Kunde> kundenListe = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
        Optional<Kunde> kundeOptional = kundenPicker.pickOneFromList(kundenListe, Kunde::toString);
        if(kundeOptional.isEmpty()){
            consoleError.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        double dBalance = this.kundeUseCases.getKundenBalance(kunde);
        System.out.println( kunde.toString()+ "\n"+ dBalance);
    }

    @ICommand(value = "getallbestellungen", category = "kunde")
    public void handleGetAllBestellungenInput() {
        Iterable<Kunde> iterable = kundeUseCases.getAllKunden();
        List<Kunde> kundenListe = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
        Optional<Kunde> kundeOptional = kundenPicker.pickOneFromList(kundenListe, Kunde::toString);
        if(kundeOptional.isEmpty()){
            consoleError.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        Iterable<Bestellung> bestellungsVec = this.kundeUseCases.getAllBestellungen(kunde);
        for(Bestellung bestellung : bestellungsVec){
            System.out.println(bestellung.toString());
        }
    }
}
