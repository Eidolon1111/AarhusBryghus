package Application.Model;

public class Salgslinje {
    private int antal;
    private Produkt produkt;

    public Salgslinje(int antal, Produkt produkt) {
        this.antal = antal;
        this.produkt = produkt;
    }

    public int getAntal() {
        return antal;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public double beregnPrisDKK(Prisliste prisliste){
        return prisliste.findPrisPaaProduktDKK(produkt)*antal;
    }

    public int beregnPrisKlip(Prisliste prisliste){
        return prisliste.findPrisPaaProduktKlip(produkt) * antal;
    }

    public String printMellemRegning(Prisliste prisliste){
        return produkt.printNavn() + "\t" + "antal: " + antal + "\t" + "DKK: " +
                beregnPrisDKK(prisliste) + " / Klip: " + beregnPrisKlip(prisliste);
    }

    @Override
    public String toString() {
        return produkt + " " + antal + " ";
    }
}
