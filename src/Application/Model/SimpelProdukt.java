package Application.Model;

public class SimpelProdukt extends Produkt{
    private int antalEnheder;
    private String enhed;

    SimpelProdukt(String navn, int antalEnheder, String enhed, String beskrivelse, ProduktGruppe produktGruppe) {
        super(navn, beskrivelse, produktGruppe);
        this.antalEnheder = antalEnheder;
        this.enhed = enhed;
    }

    public int getAntalEnheder() {
        return antalEnheder;
    }

    public String getEnhed(){
        return enhed;
    }

    @Override
    public String toString() {
        String navn = super.getNavn();
        String beskrivelse = super.getBeskrivelse();
        if (navn != "" && enhed != "" && beskrivelse != "") {
            return navn + ", " + antalEnheder + " " + enhed + ", " + beskrivelse;
        } else if (enhed == "" && beskrivelse != "") {
            return navn + ", " + beskrivelse;
        } else if (enhed != "" && beskrivelse == ""){
            return navn + ", " + antalEnheder + " " + enhed;
        } else { return navn; }
    }
}
