import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import * as jwt_decode from "jwt-decode";
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardCustomerGuard implements CanActivate {

  constructor(private router: Router, private serv: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      const decoded: any = this.serv.decodeToken();

      if (decoded !== null && decoded == 0) {
        return true;
      }

      else {
        this.serv.logout();
        return false
      }
  }

}
