package Application.Model;

import Application.Controller.Controller;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
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
    void createSalgslinjeAntal1() {
        //Arrange:
        Salg salg = new Salg();
        Pris mockedPris = mock(Pris.class);
        when(mockedPris.getPrisDKK()).thenReturn(10.00);
        when(mockedPris.getKlip()).thenReturn(2);

        //Act:
        Salgslinje salgslinje = salg.createSalgslinje(mockedPris, 1);

        //Assert:
        assertEquals(salg.getSalgslinjer().get(0), salgslinje);

    }

    //TODO
//    @Test
//    void createSalgslinjeNotifyObsevers() {
//        //Arrange:
//        Salg mockedSalg = mock(Salg.class);
//        Pris mockedPris = mock(Pris.class);
//        when(mockedPris.getPrisDKK()).thenReturn(10.00);
//        when(mockedPris.getKlip()).thenReturn(2);
//        PantPligtigtProdukt mockpantPligtigtProdukt = mock(PantPligtigtProdukt.class);
//        mockedSalg.addObserver(mockpantPligtigtProdukt);
//
//        //Act:
//        mockedSalg.createSalgslinje(mockedPris, 1);
//
//        //Assert:
//        verify(mockedSalg).notifyObservers();
//    }


    @Test
    void createSalgslinjeAntal0() {
        //Arrange:
        Salg salg = new Salg();
        Pris mockedPris = mock(Pris.class);
        when(mockedPris.getPrisDKK()).thenReturn(10.00);
        when(mockedPris.getKlip()).thenReturn(2);

        //Act & Assert:
        Exception exception = assertThrows(IllegalArgumentException.class, () -> salg.createSalgslinje(mockedPris, 0));
        assertEquals("antal skal være over 0", exception.getMessage());
    }

    @Test
    void createSalgslinjeAntalMindreEnd0() {
        //Arrange:
        Salg salg = new Salg();
        Pris mockedPris = mock(Pris.class);
        when(mockedPris.getPrisDKK()).thenReturn(10.00);
        when(mockedPris.getKlip()).thenReturn(2);

        //Act & Assert:
        Exception exception = assertThrows(IllegalArgumentException.class, () -> salg.createSalgslinje(mockedPris, -1));
        assertEquals("antal skal være over 0", exception.getMessage());
    }

    @Test
    void createSalgslinjePrisenErNull() {
        //Arrange:
        Salg salg = new Salg();

        //Act & Assert:
        Exception exception = assertThrows(IllegalArgumentException.class, () -> salg.createSalgslinje(null, 1));
        assertEquals("Prisen må ikke være null", exception.getMessage());
    }

    @Test
    void fjernSalgsLinjeSalgslinje1Bliverfjernet() {
        //Arrange:
        Salg salg = new Salg();
        Pris mockedPris1 = mock(Pris.class);
        Pris mockedPris2 = mock(Pris.class);
        Pris mockedPris3 = mock(Pris.class);
        int antal = 1;
        Salgslinje salgslinje1 = salg.createSalgslinje(mockedPris1, antal);
        Salgslinje salgslinje2 = salg.createSalgslinje(mockedPris1, antal);
        Salgslinje salgslinje3 = salg.createSalgslinje(mockedPris2, antal);
        Salgslinje salgslinje4 = salg.createSalgslinje(mockedPris3, antal);
        ArrayList<Salgslinje> expected = new ArrayList<>();
        expected.add(salgslinje2);
        expected.add(salgslinje3);
        expected.add(salgslinje4);

        //Act:
        salg.fjernSalgsLinje(salgslinje1);

        //Assert:
        assertEquals(expected, salg.getSalgslinjer());
    }

    @Test
    void fjernSalgsLinjeIngenSalgslinjerFjernet() {
        //Arrange:
        Salg salg = new Salg();
        Pris mockedPris1 = mock(Pris.class);
        Pris mockedPris2 = mock(Pris.class);
        Pris mockedPris3 = mock(Pris.class);
        int antal = 1;
        Salgslinje salgslinje1 = salg.createSalgslinje(mockedPris1, antal);
        Salgslinje salgslinje2 = salg.createSalgslinje(mockedPris1, antal);
        Salgslinje salgslinje3 = salg.createSalgslinje(mockedPris2, antal);
        Salgslinje salgslinje4 = salg.createSalgslinje(mockedPris3, antal);
        Salgslinje salgslinje5 = new Salgslinje(antal, mockedPris1);
        ArrayList<Salgslinje> expected = new ArrayList<>();
        expected.add(salgslinje1);
        expected.add(salgslinje2);
        expected.add(salgslinje3);
        expected.add(salgslinje4);

        //Act:
        salg.fjernSalgsLinje(salgslinje5);

        //Assert:
        assertEquals(expected, salg.getSalgslinjer());
    }

    @Test
    void beregnSamletPrisDKKUdenSalgslinjerRabat0() {
        //Arrange:
        Salg salg = new Salg();
        Salgslinje mockedSalgslinje1 = mock(Salgslinje.class);
        when(mockedSalgslinje1.beregnPrisDKK()).thenReturn(10.00);
        Salgslinje mockedSalgslinje2 = mock(Salgslinje.class);
        when(mockedSalgslinje2.beregnPrisDKK()).thenReturn(10.00);
        Salgslinje mockedSalgslinje3 = mock(Salgslinje.class);
        when(mockedSalgslinje3.beregnPrisDKK()).thenReturn(20.00);
        Salgslinje mockedSalgslinje4 = mock(Salgslinje.class);
        when(mockedSalgslinje4.beregnPrisDKK()).thenReturn(30.00);
        double rabat = 0;
        salg.setRabatSalg(rabat);

        //Act:
        double result = salg.beregnSamletPrisDKK();

        //Assert:
        assertEquals(0, result);
    }

    @Test
    void beregnSamletPrisDKKMedSalgslinjerRabat0() {
        //Arrange:
        Salg salg = new Salg();
        Salgslinje mockedSalgslinje1 = mock(Salgslinje.class);
        when(mockedSalgslinje1.beregnPrisDKK()).thenReturn(10.00);
        Salgslinje mockedSalgslinje2 = mock(Salgslinje.class);
        when(mockedSalgslinje2.beregnPrisDKK()).thenReturn(10.00);
        Salgslinje mockedSalgslinje3 = mock(Salgslinje.class);
        when(mockedSalgslinje3.beregnPrisDKK()).thenReturn(20.00);
        Salgslinje mockedSalgslinje4 = mock(Salgslinje.class);
        when(mockedSalgslinje4.beregnPrisDKK()).thenReturn(30.00);
        salg.addSalgslinje(mockedSalgslinje1);
        salg.addSalgslinje(mockedSalgslinje2);
        salg.addSalgslinje(mockedSalgslinje3);
        salg.addSalgslinje(mockedSalgslinje4);
        double rabat = 0;
        salg.setRabatSalg(rabat);

        //Act:
        double result = salg.beregnSamletPrisDKK();

        //Assert:
        assertEquals(70, result);
    }

    @Test
    void beregnSamletPrisDKKMedSalgslinjerRabat20Procent() {
        //Arrange:
        Salg salg = new Salg();
        Salgslinje mockedSalgslinje1 = mock(Salgslinje.class);
        when(mockedSalgslinje1.beregnPrisDKK()).thenReturn(10.00);
        Salgslinje mockedSalgslinje2 = mock(Salgslinje.class);
        when(mockedSalgslinje2.beregnPrisDKK()).thenReturn(10.00);
        Salgslinje mockedSalgslinje3 = mock(Salgslinje.class);
        when(mockedSalgslinje3.beregnPrisDKK()).thenReturn(20.00);
        Salgslinje mockedSalgslinje4 = mock(Salgslinje.class);
        when(mockedSalgslinje4.beregnPrisDKK()).thenReturn(30.00);
        salg.addSalgslinje(mockedSalgslinje1);
        salg.addSalgslinje(mockedSalgslinje2);
        salg.addSalgslinje(mockedSalgslinje3);
        salg.addSalgslinje(mockedSalgslinje4);
        double rabat = 0.2;
        salg.setRabatSalg(rabat);

        //Act:
        double result = salg.beregnSamletPrisDKK();

        //Assert:
        assertEquals(56, result);
    }

    @Test
    void beregnSamletPrisDKKMedSalgslinjerRabat40DKK() {
        //Arrange:
        Salg salg = new Salg();
        Salgslinje mockedSalgslinje1 = mock(Salgslinje.class);
        when(mockedSalgslinje1.beregnPrisDKK()).thenReturn(10.00);
        Salgslinje mockedSalgslinje2 = mock(Salgslinje.class);
        when(mockedSalgslinje2.beregnPrisDKK()).thenReturn(10.00);
        Salgslinje mockedSalgslinje3 = mock(Salgslinje.class);
        when(mockedSalgslinje3.beregnPrisDKK()).thenReturn(20.00);
        Salgslinje mockedSalgslinje4 = mock(Salgslinje.class);
        when(mockedSalgslinje4.beregnPrisDKK()).thenReturn(30.00);
        salg.addSalgslinje(mockedSalgslinje1);
        salg.addSalgslinje(mockedSalgslinje2);
        salg.addSalgslinje(mockedSalgslinje3);
        salg.addSalgslinje(mockedSalgslinje4);
        double rabat = 40;
        salg.setRabatSalg(rabat);

        //Act:
        double result = salg.beregnSamletPrisDKK();

        //Assert:
        assertEquals(30, result);
    }

    @Test
    void beregnSamletPrisKlipUdenSalgslinjer() {
        //Arrange:
        Salg salg = new Salg();

        //Act:
        double result = salg.beregnSamletPrisKlip();

        //Assert:
        assertEquals(0, result);
    }

    @Test
    void beregnSamletPrisKlipMedSalgslinjeMedKlippris0() {
        //Arrange:
        Salg salg = new Salg();

        Salgslinje mockedSalgslinje4 = mock(Salgslinje.class);
        when(mockedSalgslinje4.beregnPrisKlip()).thenReturn(0);
        salg.addSalgslinje(mockedSalgslinje4);

        //Act:
        double result = salg.beregnSamletPrisKlip();

        //Assert:
        assertEquals(0, result);
    }

    @Test
    void beregnSamletPrisKlipMedSalgslinjerMedKlipprisOverNul() {
        //Arrange:
        Salg salg = new Salg();

        Salgslinje mockedSalgslinje1 = mock(Salgslinje.class);
        when(mockedSalgslinje1.beregnPrisKlip()).thenReturn(1);
        Salgslinje mockedSalgslinje2 = mock(Salgslinje.class);
        when(mockedSalgslinje2.beregnPrisKlip()).thenReturn(1);
        Salgslinje mockedSalgslinje3 = mock(Salgslinje.class);
        when(mockedSalgslinje3.beregnPrisKlip()).thenReturn(2);
        salg.addSalgslinje(mockedSalgslinje1);
        salg.addSalgslinje(mockedSalgslinje2);
        salg.addSalgslinje(mockedSalgslinje3);


        //Act:
        double result = salg.beregnSamletPrisKlip();

        //Assert:
        assertEquals(4, result);
    }

    @Test
    void beregnSamletPrisKlipMedSalgslinjerMedKlipprisOver0OgKlipprisUnder0() {
        //Arrange:
        Salg salg = new Salg();

        Salgslinje mockedSalgslinje1 = mock(Salgslinje.class);
        when(mockedSalgslinje1.beregnPrisKlip()).thenReturn(1);
        Salgslinje mockedSalgslinje2 = mock(Salgslinje.class);
        when(mockedSalgslinje2.beregnPrisKlip()).thenReturn(1);
        Salgslinje mockedSalgslinje3 = mock(Salgslinje.class);
        when(mockedSalgslinje3.beregnPrisKlip()).thenReturn(2);
        Salgslinje mockedSalgslinje4 = mock(Salgslinje.class);
        when(mockedSalgslinje4.beregnPrisKlip()).thenReturn(0);
        salg.addSalgslinje(mockedSalgslinje1);
        salg.addSalgslinje(mockedSalgslinje2);
        salg.addSalgslinje(mockedSalgslinje3);
        salg.addSalgslinje(mockedSalgslinje4);

        //Act:
        double result = salg.beregnSamletPrisKlip();

        //Assert:
        assertEquals(4, result);
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