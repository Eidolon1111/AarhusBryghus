package Application.Model;

public class Produkt implements Comparable<Produkt> {
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

    @Override
    public String toString() {
        return navn + ", " + enhed + ", " + beskrivelse;
    }

    @Override
    public int compareTo(Produkt produkt) {
        int comp = navn.compareTo(produkt.getNavn());
        if (comp == 0) {
            comp = produktGruppe.getNavn().compareTo(produkt.getProduktGruppe().getNavn());
        }
        return comp;
    }
}
