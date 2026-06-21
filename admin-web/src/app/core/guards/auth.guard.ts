import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
export const authGuard: CanActivateFn = () => inject(AuthService).user()?true:inject(Router).parseUrl('/login');
export const adminGuard: CanActivateFn = () => inject(AuthService).isAdmin()?true:inject(Router).parseUrl('/login');
