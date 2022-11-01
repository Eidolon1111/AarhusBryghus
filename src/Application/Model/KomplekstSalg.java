package Application.Model;

import java.time.LocalDateTime;

public class KomplekstSalg extends SimpeltSalg {
    private Status status;
    private Kunde kunde;
    private LocalDateTime dato2; //Anvendes til afregning af udlejelse og booking

    public KomplekstSalg(Kunde kunde) {
        super();
        this.status = Status.REGISTRERET;
        this.kunde = kunde;
    }

    public Salgslinje createModregning(Salgslinje salgslinje, int antal){
        Pris pris = new Pris(-salgslinje.beregnPrisDKK(),0,salgslinje.getProdukt());
        return super.createSalgslinje(pris, antal);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setAfholdelsesDag(LocalDateTime afregningsDag){
        this.dato2 = afregningsDag;
    }


    public enum Status {
        REGISTRERET, PANTBETALT, AFREGNET;
    }

    @Override
    public String toString() {
        return kunde.getNavn() + " " + status;
    }
}
