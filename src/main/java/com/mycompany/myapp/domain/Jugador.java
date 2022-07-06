package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Jugador.
 */
@Entity
@Table(name = "jugador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Pattern(regexp = "(^[a-zA-Z0-9_]*$)")
    @Column(name = "apodo", nullable = false, unique = true)
    private String apodo;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotNull
    @Column(name = "nacimiento", nullable = false)
    private LocalDate nacimiento;

    @OneToMany(mappedBy = "ganador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ganador", "perdedor" }, allowSetters = true)
    private Set<Partida> ganadas = new HashSet<>();

    @OneToMany(mappedBy = "perdedor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ganador", "perdedor" }, allowSetters = true)
    private Set<Partida> perdidas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Jugador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApodo() {
        return this.apodo;
    }

    public Jugador apodo(String apodo) {
        this.setApodo(apodo);
        return this;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Jugador nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public Jugador apellido(String apellido) {
        this.setApellido(apellido);
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getNacimiento() {
        return this.nacimiento;
    }

    public Jugador nacimiento(LocalDate nacimiento) {
        this.setNacimiento(nacimiento);
        return this;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Set<Partida> getGanadas() {
        return this.ganadas;
    }

    public void setGanadas(Set<Partida> partidas) {
        if (this.ganadas != null) {
            this.ganadas.forEach(i -> i.setGanador(null));
        }
        if (partidas != null) {
            partidas.forEach(i -> i.setGanador(this));
        }
        this.ganadas = partidas;
    }

    public Jugador ganadas(Set<Partida> partidas) {
        this.setGanadas(partidas);
        return this;
    }

    public Jugador addGanada(Partida partida) {
        this.ganadas.add(partida);
        partida.setGanador(this);
        return this;
    }

    public Jugador removeGanada(Partida partida) {
        this.ganadas.remove(partida);
        partida.setGanador(null);
        return this;
    }

    public Set<Partida> getPerdidas() {
        return this.perdidas;
    }

    public void setPerdidas(Set<Partida> partidas) {
        if (this.perdidas != null) {
            this.perdidas.forEach(i -> i.setPerdedor(null));
        }
        if (partidas != null) {
            partidas.forEach(i -> i.setPerdedor(this));
        }
        this.perdidas = partidas;
    }

    public Jugador perdidas(Set<Partida> partidas) {
        this.setPerdidas(partidas);
        return this;
    }

    public Jugador addPerdida(Partida partida) {
        this.perdidas.add(partida);
        partida.setPerdedor(this);
        return this;
    }

    public Jugador removePerdida(Partida partida) {
        this.perdidas.remove(partida);
        partida.setPerdedor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jugador)) {
            return false;
        }
        return id != null && id.equals(((Jugador) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jugador{" +
            "id=" + getId() +
            ", apodo='" + getApodo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", nacimiento='" + getNacimiento() + "'" +
            "}";
    }
}
