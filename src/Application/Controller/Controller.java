package Application.Controller;

import Application.Model.Pris;
import Application.Model.Prisliste;
import Application.Model.Produkt;
import Application.Model.ProduktGruppe;
import Application.StorageInterface;

public class Controller {
    private static Controller controller;
    private StorageInterface storage;

    public Controller(StorageInterface storage) {
        this.storage = storage;
    }

    public Prisliste createPrisliste(String navn) {
        Prisliste pl = new Prisliste(navn);
        storage.addPrisliste(pl);
        return pl;
    }

    public Pris createPris(Prisliste prisliste, Produkt produkt, double pris, int klip) {
        Pris p = prisliste.createPrisTilPrisliste(produkt, pris, klip);
        return p;
    }

    public ProduktGruppe createProduktGruppe(String navn) {
        ProduktGruppe pg = new ProduktGruppe(navn);
        storage.addProduktGruppe(pg);
        return pg;
    }

    public Produkt createProdukt(ProduktGruppe produktGruppe, String navn, String beskrivelse, String enhed) {
        Produkt p = produktGruppe.createProdukt(navn, enhed, beskrivelse);
        return p;
    }

    public static void init(){
        controller.createPrisliste("Fredagsbar");
        controller.createPrisliste("Butik");

        controller.createProduktGruppe("Flaske");
        controller.createProduktGruppe("Fadøl");
        controller.createProduktGruppe("Spiritus");
        controller.createProduktGruppe("Fustage");
        controller.createProduktGruppe("Kulsyre");
        controller.createProduktGruppe("Malt");
        controller.createProduktGruppe("Beklædning");
        controller.createProduktGruppe("Anlæg");
        controller.createProduktGruppe("Glas");
        controller.createProduktGruppe("Sampakninger");
        controller.createProduktGruppe("Rundvisning");
    }


}
