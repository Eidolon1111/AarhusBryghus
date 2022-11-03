package Application.Model;

public class SimpelProdukt extends Produkt{
    private String enhed;

    SimpelProdukt(String navn, String enhed, String beskrivelse, ProduktGruppe produktGruppe) {
        super(navn, beskrivelse, produktGruppe);
        this.enhed = enhed;
    }

    public String getEnhed(){
        return enhed;
    }

    @Override
    public String toString() {
        String navn = super.getNavn();
        String beskrivelse = super.getBeskrivelse();
        if (navn != "" && enhed != "" && beskrivelse != "") {
            return navn + ", " + enhed + ", " + beskrivelse;
        } else if (enhed == "" && beskrivelse != "") {
            return navn + ", " + beskrivelse;
        } else if (enhed != "" && beskrivelse == ""){
            return navn + ", " + enhed;
        } else { return navn; }
    }
}
