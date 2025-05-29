package de.nyg.adapters.asegetraenke.console;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import de.nyg.application.asegetraenke.GetraenkeUsecases;
import de.nyg.application.asegetraenke.KundenUsecases;
import de.nyg.application.asegetraenke.builder.ProduktBuilder;
import de.nyg.adapters.asegetraenke.console.consolefunctionmapping.CommandRegistrar;
import de.nyg.adapters.asegetraenke.console.consolefunctionmapping.ICommand;
import de.nyg.adapters.asegetraenke.console.utils.ConsoleError;
import de.nyg.adapters.asegetraenke.console.utils.ConsolePrinter;
import de.nyg.adapters.asegetraenke.console.utils.ConsoleReader;
import de.nyg.adapters.asegetraenke.console.utils.EntityPicker;
import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.util.Triple;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;
import de.nyg.domain.asegetraenke.valueobjects.Preis;

public class GetraenkeInputHandler {
    private final GetraenkeUsecases getraenkeusecases;
    private final KundenUsecases kundenUsecases;
    private final ConsoleError consoleError;
    private final EntityPicker<Kunde> kundenPicker;
    private final EntityPicker<Produkt> produktPicker;
    private final EntityPicker<Pfandwert> pfandwertPicker;
    private final ConsolePrinter consolePrinter;
    private final ConsoleReader consoleReader;

    public GetraenkeInputHandler(GetraenkeUsecases getraenkeusecases, KundenUsecases kundenUsecases, ConsoleError consoleError, CommandRegistrar registrar, EntityPicker<Kunde> kundenPicker, EntityPicker<Produkt> produktPicker,EntityPicker<Pfandwert> pfandwertPicker, ConsoleReader consoleReader, ConsolePrinter consolePrinter){
        this.getraenkeusecases = getraenkeusecases;
        this.consoleError = consoleError;
        this.kundenUsecases = kundenUsecases;
        this.kundenPicker = kundenPicker;
        this.produktPicker = produktPicker;
        this.pfandwertPicker = pfandwertPicker;
        this.consoleReader = consoleReader;
        this.consolePrinter = consolePrinter;
        registrar.registerCommands(this);
    }

