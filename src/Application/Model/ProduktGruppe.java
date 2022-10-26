package Application.Model;

import java.util.ArrayList;

public class ProduktGruppe {
    private String navn;
    private ArrayList<Produkt> produkts;

    public ProduktGruppe(String navn){
        this.navn = navn;
        produkts = new ArrayList<>();
    }

    public Produkt createProdukt(String navn, String enhed, String beskrivelse){
        Produkt produkt = new Produkt(navn, enhed, beskrivelse);
        produkts.add(produkt);
        return produkt;
    }
}
