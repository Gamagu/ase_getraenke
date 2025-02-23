package com.asegetraenke.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.asegetraenke.KundeUsecases;
import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;

public class KundenInputHandler {
    private final Scanner scanner;
    private final Map<String, Runnable> kundeCommandMap;
    private final KundeUsecases kundeUseCases;
    
    public KundenInputHandler(Scanner scanner, KundeUsecases kundeUseCases) {
        this.scanner = scanner;
        this.kundeCommandMap = initializeCommandMapKunde();
        this.kundeUseCases = kundeUseCases;
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

    private void handleCreateKundeInput() {
        System.out.println("Create Customer: ");
        String name = readStringInputWithPrompt("Name: ");
        String nachname = readStringInputWithPrompt("Nachname: ");    
        String eMail = readStringInputWithPrompt("E-Mail: ");
        if(acceptInput()){
            kundeUseCases.createKunde(name, nachname, eMail);
            System.out.println("Kunde was created succesfully");
        }
    }

    private void handleGetAllKundenInput() {
        Iterable<Kunde> kundenVec = this.kundeUseCases.getAllKunden();
        List<Kunde> kundenList = StreamSupport.stream(kundenVec.spliterator(), false)
                                 .collect(Collectors.toList());
        
        if(kundenList.isEmpty()){
            errorNoKunden();
            return;
        }
        int count = 1;
        for(Kunde kunde : kundenList){
            printKundeWithNumber(kunde,count);
            count++;
        }
    }

    private void handleSetNameInput() {
        Optional<Kunde> kundeOptional = pickOneUserFromAllUsers();
        if(!acceptInput()){
            errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        System.out.println(kunde.toString());
        String newFirstName = readStringInputWithPrompt("Please enter a new first Name: ");
        String newSecondName = readStringInputWithPrompt("Please enter a new first Name: ");
        this.kundeUseCases.setName(kunde, newFirstName, newSecondName);
        System.out.println("Name was succesfully changed");
    }

    private void handleGetKundeInput() {
        while(true){
            String eMail = readStringInputWithPrompt("Enter a EMail: ");
            Optional<Kunde> kunde = this.kundeUseCases.getKunde(eMail);
            if(kunde.isPresent()){
                break;
            }
            System.out.println("No Customer with the Mail: " + eMail + " was found.");
        }
    }

    private void handleGetKundenBalanceInput() {
        Optional<Kunde> kundeOptional = pickOneUserFromAllUsers();
        if(kundeOptional.isEmpty()){
            errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        double dBalance = this.kundeUseCases.getKundenBalance(kunde);
        System.out.println( kunde.toString()+ "\n"+ dBalance);
    }

    private void handleGetAllBestellungenInput() {
        Optional<Kunde> kundeOptional = pickOneUserFromAllUsers();
        if(kundeOptional.isEmpty()){
            errorNoKunden();
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

    private Optional<Kunde> pickOneUserFromAllUsers(){
        Iterable<Kunde> kundenOptVec = this.kundeUseCases.getAllKunden();
        List<Kunde> kundenList = new ArrayList<Kunde>();
        kundenList = StreamSupport.stream(kundenOptVec.spliterator(), false).collect(Collectors.toList());
        int count = 1;
        for(Kunde kunde : kundenList) {
            printKundeWithNumber(kunde, count);
            kundenList.add(kunde);
            
        }
        int indexCustomer = 0;
        while (true) {
            indexCustomer = readIntInputWithPrompt("Which Customername do you want to change? Enter the Number: ");
            if(indexCustomer < count && indexCustomer > 0){
                break;
            }
            System.out.println("Something went wrong the "+ indexCustomer +  " is not in the list");
        }
        return Optional.of(kundenList.get(indexCustomer-1));
    }

    private String readStringInputWithPrompt(String prompt){
        System.out.print(prompt);
        return this.scanner.nextLine();
    }

    private int readIntInputWithPrompt(String prompt){
        System.out.print(prompt);
        while(true){
            String input = this.scanner.nextLine();
            try{
                int inputCastInt = Integer.parseInt(input);
                return inputCastInt;
            }catch(Exception e){
                System.out.println("The input: "+ input+ " can not be translated into a number");
            }
        }
    }

    private Boolean acceptInput(){
        while(true){
            System.out.print("Finish process yes[y] / no[n]");
            String input = this.scanner.nextLine();
            input = input.toLowerCase();
            if(input.equals("y")){
                return true;
            }
            if(input.equals("n")){
                System.out.println("Process was aborted");
                return false;
            }
        }
    }

    private void printKundeWithNumber(Kunde kunde, int number){
        System.out.println(number + ". "+ kunde.toString());
    } 
    
    private void errorNoKunden() {
        System.out.println("There are no Customer/s found");
    }
}
