import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.LinkOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EvidenceZavodniku {
    String nazevSouboru = "src/vstup";
    List<Zavodnik> seznam = new ArrayList<>();

    public void pridavaniZavodniku(Zavodnik zavodnik){
        seznam.add(zavodnik);
    }
    public void odebiraniZavodniku(Zavodnik zavodnik){
        seznam.remove(zavodnik);
    }
    public List<Zavodnik> seznam (){
       return seznam;
    }

    public void nacteniEvidence(){
        try(Scanner sc = new Scanner(new BufferedReader(new FileReader("src/vstup")))){
            while(sc.hasNextLine()){
                String radek = sc.nextLine();
                String bloky[] = radek.split("/");
                if(bloky.length != 6){
                    throw new RuntimeException("Špatný počet položek na řádku"+radek);
                }
                try {
                    String jmeno = bloky[0].trim();
                    Double vysledek = Double.parseDouble(bloky[1].trim());
                    Integer kategorie = Integer.parseInt(bloky[2].trim());
                    BigDecimal vyhra = new BigDecimal(bloky[3].trim());
                    Boolean naDivokouKartu = Boolean.parseBoolean(bloky[4].trim());
                    LocalDate datum = LocalDate.parse(bloky[5].trim());
                    Zavodnik zavodnik = new Zavodnik(jmeno, kategorie);
                    seznam.add(zavodnik);

                } catch (NumberFormatException | DateTimeParseException e) {
                    System.err.println("Chyba při zpracování řádku: " + radek + " - " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Nebyl nalezen soubor: "+nazevSouboru+e.getLocalizedMessage());
        }
    }
}
