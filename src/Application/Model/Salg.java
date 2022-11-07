package Application.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Salg implements Observerbar{
    private LocalDate registreringsDato;
    private ArrayList<Salgslinje> salgslinjer = new ArrayList<Salgslinje>();
    private ArrayList<Observer> observers = new ArrayList<>();
    private Betalingsform betalingsform;
    private Status status;
    private double rabat;


    public Salg() {
        this.registreringsDato = LocalDate.now();
    }

    public Salgslinje createSalgslinje(Pris Pris, int antal){
        Salgslinje salgslinje = new Salgslinje(antal, Pris);
        salgslinjer.add(salgslinje);
        notifyObservers();
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
        if (rabat != 0){
            if(rabat < 1){
                result = (result) - ((result) * rabat);
            } else {
                result = (result) - rabat;
            }
        }
        return result;
    }

    public int beregnSamletPrisKlip() {
        int result = 0;
        for (Salgslinje s : salgslinjer){
            result += s.beregnPrisKlip();
        }
        return result;
    }

    public ArrayList<Salgslinje> getSalgslinjer() {
        return new ArrayList<>(salgslinjer);
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

    public double getRabat(){
        return rabat;
    }

    public LocalDate getRegistreringsDato() {
        return registreringsDato;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus(){
        return status;
    }

    @Override
    public void addObserver(Observer observer) {
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }

    @Override
    public void removeObsercer(Observer observer) {
        observers.remove(observer);

    }

    private void notifyObservers(){
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public String toString() {
        return beregnSamletPrisDKK() + " DKK, " + betalingsform;
    }

    public enum Betalingsform {
        DANKORT, KONTANT, KLIPPEKORT, MOBILEPAY, REGNING;
    }

    public enum Status {
        REGISTRERET, PANTBETALT, AFREGNET;
    }
}
