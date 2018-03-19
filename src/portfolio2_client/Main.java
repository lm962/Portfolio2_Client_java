
package portfolio2_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.Holder;


public class Main {

    //private final TVGlobalSoapWebservice ws;
    private final BufferedReader fromKeyboard;

    
    public static void main(String[] args)throws IOException, DatatypeConfigurationException {
        Main main = new Main();
        main.runMainMenu();
        
    }
    
    public Main() {
        // Webservice-Stub erzeugen
        TVGlobal tvglobal = new TVGlobal();
        this.ws = tvglobal.getTVGlobalSoapWebservicePort();
        
        this.fromKeyboard = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public void runMainMenu() throws IOException, DatatypeConfigurationException {
        System.out.println("         .----. ");
        System.out.println("      _.'__    `.   ");
        System.out.println("  .--(#)(##)---/#\  ");
        System.out.println(".' @          /###\    ");
        System.out.println(":         ,   ##### ");
        System.out.println(" `-..__.-' _.-\###/ ");
        System.out.println("       `;_:    `"'  ");
        System.out.println("     .'"""""`.       ");
        System.out.println("    /,  JOE  ,\     ");
        System.out.println("   //  COOL!  \\    ");
        System.out.println("   -Car Sharing-     ");
        System.out.println("   `-._______.-'       ");
        System.out.println("   ___`. | .'___        ");
        System.out.println("  (______|______)       ");
        System.out.println();
        
        boolean quit = false;

        while (!quit) {
            System.out.println("=========");
            System.out.println("Hauptmenü");
            System.out.println("=========");
            System.out.println();
            System.out.println("  [K] Kunde anlegen");
            System.out.println("  [F] Fahrzeug anlegen");
            System.out.println("  [A] Fahrzeug ausleihen");
            System.out.println("  [L] Leihverträge auflisten");
            System.out.println("  [E] Ende");
            System.out.println();

            System.out.print("Deine Auswahl: ");
            String command = this.fromKeyboard.readLine();
            System.out.println();

            switch (command.toUpperCase()) {
                case "K":
                    this.kundeAnlegen();
                    break;
                case "F":
                    this.fahrzeugAnlegen();
                    break;
                case "A":
                    this.fahrzeugAusleihen();
                    break;
                case "L":
                    this.leihverträgeAuflisten();
                    break;
                case "E":
                    quit = true;
                    break;
                default:
                    System.out.println("Sorry, ich habe dich nicht verstanden …");
                    System.out.println();
            }
        }
    }
    public void kundeAnlegen() throws IOException, DatatypeConfigurationException {
        System.out.println("================");
        System.out.println("Kunde anlegen");
        System.out.println("================");
        System.out.println();

        Customer customer = new Customer();
        
        System.out.print("Vorname: ");
        String vorname = this.fromKeyboard.readLine();
        customer.setVorname(vorname);

        System.out.print("Nachname: ");
        String nachname = this.fromKeyboard.readLine();
        customer.setNachname(nachname);
        
        System.out.print("Straße: ");
        String straße = this.fromKeyboard.readLine();
        customer.setStraße(straße);
        
        System.out.print("Hausnummer: ");
        String hausnummer = this.fromKeyboard.readLine();
        customer.setHausnummer(hausnummer);
        
        System.out.print("Postleitzahl: ");
        String postleitzahl = this.fromKeyboard.readLine();
        customer.setPostleitzahl(postleitzahl);
        
        System.out.print("Ort: ");
        String ort = this.fromKeyboard.readLine();
        customer.setOrt(ort);
        
        System.out.print("Land: ");
        String land = this.fromKeyboard.readLine();
        customer.setLand(land);
        
        System.out.println();
        
        Holder<Customer> hCustomer = new Holder<>(customer);
        webservice.saveNewCustomer(hCustomer);

        System.out.println("Kunde mit der ID " + hCustomer.value.getId() + " wurde angelegt.");   
    }
    
    public void fahrzeugAnlegen() throws IOException, DatatypeConfigurationException {
        System.out.println("================");
        System.out.println("Fahrzeug anlegen");
        System.out.println("================");
        System.out.println();

        Car car = new Car();

        System.out.print("Hersteller: ");
        String hersteller = this.fromKeyboard.readLine();
        car.setHersteller(hersteller);

        System.out.print("Modell: ");
        String modell = this.fromKeyboard.readLine();
        car.setModell(modell);
        
        System.out.print("Baujahr: ");
        String baujahr = this.fromKeyboard.readLine();
        car.setBaujahr(baujahr);
       
        System.out.println();

        Vehicle<Car> vCar = new Vehicle<>(car);
        webservice.saveNewVehicle(hCar);

        System.out.println("Fahrzeug mit der ID " + hCar.value.getId() + " wurde angelegt."); 
    }

    private void fahrzeugAusleihen() throws IOException, DatatypeConfigurationException{
        System.out.println("================");
        System.out.println("Fahrzeug ausleihen");
        System.out.println("================");
        System.out.println();
        
        Leihvertrag leihvertrag = new Leihvertrag();
        
        System.out.println("Folgende Fahrzeuge stehen zur Verfügung: " +hCar.value.getAll());
        
        System.out.println();
        
        System.out.println("Kundennummer: ");
        String kundennummer = this.fromKeyboard.readLine();
        Leihvertrag.setKundennummer(kundennummer);
        
        System.out.println("Fahrzeug-ID: ");
        String fahrzeugID = this.fromKeyboard.readLine();
        Leihvertrag.setFahrzeugID(fahrzeugID);
        
        System.out.print("Abholung am (yyyy-mm-dd): ");
        String abholung = this.fromKeyboard.readLine();
        Leihvertrag.setAbholung(abholung);
        
        System.out.print("Rückgabe am (yyyy-mm-dd): ");
        String rueckgabe = this.fromKeyboard.readLine();
        Leihvertrag.setRueckgabe(rueckgabe);
        
        Leihvertrag<leihvertrag> lLeihvertrag = new Leihvertrag<>(leihvertrag);
        webservice.saveNewLeihvertrag(lLeihvertrag);
        
        System.out.println("Alles klar! Leihvertrag mit der ID " +hCar.value.getID() + "wurde angelegt");
    }

    private void leihverträgeAuflisten() throws IOException, DatatypeConfigurationException{
        System.out.println("================");
        System.out.println("Leihverträge auflisten");
        System.out.println("================");
        System.out.println();
        
        System.out.println("Folgende Leihverträge sind vorhanden: " lLeihvertrag.value.getAll());
        System.out.println();
    }
}
