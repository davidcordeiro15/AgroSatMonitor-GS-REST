package com.agrosatmonitor.api.entity;

import com.agrosatmonitor.api.enums.NivelRisco;
import com.agrosatmonitor.api.enums.TipoAlerta;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_ALERTA_AGRICOLA")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AlertaAgricola {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_alerta")
    @SequenceGenerator(name = "sq_alerta", sequenceName = "SQ_ALERTA", allocationSize = 1)
    @Column(name = "ID_ALERTA")
    private Long id;

    @Column(name = "TP_ALERTA", nullable = false)
    private Integer tipoAlertaCodigo;

    @Transient
    public TipoAlerta getTipoAlerta() {
        return tipoAlertaCodigo != null ? TipoAlerta.fromCodigo(tipoAlertaCodigo) : null;
    }

    public void setTipoAlerta(TipoAlerta tipo) {
        this.tipoAlertaCodigo = tipo.getCodigo();
    }

    @Column(name = "DS_ALERTA", length = 500)
    private String descricao;

    @Column(name = "TP_NIVEL_RISCO", nullable = false)
    private Integer nivelRiscoCodigo;

    @Transient
    public NivelRisco getNivelRisco() {
        return nivelRiscoCodigo != null ? NivelRisco.fromCodigo(nivelRiscoCodigo) : null;
    }

    public void setNivelRisco(NivelRisco nivel) {
        this.nivelRiscoCodigo = nivel.getCodigo();
    }

    @Column(name = "DT_GERACAO", nullable = false)
    private LocalDateTime dataGeracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FAZENDA", nullable = false)
    private Fazenda fazenda;

    @PrePersist
    protected void prePersist() {
        if (dataGeracao == null) dataGeracao = LocalDateTime.now();
    }
}
