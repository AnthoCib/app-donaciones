package com.donacion.app.entrega.domain;

import com.donacion.app.solicitud.domain.Solicitud;
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
@Table(name = "entrega")
public class Entrega {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_entrega")
	private Long idEntrega;
	@Column(nullable = false, unique = true, length = 20)
	private String codigo;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_solicitud", nullable = false, unique = true)
	private Solicitud solicitud;
	@Column(name = "fecha_entrega")
	private LocalDateTime fechaEntrega;
	@Column(name = "cantidad_entregada", precision = 10, scale = 2)
	private BigDecimal cantidadEntregada;
	@Column(name = "peso_entregado_kg", precision = 10, scale = 2)
	private BigDecimal pesoEntregadoKg;
	@Column(name = "personas_beneficiadas", nullable = false)
	private Integer personasBeneficiadas;
	@Column(name = "confirmada_donante", nullable = false)
	private Boolean confirmadaDonante;
	@Column(name = "confirmada_receptor", nullable = false)
	private Boolean confirmadaReceptor;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 25)
	private EstadoEntrega estado;
	@Column(length = 300)
	private String observacion;

	@PrePersist
	void pre() {
		if (confirmadaDonante == null)
			confirmadaDonante = false;
		if (confirmadaReceptor == null)
			confirmadaReceptor = false;
		if (estado == null)
			estado = EstadoEntrega.PENDIENTE;
	}
}
