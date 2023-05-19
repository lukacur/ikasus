import { Component, OnInit } from '@angular/core';
import { VehiclesService } from '../vehicles.service';
import { ActivatedRoute, Router } from '@angular/router';
import { VehicleDetails, VehicleType, Location, Rental } from '../vehicle.models';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.scss']
})
export class VehicleDetailsComponent implements OnInit {
  types: VehicleType[] = [];
  locations: Location[] = [];
  rentals: Rental[] = [];
  vehicleForm!: FormGroup;
  rentalForm: FormGroup | undefined;
  modalActive: boolean = false;

  constructor(private serv: VehiclesService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.serv.getLocations().subscribe(locs => {
      this.locations = locs;
      this.serv.getTypes().subscribe(types => {
        this.types = types;
        this.serv.getVehicle(this.route.snapshot.params["id"]).subscribe(vh => {
          this.rentals = vh.rentals;
          this.initForm(vh);
        })
      })
    })
  }

  openModal(rental: Rental) {
    this.rentalForm = new FormGroup({
      "id": new FormControl(rental.id),
      "timeFrom": new FormControl(this.timestampToDatetimeInputString(rental.timeFrom)),
      "timeTo": new FormControl(this.timestampToDatetimeInputString(rental.timeTo)),
      "kmDriven": new FormControl(rental.kmDriven),
      "active": new FormControl(rental.active)
    });
    // "contractId": new FormControl(rental.contractId, Validators.required),
    this.modalActive = true;
  }

  timestampToDatetimeInputString(timestamp: string) {
    if (!timestamp) return null;
    const date = new Date((timestamp));
    return date.toISOString().slice(0, 19);
  }

  updateRental() {
    if (this.rentalForm && this.rentalForm.valid) {
      const id = this.rentalForm.get("id")!.value;
      this.rentalForm.removeControl("id");

      this.serv.updateRental(this.rentalForm.value, id).subscribe(_ => {
        this.closeModal();
      })
    }
  }

  closeModal() {
    this.modalActive = false;
    this.rentalForm = undefined;
  }

  initForm(vehicle: VehicleDetails) {
    this.vehicleForm = new FormGroup({
      "id": new FormControl(vehicle.id),
      "registration": new FormControl(vehicle.registration, Validators.required),
      "name": new FormControl(vehicle.name, Validators.required),
      "manufacturer": new FormControl(vehicle.manufacturer, Validators.required),
      "kmDriven": new FormControl(vehicle.kmDriven, Validators.required),
      "pricePerDay": new FormControl(vehicle.pricePerDay, Validators.required),
      "imageUrl": new FormControl(vehicle.imagePath, Validators.required),
      "vehicleTypeId": new FormControl(vehicle.vehicleTypeId, Validators.required),
      "locationId": new FormControl(vehicle.locationId, Validators.required)
    })
  }

  patchVehicle() {
    console.log(this.vehicleForm.value)
    this.serv.updateVehicle(this.vehicleForm.value as VehicleDetails).subscribe(res => {
      window.location.reload()
    })
  }

  deleteVehicle() {
    this.serv.deleteVehicle(this.vehicleForm.get("id")!.value).subscribe(_ => {
      this.router.navigate(["../"], { relativeTo: this.route })
    })
  }
}
