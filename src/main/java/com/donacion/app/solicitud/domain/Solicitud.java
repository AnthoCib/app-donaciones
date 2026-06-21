package com.donacion.app.solicitud.domain;

import com.donacion.app.publicacion.domain.Publicacion;
import com.donacion.app.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.math.*;
import java.time.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitud", uniqueConstraints = @UniqueConstraint(columnNames = { "id_publicacion", "id_receptor" }))
public class Solicitud {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_solicitud")
	private Long idSolicitud;
	@Column(nullable = false, unique = true, length = 20)
	private String codigo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_publicacion", nullable = false)
	private Publicacion publicacion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_receptor", nullable = false)
	private Usuario receptor;
	@Column(nullable = false, length = 300)
	private String motivo;
	@Column(name = "cantidad_solicitada", nullable = false, precision = 10, scale = 2)
	private BigDecimal cantidadSolicitada;
	@Column(name = "personas_beneficiadas", nullable = false)
	private Integer personasBeneficiadas;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private EstadoSolicitud estado;
	@Column(name = "fecha_solicitud", nullable = false, updatable = false)
	private LocalDateTime fechaSolicitud;
	@Column(name = "fecha_respuesta")
	private LocalDateTime fechaRespuesta;
	@Column(name = "fecha_reserva")
	private LocalDateTime fechaReserva;
	@Column(name = "fecha_confirmacion_entrega")
	private LocalDateTime fechaConfirmacionEntrega;
	@Column(name = "observacion_respuesta", length = 300)
	private String observacionRespuesta;

	@PrePersist
	void pre() {
		if (estado == null)
			estado = EstadoSolicitud.PENDIENTE;
		if (fechaSolicitud == null)
			fechaSolicitud = LocalDateTime.now();
	}
}
