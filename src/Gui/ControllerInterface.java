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

    public Produkt createSimpelProdukt(ProduktGruppe produktGruppe, String navn, String beskrivelse, String enhed);

    public Produkt createProduktSamling(ProduktGruppe produktGruppe, String navn, String beskrivelse);

    public Salg createSimpelSalg();

    public KomplekstSalg createKompleksSalg(Kunde kunde);

    public ArrayList<String> printMellemRegning(Salg salg);

    public String printSamletPrisDKKOgKlip(Salg salg);

    public Salgslinje createSalgslinje(Salg salg, int antal, Pris pris);

    public Salgslinje findSalgslinjeFraKurv(Prisliste prisliste, Salg salg, String target);

    public ArrayList<Salg> getSalg();

    public void fjernSalgslinje(Salg salg, Salgslinje salgslinje);

    public ArrayList<ProduktGruppe> getProduktGrupperIPrisliste(Prisliste prisliste);

    public ArrayList<Produkt> getProdukterFraProduktgruppe(ProduktGruppe pg);

    public String getProduktGruppeNavn(ProduktGruppe pg);

    public void betalSalg(Salg salg, Salg.Betalingsform betalingsform);

    public ArrayList<Salg.Betalingsform> getMuligeBetalingsformer(Salg salg);

    public Pris findPrisPaaProdukt(Prisliste prisliste, Produkt produkt);

    public boolean klippeKortBetalingMuligt(Salg salg);

    public void createRundvisning(Kunde kunde, LocalDateTime afholdesesDato,Pris pris, int antal);

    public void setRabatSalg(Salg salg, double rabat);

    public void setRabatSalgslinje(Salgslinje salgslinje, double rabat);

    public void init();

    public Kunde createKunde(String navn, String tlfNr, String email);

    public ArrayList<Kunde> getKunder();

    public void setAfholdelsesDag(KomplekstSalg komplekstSalg, LocalDateTime afholdelsesDag);

    public Prisliste getPrisliste (String navn);

    public ArrayList<Salg> dagsRapport(LocalDate dato);

    public double beregnDagsomsætning(LocalDate dato);

    public int solgteKlipForPeriode(LocalDate fraDato, LocalDate tilDato);

    public ArrayList<KomplekstSalg> getRundvisninger();

    public ArrayList<KomplekstSalg> getUadsluttedeUdlejninger();

    public Salgslinje createModregning(KomplekstSalg salg, Salgslinje salgslinje, int antal);

    public void setAntalPåSalgslinje(Salgslinje salgslinje, int antal);

    public String printMellemRegningSalgslinje(Salgslinje salgslinje);

    public double beregnReturBeløbUdlejning(KomplekstSalg udlejning);

    public void udbetalModregning(KomplekstSalg udlejning);

}
