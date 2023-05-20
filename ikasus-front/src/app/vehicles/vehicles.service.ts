import { Injectable } from "@angular/core";
import { Router } from '@angular/router';
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

  getVehiclesByDate(params: any) {
    return this.http.post<Vehicle[]>(Routes.authorizedORIGIN + Routes.availableVehicles, {
      from: params.from,
      to: params.to,
      maxPricePerDay: params.price ? params.price : null,
    });
  }

  getAllContracts() {
    return this.http.get<Contract[]>(Routes.authorizedORIGIN + Routes.contracts);
  }

  getAllTypes() {
    return this.http.get<any[]>(Routes.authorizedORIGIN + Routes.types);
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

  updateType(type: any) {
    return this.http.patch(Routes.authorizedORIGIN + Routes.types + "/" + type.id, type);
  }

  addType(type: any) {
    return this.http.post(Routes.authorizedORIGIN + Routes.types, type);
  }

  deleteType(id: string) {
    return this.http.delete(Routes.authorizedORIGIN + Routes.types, {
      body: {
        id: id
      }
    });
  }

  updateVehicle(vehicle: VehicleDetails) {
    return this.http.patch(Routes.authorizedORIGIN + Routes.vehicles + "/" + vehicle.id.toString(), vehicle);
  }

  postRental(rental: Rental) {
    return this.http.post(Routes.authorizedORIGIN + Routes.rentals, rental)
  }

  updateRental(rental: Rental, id: number) {
    return this.http.patch(Routes.authorizedORIGIN + Routes.rentals + "/" + id.toString(), rental);
  }

  getTypes() {
    return this.http.get<VehicleType[]>(Routes.authorizedORIGIN + Routes.types);
  }

  getLocations() {
    return this.http.get<Location[]>(Routes.authorizedORIGIN + Routes.locations);
  }
}
