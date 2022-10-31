package Application.Controller;

import Application.Model.*;
import Application.StorageInterface;
import Gui.ControllerInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller implements ControllerInterface {
    private static Controller controller;
    private StorageInterface storage;

    public Controller(StorageInterface storage) {
        this.storage = storage;
    }
    
    public ArrayList<Prisliste> getPrislister() {
        return storage.getPrislister();
    }

    public ArrayList<ProduktGruppe> getProduktGrupper() {
        return storage.getProduktGrupper();
    }
    
    public ArrayList<Produkt> getProdukter() {
        ArrayList<Produkt> result = new ArrayList<>();
        for (ProduktGruppe pg : getProduktGrupper()) {
            result.addAll(pg.getProdukts());
        }
        return result;
    }

    public ArrayList<Salg> getSalg() {return storage.getSalg(); }

    public Prisliste createPrisliste(String navn) {
        Prisliste pl = new Prisliste(navn);
        storage.addPrisliste(pl);
        return pl;
    }

    public Pris createPris(Prisliste prisliste, Produkt produkt, double pris, int klip) {
        Pris p = prisliste.createPrisTilPrisliste(produkt, pris, klip);
        return p;
    }
    
    public void fjernPris(Prisliste prisliste, Pris pris) {
        prisliste.fjernPris(pris);
    }

    public ProduktGruppe createProduktGruppe(String navn) {
        ProduktGruppe pg = new ProduktGruppe(navn);
        storage.addProduktGruppe(pg);
        return pg;
    }

    public Produkt createProdukt(ProduktGruppe produktGruppe, String navn, String beskrivelse, String enhed) {
        Produkt p = produktGruppe.createProdukt(navn, enhed, beskrivelse);
        return p;
    }


    public Salg createSalg() {
        Salg s = new Salg();
        storage.addSalg(s);
        return s;
    }

    public ArrayList<String> printMellemRegning(Prisliste prisliste, Salg salg) {
        return salg.printMellemRegning(prisliste);
    }

    public String printSamletPrisDKKOgKlip(Prisliste prisliste, Salg salg) {
        return "DKK: " + salg.beregnSamletPrisDKK(prisliste) + " / Klip: " + salg.beregnSamletPrisKlip(prisliste);
    }

    public Salgslinje createSalgslinje(Salg salg, int antal, Pris pris) {
        Salgslinje sl = salg.createSalgslinje(pris, antal);
        return sl;
    }

    public Salgslinje findSalgslinjeFraKurv(Prisliste prisliste, Salg salg, String target){
        int index = 0;
        Salgslinje kandidat;
        Salgslinje result = null;
        while (result == null && index <= salg.getSalgslinjer().size()) {
            kandidat = salg.getSalgslinjer().get(index);
            if (kandidat.printMellemRegning(prisliste).equals(target)) {
                result = kandidat;
            } else {
                index++;
            }
        }
        return result;
    }

    public void fjernSalgslinje(Salg salg, Salgslinje salgslinje) {
        salg.fjernSalgsLinje(salgslinje);
    }

    public ArrayList<Produkt> getProdukterFraProduktgruppe(ProduktGruppe pg){
        return pg.getProdukts();
    }

    public String getProduktGruppeNavn(ProduktGruppe pg) {
        return pg.getNavn();
    }

    public void betalSalg(Salg salg, Salg.Betalingsform betalingsform) {
        salg.setBetalingsform(betalingsform);
    }

    public ArrayList<ProduktGruppe> getProduktGrupperIPrisliste(Prisliste prisliste) {
        ArrayList<ProduktGruppe> result = new ArrayList<>();
        for(Pris p : prisliste.getPrislisten()){
            ProduktGruppe pG = p.getProdukt().getProduktGruppe();
            if(!result.contains(pG)){
                result.add(pG);
            }
        }
        return result;
    }

    public boolean klippeKortBetalingMuligt(Salg salg) {
        return salg.klippeKortBetalingMuligt();
    }

    public ArrayList<Salg.Betalingsform> getMuligeBetalingsformer(Salg salg){
        ArrayList<Salg.Betalingsform> muligeBetalingsformer = new ArrayList<>(Arrays.asList(Salg.Betalingsform.values()));
        if(!salg.klippeKortBetalingMuligt()){
            muligeBetalingsformer.remove(Salg.Betalingsform.KLIPPEKORT);
        }
        return  muligeBetalingsformer;
    }

    public Pris findPrisPaaProdukt(Prisliste prisliste, Produkt produkt) {
        return prisliste.findPrisPaaProdukt(produkt);
    }




    public void init(){
        Prisliste fredagsbar = this.createPrisliste("Fredagsbar");
        Prisliste butik = this.createPrisliste("Butik");

        ProduktGruppe pg1 = this.createProduktGruppe("Flaske");
        ProduktGruppe pg2 = this.createProduktGruppe("Fadøl");
        ProduktGruppe pg3 = this.createProduktGruppe("Spiritus");
        ProduktGruppe pg4 = this.createProduktGruppe("Fustage");
        ProduktGruppe pg5 = this.createProduktGruppe("Kulsyre");
        ProduktGruppe pg6 = this.createProduktGruppe("Malt");
        ProduktGruppe pg7 = this.createProduktGruppe("Beklædning");
        ProduktGruppe pg8 = this.createProduktGruppe("Anlæg");
        ProduktGruppe pg9 = this.createProduktGruppe("Glas");
        ProduktGruppe pg10 = this.createProduktGruppe("Sampakninger");
        ProduktGruppe pg11 = this.createProduktGruppe("Rundvisning");

        //Flaske produkter
        Produkt p1 = this.createProdukt(pg1,"Klosterbryg","","");
        Produkt p2 = this.createProdukt(pg1,"Sweet Georgia Brown","","");
        Produkt p3 = this.createProdukt(pg1,"Extra Pilsner","","");
        Produkt p4 = this.createProdukt(pg1,"Celebration","","");
        Produkt p5 = this.createProdukt(pg1,"Blondie","","");

        //Fadøl produkter
        this.createProdukt(pg2, "Klosterbryg", "", "40 cl");
        this.createProdukt(pg2, "Jazz Classic", "", "40 cl");
        this.createProdukt(pg2, "Extra Pilsner", "", "40 cl");
        this.createProdukt(pg2, "Celebration", "", "40 cl");
        this.createProdukt(pg2, "Blondie", "", "40 cl");

        //Spritus produkter
        this.createProdukt(pg3, "Whisky", "45%", "50 cl rør");

        //Fustage produkter
        this.createProdukt(pg4, "Klosterbryg", "","20 liter");

        //Kulsyre
        this.createProdukt(pg5, "6 kg", "", "");

        //Malt
        this.createProdukt(pg6, "Maltsæk", "", "25 kg");

        //Beklædning
        this.createProdukt(pg7, "t-shirt", "", "");

        //Anlæg
        this.createProdukt(pg8, "1-hane", "", "");

        //Glas
        this.createProdukt(pg9, "Glas", "uanset størrelse", "");

        //Sampakninger
        this.createProdukt(pg10, "Gaveæske", "2 øl, 2 glas", "");

        //Rundvisning
        this.createProdukt(pg11, "Rundvisning", "kan variere afhængig af dag/aften/studierabat", "pr person dag");


        //Priser for fredagsbar for flasker
        this.createPris(fredagsbar,p1, 70,2);
        this.createPris(fredagsbar,p2, 70,2);
        this.createPris(fredagsbar,p3, 70,2);
        this.createPris(fredagsbar,p4, 70,2);
        this.createPris(fredagsbar,p5, 70,2);

        //Priser for butik for flasker
        this.createPris(butik,p1, 36,0);
        this.createPris(butik,p2, 36,0);
        this.createPris(butik,p3, 36,0);
        this.createPris(butik,p4, 36,0);
        this.createPris(butik,p5, 36,0);
    }
}
