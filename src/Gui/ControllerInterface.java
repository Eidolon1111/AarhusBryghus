package Gui;

import Application.Model.*;
import java.time.LocalDate;
import java.util.ArrayList;

public interface ControllerInterface {

    public ArrayList<Prisliste> getPrislister();

    public ArrayList<ProduktGruppe> getProduktGrupper();
    
    public ArrayList<Produkt> getProdukter();

    public Prisliste createPrisliste(String navn);

    public Pris createPris(Prisliste prisliste, Produkt produkt, double pris, int klip);

    public void fjernPris(Prisliste prisliste, Pris pris);
        
    public ProduktGruppe createProduktGruppe(String navn);

    public Produkt createProdukt(ProduktGruppe produktGruppe, String navn, String beskrivelse, String enhed);

    public Salg createSalg();

    public ArrayList<String> printMellemRegning(Prisliste prisliste, Salg salg);

    public String printSamletPrisDKKOgKlip(Prisliste prisliste, Salg salg);

    public Salgslinje createSalgslinje(Salg salg, int antal, Produkt produkt);

    public boolean fjernSalgslinje(Prisliste prisliste, Salg salg, String target);

    public ArrayList<ProduktGruppe> getProduktGrupperIPrisliste(Prisliste prisliste);

    public ArrayList<Produkt> getProdukterFraProduktgruppe(ProduktGruppe pg);

    public String getProduktGruppeNavn(ProduktGruppe pg);

    public void betalSalg(Salg salg, Salg.Betalingsform betalingsform);

    public void init();

}
