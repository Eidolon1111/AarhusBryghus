package Application;

import Application.Model.Kunde;
import Application.Model.Prisliste;
import Application.Model.ProduktGruppe;
import Application.Model.Salg;

import java.util.ArrayList;

public interface StorageInterface {
    ArrayList<Prisliste> getPrislister();

    void addPrisliste(Prisliste prisliste);
    
    ArrayList<ProduktGruppe> getProduktGrupper();

    void addProduktGruppe(ProduktGruppe produktGruppe);

    ArrayList<Salg> getSalg();

    void sletSalg(Salg salg);

    void addSalg(Salg salg);

    void addKunde(Kunde kunde);

    ArrayList<Kunde> getKunder();

}
