import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Customer, Employee, Manager } from './models/user.model';
import { HttpClient } from '@angular/common/http';
import { Routes } from './routes';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user = new BehaviorSubject<Customer | Employee | Manager | undefined>(undefined);

  constructor(private http: HttpClient, private router: Router) { }

  login(username: string, password: string, type: number): Observable<any> {
    let url = "";

    if (type == 1)
      url = Routes.loginUser
    else if (type == 2)
      url = Routes.loginEmployee
    else
      url = Routes.loginManager

    return this.http.post(Routes.ORIGIN + url,
      { principal: username, credentials: password
    }, {
      headers: { "Access-Control-Allow-Origin": "*" }
    })
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
    const userData: Customer = JSON.parse(localStorage.getItem("User")!);
    if (!userData) return;
    const storedUser = userData;
    this.user.next(storedUser);
  }


  private authenticate(user: any) {
    console.log(user);
    this.user.next(user);
    localStorage.setItem("User", JSON.stringify(user));
  }
}
