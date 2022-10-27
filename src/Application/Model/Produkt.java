package Application.Model;

public class Produkt {
    private ProduktGruppe produktGruppe;
    private String navn;
    private String enhed;
    private String beskrivelse;

    Produkt(String navn, String enhed, String beskrivelse, ProduktGruppe produktGruppe) {
        this.navn = navn;
        this.enhed = enhed;
        this.beskrivelse = beskrivelse;
        this.produktGruppe = produktGruppe;
    }

    public String getNavn() {
        return navn;
    }

    public String getBeskrivelse(){
        return beskrivelse;
    }

    public String getEnhed(){
        return enhed;
    }

    public ProduktGruppe getProduktGruppe(){
        return produktGruppe;
    }

    public String printNavn(){
        return navn;
    }

    public String toString() {
        return navn + ", " + enhed + ", " + beskrivelse;
    }
}
