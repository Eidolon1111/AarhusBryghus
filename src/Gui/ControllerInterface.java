package Gui;

import Application.Model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ControllerInterface {

    ArrayList<Prisliste> getPrislister();

    ArrayList<ProduktGruppe> getProduktGrupper();
    
    ArrayList<Produkt> getProdukter();

    Prisliste createPrisliste(String navn);

    Pris createPris(Prisliste prisliste, Produkt produkt, double pris, int klip);

    void fjernPris(Prisliste prisliste, Pris pris);
        
    ProduktGruppe createProduktGruppe(String navn);

    Produkt createSimpelProdukt(ProduktGruppe produktGruppe, String navn, String beskrivelse, int antalEnheder, String enhed, boolean pantPligtig);

    Produkt createProduktSamling(ProduktGruppe produktGruppe, String navn, String beskrivelse);

    Salg createSimpelSalg();

    ArrayList<Salgslinje> getSalgslinjerPaaSalg(Salg salg);

    ArrayList<Salgslinje> getModregningerPaaUdlejning(Udlejning udlejning);

    String printSamletPrisDKKOgKlip(Salg salg);

    Salgslinje createSalgslinje(Salg salg, int antal, Pris pris);

    ArrayList<Salg> getSalg();

    void fjernSalgslinje(Salg salg, Salgslinje salgslinje);

    ArrayList<ProduktGruppe> getProduktGrupperIPrisliste(Prisliste prisliste);

    ArrayList<Produkt> getProdukterFraProduktgruppe(ProduktGruppe pg);

    void betalSalg(Salg salg, Salg.Betalingsform betalingsform);

    ArrayList<Salg.Betalingsform> getMuligeBetalingsformer(Salg salg);

    Pris findPrisPaaProdukt(Prisliste prisliste, Produkt produkt);

    Rundvisning createRundvisning(Kunde kunde, LocalDateTime afholdesesDato,Pris pris, int antal);

    Udlejning createUdlejning(Kunde kunde);

    void setRabatSalg(Salg salg, double rabat);

    void setRabatSalgslinje(Salgslinje salgslinje, double rabat);

    void init();

    Kunde createKunde(String navn, String tlfNr, String email);

    ArrayList<Kunde> getKunder();

    Prisliste getPrisliste (String navn);

    ArrayList<Salg> dagsRapport(LocalDate dato);

    double beregnDagsomsætning(LocalDate dato);

    int solgteKlipForPeriode(LocalDate fraDato, LocalDate tilDato);

    ArrayList<Rundvisning> getRegistreredeRundvisninger();

    int brugteKlipForPeriode(LocalDate fraDato, LocalDate tilDato);

    ArrayList<Udlejning> getUafsluttedeUdlejninger();

    Salgslinje createModregning(Udlejning udlejning, Salgslinje salgslinje, int antal);

    double beregnReturBeløbUdlejning(Udlejning udlejning);

    void udbetalModregning(Udlejning udlejning);

    ArrayList<Salgslinje> createTempSalgslinjer(Udlejning udlejning);

    Pris getPrisPaaSalgslinje(Salgslinje salgslinje);

    void sletSalg(Salg salg);

    Produkt getPantIProduktgruppe(ProduktGruppe produktGruppe);

    void setPantProduktPaaProduktGruppe(ProduktGruppe produktGruppe, Produkt produkt);

}
