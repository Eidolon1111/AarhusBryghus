package Application.Controller;

import Application.Model.*;
import Application.StorageInterface;
import Gui.ControllerInterface;

import java.time.LocalDate;
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

    @Override
    public Salg createSimpelSalg() {
        Salg salg = new Salg();
        storage.addSalg(salg);
        return salg;
    }

    public KomplekstSalg createKompleksSalg(Kunde kunde) {
        KomplekstSalg komplekstSalg = new KomplekstSalg(kunde);
        storage.addSalg(komplekstSalg);
        return komplekstSalg;
    }

    public ArrayList<String> printMellemRegning(Salg salg) {
        return salg.printMellemRegning();
    }

    public String printSamletPrisDKKOgKlip(Salg salg) {
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
            if (kandidat.printMellemRegning().equals(target)) {
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
        if(salg instanceof KomplekstSalg){
            salg.setBetalingsform(betalingsform);
            if(((KomplekstSalg) salg).getAfholdelsesDag() == null){
                ((KomplekstSalg) salg).setStatus(KomplekstSalg.Status.PANTBETALT);
            } else {
                ((KomplekstSalg) salg).setStatus(KomplekstSalg.Status.AFREGNET);
            }
        } else {
            salg.setBetalingsform(betalingsform);
        }
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

    public ArrayList<Kunde> getKunder() {
        return storage.getKunder();
    }

    public Kunde createKunde(String navn, String tlfNr, String email) {
        Kunde kunde = new Kunde(navn, tlfNr, email);
        storage.addKunde(kunde);
        return kunde;
    }

    public void createRundvisning(Kunde kunde, LocalDateTime afholdesesDato,Pris pris, int antal) {
        KomplekstSalg Rundvisning = new KomplekstSalg(kunde);
        Rundvisning.setAfholdelsesDag(afholdesesDato);
        Rundvisning.createSalgslinje(pris, antal);
        storage.addSalg(Rundvisning);
    }

    public Pris findPrisIPrisliste(Prisliste prisliste, String Produktnavn) {
        return prisliste.findPris(Produktnavn);
    }

    public ArrayList<Prisliste> getPrislisterMedSpecifiktProdukt(String Produktnavn){
        ArrayList<Prisliste> res = new ArrayList<>();
        for (Prisliste pl : storage.getPrislister()) {
            for (Pris p : pl.getPrislisten()){
                if (p.getProdukt().getNavn().equals(Produktnavn)){
                    res.add(pl);
                }
            }
        } return res;
    }

    public void setRabatSalg(Salg salg, double rabat) {
        salg.setRabatSalg(rabat);
    }

    public void setRabatSalgslinje(Salgslinje salgslinje, double rabat) {
        salgslinje.setRabat(rabat);
    }

    public void setAfholdelsesDag(KomplekstSalg komplekstSalg, LocalDateTime afholdelsesDag){
        komplekstSalg.setAfholdelsesDag(afholdelsesDag);
    }

    public ArrayList<Salg> dagsRapport(LocalDate dato) {
        ArrayList<Salg> result = new ArrayList<>();
        for (Salg salg : storage.getSalg()) {
            if (salg.getRegistreringsDato().equals(dato)) {
                result.add(salg);
            }
        }
        return result;
    }

    public double beregnDagsomsætning(LocalDate dato) {
        double result = 0.0;
        for (Salg salg : storage.getSalg()) {
            if (salg.getRegistreringsDato().equals(dato)) {
                result += salg.beregnSamletPrisDKK();
            }
        }
        return result;
    }

    public int solgteKlipForPeriode(LocalDate fraDato, LocalDate tilDato) {
        return 0;
    }

    public Prisliste getPrisliste(String navn) {
        Prisliste res = null;
        for (Prisliste pl : getPrislister()) {
            if (pl.getNavn().equals(navn)){
                res = pl;
            }
        } return res;
    }

    public ArrayList<KomplekstSalg> getRundvisninger() {
        ArrayList<KomplekstSalg> rundvisninger = new ArrayList<>();
        for (Salg ss : storage.getSalg()){
            if(ss instanceof KomplekstSalg){
                if(((KomplekstSalg) ss).getAfholdelsesdag() != null && ((KomplekstSalg) ss).getStatus() == KomplekstSalg.Status.REGISTRERET) {
                    rundvisninger.add((KomplekstSalg) ss);
                }
            }
        } return rundvisninger;
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
        Produkt fustageKlosterbrug = this.createProdukt(pg4, "Klosterbryg", "","20 liter");
        Produkt fustageJazzClassic = this.createProdukt(pg4, "Jazz Classic", "", "25 liter");
        Produkt fustageEkstraPilsner = this.createProdukt(pg4, "Ekstra Pilsner", "", "25 liter");
        Produkt fustageCelebration = this.createProdukt(pg4, "Celebration", "", "20 liter");
        Produkt fustageBlondie = this.createProdukt(pg4, "Blondie", "", "25 liter");
        Produkt fustageFårsbryg = this.createProdukt(pg4, "Forårsbryg", "", "20 liter");
        Produkt fustageIndiaPaleAle = this.createProdukt(pg4, "India Pale Ale", "", "20 liter");
        Produkt fustageJuleBryg = this.createProdukt(pg4, "Julebryg", "", "20 liter");
        Produkt fustageImperialStout = this.createProdukt(pg4, "Imperial Stout", "", "20 liter");
        Produkt fustagePant = this.createProdukt(pg4, "Pant", "", "");

        //Kulsyre
        Produkt kulsyre10kg = this.createProdukt(pg5, "10 kg", "", "");
        Produkt kulsyre6kg = this.createProdukt(pg5, "6 kg", "", "");
        Produkt kulsyre4kg = this.createProdukt(pg5, "4 kg", "", "");
        Produkt kulsyrePant = this.createProdukt(pg5, "Pant", "", "");


        //Malt
        this.createProdukt(pg6, "Maltsæk", "", "25 kg");

        //Beklædning
        this.createProdukt(pg7, "t-shirt", "", "");

        //Anlæg
        Produkt anlæg1hane = this.createProdukt(pg8, "1-hane", "", "");
        Produkt anlæg2hane = this.createProdukt(pg8, "2-haner", "", "");
        Produkt anlægBarflerehaner = this.createProdukt(pg8, "Bar med flere haner", "", "");
        Produkt anlægLevering = this.createProdukt(pg8, "Levering", "", "");
        Produkt anlægKrus = this.createProdukt(pg8, "Krus", "", "");

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
        Kunde k1 = this.createKunde("Hans", "60453980", "Hans@gmail.com");
        Kunde k2 = this.createKunde("Jens", "61235789", "Jens@gmail.com");
        Kunde k3 = this.createKunde("Poul", "23466892", "Poul@gmail.com");

        //Prislister
        Prisliste rundvisning = this.createPrisliste("Rundvisning");
        Prisliste udlejning = this.createPrisliste("Udlejning");

        //Rundvisnings produkter
        Produkt rundvisningDag = this.createProdukt(pg11,
                "Rundvisning dag", "", "Pris pr person");
        Produkt rundvisningAften = this.createProdukt(pg11,
                "Rundvisning aften", "", "Pris pr person");

        //Priser til rundvisningsprodukter
        Pris rundvisningDagPris = rundvisning.createPrisTilPrisliste(rundvisningDag, 100, 0);
        Pris rundvisningAftenPris = rundvisning.createPrisTilPrisliste(rundvisningAften, 150, 0);

        //Oprettelse af rundvisninger
        this.createRundvisning(k1,LocalDateTime.of(2023, 1, 20, 10, 0),rundvisningDagPris,20);
        this.createRundvisning(k2,LocalDateTime.of(2022, 12, 20, 20, 0),rundvisningAftenPris,30);
        this.createRundvisning(k3,LocalDateTime.of(2022, 11, 30, 10, 0),rundvisningDagPris,40);

        //Priser til Udlejning
        udlejning.createPrisTilPrisliste(anlæg1hane,250, 0);
        udlejning.createPrisTilPrisliste(anlæg2hane,400, 0);
        udlejning.createPrisTilPrisliste(anlægBarflerehaner,500, 0);
        udlejning.createPrisTilPrisliste(anlægLevering,500, 0);
        udlejning.createPrisTilPrisliste(anlægKrus,60, 0);
        udlejning.createPrisTilPrisliste(anlæg1hane,250, 0);

        udlejning.createPrisTilPrisliste(fustageKlosterbrug,775, 0);
        udlejning.createPrisTilPrisliste(fustageJazzClassic,625, 0);
        udlejning.createPrisTilPrisliste(fustageEkstraPilsner,575, 0);
        udlejning.createPrisTilPrisliste(fustageCelebration, 775, 0);
        udlejning.createPrisTilPrisliste(fustageBlondie,700, 0);
        udlejning.createPrisTilPrisliste(fustageFårsbryg,775, 0);
        udlejning.createPrisTilPrisliste(fustageIndiaPaleAle,775, 0);
        udlejning.createPrisTilPrisliste(fustageJuleBryg,775, 0);
        udlejning.createPrisTilPrisliste(fustageImperialStout,775, 0);
        udlejning.createPrisTilPrisliste(fustagePant,200, 0);

        udlejning.createPrisTilPrisliste(kulsyre10kg,600, 0);
        udlejning.createPrisTilPrisliste(kulsyre6kg,400, 0);
        udlejning.createPrisTilPrisliste(kulsyre4kg,300, 0);
        udlejning.createPrisTilPrisliste(kulsyrePant,1000, 0);


    }
}
