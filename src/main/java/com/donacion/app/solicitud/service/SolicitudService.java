package com.donacion.app.solicitud.service;

import com.donacion.app.solicitud.dto.*;
import java.util.*;

public interface SolicitudService {
	SolicitudResponse solicitar(SolicitudRequest r);

	List<SolicitudResponse> mias();

	List<SolicitudResponse> recibidas();

	SolicitudResponse aceptar(Long id);

	SolicitudResponse rechazar(Long id, String observacion);

	SolicitudResponse cancelar(Long id);
}
