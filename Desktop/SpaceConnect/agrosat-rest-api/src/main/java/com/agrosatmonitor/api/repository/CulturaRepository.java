package com.agrosatmonitor.api.repository;

import com.agrosatmonitor.api.entity.CulturaAgricola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CulturaRepository extends JpaRepository<CulturaAgricola, Long> {
    List<CulturaAgricola> findByFazendaId(Long fazendaId);
}
