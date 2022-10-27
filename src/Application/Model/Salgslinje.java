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

    public double beregnPris(Prisliste prisliste){
        return prisliste.findPrisPaaProdukt(produkt)*antal;
    }
}
