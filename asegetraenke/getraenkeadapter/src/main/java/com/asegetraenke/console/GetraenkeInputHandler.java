package com.asegetraenke.console;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Produkt;
import com.asegetraenke.util.Triple;
import com.asegetraenke.valueobjects.Pfandwert;
import com.asegetraenke.valueobjects.Preis;
import com.asegetraenke.GetraenkeUsecases;
import com.asegetraenke.KundenUsecases;

public class GetraenkeInputHandler {
    private final String NOPRODUKTMESSAGE = "There are no Product/s found";
    private final String NOPFANDWERTMESSAGE = "There are no Pfandwert/s found";

    private final Scanner scanner;
    private final Map<String,Runnable> getrankeCommandMap;
    private final GetraenkeUsecases getraenkeusecases;
    private final KundenUsecases kundeUseCases;

    public GetraenkeInputHandler(Scanner sc, GetraenkeUsecases getraenkeusecases, KundenUsecases kundeusecases){
        this.scanner = sc;
        this.getrankeCommandMap = initializeCommandMapGetraenke();
        this.getraenkeusecases = getraenkeusecases;
        this.kundeUseCases = kundeusecases;
    }

    private Map<String, Runnable> initializeCommandMapGetraenke() {
        Map<String, Runnable> map = new HashMap<>();
        map.put("acceptlieferung", () -> handleAcceptLieferungInput());
        map.put("addpfandwert", () -> handleAddPfandWertInput());
        map.put("setpfandwert", () -> handleSetPfandwertInput());
        map.put("setpfandwertprodukt", () -> handleSetPfandwertProduktInput());
        map.put("getallpfandwerte", () -> handleGetAllPfandwerteInput());
        map.put("getpfandwert", () -> handleGetPfandWertInput());
        map.put("getallproducts", () -> handleGetAllProductsInput());
        map.put("getpriceforprodukt", () -> handleGetPriceForProduktInput());
        map.put("getpricehistoryforprodukt", () -> handleGetPriceHistoryForProduktInput());
        map.put("setpriceforprodukt", () -> handleSetPriceForProduktInput());
        map.put("getstockamountforprodukt", () -> handleGetStockAmountForProduktInput());
        map.put("addprodukt", () -> handleAddProduktInput());
        map.put("getproduct", () -> handleGetProductInput());
        map.put("addbestellung", () -> handleAddBestellungInput());
        map.put("addzahlungsvorgang", () -> handleAddZahlungsvorgangInput());
        return map;
    }

    //TODO Ask Nikals what he was thinking here 
    public void handleAcceptLieferungInput() {

    }

    public void handleAddPfandWertInput() {
        System.out.println("Add new Pfand value to existing");
        String description = readStringInputWithPrompt("Description: ");
        Double value = readDoubleInputWithPrompt("Value: ");
        if(!acceptInput()){
            return;
        }
        try {
            getraenkeusecases.addPfandWert(description, value);
        } catch (Exception e) {
            System.out.println("An Error Occurt while storing the new Pfandwert: \n"+ 
                                "Value: " + value +"\n"+
                                "Description: "+ description);
            System.err.println(e.getMessage());
        }
    }

    public void handleSetPfandwertInput() {
        Optional<Pfandwert> pfandwerOptional = pickOnePfandwertFromAllPfandwerts();
        if(pfandwerOptional.isEmpty()){
            errorNoPfandWert();
            return;
        }
        Pfandwert pfandwert = pfandwerOptional.get();
        double newValue = readDoubleInputWithPrompt("New Value for Pfandwert: ");
        if(!acceptInput()){
            return;
        }
        try {
            getraenkeusecases.setPfandwert(pfandwert, newValue);
        } catch (Exception e) {
            System.out.println("An Error Occurt while setting the new Pfandwert: \n"+ 
            "Pfandwert: " + pfandwert.toString() +"\n"+
            "New Value: "+ newValue);
            System.err.println(e.getMessage());
        }
    }
    
    public void handleSetPfandwertProduktInput() {
        Optional<Pfandwert> pfandwertOptional = pickOnePfandwertFromAllPfandwerts();
        if (pfandwertOptional.isEmpty()) {
            errorNoPfandWert();
            return;
        }
        Pfandwert pfandwert = pfandwertOptional.get();

        Optional<Produkt> produktOptional = pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        Iterable<Pfandwert> pfandwertVec = Collections.singleton(pfandwert);
        try {
            getraenkeusecases.setPfandwertProdukt(produkt, pfandwertVec);
        } catch (Exception e) {
            System.out.println("An Error Occurt while storing the : \n"+ 
            "Pfandwert: " + pfandwert.toString() +"\n"+
            "Product: "+ produkt.toString());
            System.err.println(e.getMessage());
        }
    }

