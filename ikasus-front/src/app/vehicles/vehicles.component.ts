import { Component, OnInit } from '@angular/core';
import { VehiclesService } from './vehicles.service';
import { Vehicle } from './vehicle.models';
import { ActivatedRoute, Router } from '@angular/router';
import { Routes } from '../routes';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.scss']
})
export class VehiclesComponent implements OnInit {
  public vehicles!: Vehicle[];
  origin = Routes.ORIGIN;

  constructor(private serv: VehiclesService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.serv.getAllVehicles().subscribe(vehicles => {
      this.vehicles = vehicles
      this.vehicles.forEach(v => {
        if (v.imagePath && !v.imagePath.startsWith("https")) {
          v.imagePath = this.origin + v.imagePath.substring(1);
        }
      })
    })
  }

  navigateTo(id: number) {
    this.router.navigate([id.toString()], { relativeTo: this.route})
  }
}
