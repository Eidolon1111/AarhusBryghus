package Application.Model;

public abstract class Produkt implements Comparable<Produkt> {
    private ProduktGruppe produktGruppe;
    private String navn;
    private String beskrivelse;

    Produkt(String navn, String beskrivelse, ProduktGruppe produktGruppe) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.produktGruppe = produktGruppe;
    }

    public String getNavn() {
       return navn;
   }

    public String getBeskrivelse(){
        return beskrivelse;
    }

    public ProduktGruppe getProduktGruppe(){
        return produktGruppe;
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
