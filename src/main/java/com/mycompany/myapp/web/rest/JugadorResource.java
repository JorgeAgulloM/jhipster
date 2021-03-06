package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Jugador;
import com.mycompany.myapp.repository.JugadorRepository;
import com.mycompany.myapp.service.JugadorService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Jugador}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class JugadorResource {

    private final Logger log = LoggerFactory.getLogger(JugadorResource.class);

    private static final String ENTITY_NAME = "jugador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JugadorRepository jugadorRepository;

    private final JugadorService jugadorService;

    public JugadorResource(JugadorRepository jugadorRepository, JugadorService jugadorService) {
        this.jugadorRepository = jugadorRepository;
        this.jugadorService = jugadorService;
    }

    //get métrica 3
    @GetMapping("/jugadores/ganadores-juego")
    public ResponseEntity<List<Jugador>> getGanadasJuego(String juego) {
        if (juego.isBlank() || juego.isEmpty()) {
            throw new BadRequestAlertException("Es necesario conocer el juego", ENTITY_NAME, "notnull");
        }

        List<Jugador> result = jugadorService.findByGanadas_Juego(juego);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code POST  /jugadors} : Create a new jugador.
     *
     * @param jugador the jugador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jugador, or with status {@code 400 (Bad Request)} if the jugador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jugadors")
    public ResponseEntity<Jugador> createJugador(@Valid @RequestBody Jugador jugador) throws URISyntaxException {
        log.debug("REST request to save Jugador : {}", jugador);
        if (jugador.getId() != null) {
            throw new BadRequestAlertException("A new jugador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Jugador result = jugadorRepository.save(jugador);
        return ResponseEntity
            .created(new URI("/api/jugadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jugadors/:id} : Updates an existing jugador.
     *
     * @param id the id of the jugador to save.
     * @param jugador the jugador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jugador,
     * or with status {@code 400 (Bad Request)} if the jugador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jugador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jugadors/{id}")
    public ResponseEntity<Jugador> updateJugador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Jugador jugador
    ) throws URISyntaxException {
        log.debug("REST request to update Jugador : {}, {}", id, jugador);
        if (jugador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jugador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jugadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Jugador result = jugadorRepository.save(jugador);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jugador.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /jugadors/:id} : Partial updates given fields of an existing jugador, field will ignore if it is null
     *
     * @param id the id of the jugador to save.
     * @param jugador the jugador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jugador,
     * or with status {@code 400 (Bad Request)} if the jugador is not valid,
     * or with status {@code 404 (Not Found)} if the jugador is not found,
     * or with status {@code 500 (Internal Server Error)} if the jugador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/jugadors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Jugador> partialUpdateJugador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Jugador jugador
    ) throws URISyntaxException {
        log.debug("REST request to partial update Jugador partially : {}, {}", id, jugador);
        if (jugador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jugador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jugadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Jugador> result = jugadorRepository
            .findById(jugador.getId())
            .map(existingJugador -> {
                if (jugador.getApodo() != null) {
                    existingJugador.setApodo(jugador.getApodo());
                }
                if (jugador.getNombre() != null) {
                    existingJugador.setNombre(jugador.getNombre());
                }
                if (jugador.getApellido() != null) {
                    existingJugador.setApellido(jugador.getApellido());
                }
                if (jugador.getNacimiento() != null) {
                    existingJugador.setNacimiento(jugador.getNacimiento());
                }

                return existingJugador;
            })
            .map(jugadorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jugador.getId().toString())
        );
    }

    /**
     * {@code GET  /jugadors} : get all the jugadors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jugadors in body.
     */
    @GetMapping("/jugadors")
    public List<Jugador> getAllJugadors() {
        log.debug("REST request to get all Jugadors");
        return jugadorRepository.findAll();
    }

    /**
     * {@code GET  /jugadors/:id} : get the "id" jugador.
     *
     * @param id the id of the jugador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jugador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jugadors/{id}")
    public ResponseEntity<Jugador> getJugador(@PathVariable Long id) {
        log.debug("REST request to get Jugador : {}", id);
        Optional<Jugador> jugador = jugadorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jugador);
    }

    /**
     * {@code DELETE  /jugadors/:id} : delete the "id" jugador.
     *
     * @param id the id of the jugador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jugadors/{id}")
    public ResponseEntity<Void> deleteJugador(@PathVariable Long id) {
        log.debug("REST request to delete Jugador : {}", id);
        jugadorRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
