package Application.Model;

import java.util.ArrayList;

public class ProduktGruppe {
    private String navn;
    private ArrayList<Produkt> produkts;

    public ProduktGruppe(String navn){
        this.navn = navn;
        produkts = new ArrayList<>();
    }

    public Produkt createSimpelProdukt(String navn, String enhed, String beskrivelse){
        Produkt produkt = new SimpelProdukt(navn, enhed, beskrivelse, this);
        produkts.add(produkt);
        return produkt;
    }

    public String getNavn() {
        return navn;
    }
    
    public ArrayList<Produkt> getProdukts() {
        return new ArrayList<>(produkts);
    }

    @Override
    public String toString() {
        return navn;
    }
}
