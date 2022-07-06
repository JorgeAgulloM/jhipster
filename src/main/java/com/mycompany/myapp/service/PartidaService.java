package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Partida;
import com.mycompany.myapp.repository.PartidaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PartidaService {

    private final PartidaRepository partidaRepository;

    public PartidaService(PartidaRepository partidaRepository) {
        this.partidaRepository = partidaRepository;
    }

    //Métrica 1
    public List<Partida> findByGanador_Apodo(String apodo) {
        return partidaRepository.findByGanador_Apodo(apodo);
    }

    //Métrica 2
    public long countByGanador_Apodo(String apodo) {
        return partidaRepository.countByGanador_Apodo(apodo);
    }
}
