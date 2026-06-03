package com.agrosatmonitor.api.repository;

import com.agrosatmonitor.api.entity.MonitoramentoVegetacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonitoramentoVegetacaoRepository extends JpaRepository<MonitoramentoVegetacao, Long> {
    List<MonitoramentoVegetacao> findByFazendaIdOrderByDataLeituraDesc(Long fazendaId);
    Optional<MonitoramentoVegetacao> findFirstByFazendaIdOrderByDataLeituraDesc(Long fazendaId);
}
