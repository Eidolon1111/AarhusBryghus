package Application.Model;

import java.util.ArrayList;

public class ProduktSamling extends Produkt{
    ArrayList<Produkt> produkter;

    ProduktSamling(String navn, String beskrivelse, ProduktGruppe produktGruppe) {
        super(navn, beskrivelse, produktGruppe);
        produkter = new ArrayList<>();
    }

    public void addProdukt(Produkt produkt) {
        produkter.add(produkt);
    }

    public void removeProdukt(Produkt produkt) {
        produkter.remove(produkt);
    }

    public ArrayList<Produkt> getProdukter() {
        return new ArrayList<>(produkter);
    }
}
