package Application.Model;

public class Kunde {
    String navn;
    String tlfNr;
    String email;

    public Kunde(String navn, String tlfNr, String email) {
        this.navn = navn;
        this.tlfNr = tlfNr;
        this.email = email;
    }

    public String getNavn() {
        return navn;
    }

    public String getTlfNr() {
        return tlfNr;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return navn + ", Tlf: " + tlfNr + ", Email: " + email;
    }
}
