package Application.Model;

public class KompleksSalg extends Salg {
    private Status status;
    private Kunde kunde;
    private SimpelSalg afregning;

    public KompleksSalg(Kunde kunde) {
        super();
        this.status = Status.REGISTRERET;
        this.kunde = kunde;
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

    public void createAfregning() {
        SimpelSalg afregning = new SimpelSalg();
        this.afregning = afregning;
    }

    public SimpelSalg getAfregning() {
        return afregning;
    }

    public double getModregning(){
        return 0;
    }

    public enum Status {
        REGISTRERET, PANTBETALT, AFREGNET;
    }
}