    @ICommand(value = "addpfandwert", category = "getraenke")
    public void handleAddPfandWertInput() {
        System.out.println("Add new Pfand value to existing");
        String description = consoleReader.readStringInputWithPrompt("Description: ");
        Double value = consoleReader.readDoubleInputWithPrompt("Value: ");
        if(!consoleReader.acceptInput()){
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
    
    @ICommand(value = "setpfandwert", category = "getraenke")
    public void handleSetPfandwertInput() {
        Iterable<Pfandwert> iterable = getraenkeusecases.getAllPfandwerte();
        List<Pfandwert> pfandwertList = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
        Optional<Pfandwert> pfandwertOptional = pfandwertPicker.pickOneFromList(pfandwertList, Pfandwert::toString);
        if(pfandwertOptional.isEmpty()){
            consoleError.errorNoPfandWert();
            return;
        }
        Pfandwert pfandwert = pfandwertOptional.get();
        double newValue = consoleReader.readDoubleInputWithPrompt("New Value for Pfandwert: ");
        if(!consoleReader.acceptInput()){
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
    
    @ICommand(value = "setpfandwertprodukt", category = "getraenke")
    public void handleSetPfandwertProduktInput() {
        Iterable<Pfandwert> iterablePfandwert = getraenkeusecases.getAllPfandwerte();
        List<Pfandwert> pfandwertList = StreamSupport.stream(iterablePfandwert.spliterator(), false).collect(Collectors.toList());
        Optional<Pfandwert> pfandwertOptional = pfandwertPicker.pickOneFromList(pfandwertList, Pfandwert::toString);

        if (pfandwertOptional.isEmpty()) {
            consoleError.errorNoPfandWert();
            return;
        }
        Pfandwert pfandwert = pfandwertOptional.get();


        Iterable<Produkt> iterableProdukt = getraenkeusecases.getAllProducts();
        List<Produkt> produktsList = StreamSupport.stream(iterableProdukt.spliterator(), false).collect(Collectors.toList());
        Optional<Produkt> produktOptional = produktPicker.pickOneFromList(produktsList, Produkt::toString);
        if(produktOptional.isEmpty()){
            consoleError.errorNoProdukt();
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

    @ICommand(value = "getallpfandwerte", category = "getraenke")
    public void handleGetAllPfandwerteInput() {
        Iterable<Pfandwert> pfandwertVec = getraenkeusecases.getAllPfandwerte();
        List<Pfandwert> pfandwertList = StreamSupport.stream(pfandwertVec.spliterator(), false)
                                    .collect(Collectors.toList());
        if(pfandwertList.isEmpty()){
            consoleError.errorNoProdukt();
            return;
        }
        int count = 1;
        for(Pfandwert pfandwert : pfandwertList){
            consolePrinter.printPfandwertWithNumber(pfandwert, count);
            count++;
        }
    }

    
    @ICommand(value = "getpfandwert", category = "getraenke")
    public void handleGetPfandWertInput() {
        String uuidString = consoleReader.readStringInputWithPrompt("UUID of Pfandwert: ");
        UUID uuid = null;
        try {
            uuid = UUID.fromString(uuidString);    
        } catch (Exception e) {
            System.out.println("Conversion from " + uuidString + " to UUID was not possible");
            return;
        }
        if(uuid==null){
            return;
        }
        
        Optional<Pfandwert> pfandwert = getraenkeusecases.getPfandWert(uuid);
        if(pfandwert.isEmpty()){
            consoleError.errorNoPfandWert();
            return;
        }
        consolePrinter.printPfandwertWithNumber(pfandwert.get(), 1);
    }

    
    @ICommand(value = "getallproducts", category = "getraenke")
    public void handleGetAllProductsInput() {
        Iterable<Produkt> productVec = getraenkeusecases.getAllProducts();
        List<Produkt> productList = StreamSupport.stream(productVec.spliterator(), false)
                                    .collect(Collectors.toList());
        if(productList.isEmpty()){
            consoleError.errorNoProdukt();
            return;
        }
        int count = 1;
        for(Produkt produkt : productList){
            consolePrinter.printProduktWithNumber(produkt, count);
            count++;
        }
    }
    
    @ICommand(value = "getpriceforprodukt", category = "getraenke")
    public void handleGetPriceForProduktInput() {
        Iterable<Produkt> iterableProdukt = getraenkeusecases.getAllProducts();
        List<Produkt> produktsList = StreamSupport.stream(iterableProdukt.spliterator(), false).collect(Collectors.toList());
        Optional<Produkt> produktOptional = produktPicker.pickOneFromList(produktsList, Produkt::toString);
        if(produktOptional.isEmpty()){
            consoleError.errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        System.out.println("The Price of the product: \n "+ produkt.toString() + 
                           "\n is: \n" + 
                           getraenkeusecases.getPriceForProdukt(produkt));
    }
    
    @ICommand(value = "getpricehistoryforprodukt", category = "getraenke")
    public void handleGetPriceHistoryForProduktInput() {
        Iterable<Produkt> iterableProdukt = getraenkeusecases.getAllProducts();
        List<Produkt> produktsList = StreamSupport.stream(iterableProdukt.spliterator(), false).collect(Collectors.toList());
        Optional<Produkt> produktOptional = produktPicker.pickOneFromList(produktsList, Produkt::toString);
        if(produktOptional.isEmpty()){
            consoleError.errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        Iterable<Preis> priceHistory  = getraenkeusecases.getPriceHistoryForProdukt(produkt);
        System.out.println("Price History of " + produkt.toString() + ":");
        for(Preis preis : priceHistory){
            System.out.println(preis.toString());
        }

    }

    @ICommand(value = "setpriceforprodukt", category = "getraenke")
    public void handleSetPriceForProduktInput() {
        Iterable<Produkt> iterableProdukt = getraenkeusecases.getAllProducts();
        List<Produkt> produktsList = StreamSupport.stream(iterableProdukt.spliterator(), false).collect(Collectors.toList());
        Optional<Produkt> produktOptional = produktPicker.pickOneFromList(produktsList, Produkt::toString);
        if(produktOptional.isEmpty()){
            consoleError.errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        int newPrice = consoleReader.readIntInputWithPrompt("Input new price for product: ");
        if(!consoleReader.acceptInput()){
            return;
        }
        getraenkeusecases.setPriceForProdukt(produkt, newPrice);
    }

    @ICommand(value = "getstockamountforprodukt", category = "getraenke")
    public void handleGetStockAmountForProduktInput() {
        Iterable<Produkt> iterableProdukt = getraenkeusecases.getAllProducts();
        List<Produkt> produktsList = StreamSupport.stream(iterableProdukt.spliterator(), false).collect(Collectors.toList());
        Optional<Produkt> produktOptional = produktPicker.pickOneFromList(produktsList, Produkt::toString);
        if(produktOptional.isEmpty()){
            consoleError.errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        int stock = getraenkeusecases.getStockAmountForProdukt(produkt);
        System.out.println("The Stock of the Product "+ produkt.toString()+ " is : " + stock);        
    }
    
    @ICommand(value = "addprodukt", category = "getraenke")
    public void handleAddProduktInput() {
        System.out.println("Add Product to existing Products");
        String name = consoleReader.readStringInputWithPrompt("Name: ");
        String beschreibung = consoleReader.readStringInputWithPrompt("Beschreibung: ");
        String kategorie = consoleReader.readStringInputWithPrompt("Kategorie: ");
        Double price = consoleReader.readDoubleInputWithPrompt("Preis: ");

        if(!consoleReader.acceptInput()){
            return;
        }
        try {
            Produkt produkt = new ProduktBuilder()
                .withName(name)
                .withBeschreibung(beschreibung)
                .withKategorie(kategorie)
                .build();
            getraenkeusecases.addProdukt(produkt, price);    
        } catch (Exception e) {
            System.out.println("An error occured while adding a new product");
            System.err.println(e.getMessage());
        }
        
    }
    
    @ICommand(value = "getproduct", category = "getraenke")
    public void handleGetProductInput() {
        String uuidString = consoleReader.readStringInputWithPrompt("UUID for product: ");
        UUID uuid = null;
        try {
            uuid = UUID.fromString(uuidString);
        } catch (Exception e) {
            System.out.println("Conversion from " + uuidString + " to UUID was not possible");
            
        }

        if(uuid == null) {
            return;
        }

        try {
            Optional<Produkt> produkt = getraenkeusecases.getProduct(uuid);    
            if(produkt.isEmpty()){
                consoleError.errorNoProdukt();
                return;
            }
            System.out.println(produkt.toString());
        } catch (Exception e) {
            System.err.println("Input: "+ uuid+ "\n" + e.getMessage());
        }
        
    }

    @ICommand(value = "addbestellung", category = "getraenke")
    public void handleAddBestellungInput() {
        Iterable<Kunde> iterable = kundenUsecases.getAllKunden();
        List<Kunde> kundenListe = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
        Optional<Kunde> kundeOptional = kundenPicker.pickOneFromList(kundenListe, Kunde::toString);
        if(kundeOptional.isEmpty()){
            consoleError.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        int amountProdukts = consoleReader.readIntInputWithPrompt("How many Products do you want to add: ");
        List<Triple<Produkt, Integer, Double>> bestellungsList = new ArrayList<>();
        for(int i = 0; i < amountProdukts; i++){
            while(true){
                try {
                    System.out.println("Produkt number :"+ (i+1));
                    Iterable<Produkt> iterableProdukt = getraenkeusecases.getAllProducts();
                    List<Produkt> produktsList = StreamSupport.stream(iterableProdukt.spliterator(), false).collect(Collectors.toList());
                    Optional<Produkt> produktOptional = produktPicker.pickOneFromList(produktsList, Produkt::toString);
                    int amount = consoleReader.readIntInputWithPrompt("Wie viele Produkte: ");
                    Double value = consoleReader.readDoubleInputWithPrompt("Wie viel kostetet das Produkt: ");
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
            e.printStackTrace();
        }

    }

    @ICommand(value = "addzahlungsvorgang", category = "getraenke")
    public void handleAddZahlungsvorgangInput() {
        Iterable<Kunde> iterable = kundenUsecases.getAllKunden();
        List<Kunde> kundenListe = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
        Optional<Kunde> kundeOptional = kundenPicker.pickOneFromList(kundenListe, Kunde::toString);
        if(kundeOptional.isEmpty()){
            consoleError.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        String zahlungsWeString = consoleReader.readStringInputWithPrompt("Zahlungsweg: ");
        Double betrag = consoleReader.readDoubleInputWithPrompt("Betrag: ");
        LocalDateTime localDateTime = LocalDateTime.now();
        
        try {
            kundenUsecases.addZahlungsvorgang(kunde, zahlungsWeString, betrag, localDateTime);
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
}
