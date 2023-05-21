import { Component, OnDestroy, OnInit } from '@angular/core';
import { VehiclesService } from '../vehicles/vehicles.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-vehicle-types',
  templateUrl: './vehicle-types.component.html',
  styleUrls: ['./vehicle-types.component.scss']
})
export class VehicleTypesComponent implements OnInit, OnDestroy {
  types: any[] = []
  modalActive = false;
  typeForm: FormGroup | undefined;
  add = false;
  sub = new Subscription();

  constructor(private serv: VehiclesService) { }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.sub.add(
      this.serv.getAllTypes().subscribe(tp => {
        this.types = tp;
      })
    )
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe()
    }
  }

  openModal(type: any) {
    this.modalActive = true;

    if (type) {
      this.typeForm = new FormGroup({
        "id": new FormControl(type.id, Validators.required),
        "typeName": new FormControl(type.typeName, Validators.required),
        "category": new FormControl(type.category, Validators.required)
      })
    }

    else {
      this.add = true;
      this.typeForm = new FormGroup({
        "id": new FormControl("", Validators.required),
        "typeName": new FormControl("", Validators.required),
        "category": new FormControl("", Validators.required)
      })
    }

  }

  closeModal() {
    this.typeForm = undefined;
    this.add = false;
    this.modalActive = false;
  }

  deleteType(id: string) {
    this.serv.deleteType(id).subscribe(_ => {
      this.getData();
    })
  }

  updateType() {
    if (this.add) {
      this.serv.addType(this.typeForm?.value).subscribe(_ => {
        this.getData();
        this.closeModal()
      })
    }
    else {
      this.serv.updateType(this.typeForm?.value).subscribe(_ => {
        this.getData();
        this.closeModal()
      })
    }
  }
}
