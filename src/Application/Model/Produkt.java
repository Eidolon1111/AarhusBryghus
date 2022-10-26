package Application.Model;

public class Produkt {
    private String navn;
    private String enhed;
    private String beskrivelse;

    public Produkt(String navn, String enhed, String beskrivelse) {
        this.navn = navn;
        this.enhed = enhed;
        this.beskrivelse = beskrivelse;
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

}
