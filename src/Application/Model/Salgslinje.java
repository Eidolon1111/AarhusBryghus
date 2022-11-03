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

    public void setAntal(int antal){
        this.antal = antal;
    }

    public Produkt getProdukt() {
        return pris.getProdukt();
    }

    public double beregnPrisDKK(){
        double result;
        if(rabat != 0){
            if(rabat < 1 ){
                result = (pris.getPrisDKK() * antal) - ((pris.getPrisDKK() * antal) * rabat);
            } else {
                result = (pris.getPrisDKK() * antal) - rabat;
            }
        }else {
            result = (pris.getPrisDKK() * antal);
        }
        return result;
    }

    public int beregnPrisKlip(){
        return pris.getKlip() * antal;
    }

    public String printMellemRegning() {
        return "Jeg Bliver Brug HER!!";
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

    public Pris getPris(){
        return pris;
    }

    @Override
    public String toString() {
        String result;
        if (klippeKortBetalingMuligt()) {
            if (rabat != 0 && rabat < 1) {
                result = pris.getProdukt().printNavn() + "\t" + "antal: " + antal + "\t" + "DKK: " +
                        beregnPrisDKK() + " / Klip: " + beregnPrisKlip() + " -" + (rabat * 100) + "%";
            } else if (rabat != 0 && rabat > 1) {
                result = pris.getProdukt().printNavn() + "\t" + "antal: " + antal + "\t" + "DKK: " +
                        beregnPrisDKK() + " / Klip: " + beregnPrisKlip() + " -" + rabat + " DKK";
            } else {
                result = pris.getProdukt().printNavn() + "\t" + "antal: " + antal + "\t" + "DKK: " +
                        beregnPrisDKK() + " / Klip: " + beregnPrisKlip();
            }
        } else {
            if (rabat != 0 && rabat < 1) {
                result = pris.getProdukt().printNavn() + "\t" + "antal: " + antal + "\t" + "DKK: " +
                        beregnPrisDKK() + " -" + (rabat * 100) + "%";
            } else if (rabat != 0 && rabat > 1) {
                result = pris.getProdukt().printNavn() + "\t" + "antal: " + antal + "\t" + "DKK: " +
                        beregnPrisDKK() + " -" + rabat + " DKK";
            } else {
                result = pris.getProdukt().printNavn() + "\t" + "antal: " + antal + "\t" + "DKK: " +
                        beregnPrisDKK();
            }
        }
        return result;
    }
}
