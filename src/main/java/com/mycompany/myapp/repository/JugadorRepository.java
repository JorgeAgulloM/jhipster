package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Jugador;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Jugador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    //Métrica 3
    List<Jugador> findByGanadas_Juego(String juego);
}
