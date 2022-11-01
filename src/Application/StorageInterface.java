package Application;

import Application.Model.Prisliste;
import Application.Model.ProduktGruppe;
import Application.Model.SimpeltSalg;

import java.util.ArrayList;

public interface StorageInterface {
    public ArrayList<Prisliste> getPrislister();

    public void addPrisliste(Prisliste prisliste);
    
    public ArrayList<ProduktGruppe> getProduktGrupper();

    public void addProduktGruppe(ProduktGruppe produktGruppe);

    public ArrayList<SimpeltSalg> getSalg();

    public void addSalg(SimpeltSalg salg);

}
