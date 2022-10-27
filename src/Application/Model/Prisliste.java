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
        //prislisten.add(prisListeProdukt);
        addPris(prisListeProdukt);
        return prisListeProdukt;
    }

    public Pris createPrisTilPrisliste(Produkt produkt, double pris) {
        Pris prisListeProdukt = new Pris(pris, produkt);
        //prislisten.add(prisListeProdukt);
        addPris(prisListeProdukt);
        return prisListeProdukt;
    }
    
    private void addPris(Pris pris) {
        if (prislisten.isEmpty()) {
            prislisten.add(pris);
        } else {
            for (int i = 0; i < prislisten.size(); i++) {
                Pris p = prislisten.get(i);
                if (p.getProdukt().compareTo(pris.getProdukt()) == 0) {
                    if (p.getPris() != pris.getPris()) {
                        p.setPris(pris.getPris());
                    }
                    if (p.getKlip() != pris.getKlip()) {
                        p.setKlip(pris.getKlip());
                    }
                    return;
                } else {
                    prislisten.add(pris);
                    return;
                }
            }
        }
    }

    public ArrayList<Pris> getPrislisten() {
        return new ArrayList<>(prislisten);
    }
    
    public void fjernPris(Pris pris) {
        if (prislisten.contains(pris)) {
            prislisten.remove(pris);
        }
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
    
    @Override
    public String toString() {
        return navn;
    }
}
