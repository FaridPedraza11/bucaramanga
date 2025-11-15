package com.bucaramanga.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.bucaramanga.app.model.Club;
import com.bucaramanga.app.model.Entrenador;
import com.bucaramanga.app.model.Jugador;
import com.bucaramanga.app.repository.ClubRepository;
import com.bucaramanga.app.repository.JugadorRepository;
import jakarta.validation.Valid;

@Controller
public class ClubController {
	
	private final ClubRepository clubs;
    private final JugadorRepository jugadoresRepo;

    public ClubController(ClubRepository clubs, JugadorRepository jugadoresRepo) {
        this.clubs = clubs;
        this.jugadoresRepo = jugadoresRepo;
    }

 // ====== Portada ======
    @GetMapping("/")
    public String home(Model model) {
        Club club = clubs.findAll().stream().findFirst().orElse(null);
        model.addAttribute("club", club);
        return "index";
    }

    // ====== Club / Entrenador ======
    @GetMapping("/club/editar")
    public String editar(Model model) {
        Club club = clubs.findAll().stream().findFirst().orElse(null);
        if (club == null) return "redirect:/";
        Entrenador entrenador = club.getEntrenador();
        if (entrenador == null) entrenador = new Entrenador();
        model.addAttribute("club", club);
        model.addAttribute("entrenador", entrenador);
        return "club_editar";
    }

    @PostMapping("/club/guardar")
    public String guardar(@ModelAttribute("club") Club clubForm,
                          @Valid @ModelAttribute("entrenador") Entrenador entrenador,
                          BindingResult br,
                          Model model) {
        Club club = clubs.findAll().stream().findFirst().orElse(null);
        if (club == null) return "redirect:/";
        if (br.hasErrors()) {
            model.addAttribute("club", club);
            return "club_editar";
        }
        club.setEntrenador(entrenador);
        club.setNombre(clubForm.getNombre());
        club.setCiudad(clubForm.getCiudad());
        club.setEstadio(clubForm.getEstadio());
        club.setFundacion(clubForm.getFundacion());
        clubs.save(club);
        return "redirect:/";
    }

    // ====== Plantel (jugadores) ======
    @GetMapping("/jugadores")
    public String listarJugadores(Model model) {
        Club club = clubs.findAll().stream().findFirst().orElse(null);
        if (club == null) return "redirect:/";
        model.addAttribute("club", club);
        model.addAttribute("jugadores", club.getJugadores());
        // Coincide con tu archivo: src/main/resources/templates/listar_jugadores.html
        return "listar_jugadores";
    }
    
   

    @GetMapping("/jugador/nuevo")
    public String nuevoJugador(Model model) {
        model.addAttribute("jugador", new Jugador());
        return "jugador_form";
    }

    @GetMapping("/jugador/editar/{id}")
    public String editarJugador(@PathVariable Long id, Model model) {
        Jugador j = jugadoresRepo.findById(id).orElse(null);
        if (j == null) return "redirect:/jugadores"; // <-- corregido
        model.addAttribute("jugador", j);
        return "jugador_form";
    }

    @PostMapping("/jugador/guardar")
    public String guardarJugador(@Valid @ModelAttribute("jugador") Jugador jugador,
                                 BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "jugador_form";
        }
        Club club = clubs.findAll().stream().findFirst().orElse(null);
        if (club == null) return "redirect:/";

        if (jugador.getId() == null) {
            club.getJugadores().add(jugador); // setea id_club vía @JoinColumn
            clubs.save(club);
        } else {
            jugadoresRepo.save(jugador);
        }
        return "redirect:/jugadores"; // <-- corregido
    }
    @PostMapping("/jugador/eliminar/{id}")
    public String eliminarJugador(@PathVariable Long id) {
        Club club = clubs.findAll().stream().findFirst().orElse(null);
        if (club != null) {
            club.getJugadores().removeIf(j -> j.getId().equals(id));
            clubs.save(club); // orphanRemoval=true borra el registro
        }
        return "redirect:/jugadores"; // <-- corregido
    }
}
