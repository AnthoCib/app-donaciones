package com.donacion.app.shared.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public abstract class ICRUDImpl<T,ID> implements ICrud<T, ID> {

	//Definir metodo Abstract
	
	public abstract JpaRepository<T, ID> repo();
	
	@Override
	public T registrar(T bean) throws Exception {
		// TODO Auto-generated method stub
		return repo().save(bean);
	}

	@Override
	public T actualizar(T bean) throws Exception {
		// TODO Auto-generated method stub
		return repo().save(bean);
	}

	@Override
	public void eliminar(ID cod) throws Exception {
		repo().deleteById(cod);
		
	}

	@Override
	public T buscarPorCodigo(ID cod) throws Exception {
		// TODO Auto-generated method stub
		return repo().findById(cod).orElse(null);
	}

	@Override
	public List<T> listar() throws Exception {
		// TODO Auto-generated method stub
		return repo().findAll();
	}

}
