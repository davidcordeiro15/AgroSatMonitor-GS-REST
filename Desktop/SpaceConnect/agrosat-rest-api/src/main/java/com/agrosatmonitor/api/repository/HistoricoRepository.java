package com.agrosatmonitor.api.repository;

import com.agrosatmonitor.api.entity.HistoricoConsulta;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistoricoRepository extends JpaRepository<HistoricoConsulta, Long> {
    List<HistoricoConsulta> findByFazendaIdOrderByDataConsultaDesc(Long fazendaId, Pageable pageable);
}
