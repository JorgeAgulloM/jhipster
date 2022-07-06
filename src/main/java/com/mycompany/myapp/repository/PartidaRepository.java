package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Partida;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Partida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    //Métrica 1
    List<Partida> findByGanador_Apodo(String apodo);

    //Métrica 2
    long countByGanador_Apodo(String apodo);
}
