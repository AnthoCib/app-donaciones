package com.donacion.app.shared.service;

import java.util.List;

public interface ICrud<T, ID> {

    T registrar(T bean) throws Exception;

    T actualizar(T bean) throws Exception;

    void eliminar(ID cod)throws Exception;

    T buscarPorCodigo(ID cod)throws Exception;

    List<T> listar()throws Exception;
}