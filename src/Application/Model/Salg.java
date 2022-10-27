package Application.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Salg {
    private LocalDate registreringsDato;
    private ArrayList<Salgslinje> salgslinjer = new ArrayList<Salgslinje>();
    private Betalingsform betalingsform;


    public Salg() {
        this.registreringsDato = LocalDate.now();
    }

    public Salgslinje createSalgslinje(Produkt produkt, int antal){
        Salgslinje salgslinje = new Salgslinje(antal, produkt);
        salgslinjer.add(salgslinje);
        return salgslinje;
    }

    public double beregnSamletPrisDKK(Prisliste prisliste) {
        double result = 0;
        for (Salgslinje s : salgslinjer){
            result += prisliste.findPrisPaaProduktDKK(s.getProdukt()) * s.getAntal();
        }
        return result;
    }

    public int beregnSamletPrisKlip(Prisliste prisliste) {
        int result = 0;
        for (Salgslinje s : salgslinjer){
            result += prisliste.findPrisPaaProduktKlip(s.getProdukt()) * s.getAntal();
        }
        return result;
    }

    public ArrayList<Salgslinje> getSalgslinjer() {
        return new ArrayList<>(salgslinjer);
    }

    public ArrayList<String> printMellemRegning(Prisliste prisliste){
        ArrayList<String> result = new ArrayList<>();
        for (Salgslinje salgslinje : salgslinjer){
            result.add(salgslinje.printMellemRegning(prisliste));
        }
        return result;
    }

    public void setBetalingsform(Betalingsform betalingsform){
        this.betalingsform = betalingsform;
    }

    public Betalingsform getBetalingsform() {
        return this.betalingsform;
    }

    public enum Betalingsform {
        DANKORT, KONTANT, KLIPPEKORT, MOBILEPAY, REGNING;
    }

}
