package com.donacion.app.historial.domain;

import com.donacion.app.publicacion.domain.Publicacion;
import com.donacion.app.solicitud.domain.Solicitud;
import com.donacion.app.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "historial_operacion")
public class HistorialOperacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long idHistorial;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private TipoOperacion tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_actor")
    private Usuario usuarioActor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_relacionado")
    private Usuario usuarioRelacionado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_publicacion")
    private Publicacion publicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud")
    private Solicitud solicitud;

    @Column(nullable = false, length = 300)
    private String descripcion;

    @Column(name = "fecha_operacion", nullable = false, updatable = false)
    private LocalDateTime fechaOperacion;

    @PrePersist
    void prePersist() {
        if (fechaOperacion == null) fechaOperacion = LocalDateTime.now();
    }
}
