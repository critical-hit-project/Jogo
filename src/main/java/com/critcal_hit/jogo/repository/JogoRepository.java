// jogo-service/src/main/java/com/criticalhit/jogo/repository/JogoRepository.java
package com.critcal_hit.jogo.repository;


import com.critcal_hit.jogo.model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JogoRepository extends JpaRepository<Jogo, Long> {
    List<Jogo> findByHabilitadoTrue();
    List<Jogo> findByIdIn(List<Long> ids);

}