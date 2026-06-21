package com.donacion.app.publicacion.domain;

import com.donacion.app.usuario.domain.Usuario;
import com.donacion.app.categoria.domain.Categoria;
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
@Table(name = "publicacion")
public class Publicacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_publicacion")
	private Long idPublicacion;
	@Column(nullable = false, unique = true, length = 20)
	private String codigo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_donante", nullable = false)
	private Usuario donante;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_categoria", nullable = false)
	private Categoria categoria;
	@Column(name = "nombre_alimento", nullable = false, length = 120)
	private String nombreAlimento;
	@Column(length = 400)
	private String descripcion;
	@Column(name = "cantidad_disponible", nullable = false, precision = 10, scale = 2)
	private BigDecimal cantidadDisponible;
	@Enumerated(EnumType.STRING)
	@Column(name = "unidad_medida", nullable = false, length = 20)
	private UnidadMedida unidadMedida;
	@Column(name = "fecha_vencimiento", nullable = false)
	private LocalDateTime fechaVencimiento;
	@Column(name = "imagen_url", length = 500)
	private String imagenUrl;
	@Column(nullable = false, length = 80)
	private String distrito;
	@Column(nullable = false, length = 200)
	private String direccion;
	@Column(nullable = false, precision = 10, scale = 7)
	private BigDecimal latitud;
	@Column(nullable = false, precision = 10, scale = 7)
	private BigDecimal longitud;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 25)
	private EstadoPublicacion estado;
	@Column(name = "fecha_publicacion", nullable = false, updatable = false)
	private LocalDateTime fechaPublicacion;
	@Column(name = "fecha_aprobacion")
	private LocalDateTime fechaAprobacion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_admin_aprobador")
	private Usuario adminAprobador;
	@Column(name = "motivo_observacion", length = 300)
	private String motivoObservacion;

	@PrePersist
	void pre() {
		if (fechaPublicacion == null)
			fechaPublicacion = LocalDateTime.now();
		if (estado == null)
			estado = EstadoPublicacion.PENDIENTE;
	}
}
