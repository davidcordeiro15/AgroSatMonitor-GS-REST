package com.agrosatmonitor.api.repository;

import com.agrosatmonitor.api.entity.MonitoramentoClimatico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonitoramentoClimaticoRepository extends JpaRepository<MonitoramentoClimatico, Long> {
    List<MonitoramentoClimatico> findByFazendaIdOrderByDataLeituraDesc(Long fazendaId);
    Optional<MonitoramentoClimatico> findFirstByFazendaIdOrderByDataLeituraDesc(Long fazendaId);
}
