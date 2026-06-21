package com.donacion.app.categoria.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categoria")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria")
	private Long idCategoria;

	@Column(name = "nombre", nullable = false, unique = true, length = 80)
	private String nombre;

	@Column(name = "estado", nullable = false)
	private Boolean estado;
}