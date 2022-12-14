package Application.Model;

import java.util.ArrayList;

public class ProduktGruppe {
    private String navn;
    private ArrayList<Produkt> produkts;
    private Produkt pantProdukt;

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

    public void setPantProdukt(Produkt pantProdukt){
        this.pantProdukt = pantProdukt;
    }

    public Produkt getPantProdukt(){
        return pantProdukt;
    }

    @Override
    public String toString() {
        return navn;
    }
}
