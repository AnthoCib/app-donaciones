import { Injectable, computed, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { ApiResponse, AuthResponse, LoginRequest } from '../models/api.models';
import { tap } from 'rxjs';
@Injectable({providedIn:'root'})
export class AuthService{
 private key='admin_session'; private _user=signal<AuthResponse|null>(this.read()); user=computed(()=>this._user()); isAdmin=computed(()=>['ADMIN','ADMINISTRADOR'].includes(this._user()?.rol??''));
 constructor(private http:HttpClient, private router:Router){}
 login(req:LoginRequest){return this.http.post<ApiResponse<AuthResponse>>(`${environment.apiUrl}/api/auth/login`,req).pipe(tap(r=>{if(!['ADMIN','ADMINISTRADOR'].includes(r.data.rol)){throw new Error('No tienes permisos de administrador');} localStorage.setItem(this.key,JSON.stringify(r.data)); this._user.set(r.data);}));}
 token(){return this._user()?.token??''} logout(){localStorage.removeItem(this.key); this._user.set(null); this.router.navigateByUrl('/login')}
 private read(){const raw=localStorage.getItem(this.key); return raw?JSON.parse(raw) as AuthResponse:null}
}
