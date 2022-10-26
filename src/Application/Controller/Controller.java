package Application.Controller;

import Application.Model.Pris;
import Application.Model.Prisliste;
import Application.Model.Produkt;
import Application.Model.ProduktGruppe;
import Application.StorageInterface;

import java.util.ArrayList;

public class Controller {
    private static Controller controller;
    private StorageInterface storage;

    public Controller(StorageInterface storage) {
        this.storage = storage;
    }
    
    public ArrayList<Prisliste> getPrislister() {
        return storage.getPrislister();
    }

    public ArrayList<ProduktGruppe> getProduktGrupper() {
        return storage.getProduktGrupper();
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
        Prisliste fredagsbar = controller.createPrisliste("Fredagsbar");
        Prisliste butik = controller.createPrisliste("Butik");

        ProduktGruppe pg1 = controller.createProduktGruppe("Flaske");
        ProduktGruppe pg2 = controller.createProduktGruppe("Fadøl");
        ProduktGruppe pg3 = controller.createProduktGruppe("Spiritus");
        ProduktGruppe pg4 = controller.createProduktGruppe("Fustage");
        ProduktGruppe pg5 = controller.createProduktGruppe("Kulsyre");
        ProduktGruppe pg6 = controller.createProduktGruppe("Malt");
        ProduktGruppe pg7 = controller.createProduktGruppe("Beklædning");
        ProduktGruppe pg8 = controller.createProduktGruppe("Anlæg");
        ProduktGruppe pg9 = controller.createProduktGruppe("Glas");
        ProduktGruppe pg10 = controller.createProduktGruppe("Sampakninger");
        ProduktGruppe pg11 = controller.createProduktGruppe("Rundvisning");

        //Flaske produkter
        Produkt p1 = controller.createProdukt(pg1,"Klosterbryg","","");
        Produkt p2 = controller.createProdukt(pg1,"Sweet Georgia Brown","","");
        Produkt p3 = controller.createProdukt(pg1,"Extra Pilsner","","");
        Produkt p4 = controller.createProdukt(pg1,"Celebration","","");
        Produkt p5 = controller.createProdukt(pg1,"Blondie","","");

        //Fadøl produkter
        controller.createProdukt(pg2, "Klosterbryg", "", "40 cl");
        controller.createProdukt(pg2, "Jazz Classic", "", "40 cl");
        controller.createProdukt(pg2, "Extra Pilsner", "", "40 cl");
        controller.createProdukt(pg2, "Celebration", "", "40 cl");
        controller.createProdukt(pg2, "Blondie", "", "40 cl");

        //Spritus produkter
        controller.createProdukt(pg3, "Whisky", "45%", "50 cl rør");

        //Fustage produkter
        controller.createProdukt(pg4, "Klosterbryg", "","20 liter");

        //Kulsyre
        controller.createProdukt(pg5, "6 kg", "", "");

        //Malt
        controller.createProdukt(pg6, "Maltsæk", "", "25 kg");

        //Beklædning
        controller.createProdukt(pg7, "t-shirt", "", "");

        //Anlæg
        controller.createProdukt(pg8, "1-hane", "", "");

        //Glas
        controller.createProdukt(pg9, "Glas", "uanset størrelse", "");

        //Sampakninger
        controller.createProdukt(pg10, "Gaveæske", "2 øl, 2 glas", "");

        //Rundvisning
        controller.createProdukt(pg11, "Rundvisning", "kan variere afhængig af dag/aften/studierabat", "pr person dag");


        //Priser for fredagsbar for flasker
        controller.createPris(fredagsbar,p1, 70,2);
        controller.createPris(fredagsbar,p2, 70,2);
        controller.createPris(fredagsbar,p3, 70,2);
        controller.createPris(fredagsbar,p4, 70,2);
        controller.createPris(fredagsbar,p5, 70,2);

        //Priser for butik for flasker
        controller.createPris(fredagsbar,p1, 36,2);
        controller.createPris(fredagsbar,p2, 36,2);
        controller.createPris(fredagsbar,p3, 36,2);
        controller.createPris(fredagsbar,p4, 36,2);
        controller.createPris(fredagsbar,p5, 36,2);
    }


}
