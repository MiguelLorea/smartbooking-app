package com.smartbooking.app;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.smartbooking.app.utils.Validadores;

import org.junit.Test;

public class ValidadoresTest {
    @Test
    public void rechazaPasswordCorta() {
        assertNotNull(Validadores.password("Abc123!"));
    }

    @Test
    public void aceptaPassword() {
        assertNull(Validadores.password("Usuario123!"));
    }

    @Test
    public void horaFinTieneQueSerPosterior() {
        assertNotNull(Validadores.horario("10:00", "09:00"));
        assertNull(Validadores.horario("10:00", "11:00"));
    }

    @Test
    public void noReservaEnElPasado() {
        assertTrue(Validadores.esPasada("2000-01-01", "10:00"));
    }
}
