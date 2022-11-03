package Application.Model;

import java.time.LocalDateTime;

public class Rundvisning extends Salg{

    private LocalDateTime afholdelsesDato;
    private Kunde kunde;

    public Rundvisning(Kunde kunde) {
        super();
        super.setStatus(Status.REGISTRERET);
        this.kunde = kunde;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setAfholdelsesDag(LocalDateTime afholdelsesDato){
        this.afholdelsesDato = afholdelsesDato;
    }

    public LocalDateTime getAfholdelsesdag() {
        return afholdelsesDato;
    }

    @Override
    public String toString() {
        return kunde.getNavn() + ", " + afholdelsesDato;
    }

}

