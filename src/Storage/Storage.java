package Storage;

import Application.Model.Kunde;
import Application.Model.Prisliste;
import Application.Model.ProduktGruppe;
import Application.Model.Salg;
import Application.StorageInterface;

import java.util.ArrayList;

public class Storage implements StorageInterface {
    private static Storage uniqueStorage;
    private ArrayList<Prisliste> prislister = new ArrayList<>();
    private ArrayList<ProduktGruppe> produktGrupper = new ArrayList<>();
    private ArrayList<Salg> salg = new ArrayList<>();
    private ArrayList<Kunde> kunder = new ArrayList<>();

    private Storage(){
    }

    public static synchronized Storage getInstance(){
        if(uniqueStorage == null){
            uniqueStorage = new Storage();
        }
        return uniqueStorage;
    }

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

    public ArrayList<Salg> getSalg() {
        return new ArrayList<>(salg);
    }

    public void addSalg(Salg s) {
        if (!salg.contains(s)) {
            salg.add(s);
        }
    }

    public void addKunde(Kunde kunde) {
        if(!kunder.contains(kunde)) {
            kunder.add(kunde);
        }
    }

    public ArrayList<Kunde> getKunder() {
        return new ArrayList<>(kunder);
    }



}
