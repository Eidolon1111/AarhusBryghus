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

    public double findPrisPaaProdukt(Produkt produkt){
        double res = 0.0;
        for (Pris p : prislisten){
            if (p.getProdukt().getNavn().equals(produkt.getNavn())){
                res = p.getPris();
            }
        }
        return res;
    }
    
    public String toString() {
        return navn;
    }
}
