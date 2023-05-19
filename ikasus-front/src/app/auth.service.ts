import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Routes } from './routes';
import * as jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user = new BehaviorSubject<{ token: string } | undefined>(undefined);

  constructor(private http: HttpClient, private router: Router) { }

  decodeToken() {
    if (!this.user.value) return undefined;
    else {
      const decoded: any = jwt_decode.default(this.user.value!.token);
      if (decoded.userAuthority == 'MANAGER') return 2;
      else if (decoded.userAuthority == 'EMPLOYEE') return 1;
      else if (decoded.userAuthority == 'CUSTOMER') return 0;
    }

    return undefined;
  }

  login(username: string, password: string, type: number): Observable<any> {
    let url = "";

    if (type == 1)
      url = Routes.loginCustomer
    else if (type == 2)
      url = Routes.loginEmployee
    else
      url = Routes.loginManager

    return this.http.post(Routes.ORIGIN + url,
      { principal: username, credentials: password})
    .pipe(tap(data => {
      this.authenticate(data);
    }))
  }

  logout() {
    this.user.next(undefined);
    localStorage.clear();
    this.router.navigate(["/login"]);
  }

  autoLogin() {
    const userData = JSON.parse(localStorage.getItem("User")!);
    if (!userData) return;
    this.user.next(userData);
  }


  private authenticate(user: any) {

    this.user.next({ token: user.token });
    localStorage.setItem("User", JSON.stringify(user));
  }
}
