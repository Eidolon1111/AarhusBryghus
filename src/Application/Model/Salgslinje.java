package Application.Model;

public class Salgslinje {
    private int antal;
    private Pris pris;

    public Salgslinje(int antal, Pris pris) {
        this.antal = antal;
        this.pris = pris;
    }

    public int getAntal() {
        return antal;
    }

    public Produkt getProdukt() {
        return pris.getProdukt();
    }

    public double beregnPrisDKK(Prisliste prisliste){
        return pris.getPris() * antal;
    }

    public int beregnPrisKlip(Prisliste prisliste){
        return pris.getKlip() * antal;
    }

    public String printMellemRegning(Prisliste prisliste){
        return pris.getProdukt().printNavn() + "\t" + "antal: " + antal + "\t" + "DKK: " +
                beregnPrisDKK(prisliste) + " / Klip: " + beregnPrisKlip(prisliste);
    }

    //TODO
    public boolean klippeKortBetalingMuligt(){
        boolean result = false;
        return result;
    }

    @Override
    public String toString() {
        return pris.getProdukt() + " " + antal + " ";
    }
}
