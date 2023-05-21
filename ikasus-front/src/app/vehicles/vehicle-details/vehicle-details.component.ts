import { Component, OnDestroy, OnInit } from '@angular/core';
import { VehiclesService } from '../vehicles.service';
import { ActivatedRoute, Router } from '@angular/router';
import { VehicleDetails, VehicleType, Location, Rental, Contract, Vehicle } from '../vehicle.models';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription, tap } from 'rxjs';

@Component({
  selector: 'app-vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.scss']
})
export class VehicleDetailsComponent implements OnInit, OnDestroy {
  types: VehicleType[] = [];
  locations: Location[] = [];
  rentals: Rental[] = [];
  contracts: Contract[] = [];
  vehicles: Vehicle[] = [];
  vehicleForm!: FormGroup;
  rentalForm: FormGroup | undefined;
  modalActive: boolean = false;
  subs!: Subscription;
  imgIsFile: boolean = true;
  file: string | undefined;

  create: boolean = false;

  constructor(private serv: VehiclesService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.subs = new Subscription();
    this.getData();
    this.serv.getAllVehicles().subscribe(vs => {
      this.vehicles = vs.sort((a, b) => (a.id > b.id) ? 1 : -1);
    })
  }

  ngOnDestroy(): void {
    if (this.subs) {
      this.subs.unsubscribe()
    }
  }

  prev() {
    const vehicle = this.vehicles.filter(v => v.id == this.route.snapshot.params["id"])[0]
    const id = this.vehicles.indexOf(vehicle);

    if (id == 0) return;

    this.router.navigate(["../", this.vehicles[id - 1].id], { relativeTo: this.route}).then(() => {
      window.location.reload()
    })
  }

  next() {
    const vehicle = this.vehicles.filter(v => v.id == this.route.snapshot.params["id"])[0]
    const id = this.vehicles.indexOf(vehicle)

    if (id + 1 == this.vehicles.length) return;

    this.router.navigate(["../", this.vehicles[id + 1].id], { relativeTo: this.route}).then(() => {
      window.location.reload()
    })
  }

  getData() {
    this.subs.add(this.serv.getAllContracts().subscribe(cons => {
      this.contracts = cons;

      this.subs.add(this.serv.getLocations().subscribe(locs => {
        this.locations = locs;

        this.subs?.add(
          this.serv.getTypes().subscribe(types => {
            this.types = types;

            this.subs?.add(
              this.serv.getVehicle(this.route.snapshot.params["id"]).subscribe(vh => {
                this.rentals = vh.rentals;
                this.initForm(vh);
              }))
          }))
      }))
    }))
  }

  openModal(rental: Rental) {
    this.rentalForm = new FormGroup({
      "id": new FormControl(rental.id),
      "timeFrom": new FormControl(this.timestampToDate(rental.timeFrom)),
      "timeTo": new FormControl(this.timestampToDate(rental.timeTo)),
      "kmDriven": new FormControl(rental.kmDriven),
      "active": new FormControl(rental.active),
      "contractId": new FormControl(rental.contractId),
    });
    this.modalActive = true;
  }

  timestampToDate(timestamp: string) {
    if (!timestamp) return null;
    const date = new Date(timestamp);
    return date.toISOString().slice(0, 10);
  }

  updateRental() {
    if (this.rentalForm && !this.rentalForm.valid) return;

    if (this.rentalForm && this.rentalForm.value) {

      if (this.create) {
        this.serv.postRental(this.rentalForm.value).subscribe(_ => {
          this.getData();
          this.closeModal();
        })
      }

      else {
        const id = this.rentalForm.get("id")!.value;
        this.rentalForm.removeControl("id");

        this.serv.updateRental(this.rentalForm.value, id).subscribe(_ => {
          this.getData();
          this.closeModal();
        })
      }
    }
  }

  deleteRental(id: number) {
    this.serv.deleteRental(id).subscribe(_ => {
      this.getData();
    });
  }

  closeModal() {
    this.modalActive = false;
    this.create = false;
    this.rentalForm = undefined;
  }

  initForm(vehicle: VehicleDetails) {
    this.imgIsFile = vehicle.imagePath != null && vehicle.imagePath.startsWith("base64");

    this.vehicleForm = new FormGroup({
      "id": new FormControl(vehicle.id),
      "registration": new FormControl(vehicle.registration),
      "name": new FormControl(vehicle.name),
      "manufacturer": new FormControl(vehicle.manufacturer),
      "kmDriven": new FormControl(vehicle.kmDriven),
      "pricePerDay": new FormControl(vehicle.pricePerDay),
      "imageUrl": new FormControl(vehicle.imagePath),
      "vehicleTypeId": new FormControl(vehicle.vehicleTypeId),
      "locationId": new FormControl(vehicle.locationId)
    })
  }

  patchVehicle() {
    if (this.file) {
      this.vehicleForm.addControl("imageBase64Encoded", new FormControl(this.file));
      this.vehicleForm.setControl("imageUrl", null);
    }

    this.serv.updateVehicle(this.vehicleForm.value as VehicleDetails).subscribe(_ => {
      this.getData()
    })
  }

  async onFileChange(event: any) {
    if (event.target!.files.length > 0) {
      const file = event.target.files[0];
      this.file = (await this.blobToBase64(file)) as string;
      this.file = this.file.slice(this.file.indexOf(",") + 1)
    }
  }

  blobToBase64(blob: Blob) {
    return new Promise((resolve, _) => {
      const reader = new FileReader();
      reader.onloadend = () => resolve(reader.result);
      reader.readAsDataURL(blob);
    });
  }

  deleteVehicle() {
    this.serv.deleteVehicle(this.vehicleForm.get("id")!.value).subscribe(_ => {
      this.router.navigate(["../"], { relativeTo: this.route })
    })
  }

  add() {
    this.create = true;

    this.rentalForm = new FormGroup({
      "vehicleId": new FormControl(this.vehicleForm.get("id")?.value),
      "timeFrom": new FormControl(null, Validators.required),
      "timeTo": new FormControl(null, Validators.required),
      "kmDriven": new FormControl(),
      "active": new FormControl(),
      "contractId": new FormControl(1, Validators.required)
    });

    this.modalActive = true;
  }
}
