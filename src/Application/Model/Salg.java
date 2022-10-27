package Application.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Salg {
    private LocalDate registreringsDato;
    private ArrayList<Salgslinje> salgslinjer = new ArrayList<Salgslinje>();
    private Betalingsform betalingsform;


    public Salg(LocalDate registreringsDato) {
        this.registreringsDato = LocalDate.now();
    }

    public Salgslinje createSalgslinje(Produkt produkt, int antal){
        Salgslinje salgslinje = new Salgslinje(antal, produkt);
        salgslinjer.add(salgslinje);
        return salgslinje;
    }

    public double beregnSamletPris(Prisliste prisliste) {
        double result = 0;
        for (Salgslinje s : salgslinjer){
            result += prisliste.findPrisPaaProdukt(s.getProdukt()) * s.getAntal();
        }
        return result;
    }

    public ArrayList<Salgslinje> getSalgslinjer() {
        return new ArrayList<>(salgslinjer);
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
