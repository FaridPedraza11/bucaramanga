package com.bucaramanga.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bucaramanga.app.model.Club;
import com.bucaramanga.app.model.Entrenador;
import com.bucaramanga.app.repository.ClubRepository;

@Configuration
public class DatosIniciales {
	
	@Bean
    CommandLineRunner seed(ClubRepository clubs) {
        return args -> {
            if (clubs.count() == 0) {
                Entrenador e = new Entrenador();
                e.setNombre("Rafael");
                e.setApellido("Dudamel");
                e.setEdad(51);
                e.setNacionalidad("Venezolana");

                Club c = new Club();
                c.setNombre("Atlético Bucaramanga");
                c.setCiudad("Bucaramanga");
                c.setFundacion(1949);
                c.setEstadio("Alfonso López");
                c.setEntrenador(e);

                clubs.save(c);
            }
        };
    }

}
