package com.somesystem.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parametro_reiksmes")
@NoArgsConstructor
@Getter
@Setter
public class ParameterValue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long parametroReikmesId;
    @Column(name = "reiksme")
    private String reiksme;
    @Column(name = "galiojaNuo")
    private LocalDateTime galiojaNuo;
    @Column(name = "galiojaIki")
    private LocalDateTime galiojaIki;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Parameter parametras;

    public ParameterValue(final String reiksme, final LocalDateTime galiojaNuo, final LocalDateTime galiojaIki, Parameter param) {
        this.reiksme = reiksme;
        this.galiojaNuo = galiojaNuo;
        this.galiojaIki = galiojaIki;
        this.parametras = param;
    }
}
