package Application.Model;

public class Pris {
    private double pris;
    private int klip;
    private Produkt produkt;
    private Prisliste prisliste;

    Pris(double pris, int klip, Produkt produkt, Prisliste prisliste) {
        this.pris = pris;
        this.klip = klip;
        this.produkt = produkt;
        this.prisliste = prisliste;
    }

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

    public void setPris(double pris) {
        this.pris = pris;
    }

    public int getKlip() {
        return klip;
    }

    public void setKlip(int klip) {
        this.klip = klip;
    }

    public Prisliste getPrisliste(){
        return prisliste;
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
