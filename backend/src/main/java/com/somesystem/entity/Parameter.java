package com.somesystem.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parametrai")
@NoArgsConstructor
@Getter
@Setter
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "kodas")
    private String kodas;
    @Column(name = "pavadinimas")
    private String pavadinimas;
    @Column(name = "aprasymas")
    private String aprasymas;

    @Column(name = "galiojaNuo")
    private LocalDateTime galiojaNuo;

    @Column(name = "galiojaIki")
    private LocalDateTime galiojaIki;

    public Parameter(final String kodas, final String pavadinimas, final String aprasymas, final LocalDateTime galiojaNuo, final LocalDateTime galiojaIki) {
        this.kodas = kodas;
        this.pavadinimas = pavadinimas;
        this.aprasymas = aprasymas;
        this.galiojaNuo = galiojaNuo;
        this.galiojaIki = galiojaIki;
    }
}
