package com.donacion.app.usuario.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.donacion.app.usuario.domain.RolUsuario;
import com.donacion.app.usuario.domain.Usuario;
import com.donacion.app.usuario.dto.CambiarEstadoRequest;
import com.donacion.app.usuario.dto.UsuarioRequest;
import com.donacion.app.usuario.dto.UsuarioResponse;
import com.donacion.app.usuario.mapper.UsuarioMapper;
import com.donacion.app.usuario.service.UsuarioActualServiceImpl;
import com.donacion.app.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioActualServiceImpl usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioResponse>> registrar(
            @Valid @RequestBody UsuarioRequest request
    ) throws Exception {

        Usuario usuario =
                usuarioMapper.toEntity(request);

        Usuario registrado =
                usuarioService.registrar(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.ok(
                                "Usuario registrado correctamente",
                                usuarioMapper.toResponse(registrado)
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request
    ) throws Exception {

        Usuario usuario =
                usuarioMapper.toEntity(request);

        usuario.setIdUsuario(id);

        Usuario actualizado =
                usuarioService.actualizar(usuario);

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Usuario actualizado correctamente",
                        usuarioMapper.toResponse(actualizado)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> buscarPorId(
            @PathVariable Long id
    ) throws Exception {

        Usuario usuario =
                usuarioService.buscarPorCodigo(id);

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Usuario encontrado",
                        usuarioMapper.toResponse(usuario)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioResponse>>> listar()
            throws Exception {

        List<UsuarioResponse> usuarios =
                usuarioService.listar()
                        .stream()
                        .map(usuarioMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Lista de usuarios",
                        usuarios
                )
        );
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<ApiResponse<List<UsuarioResponse>>>
    listarPorRol(
            @PathVariable RolUsuario rol
    ) throws Exception {

        List<UsuarioResponse> usuarios =
                usuarioService.listarPorRol(rol)
                        .stream()
                        .map(usuarioMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Usuarios encontrados",
                        usuarios
                )
        );
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<UsuarioResponse>> cambiarEstado(
            @PathVariable Long id,
            @Valid @RequestBody CambiarEstadoRequest request
    ) throws Exception {

        Usuario usuario =
                usuarioService.cambiarEstado(
                        id,
                        request.estado()
                );

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Estado del usuario actualizado",
                        usuarioMapper.toResponse(usuario)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @PathVariable Long id
    ) throws Exception {

        usuarioService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.exito(
                        "Usuario desactivado correctamente"
                )
        );
    }
}
