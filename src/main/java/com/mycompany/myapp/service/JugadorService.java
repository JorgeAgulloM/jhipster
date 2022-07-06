package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Jugador;
import com.mycompany.myapp.repository.JugadorRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    //Servicio métrica 3
    public List<Jugador> findByGanadas_Juego(String juego) {
        return jugadorRepository.findByGanadas_Juego(juego);
    }
}
