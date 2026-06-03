package com.agrosatmonitor.api.repository;

import com.agrosatmonitor.api.entity.AlertaAgricola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<AlertaAgricola, Long> {
    List<AlertaAgricola> findByFazendaIdOrderByDataGeracaoDesc(Long fazendaId);
    long countByFazendaId(Long fazendaId);
}
