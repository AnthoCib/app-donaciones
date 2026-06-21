package com.donacion.app.reporte.service;

import com.donacion.app.reporte.dto.*;
import com.donacion.app.reporte.domain.EstadoReporte;
import java.util.*;

public interface ReporteService {
	ReporteResponse registrar(ReporteRequest r);

	List<ReporteResponse> listar();

	ReporteResponse atender(Long id, EstadoReporte e, String respuesta);
}
