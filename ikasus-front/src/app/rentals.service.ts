import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Routes } from './routes';
import { Rental } from './vehicles/vehicle.models';

@Injectable({
  providedIn: 'root'
})
export class RentalsService {

  constructor(private http: HttpClient, private router: Router) { }

  createRequest(request: any) {
    return this.http.post(Routes.authorizedORIGIN + Routes.rentalRequests, request);
  }

  getAllRentalRequests() {
    return this.http.get<RentalRequest[]>(Routes.authorizedORIGIN + Routes.rentalRequests)
  }

  getContract(id: number) {
    return this.http.get<any>(Routes.authorizedORIGIN + Routes.contracts + "/" + id.toString());
  }

  deleteContract(id: number) {
    return this.http.delete(Routes.authorizedORIGIN + Routes.contracts, {
      body: {
        id: id
      }
    });
  }

  getAllRentals() {
    return this.http.get<Rental[]>(Routes.authorizedORIGIN + Routes.rentals)
  }

  issueContract(contract: any) {
    return this.http.post(Routes.authorizedORIGIN + Routes.issue, contract);
  }

  signContract(signature: any) {
    return this.http.post(Routes.authorizedORIGIN + Routes.sign, signature);
  }

  completeContract(id: number) {
    return this.http.post(Routes.authorizedORIGIN + Routes.completeContract + "/" + id.toString(), null);
  }

  addContract(contract: any) {
    return this.http.post(Routes.authorizedORIGIN + Routes.contracts, contract);
  }

  updateContract(contract: any, id: number) {
    return this.http.patch(Routes.authorizedORIGIN + Routes.contracts + "/" + id.toString(), contract);
  }

  deleteRequest(id: number) {
    return this.http.delete(Routes.authorizedORIGIN + Routes.rentalRequests, {
      body: {
        id: id
      }
    });
  }
}

export interface RentalRequest {
  id: number;
  vehicleDetails: Array<any>;
  status: string;
  timeCreated: string;
  processed: boolean;
  timeProcessed: string;
  employeeProcessedId: number;
  customerRequestedId: number;
  customerFullName: string;
}
