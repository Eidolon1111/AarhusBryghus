package Application.Model;

import java.util.Locale;

public class PantPligtigtProdukt implements Observer{

    @Override
    public void update(Observerbar observerbar) {
//        Udlejning udlejning;
//        if(observerbar instanceof Udlejning){
//            udlejning = (Udlejning) observerbar;
//            int antalSalgslinjer = udlejning.getSalgslinjer().size() - 1;
//            Salgslinje salgslinje = udlejning.getSalgslinjer().get(antalSalgslinjer);
//            Prisliste prisliste = salgslinje.getPris().getPrisliste();
//            SimpelProdukt produkt;
//            if(salgslinje.getProdukt() instanceof SimpelProdukt){
//                produkt = (SimpelProdukt) salgslinje.getProdukt();
//                if(produkt.isPantPligtig()){
//                    String produktgruppeNavn = produkt.getProduktGruppe().getNavn();
//                    Pris pantpris = prisliste.findPris(produktgruppeNavn + " " + "Pant");
//                    udlejning.createSalgslinje(pantpris, salgslinje.getAntal());
//                }
//            }
//        }

        Udlejning udlejning;
        if(observerbar instanceof Udlejning){
            udlejning = (Udlejning) observerbar;
            int antalSalgslinjer = udlejning.getSalgslinjer().size() - 1;
            Salgslinje salgslinje = udlejning.getSalgslinjer().get(antalSalgslinjer);
            Prisliste prisliste = salgslinje.getPris().getPrisliste();
            SimpelProdukt produkt;
            if(salgslinje.getProdukt() instanceof SimpelProdukt){
                produkt = (SimpelProdukt) salgslinje.getProdukt();
                if(produkt.isPantPligtig()){
                    ProduktGruppe produktgruppe = produkt.getProduktGruppe();
                    Pris produktgruppensPantpris = null;
                    for(Pris pris : prisliste.getPrislisten()){
                        if(pris.getProdukt().getNavn().toUpperCase(Locale.ROOT).equals("PANT") &&
                                pris.getProdukt().getProduktGruppe() == produktgruppe){
                            produktgruppensPantpris = pris;
                        }
                    }
                    udlejning.createSalgslinje(produktgruppensPantpris, salgslinje.getAntal());
                }
            }
        }

    }
}
