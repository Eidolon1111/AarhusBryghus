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

    public Produkt createProdukt(ProduktGruppe produktGruppe, String navn, String beskrivelse, String enhed);

    public Salg createSimpelSalg();

    public Salg createKompleksSalg(Kunde kunde);

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

    public void createRundvisning(Kunde kunde, LocalDateTime afholdesesDato);

    public Pris findPrisIPrisliste(Prisliste prisliste, String Produktnavn);

    public ArrayList<Prisliste> getPrislisterMedSpecifiktProdukt(String Produktnavn);

    public void setRabatSalg(Salg salg, double rabat);

    public void setRabatSalgslinje(Salgslinje salgslinje, double rabat);

    public void init();

    public void createKunde(String navn, String tlfNr, String email);

    public ArrayList<Kunde> getKunder();

    public void setAfholdelsesDag(KomplekstSalg komplekstSalg, LocalDateTime afholdelsesDag);

    public ArrayList<Salg> dagsRapport(LocalDate dato);

    public double beregnDagsomsætning(LocalDate dato);
}
