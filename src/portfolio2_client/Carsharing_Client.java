
package portfolio2_client;

import Portfolio2.soap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;


public class Carsharing_Client {

    private final Portfolio2SoapWebservice ws;
    private final BufferedReader fromKeyboard;

    
    public static void main(String[] args)throws IOException, DatatypeConfigurationException {
        Carsharing_Client carsharing_client = new Carsharing_Client();
        carsharing_client.runMainMenu();
        
    }
    
    public Carsharing_Client() {
        Carsharing carsharing = new Carsharing();
        this.ws = carsharing.getPortfolio2SoapWebservicePort();
        
        this.fromKeyboard = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public void runMainMenu() throws IOException, DatatypeConfigurationException {
        System.out.println("     ()_()      ");
        System.out.println("     (^.^)      ");
        System.out.println("sUBER Carsharing");
       
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
                    System.out.println("Ihre Eingabe war Fehlerhaft (╯°□°）╯︵ ┻━┻ ");
                    System.out.println("Bitte versuchen Sie es noch einmal!");
                    System.out.println();
            }
        }
    }
    public void kundeAnlegen() throws IOException, DatatypeConfigurationException {
        System.out.println("================");
        System.out.println("Kunde anlegen");
        System.out.println("================");
        System.out.println();

        Kunde kunde = new Kunde();
        
        System.out.print("Vorname: ");
        String vorname = this.fromKeyboard.readLine();
        kunde.setVorname(vorname);

        System.out.print("Nachname: ");
        String nachname = this.fromKeyboard.readLine();
        kunde.setNachname(nachname);
        
        System.out.print("Straße: ");
        String straße = this.fromKeyboard.readLine();
        kunde.setStraße(straße);
        
        System.out.print("Hausnummer: ");
        String hausnummer = this.fromKeyboard.readLine();
        kunde.setHausnummer(hausnummer);
        
        System.out.print("Postleitzahl: ");
        String postleitzahl = this.fromKeyboard.readLine();
        kunde.setPostleitzahl(postleitzahl);
        
        System.out.print("Ort: ");
        String ort = this.fromKeyboard.readLine();
        kunde.setOrt(ort);
        
        System.out.print("Land: ");
        String land = this.fromKeyboard.readLine();
        kunde.setLand(land);
        
        System.out.println();
        
        Holder<Kunde> hKunde = new Holder<>(kunde);
        this.ws.createKunde(hKunde);

        System.out.println("Kunde mit der ID " + hKunde.getId() + " wurde angelegt."); 
        System.out.println();
    }
    
    public void fahrzeugAnlegen() throws IOException, DatatypeConfigurationException {
        System.out.println("================");
        System.out.println("Fahrzeug anlegen");
        System.out.println("================");
        System.out.println();

        Fahrzeug fahrzeug = new Fahrzeug();

        System.out.print("Hersteller: ");
        String hersteller = this.fromKeyboard.readLine();
        fahrzeug.setHersteller(hersteller);

        System.out.print("Modell: ");
        String modell = this.fromKeyboard.readLine();
        fahrzeug.setModell(modell);
        
        System.out.print("Baujahr: ");
        String baujahr = this.fromKeyboard.readLine();
        fahrzeug.setBaujahr(baujahr);
       
        System.out.println();

        Holder<Fahrzeug> hFahrzeug = new Holder<>(fahrzeug);
        this.ws.createFahrzeug(hFahrzeug);

        System.out.println("Fahrzeug mit der ID " + hFahrzeug.getId() + " wurde angelegt."); 
        System.out.println();
    }

    private void fahrzeugAusleihen() throws IOException, DatatypeConfigurationException{
        System.out.println("================");
        System.out.println("Fahrzeug ausleihen");
        System.out.println("================");
        System.out.println();
        
        Leihvertrag leihvertrag = new Leihvertrag();
        List<Fahrzeug> fahrzeugliste = this.ws.findAllFahrzeuge();
        
        System.out.println("Folgende Fahrzeuge stehen zur Verfügung: ");
        for(Fahrzeug fahrzeug : fahrzeugliste){
            System.out.println(" "+fahrzeug.getHersteller() +" " +fahrzeug.getModell() +", Baujahr " +fahrzeug.getBaujahr() +", ID " +fahrzeug.getID());
        }
        
        System.out.println();
        
        System.out.println("Kundennummer: ");
        String kundennummer = this.fromKeyboard.readLine();
        leihvertrag.setKundennummer(kundennummer);
        
        System.out.println("Fahrzeug-ID: ");
        String fahrzeugID = this.fromKeyboard.readLine();
        leihvertrag.setFahrzeugID(fahrzeugID);
        
        System.out.print("Abholung am (yyyy-mm-dd): ");
        String startDateFromStr = this.fromKeyboard.readLine();
        leihvertrag.setAbholung(startDateFromStr);
        
        System.out.print("Rückgabe am (yyyy-mm-dd): ");
        String startDateToStr = this.fromKeyboard.readLine();
        leihvertrag.setRueckgabe(startDateToStr);
        
        // Webservice aufrufen
        DatatypeFactory dtf = DatatypeFactory.newInstance();
        XMLGregorianCalendar startDateFrom = dtf.newXMLGregorianCalendar(startDateFromStr +"T" +startDateFromStr);
        XMLGregorianCalendar startDateTo = dtf.newXMLGregorianCalendar(startDateToStr +"T" +startDateToStr);

        Holder<Leihvertrag> hLeihvertrag = new Holder(leihvertrag);
        this.ws.createLeihvertrag(hLeihvertrag);

        System.out.println("Alles klar! Leihvertrag mit der ID " +leihvertrag.getID() + "wurde angelegt");
    }

    private void leihverträgeAuflisten() throws IOException, DatatypeConfigurationException{
        System.out.println("================");
        System.out.println("Leihverträge auflisten");
        System.out.println("================");
        System.out.println();
        
        System.out.println("Folgende Leihverträge sind vorhanden: ");
        System.out.println();
        
        List<Leihvertrag> leihvertragliste = this.ws.findAllLeihvertraege();
        
        int counter = 0;
        for(Leihvertrag leihvertrag : leihvertragliste){
            Fahrzeug fahrzeug = ws.findFahrzeugByID(leihvertrag.getFahrzeugid().getID);
            Kunde kunde = ws.findKundeByID(leihvertrag.getKundeid().getId());
            
            System.out.println(++counter +".)");
            System.out.println(" Fahrzeug: " +fahrzeug.getHersteller() + " " + fahrzeug.getModell() +", Baujahr " +fahrzeug.getBauhjahr());
            System.out.println(" Ausleihende Person: "+kunde.getVorname() +" " +kunde.getNachneme());
            System.out.println(" Zeitraum: von " +leihvertrag.getAbholung() +" bis " +leihvertrag.getRueckgabe());
            System.out.println();
        }
    }
}
