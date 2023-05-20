import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Routes } from './routes';

@Injectable({
  providedIn: 'root'
})
export class RentalsService {

  constructor(private http: HttpClient, private router: Router) { }

  createRequest(request: any) {
    return this.http.post(Routes.authorizedORIGIN + Routes.rentalRequests, request);
  }

  getContract(id: number) {
    return this.http.get<any>(Routes.authorizedORIGIN + Routes.contracts + "/" + id.toString());
  }
}
