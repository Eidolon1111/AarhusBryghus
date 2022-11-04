package Gui;

import Application.Model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ControllerInterface {

    public ArrayList<Prisliste> getPrislister();

    public ArrayList<ProduktGruppe> getProduktGrupper();
    
    public ArrayList<Produkt> getProdukter();

    public Prisliste createPrisliste(String navn);

    public Pris createPris(Prisliste prisliste, Produkt produkt, double pris, int klip);

    public void fjernPris(Prisliste prisliste, Pris pris);
        
    public ProduktGruppe createProduktGruppe(String navn);

    public Produkt createSimpelProdukt(ProduktGruppe produktGruppe, String navn, String beskrivelse, int antalEnheder, String enhed);

    public Produkt createProduktSamling(ProduktGruppe produktGruppe, String navn, String beskrivelse);

    public Salg createSimpelSalg();

    public ArrayList<Salgslinje> getSalgslinjerPaaSalg(Salg salg);

    public ArrayList<Salgslinje> getModregningerPaaUdlejning(Udlejning udlejning);

    public String printSamletPrisDKKOgKlip(Salg salg);

    public Salgslinje createSalgslinje(Salg salg, int antal, Pris pris);

    public ArrayList<Salg> getSalg();

    public void fjernSalgslinje(Salg salg, Salgslinje salgslinje);

    public ArrayList<ProduktGruppe> getProduktGrupperIPrisliste(Prisliste prisliste);

    public ArrayList<Produkt> getProdukterFraProduktgruppe(ProduktGruppe pg);

    public String getProduktGruppeNavn(ProduktGruppe pg);

    public void betalSalg(Salg salg, Salg.Betalingsform betalingsform);

    public ArrayList<Salg.Betalingsform> getMuligeBetalingsformer(Salg salg);

    public Pris findPrisPaaProdukt(Prisliste prisliste, Produkt produkt);

    public boolean klippeKortBetalingMuligt(Salg salg);

    public Rundvisning createRundvisning(Kunde kunde, LocalDateTime afholdesesDato,Pris pris, int antal);

    public Udlejning createUdlejning(Kunde kunde);

    public void setRabatSalg(Salg salg, double rabat);

    public void setRabatSalgslinje(Salgslinje salgslinje, double rabat);

    public void init();

    public Kunde createKunde(String navn, String tlfNr, String email);

    public ArrayList<Kunde> getKunder();

    public void setAfholdelsesDag(Rundvisning rundvisning, LocalDateTime afholdelsesDag);

    public void setAfregningsDato(Udlejning udlejning, LocalDate afregningsDato);

    public Prisliste getPrisliste (String navn);

    public ArrayList<Salg> dagsRapport(LocalDate dato);

    public double beregnDagsomsætning(LocalDate dato);

    public int solgteKlipForPeriode(LocalDate fraDato, LocalDate tilDato);

    public ArrayList<Rundvisning> getRegistreredeRundvisninger();

    public int brugteKlipForPeriode(LocalDate fraDato, LocalDate tilDato);

    public ArrayList<Udlejning> getUafsluttedeUdlejninger();

    public Salgslinje createModregning(Udlejning udlejning, Salgslinje salgslinje, int antal);

    public void setAntalPåSalgslinje(Salgslinje salgslinje, int antal);

    public double beregnReturBeløbUdlejning(Udlejning udlejning);

    public void udbetalModregning(Udlejning udlejning);

    public ArrayList<Salgslinje> createTempSalgslinjer(Udlejning udlejning);

    public Pris getPrisPaaSalgslinje(Salgslinje salgslinje);

    public void sletSalg(Salg salg);

}
