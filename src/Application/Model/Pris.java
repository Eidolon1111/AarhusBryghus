package Application.Model;

public class Pris {
    private double pris;
    private int klip;
    private Produkt produkt;

    Pris(double pris, int klip, Produkt produkt) {
        this.pris = pris;
        this.klip = klip;
        this.produkt = produkt;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public double getPrisDKK() {
        return pris;
    }

    public int getPrisKlip(){
        return klip;
    }

    @Override
    public String toString() {
        if (klip>0){
            return produkt.getNavn() + " " + pris + " dkk eller " +klip + " klip";
        } else {
            return produkt.getNavn() + " " + pris + " dkk";
        }
    }
}
