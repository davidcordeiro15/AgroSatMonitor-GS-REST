package com.agrosatmonitor.api.repository;

import com.agrosatmonitor.api.entity.Fazenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FazendaRepository extends JpaRepository<Fazenda, Long> {
}
