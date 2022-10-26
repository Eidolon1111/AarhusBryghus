package Gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.w3c.dom.Text;

public class OpretProduktPane extends GridPane {
    private ListView lwProduktgrupper = new ListView<>();
    private ListView lwProdukter = new ListView<>();


    public OpretProduktPane(){
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //Oprettelse af elementer i række 1
        Label lblProduktgrupper = new Label("Vælg produktgruppe: ");
        Label lblEllerOpretNy = new Label("Eller opret ny produktgruppe: ");
        Label lblOpretProduktgruppe = new Label("Produktgruppe navn: ");
        TextField txfOpretProduktgruppe = new TextField();
        Button btnOpret = new Button("Opret");


        //Tilføjelse af elementer i række 1
        this.add(lblProduktgrupper, 0, 0);
        this.add(lwProduktgrupper, 0, 1,2,9);
        this.add(lblEllerOpretNy, 0, 11);
        this.add(lblOpretProduktgruppe, 0, 12);
        this.add(txfOpretProduktgruppe,1,12);
        this.add(btnOpret, 0, 13);

        //Oprettelse af elementer i række 2
        Label lblOpretNytProdukt = new Label("Opret nyt produkt: ");

        Label lblNavn = new Label("Navn: ");
        TextField txfNavn = new TextField();

        Label lblEnhed = new Label("Enhed: ");
        TextField txfEnhed = new TextField();

        Label lblBeskrivelse = new Label("Beskrivelse: ");
        TextField txfBeskrivelse = new TextField();

        Button btnTilføj = new Button("Tilføj");

        Label lblProdukter = new Label("Produkter:");

        //Tilføjelse af elementer i række 2
        this.add(lblOpretNytProdukt, 2, 0);
        this.add(lblNavn, 2, 1);
        this.add(txfNavn, 3, 1);
        this.add(lblEnhed, 2, 2);
        this.add(txfEnhed, 3, 2);
        this.add(lblBeskrivelse, 2, 3);
        this.add(txfBeskrivelse, 3, 3);
        this.add(btnTilføj, 2, 4);

        this.add(lblProdukter, 2, 6);
        this.add(lwProdukter, 2, 7,4,3);

        //Vinduestørrelse preset
        this.setPrefHeight(600);
        this.setPrefWidth(1000);
    }

    public void updateControls(){

    }
}
