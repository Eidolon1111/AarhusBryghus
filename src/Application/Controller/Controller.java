package Application.Controller;

import Application.Model.*;
import Application.StorageInterface;
import Gui.ControllerInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller implements ControllerInterface {
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

    public Produkt createSimpelProdukt(ProduktGruppe produktGruppe, String navn, String beskrivelse, int antalEnheder, String enhed) {
        Produkt p = produktGruppe.createSimpelProdukt(navn, antalEnheder, enhed, beskrivelse);
        return p;
    }

    public ProduktSamling createProduktSamling(ProduktGruppe produktGruppe, String navn, String beskrivelse) {
        ProduktSamling p = produktGruppe.createProduktSamling(navn, beskrivelse);
        return p;
    }

    public Salg createSimpelSalg() {
        Salg salg = new Salg();
        storage.addSalg(salg);
        return salg;
    }

    public ArrayList<Salgslinje> getSalgslinjerPaaSalg(Salg salg) {
        return salg.getSalgslinjer();
    }

    public ArrayList<Salgslinje> getModregningerPaaUdlejning(Udlejning udlejning) {
        return udlejning.getModregninger();
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

    //TODO
//    public Salgslinje findSalgslinjeFraKurv(Prisliste prisliste, Salg salg, String target){
//        int index = 0;
//        Salgslinje kandidat;
//        Salgslinje result = null;
//        while (result == null && index <= salg.getSalgslinjer().size()) {
//            kandidat = salg.getSalgslinjer().get(index);
//            if (kandidat.printMellemRegning().equals(target)) {
//                result = kandidat;
//            } else {
//                index++;
//            }
//        }
//        return result;
//    }

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
        if(salg instanceof Udlejning){
            salg.setStatus(Salg.Status.PANTBETALT);
        } else {
            salg.setStatus(Salg.Status.AFREGNET);
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

    //TODO tjek om denne bliver brugt
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

    public Rundvisning createRundvisning(Kunde kunde, LocalDateTime afholdesesDato, Pris pris, int antal) {
        Rundvisning rundvisning = new Rundvisning(kunde);
        rundvisning.setAfholdelsesDag(afholdesesDato);
        rundvisning.createSalgslinje(pris, antal);
        storage.addSalg(rundvisning);
        return rundvisning;
    }

    public Udlejning createUdlejning(Kunde kunde) {
        Udlejning udlejning = new Udlejning(kunde);
        storage.addSalg(udlejning);
        return udlejning;
    }


    public void setRabatSalg(Salg salg, double rabat) {
        salg.setRabatSalg(rabat);
    }

    public void setRabatSalgslinje(Salgslinje salgslinje, double rabat) {
        salgslinje.setRabat(rabat);
    }

    public void setAfholdelsesDag(Rundvisning rundvisning, LocalDateTime afholdelsesDag){
        rundvisning.setAfholdelsesDag(afholdelsesDag);
    }

    public void setAfregningsDato(Udlejning udlejning, LocalDate afregningdato){
        udlejning.setAfregningsDato(afregningdato);
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
        int result = 0;
        for (Salg salg : storage.getSalg()) {
            if (salg.getRegistreringsDato().isAfter(fraDato.minusDays(1)) && salg.getRegistreringsDato().isBefore(tilDato)) {
                for (Salgslinje salgslinje : salg.getSalgslinjer()) {
                    Produkt produkt = salgslinje.getPris().getProdukt();
                    if (produkt.getProduktGruppe().getNavn().equals("Klippekort")) {
                        result += ((SimpelProdukt) produkt).getAntalEnheder();
                    }
                }
            }
        }
        return result;
    }

    public int brugteKlipForPeriode(LocalDate fraDato, LocalDate tilDato) {
        int result = 0;
        for (Salg salg : storage.getSalg()) {
            if (salg.getBetalingsform() != null) {
                if (salg.getRegistreringsDato().isAfter(fraDato.minusDays(1)) && salg.getRegistreringsDato().isBefore(tilDato)) {
                    if (salg.getBetalingsform().equals(Salg.Betalingsform.KLIPPEKORT)) {
                        for (Salgslinje salgslinje : salg.getSalgslinjer()) {
                            result += salgslinje.beregnPrisKlip();
                        }
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<Udlejning> getUadsluttedeUdlejninger() {
        ArrayList<Udlejning> result = new ArrayList<>();
        for (Salg s : storage.getSalg()){
            if(s instanceof Udlejning) {
                if (s.getStatus() == Salg.Status.PANTBETALT){
                    result.add((Udlejning) s);
                }
            }
        }
        return result;
    }

    public Salgslinje createModregning(Udlejning udlejning, Salgslinje salgslinje, int antal) {
        return udlejning.createModregning(salgslinje, antal);
    }

    public void setAntalPåSalgslinje(Salgslinje salgslinje, int antal) {
        salgslinje.setAntal(antal);
    }

//    public String printMellemRegningSalgslinje(Salgslinje salgslinje) {
//        return salgslinje.printMellemRegning();
//    }

    public double beregnReturBeløbUdlejning(Udlejning udlejning) {
        return udlejning.beregnReturBeløbUdlejning();
    }

    public void udbetalModregning(Udlejning udlejning) {
        udlejning.setStatus(Salg.Status.AFREGNET);
    }

    public Prisliste getPrisliste(String navn) {
        Prisliste res = null;
        for (Prisliste pl : getPrislister()) {
            if (pl.getNavn().equals(navn)){
                res = pl;
            }
        } return res;
    }

    public ArrayList<Rundvisning> getRegistreredeRundvisninger() {
        ArrayList<Rundvisning> rundvisninger = new ArrayList<>();
        for (Salg ss : storage.getSalg()){
            if(ss instanceof Rundvisning){
                if(ss.getStatus() == Salg.Status.REGISTRERET){
                    rundvisninger.add((Rundvisning) ss);
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
        ProduktGruppe klippekort = this.createProduktGruppe("Klippekort");

        //Flaske produkter
        Produkt p1 = this.createSimpelProdukt(pg1,"Klosterbryg","",0,"");
        Produkt p2 = this.createSimpelProdukt(pg1,"Sweet Georgia Brown","",0,"");
        Produkt p3 = this.createSimpelProdukt(pg1,"Extra Pilsner","",0,"");
        Produkt p4 = this.createSimpelProdukt(pg1,"Celebration","",0,"");
        Produkt p5 = this.createSimpelProdukt(pg1,"Blondie","",0,"");

        //Fadøl produkter
        this.createSimpelProdukt(pg2, "Klosterbryg", "", 40,"cl");
        this.createSimpelProdukt(pg2, "Jazz Classic", "", 40, "cl");
        this.createSimpelProdukt(pg2, "Extra Pilsner", "", 40, "cl");
        this.createSimpelProdukt(pg2, "Celebration", "", 40, "cl");
        this.createSimpelProdukt(pg2, "Blondie", "", 40, "cl");

        //Spritus produkter
        this.createSimpelProdukt(pg3, "Whisky", "45%", 50 ,"cl rør");

        //Fustage produkter
        Produkt fustageKlosterbryg = this.createSimpelProdukt(pg4, "Klosterbryg", "",20, "liter");
        Produkt fustageJazzClassic = this.createSimpelProdukt(pg4, "Jazz Classic", "", 25, "liter");
        Produkt fustageEkstraPilsner = this.createSimpelProdukt(pg4, "Ekstra Pilsner", "", 25, "liter");
        Produkt fustageCelebration = this.createSimpelProdukt(pg4, "Celebration", "", 20, "liter");
        Produkt fustageBlondie = this.createSimpelProdukt(pg4, "Blondie", "", 25, "liter");
        Produkt fustageFårsbryg = this.createSimpelProdukt(pg4, "Forårsbryg", "", 20, "liter");
        Produkt fustageIndiaPaleAle = this.createSimpelProdukt(pg4, "India Pale Ale", "", 20, "liter");
        Produkt fustageJuleBryg = this.createSimpelProdukt(pg4, "Julebryg", "", 20, "liter");
        Produkt fustageImperialStout = this.createSimpelProdukt(pg4, "Imperial Stout", "", 20, "liter");
        Produkt fustagePant = this.createSimpelProdukt(pg4, "Fustage Pant", "", 0,"");

        //Kulsyre
        Produkt kulsyre10kg = this.createSimpelProdukt(pg5, "Kulsyre", "", 10,"kg");
        Produkt kulsyre6kg = this.createSimpelProdukt(pg5, "Kulsyre", "", 6, "kg");
        Produkt kulsyre4kg = this.createSimpelProdukt(pg5, "Kulsyre", "", 4,"kg");
        Produkt kulsyrePant = this.createSimpelProdukt(pg5, "Kulsyre Pant", "", 0,"");


        //Malt
        this.createSimpelProdukt(pg6, "Maltsæk", "", 25, "kg");

        //Beklædning
        this.createSimpelProdukt(pg7, "t-shirt", "", 0,"");

        //Anlæg
        Produkt anlæg1hane = this.createSimpelProdukt(pg8, "1-hane", "", 0,"");
        Produkt anlæg2hane = this.createSimpelProdukt(pg8, "2-haner", "", 0,"");
        Produkt anlægBarflerehaner = this.createSimpelProdukt(pg8, "Bar med flere haner", "", 0,"");
        Produkt anlægLevering = this.createSimpelProdukt(pg8, "Levering", "", 0,"");
        Produkt anlægKrus = this.createSimpelProdukt(pg8, "Krus", "", 0,"");

        //Glas
        this.createSimpelProdukt(pg9, "Glas", "uanset størrelse", 0,"");

        //Sampakninger
        //this.createSimpelProdukt(pg10, "Gaveæske", "2 øl, 2 glas", "");

        //Klippekort
        Produkt klippekort4Klip = this.createSimpelProdukt(klippekort, "Klippekort, 4 klip", "",4, "klip");

        //Priser for fredagsbar for flasker + klippekort
        this.createPris(fredagsbar,p1, 70,2);
        this.createPris(fredagsbar,p2, 70,2);
        this.createPris(fredagsbar,p3, 70,2);
        this.createPris(fredagsbar,p4, 70,2);
        this.createPris(fredagsbar,p5, 70,2);
        this.createPris(fredagsbar, klippekort4Klip, 130, 0);

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
        Produkt rundvisningDag = this.createSimpelProdukt(pg11,
                "Rundvisning dag", "",0, "Pris pr person");
        Produkt rundvisningAften = this.createSimpelProdukt(pg11,
                "Rundvisning aften", "",0, "Pris pr person");

        //Priser til rundvisningsprodukter
        Pris rundvisningDagPris = rundvisning.createPrisTilPrisliste(rundvisningDag, 100, 0);
        Pris rundvisningAftenPris = rundvisning.createPrisTilPrisliste(rundvisningAften, 150, 0);

        //Oprettelse af rundvisninger
        this.createRundvisning(k1,LocalDateTime.of(2023, 1, 20, 10, 0),rundvisningDagPris,20);
        this.createRundvisning(k2,LocalDateTime.of(2022, 12, 20, 20, 0),rundvisningAftenPris,30);
        this.createRundvisning(k3,LocalDateTime.of(2022, 11, 30, 10, 0),rundvisningDagPris,40);

        //Priser til Udlejning
        Pris pris1Hane = udlejning.createPrisTilPrisliste(anlæg1hane,250, 0);
        udlejning.createPrisTilPrisliste(anlæg2hane,400, 0);
        udlejning.createPrisTilPrisliste(anlægBarflerehaner,500, 0);
        udlejning.createPrisTilPrisliste(anlægLevering,500, 0);
        udlejning.createPrisTilPrisliste(anlægKrus,60, 0);
        udlejning.createPrisTilPrisliste(anlæg1hane,250, 0);

        Pris prisFustageKlosterbryg = udlejning.createPrisTilPrisliste(fustageKlosterbryg,775, 0);
        udlejning.createPrisTilPrisliste(fustageJazzClassic,625, 0);
        udlejning.createPrisTilPrisliste(fustageEkstraPilsner,575, 0);
        udlejning.createPrisTilPrisliste(fustageCelebration, 775, 0);
        udlejning.createPrisTilPrisliste(fustageBlondie,700, 0);
        udlejning.createPrisTilPrisliste(fustageFårsbryg,775, 0);
        udlejning.createPrisTilPrisliste(fustageIndiaPaleAle,775, 0);
        udlejning.createPrisTilPrisliste(fustageJuleBryg,775, 0);
        udlejning.createPrisTilPrisliste(fustageImperialStout,775, 0);
        Pris prisFustagePant = udlejning.createPrisTilPrisliste(fustagePant,200, 0);

        udlejning.createPrisTilPrisliste(kulsyre10kg,600, 0);
        udlejning.createPrisTilPrisliste(kulsyre6kg,400, 0);
        Pris prisKulsyre4kg = udlejning.createPrisTilPrisliste(kulsyre4kg,300, 0);
        Pris prisKulsyrePant = udlejning.createPrisTilPrisliste(kulsyrePant,1000, 0);

        Udlejning testUdlejning = this.createUdlejning(k1);
        this.createSalgslinje(testUdlejning, 1, pris1Hane);
        this.createSalgslinje(testUdlejning, 3, prisFustageKlosterbryg);
        this.createSalgslinje(testUdlejning, 3, prisFustagePant);
        this.createSalgslinje(testUdlejning, 1, prisKulsyre4kg);
        this.createSalgslinje(testUdlejning, 1, prisKulsyrePant);
        this.betalSalg(testUdlejning, Salg.Betalingsform.DANKORT);

        //Salg af klippekort
        Salg salgKlippekort = this.createSimpelSalg();
        salgKlippekort.createSalgslinje(this.createPris(fredagsbar, klippekort4Klip, 130,0),1);
        salgKlippekort.createSalgslinje(this.createPris(fredagsbar, klippekort4Klip, 130,0),1);
        salgKlippekort.setBetalingsform(Salg.Betalingsform.DANKORT);
    }
}
