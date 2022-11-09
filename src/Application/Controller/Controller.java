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
        boolean dublet = false;
       if (!navn.equals("")){
           for (Prisliste pl : storage.getPrislister()) {
               if (pl.getNavn().equals(navn)){
                   dublet = true;
               }
           }
           if(!dublet) {
               Prisliste pl = new Prisliste(navn);
               storage.addPrisliste(pl);
               return pl;
           } else {
               throw new IllegalArgumentException("Prisliste med samme navn eksisterer!");
           }
       } else {
            throw new IllegalArgumentException("Navn kan ikke være tom!");
       }
    }

    public Pris createPris(Prisliste prisliste, Produkt produkt, double pris, int klip) {
        Pris p = prisliste.createPrisTilPrisliste(produkt, pris, klip);
        return p;
    }
    
    public void fjernPris(Prisliste prisliste, Pris pris) {
        prisliste.fjernPris(pris);
    }

    public ProduktGruppe createProduktGruppe(String navn) {
        boolean dublet = false;
        if(!navn.equals("")) {
            for (ProduktGruppe pg : storage.getProduktGrupper()) {
                if (pg.getNavn().equals(navn)) {
                    dublet = true;
                }
            }
            if (!dublet) {
                ProduktGruppe pg = new ProduktGruppe(navn);
                storage.addProduktGruppe(pg);
                return pg;
            } else {
                throw new IllegalArgumentException("ProduktGruppe med samme navn eksisterer!");
            }
        }
        else {
            throw new IllegalArgumentException("Navn kan ikke være tom!");
        }
    }

    public Produkt createSimpelProdukt(ProduktGruppe produktGruppe, String navn, String beskrivelse, int antalEnheder, String enhed, boolean pantPligtig) {
        boolean dublet = false;
        if(!navn.equals("")) {
            if (produktGruppe != null) {
                for (Produkt p : produktGruppe.getProdukts()) {
                    if (p.getNavn().equals(navn)) {
                        dublet = true;
                    }
                }
                if (!dublet) {
                    Produkt p = produktGruppe.createSimpelProdukt(navn, antalEnheder, enhed, beskrivelse, pantPligtig);
                    return p;
                } else {
                    throw new IllegalArgumentException("Produkt med samme navn eksisterer allerede i Produktgruppen!");
                }
            } else {
                throw new IllegalArgumentException("ProduktGruppe må ikke være null!");
            }
        } else throw new IllegalArgumentException("Navn kan ikke være tom!");
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
        if(salg.klippeKortBetalingMuligt()){
            if(salg.getRabat() != 0 && salg.getRabat() < 1){
                result = "DKK: " + salg.beregnSamletPrisDKK() + " / Klip: " + salg.beregnSamletPrisKlip() + " -"
                        + (salg.getRabat() * 100) + "%";
            } else if(salg.getRabat() > 1){
                result = result = "DKK: " + salg.beregnSamletPrisDKK() + " / Klip: " + salg.beregnSamletPrisKlip() + " -"
                        + salg.getRabat() + " DKK";
            } else {
                result = "DKK: " + salg.beregnSamletPrisDKK() + " / Klip: " + salg.beregnSamletPrisKlip();
            }
        } else {
            if(salg.getRabat() != 0 && salg.getRabat() < 1){
                result = "DKK: " + salg.beregnSamletPrisDKK() + " -" + (salg.getRabat() * 100) + "%";
            } else if(salg.getRabat() > 1){
                result = result = "DKK: " + salg.beregnSamletPrisDKK() + " -" + salg.getRabat() + " DKK";
            } else {
                result = "DKK: " + salg.beregnSamletPrisDKK();
            }
        }

        return result;
    }

    public Salgslinje createSalgslinje(Salg salg, int antal, Pris pris) {
        Salgslinje sl = salg.createSalgslinje(pris, antal);
        return sl;
    }

    public void fjernSalgslinje(Salg salg, Salgslinje salgslinje) {
        salg.fjernSalgsLinje(salgslinje);
    }

    public ArrayList<Produkt> getProdukterFraProduktgruppe(ProduktGruppe pg){
        return pg.getProdukts();
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
        if (kunde != null) {
            Udlejning udlejning = new Udlejning(kunde);
            udlejning.addObserver(new PantPligtigtProdukt());
            storage.addSalg(udlejning);
            return udlejning;
        } else { throw new IllegalArgumentException("Kunde må ikke være null!"); }
    }


    public void setRabatSalg(Salg salg, double rabat) {
        salg.setRabatSalg(rabat);
    }

    public void setRabatSalgslinje(Salgslinje salgslinje, double rabat) {
        salgslinje.setRabat(rabat);
    }

    public ArrayList<Salg> dagsRapport(LocalDate dato) {
        ArrayList<Salg> result = new ArrayList<>();
        for (Salg salg : storage.getSalg()) {
            if (salg.getStatus().equals(Salg.Status.AFREGNET) || salg.getStatus().equals(Salg.Status.PANTBETALT)) {
                if (salg.getRegistreringsDato().equals(dato)) {
                    result.add(salg);
                }
            }
        }
        return result;
    }

    public double beregnDagsomsætning(LocalDate dato) {
        double result = 0.0;
        for (Salg salg : storage.getSalg()) {
            if (salg.getStatus().equals(Salg.Status.AFREGNET) || salg.getStatus().equals(Salg.Status.PANTBETALT)) {
                if (salg.getRegistreringsDato().equals(dato)) {
                    result += salg.beregnSamletPrisDKK();
                }
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

    public ArrayList<Udlejning> getUafsluttedeUdlejninger() {
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
        if (udlejning != null){
            if (antal > 0){
                if (antal <= salgslinje.getAntal()) {
                    return udlejning.createModregning(salgslinje, antal);
                } else { throw new IllegalArgumentException("Antal må ikke overstige det originale antal på salgslinjen!"); }
            } else throw new IllegalArgumentException("Antal skal være størrere end 0!");
        } else throw new IllegalArgumentException("Udlejning må ikke være null!");
    }

    public double beregnReturBeløbUdlejning(Udlejning udlejning) {
        return udlejning.beregnReturBeløbUdlejning();
    }

    public void udbetalModregning(Udlejning udlejning) {
        udlejning.setStatus(Salg.Status.AFREGNET);
    }

    public ArrayList<Salgslinje> createTempSalgslinjer(Udlejning udlejning) {
        ArrayList<Salgslinje> tempSalgslinjer = new ArrayList<>();
        for (Salgslinje salgslinje : udlejning.getSalgslinjer()){
            Salgslinje tempSalgslinje = new Salgslinje(salgslinje.getAntal(), salgslinje.getPris());
            tempSalgslinjer.add(tempSalgslinje);
        }
        return tempSalgslinjer;
    }

    public void sletSalg(Salg salg) {
        if (salg != null){
            storage.sletSalg(salg);
        }
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

    public Pris getPrisPaaSalgslinje(Salgslinje salgslinje) {
        return salgslinje.getPris();
    }

    public void init(){
        Prisliste fredagsbar = this.createPrisliste("Fredagsbar");
        Prisliste butik = this.createPrisliste("Butik");
        Prisliste rundvisning = this.createPrisliste("Rundvisning");
        Prisliste udlejning = this.createPrisliste("Udlejning");

        ProduktGruppe flasker = this.createProduktGruppe("Flasker");
        ProduktGruppe fadøl = this.createProduktGruppe("Fadøl");
        ProduktGruppe spritus = this.createProduktGruppe("Spiritus");
        ProduktGruppe fustager = this.createProduktGruppe("Fustage");
        ProduktGruppe kulsyre = this.createProduktGruppe("Kulsyre");
        ProduktGruppe malt = this.createProduktGruppe("Malt");
        ProduktGruppe beklædning = this.createProduktGruppe("Beklædning");
        ProduktGruppe anlæg = this.createProduktGruppe("Anlæg");
        ProduktGruppe glasPG = this.createProduktGruppe("Glas");
        ProduktGruppe sampakninger = this.createProduktGruppe("Sampakninger");
        ProduktGruppe rundvisningPG = this.createProduktGruppe("Rundvisning");
        ProduktGruppe klippekort = this.createProduktGruppe("Klippekort");
        ProduktGruppe snacksOgSodavand = this.createProduktGruppe("Snacks og Sodavand");

        //Flaske produkter
        Produkt flaskeKlosterbryg = this.createSimpelProdukt(flasker,"Klosterbryg","",0,"", false);
        Produkt flaskeSweetGeorgiaBrown = this.createSimpelProdukt(flasker,"Sweet Georgia Brown","",0,"", false);
        Produkt flaskeExtraPilsner = this.createSimpelProdukt(flasker,"Extra Pilsner","",0,"", false);
        Produkt flaskeCelebration = this.createSimpelProdukt(flasker,"Celebration","",0,"", false);
        Produkt flaskeBlondie = this.createSimpelProdukt(flasker,"Blondie","",0,"", false);
        Produkt flaskeForårsbryg = this.createSimpelProdukt(flasker,"Forårsbryg","",0,"", false);
        Produkt flaskeIndiaPaleAle = this.createSimpelProdukt(flasker,"India Pale Ale","",0,"", false);
        Produkt flaskeJulebryg = this.createSimpelProdukt(flasker,"Julebryg","",0,"", false);
        Produkt flaskeJuletønden = this.createSimpelProdukt(flasker,"Juletønden","",0,"", false);
        Produkt flaskeOldStrongAle = this.createSimpelProdukt(flasker,"Old Strong Ale","",0,"", false);
        Produkt flaskeFregattenJylland = this.createSimpelProdukt(flasker,"Fregatten Jylland","",0,"", false);
        Produkt flaskeImperialStout = this.createSimpelProdukt(flasker,"Imperial Stout","",0,"", false);
        Produkt flaskeTribute = this.createSimpelProdukt(flasker,"Tribute","",0,"", false);
        Produkt flaskeBlackMonster = this.createSimpelProdukt(flasker,"Black Monster","",0,"", false);

        //Fadøl produkter
        Produkt fadølKlosterbryg = this.createSimpelProdukt(fadøl, "Klosterbryg", "", 40,"cl", false);
        Produkt fadølJazzClassic = this.createSimpelProdukt(fadøl, "Jazz Classic", "", 40, "cl", false);
        Produkt fadølExtraPilsner = this.createSimpelProdukt(fadøl, "Extra Pilsner", "", 40, "cl", false);
        Produkt fadølCelebration = this.createSimpelProdukt(fadøl, "Celebration", "", 40, "cl", false);
        Produkt fadølBlondie = this.createSimpelProdukt(fadøl, "Blondie", "", 40, "cl", false);
        Produkt fadølForårsbryg = this.createSimpelProdukt(fadøl, "Forårsbryg", "", 40, "cl", false);
        Produkt fadølIndiaPaleAle = this.createSimpelProdukt(fadøl, "India Pale Ale", "", 40, "cl", false);
        Produkt fadølJulebryg = this.createSimpelProdukt(fadøl, "Julebryg", "", 40, "cl", false);
        Produkt fadølImperialStout = this.createSimpelProdukt(fadøl, "Imperial Stout", "", 40, "cl", false);
        Produkt fadølSpecial = this.createSimpelProdukt(fadøl, "Special", "", 40, "cl", false);


        //Snacks og sodavand produkter
        Produkt æblebrus = this.createSimpelProdukt(snacksOgSodavand, "Æblebrus", "", 0, "", false);
        Produkt chips = this.createSimpelProdukt(snacksOgSodavand, "Chips", "", 0, "", false);
        Produkt peanuts = this.createSimpelProdukt(snacksOgSodavand, "Peanuts", "", 0, "", false);
        Produkt cola = this.createSimpelProdukt(snacksOgSodavand, "Cola", "", 0, "", false);
        Produkt nikoline = this.createSimpelProdukt(snacksOgSodavand, "Nikoline", "", 0, "", false);
        Produkt sevenUp = this.createSimpelProdukt(snacksOgSodavand, "7-Up", "", 0, "", false);
        Produkt vand = this.createSimpelProdukt(snacksOgSodavand, "Vand", "", 0, "", false);
        Produkt ølpølser = this.createSimpelProdukt(snacksOgSodavand, "Ølpølser", "", 0, "", false);

        //Spritus produkter
        Produkt whisky45 = this.createSimpelProdukt(spritus, "Whisky 45%", "", 50 ,"cl rør", false);
        Produkt whisky4cl = this.createSimpelProdukt(spritus, "Whisky", "45%", 4 ,"cl", false);
        Produkt whisky43 = this.createSimpelProdukt(spritus, "Whisky 43%", "", 50 ,"cl rør", false);
        Produkt uEgesplint = this.createSimpelProdukt(spritus, "u/ egesplit", "", 0, "", false);
        Produkt mEgesplint = this.createSimpelProdukt(spritus, "m/ egesplit", "", 0, "", false);
        Produkt whiskGlasPlusBrikker = this.createSimpelProdukt(spritus, "2*whisky glas + brikker", "", 0, "", false);
        Produkt liquorOfAarhus = this.createSimpelProdukt(spritus, "Liquour of Aarhus", "", 0, "", false);
        Produkt lyngGin50 = this.createSimpelProdukt(spritus, "Lyng gin 50 cl", "", 50, "cl", false);
        Produkt lyngGin4 = this.createSimpelProdukt(spritus, "Lyng gin 4 cl", "", 4, "cl", false);

        //Fustage produkter
        Produkt fustageKlosterbryg = this.createSimpelProdukt(fustager, "Klosterbryg", "",20, "liter", true);
        Produkt fustageJazzClassic = this.createSimpelProdukt(fustager, "Jazz Classic", "", 25, "liter", true);
        Produkt fustageEkstraPilsner = this.createSimpelProdukt(fustager, "Ekstra Pilsner", "", 25, "liter", true);
        Produkt fustageCelebration = this.createSimpelProdukt(fustager, "Celebration", "", 20, "liter", true);
        Produkt fustageBlondie = this.createSimpelProdukt(fustager, "Blondie", "", 25, "liter", true);
        Produkt fustageFårsbryg = this.createSimpelProdukt(fustager, "Forårsbryg", "", 20, "liter", true);
        Produkt fustageIndiaPaleAle = this.createSimpelProdukt(fustager, "India Pale Ale", "", 20, "liter", true);
        Produkt fustageJuleBryg = this.createSimpelProdukt(fustager, "Julebryg", "", 20, "liter", true);
        Produkt fustageImperialStout = this.createSimpelProdukt(fustager, "Imperial Stout", "", 20, "liter", true);
        Produkt fustagePant = this.createSimpelProdukt(fustager, "Pant", "", 0,"", false);
        fustager.setPantProdukt(fustagePant);

        //Kulsyre
        Produkt kulsyre10kg = this.createSimpelProdukt(kulsyre, "Kulsyre Stor", "", 10,"kg", true);
        Produkt kulsyre6kg = this.createSimpelProdukt(kulsyre, "Kulsyre Mellem", "", 6, "kg", true);
        Produkt kulsyre4kg = this.createSimpelProdukt(kulsyre, "Kulsyre Lille", "", 4,"kg", true);
        Produkt kulsyrePant = this.createSimpelProdukt(kulsyre, "Pant", "", 0,"", false);
        kulsyre.setPantProdukt(kulsyrePant);

        //Malt
        Produkt maltsæk = this.createSimpelProdukt(malt, "Maltsæk", "", 25, "kg", false);

        //Beklædning
        Produkt tshirt = this.createSimpelProdukt(beklædning, "t-shirt", "", 0,"", false);
        Produkt polo = this.createSimpelProdukt(beklædning, "polo", "", 0,"", false);
        Produkt cap = this.createSimpelProdukt(beklædning, "cap", "", 0,"", false);

        //Anlæg
        Produkt anlæg1hane = this.createSimpelProdukt(anlæg, "1-hane", "", 0,"", false);
        Produkt anlæg2hane = this.createSimpelProdukt(anlæg, "2-haner", "", 0,"", false);
        Produkt anlægBarflerehaner = this.createSimpelProdukt(anlæg, "Bar med flere haner", "", 0,"", false);
        Produkt anlægLevering = this.createSimpelProdukt(anlæg, "Levering", "", 0,"", false);
        Produkt anlægKrus = this.createSimpelProdukt(anlæg, "Krus", "", 0,"", false);

        //Glas
        Produkt glas = this.createSimpelProdukt(glasPG, "Glas", "uanset størrelse", 0,"", false);

        //Sampakninger
        ProduktSamling gaveæske2øl2glas = this.createProduktSamling(sampakninger, "Gaveæske 2 øl, 2 glas", "");
        gaveæske2øl2glas.addProdukt(flaskeBlondie);
        gaveæske2øl2glas.addProdukt(flaskeCelebration);
        gaveæske2øl2glas.addProdukt(glas); gaveæske2øl2glas.addProdukt(glas);

        ProduktSamling gaveæske4øl = this.createProduktSamling(sampakninger, "Gaveæske 4 øl", "");
        gaveæske4øl.addProdukt(flaskeBlondie);
        gaveæske4øl.addProdukt(flaskeCelebration);
        gaveæske4øl.addProdukt(flaskeExtraPilsner);
        gaveæske4øl.addProdukt(flaskeJulebryg);

        ProduktSamling trækasse6øl = this.createProduktSamling(sampakninger, "Trækasse 6 øl", "");
        trækasse6øl.addProdukt(flaskeBlondie);
        trækasse6øl.addProdukt(flaskeCelebration);
        trækasse6øl.addProdukt(flaskeExtraPilsner);
        trækasse6øl.addProdukt(flaskeIndiaPaleAle);
        trækasse6øl.addProdukt(flaskeJulebryg);
        trækasse6øl.addProdukt(flaskeKlosterbryg);

        ProduktSamling gavekurv6øl2glas = this.createProduktSamling(sampakninger, "Gavekurv 6 øl, 2 glas", "");
        gavekurv6øl2glas.addProdukt(glas); gavekurv6øl2glas.addProdukt(glas);
        gavekurv6øl2glas.addProdukt(flaskeBlondie);
        gavekurv6øl2glas.addProdukt(flaskeCelebration);
        gavekurv6øl2glas.addProdukt(flaskeExtraPilsner);
        gavekurv6øl2glas.addProdukt(flaskeIndiaPaleAle);
        gavekurv6øl2glas.addProdukt(flaskeJulebryg);
        gavekurv6øl2glas.addProdukt(flaskeKlosterbryg);

        ProduktSamling trækasse6øl6glas = this.createProduktSamling(sampakninger, "Trækasse 6 øl, 6 glas", "");
        trækasse6øl6glas.addProdukt(glas); trækasse6øl6glas.addProdukt(glas);trækasse6øl6glas.addProdukt(glas);
        trækasse6øl6glas.addProdukt(glas);trækasse6øl6glas.addProdukt(glas); trækasse6øl6glas.addProdukt(glas);
        trækasse6øl6glas.addProdukt(glas); trækasse6øl6glas.addProdukt(glas);
        trækasse6øl6glas.addProdukt(flaskeBlondie);
        trækasse6øl6glas.addProdukt(flaskeCelebration);
        trækasse6øl6glas.addProdukt(flaskeExtraPilsner);
        trækasse6øl6glas.addProdukt(flaskeIndiaPaleAle);
        trækasse6øl6glas.addProdukt(flaskeJulebryg);
        trækasse6øl6glas.addProdukt(flaskeKlosterbryg);

        ProduktSamling trækasse12øl = this.createProduktSamling(sampakninger, "Trækasse 12 øl", "");
        trækasse12øl.addProdukt(flaskeBlondie); trækasse12øl.addProdukt(flaskeCelebration);
        trækasse12øl.addProdukt(flaskeExtraPilsner); trækasse12øl.addProdukt(flaskeIndiaPaleAle);
        trækasse12øl.addProdukt(flaskeJulebryg); trækasse12øl.addProdukt(flaskeKlosterbryg);
        trækasse12øl.addProdukt(flaskeBlondie); trækasse12øl.addProdukt(flaskeCelebration);
        trækasse12øl.addProdukt(flaskeExtraPilsner); trækasse12øl.addProdukt(flaskeIndiaPaleAle);
        trækasse12øl.addProdukt(flaskeJulebryg); trækasse12øl.addProdukt(flaskeKlosterbryg);

        ProduktSamling papkasse12øl = this.createProduktSamling(sampakninger, "Trækasse 12 øl", "");
        papkasse12øl.addProdukt(flaskeBlondie); papkasse12øl.addProdukt(flaskeCelebration);
        papkasse12øl.addProdukt(flaskeExtraPilsner); papkasse12øl.addProdukt(flaskeIndiaPaleAle);
        papkasse12øl.addProdukt(flaskeJulebryg); papkasse12øl.addProdukt(flaskeKlosterbryg);
        papkasse12øl.addProdukt(flaskeBlondie); papkasse12øl.addProdukt(flaskeCelebration);
        papkasse12øl.addProdukt(flaskeExtraPilsner); papkasse12øl.addProdukt(flaskeIndiaPaleAle);
        papkasse12øl.addProdukt(flaskeJulebryg); papkasse12øl.addProdukt(flaskeKlosterbryg);

        //Klippekort
        Produkt klippekort4Klip = this.createSimpelProdukt(klippekort, "Klippekort, 4 klip", "",4, "klip", false);

        //Priser for fredagsbar for flasker + klippekort
        this.createPris(fredagsbar,flaskeKlosterbryg, 70,2);
        this.createPris(fredagsbar,flaskeSweetGeorgiaBrown, 70,2);
        this.createPris(fredagsbar,flaskeExtraPilsner, 70,2);
        this.createPris(fredagsbar,flaskeCelebration, 70,2);
        this.createPris(fredagsbar,flaskeBlondie, 70,2);
        this.createPris(fredagsbar,flaskeForårsbryg, 70,2);
        this.createPris(fredagsbar,flaskeIndiaPaleAle, 70,2);
        this.createPris(fredagsbar,flaskeJulebryg, 70,2);
        this.createPris(fredagsbar,flaskeJuletønden, 70,2);
        this.createPris(fredagsbar,flaskeOldStrongAle, 70,2);
        this.createPris(fredagsbar,flaskeFregattenJylland, 70,2);
        this.createPris(fredagsbar,flaskeImperialStout, 70,2);
        this.createPris(fredagsbar,flaskeTribute, 70,2);
        this.createPris(fredagsbar,flaskeBlackMonster, 100,3);
        this.createPris(fredagsbar, klippekort4Klip, 130, 0);

        //Priser for fredagsbar for fadøl
        this.createPris(fredagsbar, fadølKlosterbryg, 38, 1);
        this.createPris(fredagsbar,fadølJazzClassic,38 ,1);
        this.createPris(fredagsbar,fadølExtraPilsner ,38 ,1 );
        this.createPris(fredagsbar,fadølCelebration ,38 ,1 );
        this.createPris(fredagsbar,fadølBlondie ,38 ,1 );
        this.createPris(fredagsbar,fadølForårsbryg,38 ,1 );
        this.createPris(fredagsbar,fadølIndiaPaleAle ,38 ,1 );
        this.createPris(fredagsbar,fadølJulebryg ,38 ,1 );
        this.createPris(fredagsbar,fadølImperialStout ,38 ,1 );
        this.createPris(fredagsbar,fadølSpecial ,38 ,1 );

        //Priser for fredagsbar for snack og sodavand
        this.createPris(fredagsbar,æblebrus ,15 ,0 );
        this.createPris(fredagsbar,chips ,10 ,0 );
        this.createPris(fredagsbar,peanuts ,15 ,0 );
        this.createPris(fredagsbar,cola ,15 ,0 );
        this.createPris(fredagsbar,nikoline ,15 ,0 );
        this.createPris(fredagsbar,sevenUp ,15 ,0 );
        this.createPris(fredagsbar,vand ,15 ,0 );
        this.createPris(fredagsbar,ølpølser ,30 ,1 );

        //Priser for fredagsbar for spiritus
        this.createPris(fredagsbar,whisky45 ,599 ,0 );
        this.createPris(fredagsbar,whisky4cl ,50 ,0 );
        this.createPris(fredagsbar,whisky43 ,499 ,0 );
        this.createPris(fredagsbar,uEgesplint ,300 ,0 );
        this.createPris(fredagsbar,mEgesplint ,300 ,0 );
        this.createPris(fredagsbar,whiskGlasPlusBrikker ,80 ,0 );
        this.createPris(fredagsbar,liquorOfAarhus ,175 ,0 );
        this.createPris(fredagsbar,lyngGin50 ,350 ,0 );
        this.createPris(fredagsbar,lyngGin4 ,40 ,0 );

        //Priser for fredagsbar for beklædning
        this.createPris(fredagsbar,tshirt ,70 ,0 );
        this.createPris(fredagsbar,polo ,100 ,0 );
        this.createPris(fredagsbar,cap ,30 ,0 );

        //Priser for fredagsbar for sampakninger
        this.createPris(fredagsbar, gaveæske2øl2glas, 110, 0);
        this.createPris(fredagsbar, gaveæske4øl, 140, 0);
        this.createPris(fredagsbar, trækasse6øl, 260, 0);
        this.createPris(fredagsbar, gavekurv6øl2glas, 260, 0);
        this.createPris(fredagsbar, trækasse6øl6glas, 350, 0);
        this.createPris(fredagsbar, trækasse12øl, 410, 0);
        this.createPris(fredagsbar, papkasse12øl, 370, 0);

        //Priser for butik for flasker
        this.createPris(butik,flaskeKlosterbryg, 36,0);
        this.createPris(butik,flaskeSweetGeorgiaBrown, 36,0);
        this.createPris(butik,flaskeExtraPilsner, 36,0);
        this.createPris(butik,flaskeCelebration, 36,0);
        this.createPris(butik,flaskeBlondie, 36,0);
        this.createPris(butik,flaskeForårsbryg, 36,0);
        this.createPris(butik,flaskeIndiaPaleAle, 36,0);
        this.createPris(butik,flaskeJulebryg, 36,0);
        this.createPris(butik,flaskeJuletønden, 36,0);
        this.createPris(butik,flaskeOldStrongAle, 36,0);
        this.createPris(butik,flaskeFregattenJylland, 36,0);
        this.createPris(butik,flaskeImperialStout, 36,0);
        this.createPris(butik,flaskeTribute, 36,0);
        this.createPris(butik,flaskeBlackMonster, 60,0);

        //Priser for butik for spiritus
        this.createPris(butik,whisky45, 599,0);
        this.createPris(butik,whisky43, 499,0);
        this.createPris(butik,uEgesplint, 300,0);
        this.createPris(butik,mEgesplint, 350,0);
        this.createPris(butik,whiskGlasPlusBrikker, 80,0);
        this.createPris(butik,liquorOfAarhus, 175,0);
        this.createPris(butik,lyngGin50, 350,0);

        //Pris for butik for malt
        this.createPris(butik,maltsæk, 300,0);

        //Priser for butik for beklædning
        this.createPris(butik,tshirt ,70 ,0 );
        this.createPris(butik,polo ,100 ,0 );
        this.createPris(butik,cap ,30 ,0 );

        //Pris for butik for glas
        this.createPris(butik,glas ,15 ,0 );

        //Priser for butik for sampakninger
        this.createPris(butik, gaveæske2øl2glas, 110, 0);
        this.createPris(butik, gaveæske4øl, 140, 0);
        this.createPris(butik, trækasse6øl, 260, 0);
        this.createPris(butik, gavekurv6øl2glas, 260, 0);
        this.createPris(butik, trækasse6øl6glas, 350, 0);
        this.createPris(butik, trækasse12øl, 410, 0);
        this.createPris(butik, papkasse12øl, 370, 0);

        //Initialisering af objekter anvendt i rundvisning

        //Kunder
        Kunde k1 = this.createKunde("Hans", "60453980", "Hans@gmail.com");
        Kunde k2 = this.createKunde("Jens", "61235789", "Jens@gmail.com");
        Kunde k3 = this.createKunde("Poul", "23466892", "Poul@gmail.com");

        //Rundvisnings produkter
        Produkt rundvisningDag = this.createSimpelProdukt(rundvisningPG,
                "Rundvisning dag", "",0, "Pris pr person", false);
        Produkt rundvisningAften = this.createSimpelProdukt(rundvisningPG,
                "Rundvisning aften", "",0, "Pris pr person", false);
        Produkt rundvisningDagMedSmagning = this.createSimpelProdukt(rundvisningPG,
                "Rundvisning dag med smagning", "",0, "Pris pr person", false);
        Produkt rundvisningAftenMedSmagning = this.createSimpelProdukt(rundvisningPG,
                "Rundvisning aften med smagning", "",0, "Pris pr person", false);

        //Priser til rundvisningsprodukter
        Pris rundvisningDagPris = rundvisning.createPrisTilPrisliste(rundvisningDag, 100, 0);
        Pris rundvisningAftenPris = rundvisning.createPrisTilPrisliste(rundvisningAften, 150, 0);
        Pris rundvisningDagMedSmagningPris = rundvisning.createPrisTilPrisliste(rundvisningDagMedSmagning, 200, 0);
        Pris rundvisningAftenMedSmagningPris = rundvisning.createPrisTilPrisliste(rundvisningAftenMedSmagning, 250, 0);

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
        this.createSalgslinje(testUdlejning, 1, prisKulsyre4kg);
        this.betalSalg(testUdlejning, Salg.Betalingsform.DANKORT);

        Udlejning testUdlejning1 = this.createUdlejning(k1);
        this.createSalgslinje(testUdlejning1, 2, pris1Hane);
        this.createSalgslinje(testUdlejning1, 4, prisFustageKlosterbryg);
        this.createSalgslinje(testUdlejning1, 2, prisKulsyre4kg);
        this.betalSalg(testUdlejning1, Salg.Betalingsform.KONTANT);

        //Salg af klippekort
        Salg salgKlippekort = this.createSimpelSalg();
        salgKlippekort.createSalgslinje(this.createPris(fredagsbar, klippekort4Klip, 130,0),1);
        salgKlippekort.createSalgslinje(this.createPris(fredagsbar, klippekort4Klip, 130,0),1);
        this.betalSalg(salgKlippekort, Salg.Betalingsform.DANKORT);
    }
}
