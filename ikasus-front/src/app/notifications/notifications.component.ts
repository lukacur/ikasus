import { Component, OnDestroy, OnInit } from '@angular/core';
import { Notification, NotificationService } from '../notification.service';
import { Subscription } from 'rxjs';
import { RentalsService } from '../rentals.service';
import { Rental, Vehicle } from '../vehicles/vehicle.models';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { VehiclesService } from '../vehicles/vehicles.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent implements OnInit, OnDestroy {
  notifArray: Notification[] = [];
  rentals: Rental[] = [];
  sub: Subscription = new Subscription();
  modalActive: boolean = false;

  vehicles: Vehicle[] = [];

  form: FormGroup | undefined;

  constructor(private serv: NotificationService, private rentServ: RentalsService, private vser: VehiclesService) { }

  ngOnInit(): void {
    this.getData()
  }

  getVehicle(id: number) {
    return this.vehicles.filter(v => v.id == id)[0];
  }

  getData() {
    this.sub.add(
      this.vser.getAllVehicles().subscribe(vs => {
        this.vehicles = vs;
      }
      )
    );

    this.sub.add(
      this.rentServ.getAllRentals().subscribe(rens => {
        this.rentals = rens;

        this.sub.add(
          this.serv.getAllNotifications().subscribe(ntfs => {
            this.notifArray = ntfs;
          })
        )
      })
    )
  }

  initForm() {
    this.form = new FormGroup({
      "rentalId": new FormControl(Validators.required),
      "content": new FormControl("", [Validators.required, Validators.maxLength(128)]),
      "time": new FormControl(null),
    })
  }

  ngOnDestroy(): void {
    if (this.sub)
      this.sub.unsubscribe();
  }

  closeModal() {
    this.modalActive = false;
    this.form = undefined;
  }

  openModal() {
    this.modalActive = true;
    this.initForm();
  }

  create() {
    if (!this.form?.valid)
      return;

    this.serv.createNotification(this.form!.value).subscribe(_ => {
      this.getData();
      this.closeModal()
    })
  }

  delete(n: Notification) {
    this.serv.deleteNotification(n.id, n.rentalId).subscribe(_ => {
      this.getData();
    })
  }

  signal() {
    this.serv.signal().subscribe(_ => {
      alert("Signaled expiring rentals!")
    })
  }
}
