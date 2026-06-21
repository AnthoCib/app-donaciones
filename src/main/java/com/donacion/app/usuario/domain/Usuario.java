package com.donacion.app.usuario.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.donacion.app.categoria.domain.EstadoUsuario;
import com.donacion.app.categoria.domain.TipoEntidad;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombres", nullable = false, length = 80)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "correo", nullable = false, unique = true, length = 120)
    private String correo;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 20)
    private RolUsuario rol;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_entidad", nullable = false, length = 30)
    private TipoEntidad tipoEntidad;

    @Column(name = "distrito", nullable = false, length = 80)
    private String distrito;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(
            name = "latitud",
            precision = 10
    )
    private BigDecimal latitud;

    @Column(
            name = "longitud",
            precision = 10
    )
    private BigDecimal longitud;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoUsuario estado;

    @Column(
            name = "fecha_registro",
            nullable = false,
            updatable = false
    )
    private LocalDateTime fechaRegistro;

    @PrePersist
    private void prePersist() {

        if (estado == null) {
            estado = EstadoUsuario.ACTIVO;
        }

        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }

        if (correo != null) {
            correo = correo.trim().toLowerCase();
        }
    }
}