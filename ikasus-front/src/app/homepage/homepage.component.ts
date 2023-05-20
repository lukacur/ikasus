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
}
