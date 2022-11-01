package Application.Model;

public class Salgslinje {
    private int antal;
    private Pris pris;
    private double rabat;

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

    public double beregnPrisDKK(){
        double result;
        if(rabat < 1){
            result = (pris.getPris() * antal) * rabat;
        } else {
            result = (pris.getPris() * antal) - rabat;
        }
        return result;
    }

    public int beregnPrisKlip(){
        return pris.getKlip() * antal;
    }

    public String printMellemRegning(){
        return pris.getProdukt().printNavn() + "\t" + "antal: " + antal + "\t" + "DKK: " +
                beregnPrisDKK() + " / Klip: " + beregnPrisKlip();
    }

    public boolean klippeKortBetalingMuligt(){
        boolean result = false;
        if(pris.getKlip() != 0){
            result = true;
        }
        return result;
    }

    public void setRabat(double rabat){
        this.rabat = rabat;
    }

    public double getRabat(){
        return rabat;
    }

    @Override
    public String toString() {
        return pris.getProdukt() + " " + antal + " ";
    }
}
