import { Component, OnInit } from '@angular/core';
import { Vehicle } from '../vehicles/vehicle.models';
import { VehiclesService } from '../vehicles/vehicles.service';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RentalsService } from '../rentals.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {
  modalActive: boolean = false;
  rentalForm!: FormGroup;

  vehicles: Vehicle[] = [];
  selected: Vehicle[] = [];
  vehiclesFiltered: Vehicle[] = [];
  from: string = "";
  to: string = "";

  constructor(private serv: VehiclesService, private route: ActivatedRoute, private rentServ: RentalsService) { }

  ngOnInit(): void {
    this.serv.getVehiclesByDate(this.route.snapshot.queryParams).subscribe(vhs => {
      this.vehicles = vhs;
      this.vehiclesFiltered = vhs;
      this.sort(1);
      this.from = this.route.snapshot.queryParams["from"];
      this.to = this.route.snapshot.queryParams["to"];
    })
  }

  addToRental(id: number) {
    const vehicle = this.vehiclesFiltered.filter(vh => vh.id == id)[0];
    this.selected.push(vehicle);
    this.vehiclesFiltered = this.vehiclesFiltered.filter(vh => vh.id != id);
  }

  closeModal() {
    this.modalActive = false;
  }

  filterVehicles(form: any) {
    this.vehiclesFiltered = this.vehicles;

    if (form.name) {
      this.vehiclesFiltered = this.vehiclesFiltered.filter(v => v.name.toLowerCase().includes(form.name.toLowerCase()));
    }
    if (form.kmDriven) {
      this.vehiclesFiltered = this.vehiclesFiltered.filter(v => v.kmDriven <= form.kmDriven);
    }
    if (form.pricePerDay) {
      this.vehiclesFiltered = this.vehiclesFiltered.filter(v => v.pricePerDay <= form.pricePerDay);
    }
    if (form.manufacturer) {
      this.vehiclesFiltered = this.vehiclesFiltered.filter(v => v.manufacturer.toLowerCase().includes(form.manufacturer.toLowerCase()));
    }
    if (form.vehicleTypeId) {
      this.vehiclesFiltered = this.vehiclesFiltered.filter(v => v.vehicleTypeId == form.vehicleTypeId);
    }
  }

  clearFilters() {
    this.vehiclesFiltered = this.vehicles;
  }

  openModal() {
    this.modalActive = true;
  }

  create() {
    let requests: any = [];

    this.selected.forEach(vh => {
      requests.push({
        vehicleId: vh.id,
        rentFrom: this.from,
        rentTo: this.to
      })
    })

    this.rentServ.createRequest({ requestedRentals: requests}).subscribe(_ => {
      window.location.reload()
    })
  }

  sort(type: number) {
    if (type == 1) {
      this.vehiclesFiltered = this.vehiclesFiltered.sort((a, b) => (a.pricePerDay > b.pricePerDay) ? 1 : -1)
    }
    else {
      this.vehiclesFiltered = this.vehiclesFiltered.sort((a, b) => (a.pricePerDay < b.pricePerDay) ? 1 : -1)
    }
  }
}
