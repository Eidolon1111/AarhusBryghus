package Application.Controller;

import Application.Model.*;
import Application.StorageInterface;
import Gui.ControllerInterface;
import Storage.Storage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class ControllerTest {

//    void setBetalingsform_TC1_betalingsformNotNull

    @Test
    @Order(1)
    void createPrisliste_TC1_good() {
        //Arrange
        String testString = "Test af opretPrisliste";
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);

        //Act
        Prisliste pl = controller.createPrisliste(testString);

        //Assert
        assertEquals(testString,pl.getNavn());
    }

    @Test
    @Order(2)
    void createPrisliste_TC2_emptyNavn_exception() {
        //Arrange
        String testString = "";
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Prisliste pl = controller.createPrisliste(testString);
        });
        assertEquals("Navn kan ikke være tom!", exception.getMessage());
    }

    @Test
    @Order(3)
    void createPrisliste_TC3_prislisteDublet_exception() {
        //Arrange
        String testString = "test af opretPrisliste";
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        ArrayList<Prisliste> temp = new ArrayList<>();
        temp.add(controller.createPrisliste("test af opretPrisliste"));
        when(storage.getPrislister()).thenReturn(temp);

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Prisliste pl = controller.createPrisliste(testString);
        });
        assertEquals("Prisliste med samme navn eksisterer!", exception.getMessage());
    }

    @Test
    @Order(4)
    void createProduktGruppe_TC1_good() {
        //Arrange
        String testString = "Test af opretProduktGruppe";
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);

        //Act
        ProduktGruppe pg = controller.createProduktGruppe(testString);

        //Assert
        assertEquals(testString,pg.getNavn());
    }

    @Test
    @Order(5)
    void createProduktGruppe_TC2_emptyNavn_exception() {
        //Arrange
        String testString = "";
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProduktGruppe pg = controller.createProduktGruppe(testString);
        });
        assertEquals("Navn kan ikke være tom!", exception.getMessage());
    }

    @Test
    @Order(6)
    void createProduktGruppe_TC3_produktgruppeDublet_exception() {
        //Arrange
        String testString = "test af opretProduktGruppe";
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        ArrayList<ProduktGruppe> temp = new ArrayList<>();
        temp.add(controller.createProduktGruppe("test af opretProduktGruppe"));
        when(storage.getProduktGrupper()).thenReturn(temp);

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProduktGruppe pg = controller.createProduktGruppe(testString);
        });
        assertEquals("ProduktGruppe med samme navn eksisterer!", exception.getMessage());
    }

    @Test
    @Order(7)
    void createSimpelProdukt_TC1_good() {
        //Arrange
        String testString = "Sommer Pilsner";
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        ProduktGruppe produktGruppe = new ProduktGruppe("Flaske");

        //Act
        Produkt p = controller.createSimpelProdukt(produktGruppe,testString,"", 2, "", false);

        //Assert
        assertEquals(testString, p.getNavn());
    }

    @Test
    @Order(8)
    void createSimpelProdukt_TC2_emptyNavn_exception() {
        //Arrange
        String testString = "";
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        ProduktGruppe produktGruppe = new ProduktGruppe("Flaske");

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Produkt p = controller.createSimpelProdukt(produktGruppe,testString,"", 2, "", false);
        });
        assertEquals("Navn kan ikke være tom!", exception.getMessage());
    }

    @Test
    @Order(9)
    void createSimpelProdukt_TC3_produktgruppeNull_exception() {
        //Arrange
        String testString = "Sommer Pilsner";
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Produkt p = controller.createSimpelProdukt(null,testString,"", 2, "", false);
        });
        assertEquals("ProduktGruppe må ikke være null!", exception.getMessage());
    }

    //Find ud af hvorfor nullpointer (p == null), Spørg Esben
    @Test
    @Order(10)
    void createSimpelProdukt_TC4_produktDublet_exception() {
        //Arrange
        String testString = "Sommer Pilsner";
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        ProduktGruppe produktGruppe = controller.createProduktGruppe("Flaske");
        controller.createSimpelProdukt(produktGruppe,testString,"",0,"", false);

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
        {controller.createSimpelProdukt(produktGruppe,testString,"", 2, "", false);
        });
        assertEquals("Produkt med samme navn eksisterer allerede i Produktgruppen!",  exception.getMessage());
    }

    @Test
    @Order(11)
    void createUdlejning_TC1_good() {
        //Arrange
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        Kunde kunde = mock(Kunde.class);

        //Act
        Udlejning ul = controller.createUdlejning(kunde);

        //Assert
        assertEquals(kunde, ul.getKunde());
    }

    @Test
    @Order(12)
    void createUdlejning_TC2_kundeNull_exception() {
        //Arrange
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Udlejning ul = controller.createUdlejning(null);
        });
        assertEquals("Kunde må ikke være null!", exception.getMessage());
    }

    @Test
    @Order(13)
    void createModregning_TC1_good() {
        //Arrange
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        Kunde kunde = mock(Kunde.class);
        Produkt klosterbryg = controller.createSimpelProdukt(mock(ProduktGruppe.class), "Klosterbryg 20 liter", "", 0, "", false);

        Prisliste temp = new Prisliste("temp");
        Pris klosterbrygPris = temp.createPrisTilPrisliste(klosterbryg,775, 0);

        Udlejning udlejning = controller.createUdlejning(kunde);
        Salgslinje salgslinje = controller.createSalgslinje(udlejning, 4, klosterbrygPris);

        //Act
        Salgslinje modregning = controller.createModregning(udlejning, salgslinje, 2);

        //Assert
        assertEquals(-1550, modregning.beregnPrisDKK());
    }

    @Test
    @Order(14)
    void createModregning_TC2_udlejningNull_exception() {
        //Arrange
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        Kunde kunde = mock(Kunde.class);
        Produkt klosterbryg = controller.createSimpelProdukt(mock(ProduktGruppe.class), "Klosterbryg 20 liter", "", 0, "", false);

        Prisliste temp = new Prisliste("temp");
        Pris klosterbrygPris = temp.createPrisTilPrisliste(klosterbryg,775, 0);

        Udlejning udlejning = controller.createUdlejning(kunde);
        Salgslinje salgslinje = controller.createSalgslinje(udlejning, 4, klosterbrygPris);

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Salgslinje modregning = controller.createModregning(null, salgslinje, 2);
        });
        assertEquals("Udlejning må ikke være null!", exception.getMessage());
    }

    @Test
    @Order(15)
    void createModregning_TC3_salgslinjeNotFound_exception() {
        //Arrange
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        Kunde kunde = mock(Kunde.class);
        Produkt klosterbryg = controller.createSimpelProdukt(mock(ProduktGruppe.class), "Klosterbryg 20 liter", "", 0, "", false);
        Produkt jazz = controller.createSimpelProdukt(mock(ProduktGruppe.class), "Jazz Classic 20 liter", "", 0, "", false);

        Prisliste temp = new Prisliste("temp");
        Prisliste temp2 = new Prisliste("temp2");
        Pris klosterbrygPris = temp.createPrisTilPrisliste(klosterbryg,775, 0);
        Pris jazzPris = temp2.createPrisTilPrisliste(jazz,625, 0);

        Udlejning udlejning = controller.createUdlejning(kunde);
        Salgslinje salgslinje = controller.createSalgslinje(udlejning, 4, klosterbrygPris);

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Salgslinje modregning = controller.createModregning(udlejning, new Salgslinje(2,jazzPris), 1);
        });
        assertEquals("Salgslinje findes ikke på udlejningen!", exception.getMessage());
    }

    @Test
    @Order(16)
    void createModregning_TC4_antalUnder0_exception() {
        //Arrange
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        Kunde kunde = mock(Kunde.class);
        Produkt klosterbryg = controller.createSimpelProdukt(mock(ProduktGruppe.class), "Klosterbryg 20 liter", "", 0, "", false);

        Prisliste temp = new Prisliste("temp");
        Pris klosterbrygPris = temp.createPrisTilPrisliste(klosterbryg, 775, 0);

        Udlejning udlejning = controller.createUdlejning(kunde);
        Salgslinje salgslinje = controller.createSalgslinje(udlejning, 4, klosterbrygPris);

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Salgslinje modregning = controller.createModregning(udlejning, salgslinje, 0);
        });
        assertEquals("Antal skal være størrere end 0!", exception.getMessage());
    }

    @Test
    @Order(17)
    void createModregning_TC5_antalOverOriginal_exception() {
        //Arrange
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        Kunde kunde = mock(Kunde.class);
        Produkt klosterbryg = controller.createSimpelProdukt(mock(ProduktGruppe.class), "Klosterbryg 20 liter", "", 0, "", false);

        Prisliste temp = new Prisliste("temp");
        Pris klosterbrygPris = temp.createPrisTilPrisliste(klosterbryg, 775, 0);

        Udlejning udlejning = controller.createUdlejning(kunde);
        Salgslinje salgslinje = controller.createSalgslinje(udlejning, 4, klosterbrygPris);

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Salgslinje modregning = controller.createModregning(udlejning, salgslinje, 5);
        });
        assertEquals("Antal må ikke overstige det originale antal på salgslinjen!", exception.getMessage());
    }

}