    public void handleGetAllPfandwerteInput() {
        Iterable<Pfandwert> pfandwertVec = getraenkeusecases.getAllPfandwerte();
        List<Pfandwert> pfandwertList = StreamSupport.stream(pfandwertVec.spliterator(), false)
                                    .collect(Collectors.toList());
        if(pfandwertList.isEmpty()){
            errorNoProdukt();
            return;
        }
        int count = 1;
        for(Pfandwert pfandwert : pfandwertList){
            printPfandwertWithNumber(pfandwert, count);
            count++;
        }
    }
    
    public void handleGetPfandWertInput() {
        String uuidString = readStringInputWithPrompt("UUID of Pfandwert: ");
        UUID uuid = UUID.fromString(uuidString);
        Pfandwert pfandwert = getraenkeusecases.getPfandWert(uuid).get();
        printPfandwertWithNumber(pfandwert, 1);
    }
    
    public void handleGetAllProductsInput() {
        Iterable<Produkt> productVec = getraenkeusecases.getAllProducts();
        List<Produkt> productList = StreamSupport.stream(productVec.spliterator(), false)
                                    .collect(Collectors.toList());
        if(productList.isEmpty()){
            errorNoProdukt();
            return;
        }
        int count = 1;
        for(Produkt produkt : productList){
            printProduktWithNumber(produkt, count);
            count++;
        }
    }
    
    public void handleGetPriceForProduktInput() {
        Optional<Produkt> produktOptional = pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        System.out.println("The Price of the product: \n "+ produkt.toString() + 
                           "\n is: \n" + 
                           getraenkeusecases.getPriceForProdukt(produkt));
    }
    
    public void handleGetPriceHistoryForProduktInput() {
        Optional<Produkt> produktOptional = pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        Iterable<Preis> priceHistory  = getraenkeusecases.getPriceHistoryForProdukt(produkt);
        System.out.println("Price History of " + produkt.toString() + ":");
        for(Preis preis : priceHistory){
            System.out.println(preis.toString());
        }

    }
    
    public void handleSetPriceForProduktInput() {
        Optional<Produkt> produktOptional = pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        int newPrice = readIntInputWithPrompt("Input new price for product: ");
        if(!acceptInput()){
            return;
        }
        getraenkeusecases.setPriceForProdukt(produkt, newPrice);
    }

    public void handleGetStockAmountForProduktInput() {
        Optional<Produkt> produktOptional = pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        int stock = getraenkeusecases.getStockAmountForProdukt(produkt);
        System.out.println("The Stock of the Product "+ produkt.toString()+ " is : " + stock);        
    }
    
    public void handleAddProduktInput() {
        System.out.println("Add Product to existing Products");
        String name = readStringInputWithPrompt("Name: ");
        String beschreibung = readStringInputWithPrompt("Describtion: ");
        String kategorie = readStringInputWithPrompt("Categorie: ");
        int price = readIntInputWithPrompt("Price: ");

        if(!acceptInput()){
            return;
        }
        try {
            getraenkeusecases.addProdukt(name, beschreibung, kategorie, price);    
        } catch (Exception e) {
            System.out.println("An error occured while adding a new product");
            System.err.println(e.getMessage());
        }
        
    }
    
    public void handleGetProductInput() {
        String uuidString = readStringInputWithPrompt("UUID for product: ");
        UUID uuid = UUID.fromString(uuidString);
        try {
            Optional<Produkt> produkt = getraenkeusecases.getProduct(uuid);    
            if(produkt.isEmpty()){
                errorNoProdukt();
                return;
            }
            System.out.println(produkt.toString());
        } catch (Exception e) {
            System.err.println("Input: "+ uuid+ "\n" + e.getMessage());
        }
        
    }

