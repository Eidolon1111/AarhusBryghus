package Application.Model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalgTest {

    @Test
    void salgConstructorTest(){
        //Arrange:

        //Act:
        Salg salg = new Salg();

        //Assert:
        assertEquals(LocalDate.now(), salg.getRegistreringsDato());
    }

    @Test
    void createSalgslinje() {
        //Arrange:

        //Act:
        //Assert:
    }

    @Test
    void fjernSalgsLinje() {
        //Arrange:
        //Act:
        //Assert:
    }

    @Test
    void beregnSamletPrisDKK() {
        //Arrange:
        //Act:
        //Assert:
    }

    @Test
    void beregnSamletPrisKlip() {
        //Arrange:
        //Act:
        //Assert:
    }

    @Test
    void getSalgslinjer() {
        //Arrange:
        //Act:
        //Assert:
    }

    @Test
    void setBetalingsform_TC1_betalingsformNotNull() {
        //Arrange:
        Salg salg = new Salg();

        //Act:
        salg.setBetalingsform(Salg.Betalingsform.DANKORT);

        //Assert:
        assertEquals(Salg.Betalingsform.DANKORT, salg.getBetalingsform());
    }

    @Test
    void setBetalingsform_TC2_betalingsformNull_ThrowsException() {
        //Arrange:
        Salg salg = new Salg();

        //Act and assert:
        Exception exception = assertThrows(IllegalArgumentException.class, () -> salg.setBetalingsform(null));
        assertEquals("betalingsform må ikke være null", exception.getMessage());
    }

    @Test
    void klippeKortBetalingMuligt_TC1() {
        //Arrange:
        Salg salg = new Salg();
        Pris prisMock1 = mock(Pris.class);
        salg.createSalgslinje(prisMock1, 1);
        when(prisMock1.getKlip()).thenReturn(1);

        //Act:
        boolean expected = true;
        boolean actual = salg.klippeKortBetalingMuligt();

        //Assert:
        assertEquals(expected, actual);
    }

    @Test
    void klippeKortBetalingMuligt_TC2() {
        //Arrange:
        Salg salg = new Salg();
        Pris prisMock2 = mock(Pris.class);
        salg.createSalgslinje(prisMock2, 1);
        when(prisMock2.getKlip()).thenReturn(0);

        //Act:
        boolean expected = false;
        boolean actual = salg.klippeKortBetalingMuligt();

        //Assert:
        assertEquals(expected, actual);
    }

    @Test
    void klippeKortBetalingMuligt_TC3() {
        //Arrange:
        Salg salg = new Salg();
        Pris prisMock1 = mock(Pris.class);
        Pris prisMock2 = mock(Pris.class);
        salg.createSalgslinje(prisMock1, 1);
        salg.createSalgslinje(prisMock2, 1);
        when(prisMock1.getKlip()).thenReturn(1);
        when(prisMock2.getKlip()).thenReturn(0);

        //Act:
        boolean expected = false;
        boolean actual = salg.klippeKortBetalingMuligt();

        //Assert:
        assertEquals(expected, actual);
    }

    @Test
    void getBetalingsform() {
        //Arrange:
        //Act:
        //Assert:
    }

    @Test
    void setRabatSalg_TC1_rabatOver0() {
        //Arrange:
        Salg salg = new Salg();
        double rabat = 1.5;

        //Act:
        salg.setRabatSalg(rabat);

        //Assert:
        assertEquals(rabat, salg.getRabat());
    }

    @Test
    void setRabatSalg_TC2_rabat0() {
        //Arrange:
        Salg salg = new Salg();
        double rabat = 0;

        //Act:
        salg.setRabatSalg(rabat);

        //Assert:
        assertEquals(rabat, salg.getRabat());
    }

    @Test
    void setRabatSalg_TC3_rabatUnder0_ThrowsException() {
        //Arrange:
        Salg salg = new Salg();
        double rabat = -1;

        //Act and assert:
        Exception exception = assertThrows(IllegalArgumentException.class, () -> salg.setRabatSalg(rabat));
        assertEquals("Rabat skal være over eller lig 0", exception.getMessage());
    }

    @Test
    void getRabat() {
        //Arrange:
        //Act:
        //Assert:
    }

    @Test
    void getRegistreringsDato() {
        //Arrange:
        //Act:
        //Assert:
    }

    @Test
    void setStatus_TC1_StatusNotNull() {
        //Arrange:
        Salg salg = new Salg();

        //Act:
        salg.setStatus(Salg.Status.REGISTRERET);

        //Assert:
        assertEquals(Salg.Status.REGISTRERET, salg.getStatus());
    }

    @Test
    void setStatus_TC2_StatusNull_ThrowsException() {
        //Arrange:
        Salg salg = new Salg();

        //Act and assert:
        Exception exception = assertThrows(IllegalArgumentException.class, () -> salg.setStatus(null));
        assertEquals("Status må ikke være null", exception.getMessage());
    }

    @Test
    void getStatus() {
        //Arrange:
        //Act:
        //Assert:
    }

    @Test
    void addObserver_TC1_addTilTomListe() {
        //Arrange:
        Salg salg = new Salg();
        Observer observerMock = mock(Observer.class);

        //Act:
        salg.addObserver(observerMock);

        //Assert:
        assertTrue(salg.getObservers().contains(observerMock));
    }

    @Test
    void addObserver_TC2_addTilListeMedObserverMock_ThrowsException() {
        //Arrange:
        Salg salg = new Salg();
        Observer observerMock = mock(Observer.class);
        salg.addObserver(observerMock);

        //Act and assert:
        Exception exception = assertThrows(IllegalArgumentException.class, () -> salg.addObserver(observerMock));
        assertEquals("Observeren er allerede tilknyttet", exception.getMessage());
    }

    @Test
    void addObserver_TC3_addTilListe() {
        //Arrange:
        Salg salg = new Salg();
        Observer observerMock = mock(Observer.class);
        Observer observerMock2 = mock(Observer.class);
        salg.addObserver(observerMock2);

        //Act:
        salg.addObserver(observerMock);

        //Assert:
        assertTrue(salg.getObservers().contains(observerMock));
    }

    @Test
    void removeObserver_TC1_observerMockErIListen() {
        //Arrange:
        Salg salg = new Salg();
        Observer observerMock = mock(Observer.class);
        Observer observerMock2 = mock(Observer.class);
        salg.addObserver(observerMock);
        salg.addObserver(observerMock2);

        //Act:
        salg.removeObserver(observerMock);

        //Assert:
        assertTrue(!salg.getObservers().contains(observerMock));
    }

    @Test
    void removeObserver_TC2_observerMockErIkkeIListen() {
        //Arrange:
        Salg salg = new Salg();
        Observer observerMock = mock(Observer.class);
        Observer observerMock2 = mock(Observer.class);
        salg.addObserver(observerMock2);
        int listSize = salg.getObservers().size();

        //Act:
        salg.removeObserver(observerMock);

        //Assert:
        assertEquals(listSize, salg.getObservers().size());
    }

    @Test
    void testToString() {
        //Arrange:
        //Act:
        //Assert:
    }
}