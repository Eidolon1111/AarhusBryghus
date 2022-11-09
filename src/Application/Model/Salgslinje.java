package Application.Model;

import java.util.ArrayList;

public class Salgslinje {
    private int antal;
    private Pris pris;
    private double rabat;
    private ArrayList<Observer> observers = new ArrayList<>();

    public Salgslinje(int antal, Pris pris) {
        this.antal = antal;
        this.pris = pris;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal){
        this.antal = antal;
    }

    public Produkt getProdukt() {
        return pris.getProdukt();
    }

    public double beregnPrisDKK(){
        double result;
        if(rabat != 0){
            if(rabat < 1 ){
                result = (pris.getPrisDKK() * antal) - ((pris.getPrisDKK() * antal) * rabat);
            } else {
                result = (pris.getPrisDKK() * antal) - rabat;
            }
        }else {
            result = (pris.getPrisDKK() * antal);
        }
        return result;
    }

    public int beregnPrisKlip(){
        return pris.getKlip() * antal;
    }

    public boolean klippeKortBetalingMuligt(){
        boolean result = false;
        if(pris.getKlip() != 0){
            result = true;
        }
        return result;
    }

    public void setRabat(double rabat){
        this.rabat = rabat;
    }

    public double getRabat(){
        return rabat;
    }

    public Pris getPris(){
        return pris;
    }

    @Override
    public String toString() {
        String result;
        if (klippeKortBetalingMuligt()) {
            if (rabat != 0 && rabat < 1) {
                result = pris.getProdukt().getNavn() + ", " + "antal: " + antal + " " + "DKK: " +
                        beregnPrisDKK() + " / Klip: " + beregnPrisKlip() + " -" + (rabat * 100) + "%";
            } else if (rabat != 0 && rabat > 1) {
                result = pris.getProdukt().getNavn() + ", " + "antal: " + antal + " " + "DKK: " +
                        beregnPrisDKK() + " / Klip: " + beregnPrisKlip() + " -" + rabat + " DKK";
            } else {
                result = pris.getProdukt().getNavn() + ", " + "antal: " + antal + " " + "DKK: " +
                        beregnPrisDKK() + " / Klip: " + beregnPrisKlip();
            }
        } else {
            if (rabat != 0 && rabat < 1) {
                result = pris.getProdukt().getNavn() + ", " + "antal: " + antal + " " + "DKK: " +
                        beregnPrisDKK() + " -" + (rabat * 100) + "%";
            } else if (rabat != 0 && rabat > 1) {
                result = pris.getProdukt().getNavn() + ", " + "antal: " + antal + " " + "DKK: " +
                        beregnPrisDKK() + " -" + rabat + " DKK";
            } else {
                result = pris.getProdukt().getNavn() + ", " + "antal: " + antal + " " + "DKK: " +
                        beregnPrisDKK();
            }
        }
        return result;
    }
}
