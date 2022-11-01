package Gui;

import Application.Model.*;

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

    public SimpeltSalg createSimpelSalg();

    public SimpeltSalg createKompleksSalg(Kunde kunde);

    public ArrayList<String> printMellemRegning(Prisliste prisliste, SimpeltSalg salg);

    public String printSamletPrisDKKOgKlip(Prisliste prisliste, SimpeltSalg salg);

    public Salgslinje createSalgslinje(SimpeltSalg salg, int antal, Pris pris);

    public Salgslinje findSalgslinjeFraKurv(Prisliste prisliste, SimpeltSalg salg, String target);

    public ArrayList<SimpeltSalg> getSalg();

    public void fjernSalgslinje(SimpeltSalg salg, Salgslinje salgslinje);

    public ArrayList<ProduktGruppe> getProduktGrupperIPrisliste(Prisliste prisliste);

    public ArrayList<Produkt> getProdukterFraProduktgruppe(ProduktGruppe pg);

    public String getProduktGruppeNavn(ProduktGruppe pg);

    public void betalSalg(SimpeltSalg salg, SimpeltSalg.Betalingsform betalingsform);

    public ArrayList<SimpeltSalg.Betalingsform> getMuligeBetalingsformer(SimpeltSalg salg);

    public Pris findPrisPaaProdukt(Prisliste prisliste, Produkt produkt);

    public boolean klippeKortBetalingMuligt(SimpeltSalg salg);

    public ArrayList<Kunde> getKunder();

    public void createKunde(String navn, String tlfNr, String email);

    public void createRundvisning(Kunde kunde, LocalDateTime afholdesesDato);

    public Pris findPrisIPrisliste(Prisliste prisliste, String Produktnavn);

    public ArrayList<Prisliste> getPrislisterMedSpecifiktProdukt(String Produktnavn);

    public void init();

}
