package com.bucaramanga.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.bucaramanga.app.model.*;
import com.bucaramanga.app.repository.ClubRepository;

@Component
public class DatosJugadores implements CommandLineRunner {
	
	 private final ClubRepository clubs;

	    public DatosJugadores(ClubRepository clubs) {
	        this.clubs = clubs;
	    }

	    @Override
	    public void run(String... args) {
	        if (clubs.count() == 0) {
	            Club c = new Club();
	            c.setNombre("Atlético Bucaramanga");
	            c.setCiudad("Bucaramanga");
	            c.setEstadio("Estadio Alfonso López");
	            c.setFundacion(1949);

	            // Plantel demo
	            Jugador j1 = new Jugador(); j1.setNombre("Juan"); j1.setApellido("Pérez"); j1.setNumero(1); j1.setPosicion("Portero");
	            Jugador j2 = new Jugador(); j2.setNombre("Carlos"); j2.setApellido("Gómez"); j2.setNumero(2); j2.setPosicion("Defensa");
	            Jugador j3 = new Jugador(); j3.setNombre("Luis"); j3.setApellido("Rojas"); j3.setNumero(10); j3.setPosicion("Volante");
	            Jugador j4 = new Jugador(); j4.setNombre("Andrés"); j4.setApellido("Díaz"); j4.setNumero(9); j4.setPosicion("Delantero");

	            c.getJugadores().add(j1);
	            c.getJugadores().add(j2);
	            c.getJugadores().add(j3);
	            c.getJugadores().add(j4);

	            clubs.save(c);
	        }
	    }
	

}
