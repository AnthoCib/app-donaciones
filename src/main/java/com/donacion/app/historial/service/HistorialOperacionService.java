package com.donacion.app.historial.service;

import com.donacion.app.historial.domain.*;
import com.donacion.app.historial.dto.HistorialOperacionResponse;
import com.donacion.app.historial.repository.HistorialOperacionRepository;
import com.donacion.app.publicacion.domain.Publicacion;
import com.donacion.app.solicitud.domain.Solicitud;
import com.donacion.app.usuario.domain.Usuario;
import com.donacion.app.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HistorialOperacionService {
    private final HistorialOperacionRepository repo;
    private final UsuarioService actual;

    public void registrar(TipoOperacion tipo, Usuario actor, Usuario relacionado, Publicacion publicacion,
                          Solicitud solicitud, String descripcion) {
        repo.save(HistorialOperacion.builder()
                .tipo(tipo)
                .usuarioActor(actor)
                .usuarioRelacionado(relacionado)
                .publicacion(publicacion)
                .solicitud(solicitud)
                .descripcion(descripcion)
                .build());
    }

    @Transactional(readOnly = true)
    public List<HistorialOperacionResponse> mio() {
        Long id = actual.obtener().getIdUsuario();
        return repo.findAllByUsuarioActorIdUsuarioOrUsuarioRelacionadoIdUsuarioOrderByFechaOperacionDesc(id, id)
                .stream().map(this::map).toList();
    }

    @Transactional(readOnly = true)
    public List<HistorialOperacionResponse> listar() {
        return repo.findAll().stream().map(this::map).toList();
    }

    private HistorialOperacionResponse map(HistorialOperacion h) {
        return new HistorialOperacionResponse(h.getIdHistorial(), h.getTipo(),
                h.getUsuarioActor() == null ? null : h.getUsuarioActor().getIdUsuario(),
                h.getUsuarioRelacionado() == null ? null : h.getUsuarioRelacionado().getIdUsuario(),
                h.getPublicacion() == null ? null : h.getPublicacion().getIdPublicacion(),
                h.getSolicitud() == null ? null : h.getSolicitud().getIdSolicitud(),
                h.getDescripcion(), h.getFechaOperacion());
    }
}
