package com.bucaramanga.app.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "clubes")

public class Club {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;     
    private String ciudad;     
    private int fundacion;     
    private String estadio;    

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "entrenador_id", unique = true)
    private Entrenador entrenador;

 // Unidireccional: la FK id_club vive en la tabla jugadores (sin tabla intermedia)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_club")
    private List<Jugador> jugadores = new ArrayList<>();

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public int getFundacion() { return fundacion; }
    public void setFundacion(int fundacion) { this.fundacion = fundacion; }
    public String getEstadio() { return estadio; }
    public void setEstadio(String estadio) { this.estadio = estadio; }
    public Entrenador getEntrenador() { return entrenador; }
    public void setEntrenador(Entrenador entrenador) { this.entrenador = entrenador; }

    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }

}
