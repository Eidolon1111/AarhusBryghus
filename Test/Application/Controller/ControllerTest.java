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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class ControllerTest {

    @Test
    @Order(1)
    void createPrisliste() {
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
    void createProduktGruppe() {
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
    @Order(3)
    void createSimpelProdukt() {
        //Arrange
        String testString = "Test af opretSimpelProdukt";
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        ProduktGruppe produktGruppe = new ProduktGruppe("Test");

        //Act
        Produkt p = controller.createSimpelProdukt(produktGruppe,testString,"", 2, "", false);

        //Assert
        assertEquals(testString, p.getNavn());
    }

    @Test
    @Order(4)
    void createUdlejning() {
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
    @Order(5)
    void createModregning() {
        //Arrange
        Storage storage = mock(Storage.class);
        Controller controller = new Controller(storage);
        Kunde kunde = mock(Kunde.class);
        Salgslinje salgslinje = mock(Salgslinje.class);
        Udlejning udlejning = controller.createUdlejning(kunde);

        //Act
        Salgslinje modregning = controller.createModregning(udlejning, salgslinje, 1);

        //Assert
        assertEquals(1, modregning.getAntal());
    }
}