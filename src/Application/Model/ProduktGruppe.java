package Application.Model;

import java.util.ArrayList;

public class ProduktGruppe {
    private String navn;
    private ArrayList<Produkt> produkts;

    public ProduktGruppe(String navn){
        this.navn = navn;
        produkts = new ArrayList<>();
    }

    public Produkt createSimpelProdukt(String navn, int antalEnheder, String enhed, String beskrivelse, boolean pantPligtig){
        Produkt produkt = new SimpelProdukt(navn, antalEnheder, enhed, beskrivelse, this, pantPligtig);
        produkts.add(produkt);
        return produkt;
    }

    public ProduktSamling createProduktSamling(String navn, String beskrivelse) {
        ProduktSamling produkt = new ProduktSamling(navn, beskrivelse, this);
        produkts.add(produkt);
        return produkt;
    }

    public String getNavn() {
        return navn;
    }
    
    public ArrayList<Produkt> getProdukts() {
        return new ArrayList<>(produkts);
    }

    //public ArrayList<Produkt> getProduktsFraProduktGuppenIPrisliste(Prisliste prisliste){
    //    ArrayList<Produkt> result = new ArrayList<>();
    //    for (Pris p : prisliste.getPrislisten()){
    //        if(this.getProdukts().contains(p.getProdukt())){
    //            result.add(p.getProdukt());
    //        }
    //    }
    //    return result;
    //}

    @Override
    public String toString() {
        return navn;
    }
}
