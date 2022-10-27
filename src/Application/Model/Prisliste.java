package Application.Model;

import java.util.ArrayList;

public class Prisliste {
    private ArrayList<Pris> prislisten = new ArrayList<>();
    private String navn;

    public Prisliste(String navn) {
        this.navn = navn;
    }

    public Pris createPrisTilPrisliste(Produkt produkt, double pris, int klip) {
        Pris prisListeProdukt = new Pris(pris, klip, produkt);
        prislisten.add(prisListeProdukt);
        return prisListeProdukt;
    }

    public ArrayList<Pris> getPrislisten() {
        return new ArrayList<>(prislisten);
    }
    
    public void fjernPris(Pris pris) {
        if (prislisten.contains(pris)) {
            prislisten.remove(pris);
        }
    }

    public double findPrisPaaProduktDKK(Produkt produkt){
        double res = 0.0;
        for (Pris p : prislisten){
            if (p.getProdukt().getNavn().equals(produkt.getNavn())){
                res = p.getPrisDKK();
            }
        }
        return res;
    }

    public int findPrisPaaProduktKlip(Produkt produkt){
        int res = 0;
        for (Pris p : prislisten){
            if (p.getProdukt().getNavn().equals(produkt.getNavn())){
                res = p.getPrisKlip();
            }
        }
        return res;
    }

    
    public String toString() {
        return navn;
    }
}
