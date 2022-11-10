package Application.Model;

import java.util.ArrayList;

public class Prisliste {
    private ArrayList<Pris> prislisten = new ArrayList<>();
    private String navn;
    private boolean tilBrugIDagligtSalg;

    public Prisliste(String navn, boolean tilBrugIDagligtSalg) {
        this.navn = navn;
        this.tilBrugIDagligtSalg = tilBrugIDagligtSalg;
    }

    public Pris createPrisTilPrisliste(Produkt produkt, double pris, int klip) {
        Pris prisListeProdukt = new Pris(pris, klip, produkt, this);
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
                    if (p.getPrisDKK() != pris.getPrisDKK()) {
                        p.setPris(pris.getPrisDKK());
                    }
                    if (p.getKlip() != pris.getKlip() && pris.getKlip() >= 0) {
                        p.setKlip(pris.getKlip());
                    }
                    return;
                }
            }
            prislisten.add(pris);
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

    public Pris findPrisPaaProdukt(Produkt produkt){
        Pris result = null;
        for (Pris pris : prislisten){
            if (pris.getProdukt() == produkt) {
                result = pris;
            }
        }
        return result;
    }

    public String getNavn() {
        return navn;
    }

    public boolean isTilBrugIDagligtSalg(){
        return tilBrugIDagligtSalg;
    }
    
    @Override
    public String toString() {
        return navn;
    }
}
