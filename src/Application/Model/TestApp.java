package Application.Model;

public class TestApp {
    public static void main(String[] args) {
        Produkt Klosterbryg = new Produkt("Klosterbryg");
        Produkt ExtraPilsner = new Produkt("Extra Pilsner");

        Prisliste fredagsbar = new Prisliste("Fredagsbar");
        fredagsbar.createPrislisteProdukt(Klosterbryg, 70, 2);
        fredagsbar.createPrislisteProdukt(ExtraPilsner, 70, 0);

        System.out.println(fredagsbar.getPrislisten());
    }
}
