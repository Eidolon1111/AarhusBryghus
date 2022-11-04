package Application.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Udlejning extends Salg{

    private LocalDate afregningsDato;
    private Kunde kunde;

    public Udlejning(Kunde kunde) {
        super();
        super.setStatus(Salg.Status.REGISTRERET);
        this.kunde = kunde;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setAfregningsDato(LocalDate afregningsDato){
        this.afregningsDato = afregningsDato;
    }

    public LocalDate getAfregningsDato() {
        return afregningsDato;
    }

    public Salgslinje createModregning(Salgslinje salgslinje, int antal){
        Pris pris = new Pris((-salgslinje.beregnPrisDKK() / salgslinje.getAntal()),0,salgslinje.getProdukt());
        return super.createSalgslinje(pris, antal);
    }

    public ArrayList<Salgslinje> getModregninger(){
        ArrayList<Salgslinje> modregninger = new ArrayList<>();
        for(Salgslinje salgslinje : super.getSalgslinjer()){
            if (salgslinje.beregnPrisDKK() < 0){
                modregninger.add(salgslinje);
            }
        }
        return modregninger;
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

    //TODO
    @Override
    public String toString() {
        return kunde.getNavn() + ", " + super.getRegistreringsDato();
    }




}
