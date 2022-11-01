package Storage;

import Application.Model.Prisliste;
import Application.Model.ProduktGruppe;
import Application.Model.SimpeltSalg;
import Application.StorageInterface;

import java.util.ArrayList;

public class Storage implements StorageInterface {
    private ArrayList<Prisliste> prislister = new ArrayList<>();
    private ArrayList<ProduktGruppe> produktGrupper = new ArrayList<>();
    private ArrayList<SimpeltSalg> salg = new ArrayList<>();

    public ArrayList<Prisliste> getPrislister() {
        return new ArrayList<>(prislister);
    }

    public void addPrisliste(Prisliste prisliste) {
        if (!prislister.contains(prisliste)) {
            prislister.add(prisliste);
        }
    }

    public ArrayList<ProduktGruppe> getProduktGrupper() {
        return new ArrayList<>(produktGrupper);
    }
    
    public void addProduktGruppe(ProduktGruppe produktGruppe) {
        if (!produktGrupper.contains(produktGruppe)) {
            produktGrupper.add(produktGruppe);
        }
    }

    public ArrayList<SimpeltSalg> getSalg() {
        return new ArrayList<>(salg);
    }

    public void addSalg(SimpeltSalg s) {
        if (!salg.contains(s)) {
            salg.add(s);
        }
    }

}