    public void handleAddBestellungInput() {
        Optional<Kunde> kundeOptional = pickOneUserFromAllUsers();
        if(kundeOptional.isEmpty()){
            errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        int amountProdukts = readIntInputWithPrompt("How many Products do you want to add: ");
        List<Triple<Produkt, Integer, Double>> bestellungsList = new ArrayList<>();
        for(int i = 0; i < amountProdukts; i++){
            while(true){
                try {
                    System.out.println("Produkt number :"+ (i+1));
                    Optional<Produkt> produktOptional = pickOneProductFromAllProducts();
                    int amount = readIntInputWithPrompt("Wie viele Produkte: ");
                    // TODO NAchfragen bei niklas keine Beschreibung für das Attribut 
                    Double value = readDoubleInputWithPrompt("Wie viel kostetet das Produkt: ");
                    bestellungsList.add(new Triple<Produkt,Integer,Double>(produktOptional.get(), amount, value));
                    break;
                } catch (Exception e) {
                    System.out.println("Error try this product again");
                    System.out.println(e.getMessage());
                }
            }
        }
        try {
            getraenkeusecases.addBestellung(kunde, bestellungsList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void handleAddZahlungsvorgangInput() {
        Optional<Kunde> kundeOptional = pickOneUserFromAllUsers();
        if(kundeOptional.isEmpty()){
            errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        String zahlungsWeString = readStringInputWithPrompt("Zahlungsweg: ");
        Double betrag = readDoubleInputWithPrompt("Betrag: ");
        LocalDateTime localDateTime = LocalDateTime.now();
        
        try {
            kundeUseCases.addZahlungsvorgang(kunde, zahlungsWeString, betrag, localDateTime);
            System.out.println("Zahlungsvorgang wurde erfolgreich angelegt.");
        } catch (Exception e) {
            System.out.println("An Error Occurt while storing the : \n"+ 
            "Kunde: " + kunde.toString() +"\n"+
            "ZahlungsWeg: " + zahlungsWeString +"\n"+
            "Betrag: " + betrag +"\n"+
            "LocalDateTime: "+ localDateTime);
            System.err.println(e.getMessage());
        }
    }

    public Map<String,Runnable> getGetrankeCommandMap() {
        return this.getrankeCommandMap;
    }

    private Optional<Produkt> pickOneProductFromAllProducts() {
        Iterable<Produkt> productVec = getraenkeusecases.getAllProducts();
        List<Produkt> productList = StreamSupport.stream(productVec.spliterator(), false)
                                    .collect(Collectors.toList());
        if(productList.isEmpty()){
            return Optional.empty();
        }

        int count = 1;
        for(Produkt product : productList) {
            printProduktWithNumber(product, count);
        }
        int indexProdukt = 0;
        while (true) {
            indexProdukt = readIntInputWithPrompt("Which Customername do you want to change? Enter the Number: ");
            if(indexProdukt < productList.size()+1 && indexProdukt > 0){
                break;
            }
            System.out.println("Something went wrong the "+ indexProdukt +  " is not in the list");
        }
        Produkt produkt = productList.get(indexProdukt-1);
        System.out.println("Chosen Produkt: "+ produkt.toString());
        return Optional.of(productList.get(indexProdukt-1));
    }

    private Optional<Pfandwert> pickOnePfandwertFromAllPfandwerts() {
        Iterable<Pfandwert> pfandwertVec = getraenkeusecases.getAllPfandwerte();
        List<Pfandwert> pfandwertList = StreamSupport.stream(pfandwertVec.spliterator(), false)
                                    .collect(Collectors.toList());
        if(pfandwertList.isEmpty()){
            return Optional.empty();
        }

        int count = 1;
        for(Pfandwert pfandwert : pfandwertList) {
            printPfandwertWithNumber(pfandwert, count);
        }
        int indexProdukt = 0;
        while (true) {
            indexProdukt = readIntInputWithPrompt("Which Customername do you want to change? Enter the Number: ");
            if(indexProdukt < pfandwertList.size()+1 && indexProdukt > 0){
                break;
            }
            System.out.println("Something went wrong the "+ indexProdukt +  " is not in the list");
        }
        Pfandwert pfandwert = pfandwertList.get(indexProdukt-1);
        System.out.println("Chosen Produkt: "+ pfandwert.toString());
        return Optional.of(pfandwertList.get(indexProdukt-1));
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
    
    private void printProduktWithNumber(Produkt produkt, int number){
        System.out.println(number + ". "+ produkt.toString());
    }

    private void printPfandwertWithNumber(Pfandwert pfandwert, int number){
        System.out.println(number + ". "+ pfandwert.toString());
    }

    private void printKundeWithNumber(Kunde kunde, int number){
        System.out.println(number + ". "+ kunde.toString());
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

    private Double readDoubleInputWithPrompt(String prompt){
        System.out.print(prompt);
        while(true){
            String input = this.scanner.nextLine();
            try{
                double inputCastInt = Double.parseDouble(input);
                return inputCastInt;
            }catch(Exception e){
                System.out.println("The input: "+ input+ " can not be translated into a number");
            }
        }
    }

    private String readStringInputWithPrompt(String ptompString){
        System.out.print(ptompString);
        return this.scanner.nextLine();
    }

    private void errorNoProdukt() {
        System.out.println(NOPRODUKTMESSAGE);
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

    private void errorNoPfandWert() {
        System.out.println(NOPFANDWERTMESSAGE);
    }
 
    private void errorNoKunden() {
        System.out.println("There are no Customer/s found");
    }
}
