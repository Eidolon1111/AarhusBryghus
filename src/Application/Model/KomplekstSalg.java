package Application.Model;

import java.time.LocalDateTime;

public class KomplekstSalg extends Salg {
    private Status status;
    private Kunde kunde;
    private LocalDateTime dato2; //Anvendes til afregning af udlejelse og booking

    public KomplekstSalg(Kunde kunde) {
        super();
        this.status = Status.REGISTRERET;
        this.kunde = kunde;
    }

    public Salgslinje createModregning(Salgslinje salgslinje, int antal){
        Pris pris = new Pris((-salgslinje.beregnPrisDKK() / salgslinje.getAntal()),0,salgslinje.getProdukt());
        return super.createSalgslinje(pris, antal);
    }

    public double beregnReturBeløbUdlejning(){
        double result = 0;
        for (Salgslinje salgslinje : super.getSalgslinjer()){
            if (salgslinje.beregnPrisDKK() < 0){
                result += salgslinje.beregnPrisDKK();
            }
        }
        return result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setAfholdelsesDag(LocalDateTime afregningsDag){
        this.dato2 = afregningsDag;
    }

    public LocalDateTime getAfholdelsesdag() {
        return dato2;
    }


    public enum Status {
        REGISTRERET, PANTBETALT, AFREGNET;
    }

    @Override
    public String toString() {
        if (dato2 == null) {
            return kunde.getNavn() + " Registrerings dato: " + super.getRegistreringsDato();
        } else {
            return kunde.getNavn() + ", " + dato2;
        }
    }
}
