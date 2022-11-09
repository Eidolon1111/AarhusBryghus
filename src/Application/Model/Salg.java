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

    public Salgslinje createSalgslinje(Pris pris, int antal){
        Salgslinje salgslinje;
        if(pris != null) {
            if(antal > 0){
                salgslinje = new Salgslinje(antal, pris);
                salgslinjer.add(salgslinje);
                notifyObservers();
            } else {
                throw new IllegalArgumentException("antal skal være over 0");
            }
        } else {
            throw new IllegalArgumentException("Prisen må ikke være null");
        }
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
        if (rabat != 0) {
            if (rabat < 1) {
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
        if(betalingsform != null){
            this.betalingsform = betalingsform;
        } else {
            throw new IllegalArgumentException("betalingsform må ikke være null");
        }
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
        if(rabat >= 0){
            this.rabat = rabat;
        } else throw new IllegalArgumentException("Rabat skal være over eller lig 0");
    }

    public double getRabat(){
        return rabat;
    }

    public LocalDate getRegistreringsDato() {
        return registreringsDato;
    }

    public void setStatus(Status status) {
        if(status != null){
            this.status = status;
        } else throw new IllegalArgumentException("Status må ikke være null");
    }

    public Status getStatus(){
        return status;
    }

    @Override
    public void addObserver(Observer observer) {
        if(!observers.contains(observer)){
            observers.add(observer);
        } else throw new IllegalArgumentException("Observeren er allerede tilknyttet");
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    void notifyObservers(){
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public ArrayList<Observer> getObservers() {
        return new ArrayList<>(observers);
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
