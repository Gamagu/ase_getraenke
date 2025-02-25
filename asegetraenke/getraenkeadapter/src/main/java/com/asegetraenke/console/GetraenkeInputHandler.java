package com.asegetraenke.console;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Produkt;
import com.asegetraenke.util.Triple;
import com.asegetraenke.valueobjects.Pfandwert;
import com.asegetraenke.valueobjects.Preis;
import com.asegetraenke.getraenkeusecases;

public class GetraenkeInputHandler {
    private final Map<String,Runnable> getrankeCommandMap;
    private final getraenkeusecases getraenkeusecases;
    private final ConsoleUtils consoleUtils;

    public GetraenkeInputHandler(getraenkeusecases getraenkeusecases, ConsoleUtils consoleUtils){
        this.getrankeCommandMap = initializeCommandMapGetraenke();
        this.getraenkeusecases = getraenkeusecases;
        this.consoleUtils = consoleUtils;
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
        String description = consoleUtils.readStringInputWithPrompt("Description: ");
        Double value = consoleUtils.readDoubleInputWithPrompt("Value: ");
        if(!consoleUtils.acceptInput()){
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
        Optional<Pfandwert> pfandwerOptional = consoleUtils.pickOnePfandwertFromAllPfandwerts();
        if(pfandwerOptional.isEmpty()){
            consoleUtils.errorNoPfandWert();
            return;
        }
        Pfandwert pfandwert = pfandwerOptional.get();
        double newValue = consoleUtils.readDoubleInputWithPrompt("New Value for Pfandwert: ");
        if(!consoleUtils.acceptInput()){
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
        Optional<Pfandwert> pfandwertOptional = consoleUtils.pickOnePfandwertFromAllPfandwerts();
        if (pfandwertOptional.isEmpty()) {
            consoleUtils.errorNoPfandWert();
            return;
        }
        Pfandwert pfandwert = pfandwertOptional.get();

        Optional<Produkt> produktOptional = consoleUtils.pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            consoleUtils.errorNoProdukt();
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
            consoleUtils.errorNoProdukt();
            return;
        }
        int count = 1;
        for(Pfandwert pfandwert : pfandwertList){
            consoleUtils.printPfandwertWithNumber(pfandwert, count);
            count++;
        }
    }
    
    public void handleGetPfandWertInput() {
        String uuidString = consoleUtils.readStringInputWithPrompt("UUID of Pfandwert: ");
        UUID uuid = UUID.fromString(uuidString);
        Pfandwert pfandwert = getraenkeusecases.getPfandWert(uuid);
        consoleUtils.printPfandwertWithNumber(pfandwert, 1);
    }
    
    public void handleGetAllProductsInput() {
        Iterable<Produkt> productVec = getraenkeusecases.getAllProducts();
        List<Produkt> productList = StreamSupport.stream(productVec.spliterator(), false)
                                    .collect(Collectors.toList());
        if(productList.isEmpty()){
            consoleUtils.errorNoProdukt();
            return;
        }
        int count = 1;
        for(Produkt produkt : productList){
            consoleUtils.printProduktWithNumber(produkt, count);
            count++;
        }
    }
    
    public void handleGetPriceForProduktInput() {
        Optional<Produkt> produktOptional = consoleUtils.pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            consoleUtils.errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        System.out.println("The Price of the product: \n "+ produkt.toString() + 
                           "\n is: \n" + 
                           getraenkeusecases.getPriceForProdukt(produkt));
    }
    
    public void handleGetPriceHistoryForProduktInput() {
        Optional<Produkt> produktOptional = consoleUtils.pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            consoleUtils.errorNoProdukt();
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
        Optional<Produkt> produktOptional = consoleUtils.pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            consoleUtils.errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        int newPrice = consoleUtils.readIntInputWithPrompt("Input new price for product: ");
        if(!consoleUtils.acceptInput()){
            return;
        }
        getraenkeusecases.setPriceForProdukt(produkt, newPrice);
    }

    public void handleGetStockAmountForProduktInput() {
        Optional<Produkt> produktOptional = consoleUtils.pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            consoleUtils.errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        int stock = getraenkeusecases.getStockAmountForProdukt(produkt);
        System.out.println("The Stock of the Product "+ produkt.toString()+ " is : " + stock);        
    }
    
    public void handleAddProduktInput() {
        System.out.println("Add Product to existing Products");
        String name = consoleUtils.readStringInputWithPrompt("Name: ");
        String beschreibung = consoleUtils.readStringInputWithPrompt("Describtion: ");
        String kategorie = consoleUtils.readStringInputWithPrompt("Categorie: ");
        int price = consoleUtils.readIntInputWithPrompt("Price: ");

        if(!consoleUtils.acceptInput()){
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
        String uuidString = consoleUtils.readStringInputWithPrompt("UUID for product: ");
        UUID uuid = UUID.fromString(uuidString);
        try {
            Optional<Produkt> produkt = getraenkeusecases.getProduct(uuid);    
            if(produkt.isEmpty()){
                consoleUtils.errorNoProdukt();
                return;
            }
            System.out.println(produkt.toString());
        } catch (Exception e) {
            System.err.println("Input: "+ uuid+ "\n" + e.getMessage());
        }
        
    }

    public void handleAddBestellungInput() {
        Optional<Kunde> kundeOptional = consoleUtils.pickOneUserFromAllUsers();
        if(kundeOptional.isEmpty()){
            consoleUtils.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        int amountProdukts = consoleUtils.readIntInputWithPrompt("How many Products do you want to add: ");
        List<Triple<Produkt, Integer, Double>> bestellungsList = new ArrayList<>();
        for(int i = 0; i < amountProdukts; i++){
            while(true){
                try {
                    System.out.println("Produkt number :"+ (i+1));
                    Optional<Produkt> produktOptional = consoleUtils.pickOneProductFromAllProducts();
                    int amount = consoleUtils.readIntInputWithPrompt("Wie viele Produkte: ");
                    Double value = consoleUtils.readDoubleInputWithPrompt("Wie viel kostetet das Produkt: ");
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
        Optional<Kunde> kundeOptional = consoleUtils.pickOneUserFromAllUsers();
        if(kundeOptional.isEmpty()){
            consoleUtils.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        String zahlungsWeString = consoleUtils.readStringInputWithPrompt("Zahlungsweg: ");
        Double betrag = consoleUtils.readDoubleInputWithPrompt("Betrag: ");
        LocalDateTime localDateTime = LocalDateTime.now();
        
        try {
            getraenkeusecases.addZahlungsvorgang(kunde, zahlungsWeString, betrag, localDateTime);
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
}
