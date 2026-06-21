package com.donacion.app.alerta.domain;

import com.donacion.app.publicacion.domain.Publicacion;
import com.donacion.app.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alerta")
public class Alerta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_alerta")
	private Long idAlerta;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_publicacion", nullable = false)
	private Publicacion publicacion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private TipoAlerta tipo;
	@Column(nullable = false, length = 300)
	private String mensaje;
	@Column(nullable = false)
	private Boolean leida;
	@Column(name = "fecha_creacion", nullable = false)
	private LocalDateTime fechaCreacion;

	@PrePersist
	void pre() {
		if (leida == null)
			leida = false;
		if (fechaCreacion == null)
			fechaCreacion = LocalDateTime.now();
	}
}
