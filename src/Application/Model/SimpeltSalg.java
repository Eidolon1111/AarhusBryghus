package Application.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class SimpeltSalg {
    private LocalDate registreringsDato;
    private ArrayList<Salgslinje> salgslinjer = new ArrayList<Salgslinje>();
    private Betalingsform betalingsform;
    private double rabat;


    public SimpeltSalg() {
        this.registreringsDato = LocalDate.now();
    }

    public Salgslinje createSalgslinje(Pris Pris, int antal){
        Salgslinje salgslinje = new Salgslinje(antal, Pris);
        salgslinjer.add(salgslinje);
        return salgslinje;
    }

    public void fjernSalgsLinje(Salgslinje salgslinje){
        salgslinjer.remove(salgslinje);
    }


    public double beregnSamletPrisDKK() {
        double result = 0;
        for (Salgslinje s : salgslinjer){
            result += s.beregnPrisDKK();
        }
        if(rabat < 1){
            result = (result) * rabat;
        } else {
            result = (result) - rabat;
        }
        return result;
    }

    public int beregnSamletPrisKlip() {
        int result = 0;
        for (Salgslinje s : salgslinjer){
            result += s.beregnPrisDKK();
        }
        return result;
    }

    public ArrayList<Salgslinje> getSalgslinjer() {
        return new ArrayList<>(salgslinjer);
    }

    public ArrayList<String> printMellemRegning(){
        ArrayList<String> result = new ArrayList<>();
        for (Salgslinje salgslinje : salgslinjer){
            result.add(salgslinje.printMellemRegning());
        }
        return result;
    }

    public void setBetalingsform(Betalingsform betalingsform){
        this.betalingsform = betalingsform;
    }

    public boolean klippeKortBetalingMuligt() {
        boolean result = false;
        int count = 0;
        for (Salgslinje salgslinje : salgslinjer){
            if(salgslinje.klippeKortBetalingMuligt()){
                count++;
            }
        }
        if(count == salgslinjer.size()){
            result = true;
        }
        return result;
    }

    public Betalingsform getBetalingsform() {
        return this.betalingsform;
   }

    public void setRabatSalg(double rabat){
        this.rabat = rabat;
    }

    public enum Betalingsform {
        DANKORT, KONTANT, KLIPPEKORT, MOBILEPAY, REGNING;
    }

}
