
package portfolio2_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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
        System.out.println("████████╗██╗   ██╗     ██████╗ ██╗      ██████╗ ██████╗  █████╗ ██╗");
        System.out.println("╚══██╔══╝██║   ██║    ██╔════╝ ██║     ██╔═══██╗██╔══██╗██╔══██╗██║");
        System.out.println("   ██║   ██║   ██║    ██║  ███╗██║     ██║   ██║██████╔╝███████║██║");
        System.out.println("   ██║   ╚██╗ ██╔╝    ██║   ██║██║     ██║   ██║██╔══██╗██╔══██║██║");
        System.out.println("   ██║    ╚████╔╝     ╚██████╔╝███████╗╚██████╔╝██████╔╝██║  ██║███████╗");
        System.out.println("   ╚═╝     ╚═══╝       ╚═════╝ ╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝");
        System.out.println();

        boolean quit = false;

        while (!quit) {
            System.out.println("=========");
            System.out.println("Hauptmenü");
            System.out.println("=========");
            System.out.println();
            System.out.println("  [S] Sendungen suchen");
            System.out.println("  [E] Ende");
            System.out.println();

            System.out.print("Deine Auswahl: ");
            String command = this.fromKeyboard.readLine();
            System.out.println();

            switch (command.toUpperCase()) {
                case "S":
                    this.searchPrograms();
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
    public void searchPrograms() throws IOException, DatatypeConfigurationException {
        System.out.println("================");
        System.out.println("Sendungen suchen");
        System.out.println("================");
        System.out.println();

        // Datum und Uhrzeit vom Benutzer abfragen
        System.out.print("Startdatum von  (yyyy-mm-dd)  : ");
        String startDateFromStr = this.fromKeyboard.readLine();

        System.out.print("Startzeit von   (hh:mm:ss)    : ");
        String startTimeFromStr = this.fromKeyboard.readLine();

        System.out.print("Startdatum bis  (yyyy-mm-dd)  : ");
        String startDateToStr = this.fromKeyboard.readLine();

        System.out.print("Startzeit bis   (hh:mm:ss)    : ");
        String startTimeToStr = this.fromKeyboard.readLine();
        
        System.out.println();

        // Webservice aufrufen
        DatatypeFactory dtf = DatatypeFactory.newInstance();
        XMLGregorianCalendar startTimeFrom = dtf.newXMLGregorianCalendar(startDateFromStr + "T" + startTimeFromStr);
        XMLGregorianCalendar startTimeTo = dtf.newXMLGregorianCalendar(startDateToStr + "T" + startTimeToStr);

        List<Program> programs = this.ws.findProgramByStartBetween(startTimeFrom, startTimeTo);

        // Emfpangene Ergebnisse anzeigen
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        
        for (Program program : programs) {
            Date startTime = program.getStartTime().toGregorianCalendar().getTime();
            Date endTime = program.getEndTime().toGregorianCalendar().getTime();
            
            System.out.println("  Sender:       " + program.getStation().getCode());
            System.out.println("  Sendung:      " + program.getName());
            System.out.println("  Beschreibung: " + program.getDescription());
            System.out.println("  Startzeit:    " + fmt.format(startTime));
            System.out.println("  Endezeit:     " + fmt.format(endTime));
            System.out.println();
        }
    }
    
}
