package Application.Controller;

import Application.Model.*;
import Application.StorageInterface;
import Gui.ControllerInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

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

    public ArrayList<SimpeltSalg> getSalg() {return storage.getSalg(); }

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

    @Override
    public SimpeltSalg createSimpelSalg() {
        SimpeltSalg simpeltSalg = new SimpeltSalg();
        storage.addSalg(simpeltSalg);
        return simpeltSalg;
    }

    public SimpeltSalg createKompleksSalg(Kunde kunde) {
        KomplekstSalg komplekstSalg = new KomplekstSalg(kunde);
        storage.addSalg(komplekstSalg);
        return komplekstSalg;
    }

    public ArrayList<String> printMellemRegning(SimpeltSalg salg) {
        return salg.printMellemRegning();
    }

    public String printSamletPrisDKKOgKlip(SimpeltSalg salg) {
        String result;
        if(salg.getRabat() != 0 && salg.getRabat() < 1){
            result = "DKK: " + salg.beregnSamletPrisDKK() + " / Klip: " + salg.beregnSamletPrisKlip() + " -"
                    + (salg.getRabat() * 100) + "%";
        } else if(salg.getRabat() > 1){
            result = result = "DKK: " + salg.beregnSamletPrisDKK() + " / Klip: " + salg.beregnSamletPrisKlip() + " -"
                    + salg.getRabat() + " DKK";
        } else {
            result = "DKK: " + salg.beregnSamletPrisDKK() + " / Klip: " + salg.beregnSamletPrisKlip();
        }
        return result;
    }

    public Salgslinje createSalgslinje(SimpeltSalg salg, int antal, Pris pris) {
        Salgslinje sl = salg.createSalgslinje(pris, antal);
        return sl;
    }

    public Salgslinje findSalgslinjeFraKurv(Prisliste prisliste, SimpeltSalg salg, String target){
        int index = 0;
        Salgslinje kandidat;
        Salgslinje result = null;
        while (result == null && index <= salg.getSalgslinjer().size()) {
            kandidat = salg.getSalgslinjer().get(index);
            if (kandidat.printMellemRegning().equals(target)) {
                result = kandidat;
            } else {
                index++;
            }
        }
        return result;
    }

    public void fjernSalgslinje(SimpeltSalg salg, Salgslinje salgslinje) {
        salg.fjernSalgsLinje(salgslinje);
    }

    public ArrayList<Produkt> getProdukterFraProduktgruppe(ProduktGruppe pg){
        return pg.getProdukts();
    }

    public String getProduktGruppeNavn(ProduktGruppe pg) {
        return pg.getNavn();
    }

    public void betalSalg(SimpeltSalg salg, SimpeltSalg.Betalingsform betalingsform) {
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

    public boolean klippeKortBetalingMuligt(SimpeltSalg salg) {
        return salg.klippeKortBetalingMuligt();
    }

    public ArrayList<SimpeltSalg.Betalingsform> getMuligeBetalingsformer(SimpeltSalg salg){
        ArrayList<SimpeltSalg.Betalingsform> muligeBetalingsformer = new ArrayList<>(Arrays.asList(SimpeltSalg.Betalingsform.values()));
        if(!salg.klippeKortBetalingMuligt()){
            muligeBetalingsformer.remove(SimpeltSalg.Betalingsform.KLIPPEKORT);
        }
        return  muligeBetalingsformer;
    }

    public Pris findPrisPaaProdukt(Prisliste prisliste, Produkt produkt) {
        return prisliste.findPrisPaaProdukt(produkt);
    }

    public ArrayList<Kunde> getKunder() {
        return storage.getKunder();
    }

    public void createKunde(String navn, String tlfNr, String email) {
        Kunde kunde = new Kunde(navn, tlfNr, email);
        storage.addKunde(kunde);
    }

    public void createRundvisning(Kunde kunde, LocalDateTime afholdesesDato) {
        KomplekstSalg Rundvisning = new KomplekstSalg(kunde);
        Rundvisning.setAfholdelsesDag(afholdesesDato);
        storage.addSalg(Rundvisning);
    }

    public void setRabatSalg(SimpeltSalg salg, double rabat) {
        salg.setRabatSalg(rabat);
    }

    public void setRabatSalgslinje(Salgslinje salgslinje, double rabat) {
        salgslinje.setRabat(rabat);
    }

    public void setAfholdelsesDag(KomplekstSalg komplekstSalg, LocalDateTime afholdelsesDag){
        komplekstSalg.setAfholdelsesDag(afholdelsesDag);
    }

    public Prisliste getPrisliste (String navn) {
        Prisliste res = null;
        for (Prisliste pl : getPrislister()) {
            if (pl.getNavn().equals(navn)){
                res = pl;
            }
        } return res;
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


        //Initialisering af objekter anvendt i rundvisning

        //Kunder
        this.createKunde("Hans", "60453980", "Hans@gmail.com");
        this.createKunde("Jens", "61235789", "Jens@gmail.com");
        this.createKunde("Poul", "23466892", "Poul@gmail.com");

        //Prislister
        Prisliste rundvisning = this.createPrisliste("Rundvisning");
        Prisliste udlejning = this.createPrisliste("Udlejning");

        //Rundvisnings produkter
        Produkt rundvisningDag = this.createProdukt(pg11,
                "Rundvisning dag", "", "Pris pr person");
        Produkt rundvisningAften = this.createProdukt(pg11,
                "Rundvisning aften", "", "Pris pr person");

        //Priser til rundvisningsprodukter
        rundvisning.createPrisTilPrisliste(rundvisningDag, 100, 0);
        rundvisning.createPrisTilPrisliste(rundvisningAften, 150, 0);

        //TODO
        //Udlejnings Produkter
    }
}
