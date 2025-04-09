package adapter.asegetraenke.console;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import adapter.asegetraenke.console.Utils.ConsoleUtils;
import adapter.asegetraenke.console.consolefunctionmapping.CommandRegistrar;
import adapter.asegetraenke.console.consolefunctionmapping.ICommand;
import application.asegetraenke.GetraenkeUsecases;
import application.asegetraenke.KundenUsecases;
import domain.asegetraenke.entities.Kunde;
import domain.asegetraenke.entities.Produkt;
import domain.asegetraenke.util.Triple;
import domain.asegetraenke.valueobjects.Pfandwert;
import domain.asegetraenke.valueobjects.Preis;

public class GetraenkeInputHandler {
    private final GetraenkeUsecases getraenkeusecases;
    private final KundenUsecases kundenUsecases;
    private final ConsoleUtils consoleUtils;

    public GetraenkeInputHandler(GetraenkeUsecases getraenkeusecases, KundenUsecases kundenUsecases, ConsoleUtils consoleUtils, CommandRegistrar registrar){
        this.getraenkeusecases = getraenkeusecases;
        this.consoleUtils = consoleUtils;
        this.kundenUsecases = kundenUsecases;
        registrar.registerCommands(this);
    }

    @ICommand(value = "addpfandwert", category = "getraenke")
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
    
    @ICommand(value = "setpfandwert", category = "getraenke")
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
    
    @ICommand(value = "setpfandwertprodukt", category = "getraenke")
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

    @ICommand(value = "getallpfandwerte", category = "getraenke")
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

    
    @ICommand(value = "getpfandwert", category = "getraenke")
    public void handleGetPfandWertInput() {
        String uuidString = consoleUtils.readStringInputWithPrompt("UUID of Pfandwert: ");
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
            consoleUtils.errorNoPfandWert();
            return;
        }
        consoleUtils.printPfandwertWithNumber(pfandwert.get(), 1);
    }

    
    @ICommand(value = "getallproducts", category = "getraenke")
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
    
    @ICommand(value = "getpriceforprodukt", category = "getraenke")
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
    
    @ICommand(value = "getpricehistoryforprodukt", category = "getraenke")
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

    @ICommand(value = "setpriceforprodukt", category = "getraenke")
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

    @ICommand(value = "getstockamountforprodukt", category = "getraenke")
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
    
    @ICommand(value = "addprodukt", category = "getraenke")
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
    
    @ICommand(value = "getproduct", category = "getraenke")
    public void handleGetProductInput() {
        String uuidString = consoleUtils.readStringInputWithPrompt("UUID for product: ");
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
                consoleUtils.errorNoProdukt();
                return;
            }
            System.out.println(produkt.toString());
        } catch (Exception e) {
            System.err.println("Input: "+ uuid+ "\n" + e.getMessage());
        }
        
    }

    @ICommand(value = "addbestellung", category = "getraenke")
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
            e.printStackTrace();
        }

    }

    @ICommand(value = "addzahlungsvorgang", category = "getraenke")
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
