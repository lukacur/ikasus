import { Injectable } from "@angular/core";
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Routes } from "../routes";
import { Vehicle, VehicleDetails, VehicleType, Location, Rental, Contract } from "./vehicle.models";

@Injectable({
  providedIn: 'root'
})
export class VehiclesService {

  constructor(private http: HttpClient, private router: Router) { }

  getAllVehicles() {
    return this.http.get<Vehicle[]>(Routes.authorizedORIGIN + Routes.vehicles);
  }

  getAllContracts() {
    return this.http.get<Contract[]>(Routes.authorizedORIGIN + Routes.contracts);
  }

  getVehicle(id: number) {
    return this.http.get<VehicleDetails>(Routes.authorizedORIGIN + Routes.vehicles + "/" + id.toString());
  }

  deleteVehicle(id: number) {
    return this.http.delete(Routes.authorizedORIGIN + Routes.vehicles, {
      body: {
        id: id
      }
    });
  }

  deleteRental(id: number) {
    return this.http.delete(Routes.authorizedORIGIN + Routes.rentals, {
      body: {
        id: id
      }
    });
  }

  updateVehicle(vehicle: VehicleDetails) {
    return this.http.patch(Routes.authorizedORIGIN + Routes.vehicles + "/" + vehicle.id.toString(), vehicle);
  }

  updateRental(rental: Rental, id: number) {
    console.log(rental);
    console.log(id);

    return this.http.patch(Routes.authorizedORIGIN + Routes.rentals + "/" + id.toString(), rental);
  }

  getTypes() {
    return this.http.get<VehicleType[]>(Routes.authorizedORIGIN + Routes.types);
  }

  getLocations() {
    return this.http.get<Location[]>(Routes.authorizedORIGIN + Routes.locations);
  }
}
