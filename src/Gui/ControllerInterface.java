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
    
    public Pris createPris(Prisliste prisliste, Produkt produkt, double pris);

    public void fjernPris(Prisliste prisliste, Pris pris);
        
    public ProduktGruppe createProduktGruppe(String navn);

    public Produkt createProdukt(ProduktGruppe produktGruppe, String navn, String beskrivelse, String enhed);

    public Salg createSalg(LocalDate registreringsDato);

    public Salgslinje createSalgslinje(Salg salg, int antal, Produkt produkt);

    public ArrayList<ProduktGruppe> getProduktGupperIPrisliste(Prisliste prisliste);

    public void init();

}
