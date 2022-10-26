package Application.Model;

public class TestApp {
    public static void main(String[] args) {
        Produkt Klosterbryg = new Produkt("Klosterbryg");
        Produkt ExtraPilsner = new Produkt("Extra Pilsner");

        Prisliste fredagsbar = new Prisliste("Fredagsbar");
        fredagsbar.createPrisTilPrisliste(Klosterbryg, 70, 2);
        fredagsbar.createPrisTilPrisliste(ExtraPilsner, 70, 0);

        System.out.println(fredagsbar.getPrislisten());
    }
}
