import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ApiResponse, CategoriaRequest, CategoriaResponse, DashboardResumenResponse, HistorialOperacionResponse, PublicacionResponse, ReporteResponse, UsuarioResponse } from '../models/api.models';
@Injectable({providedIn:'root'})
export class AdminApiService{private base=environment.apiUrl; constructor(private http:HttpClient){}
 dashboard(){return this.http.get<ApiResponse<DashboardResumenResponse>>(`${this.base}/api/admin/dashboard/resumen`)}
 usuarios(){return this.http.get<ApiResponse<UsuarioResponse[]>>(`${this.base}/api/admin/usuarios`)} usuarioPorRol(rol:string){return this.http.get<ApiResponse<UsuarioResponse[]>>(`${this.base}/api/admin/usuarios/rol/${rol}`)} cambiarEstadoUsuario(id:number,estado:string){return this.http.patch<ApiResponse<UsuarioResponse>>(`${this.base}/api/admin/usuarios/${id}/estado`,{estado})}
 publicaciones(){return this.http.get<ApiResponse<PublicacionResponse[]>>(`${this.base}/api/admin/publicaciones`)} aprobarPublicacion(id:number){return this.http.patch<ApiResponse<PublicacionResponse>>(`${this.base}/api/admin/publicaciones/${id}/aprobar`,{})} bloquearPublicacion(id:number,motivo='Bloqueado desde admin web'){return this.http.patch<ApiResponse<PublicacionResponse>>(`${this.base}/api/admin/publicaciones/${id}/bloquear?motivo=${encodeURIComponent(motivo)}`,{})}
 categorias(){return this.http.get<ApiResponse<CategoriaResponse[]>>(`${this.base}/api/categorias`)} crearCategoria(req:CategoriaRequest){return this.http.post<ApiResponse<CategoriaResponse>>(`${this.base}/api/categorias`,req)} actualizarCategoria(id:number,req:CategoriaRequest){return this.http.put<ApiResponse<CategoriaResponse>>(`${this.base}/api/categorias/${id}`,req)} estadoCategoria(id:number,estado:boolean){return this.http.patch<ApiResponse<CategoriaResponse>>(`${this.base}/api/categorias/${id}/estado?estado=${estado}`,{})}
 reportes(){return this.http.get<ApiResponse<ReporteResponse[]>>(`${this.base}/api/admin/reportes`)} historial(){return this.http.get<ApiResponse<HistorialOperacionResponse[]>>(`${this.base}/api/admin/historial/operaciones`)}
}
