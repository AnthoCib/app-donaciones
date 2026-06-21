package com.donacion.app.reporte.domain;

import com.donacion.app.usuario.domain.Usuario;
import com.donacion.app.publicacion.domain.Publicacion;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reporte")
public class Reporte {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_reporte")
	private Long idReporte;
	@Column(nullable = false, unique = true, length = 20)
	private String codigo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_reportante", nullable = false)
	private Usuario reportante;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_publicacion")
	private Publicacion publicacion;
	@Column(nullable = false, length = 120)
	private String asunto;
	@Column(nullable = false, length = 400)
	private String descripcion;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private EstadoReporte estado;
	@Column(name = "fecha_reporte", nullable = false)
	private LocalDateTime fechaReporte;
	@Column(name = "fecha_atencion")
	private LocalDateTime fechaAtencion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_admin")
	private Usuario admin;
	@Column(name = "respuesta_admin", length = 400)
	private String respuestaAdmin;

	@PrePersist
	void pre() {
		if (estado == null)
			estado = EstadoReporte.PENDIENTE;
		if (fechaReporte == null)
			fechaReporte = LocalDateTime.now();
	}
}
