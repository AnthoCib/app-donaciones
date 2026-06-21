package com.donacion.app.shared.service;

import java.util.List;

public interface CrudService<REQ, RES, ID> {
	RES registrar(REQ request);

	RES actualizar(ID id, REQ request);

	RES buscarPorId(ID id);

	List<RES> listar();

	void eliminar(ID id);
}
