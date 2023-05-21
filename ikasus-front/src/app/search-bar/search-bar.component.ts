import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { VehicleType } from '../vehicles/vehicle.models';
import { VehiclesService } from '../vehicles/vehicles.service';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss']
})
export class SearchBarComponent implements OnInit {
  types: VehicleType[] = [];
  form!: FormGroup;
  @Output() emitter: EventEmitter<any> = new EventEmitter();
  @Output() clear: EventEmitter<void> = new EventEmitter();
  @Output() sort: EventEmitter<number> = new EventEmitter();

  constructor(private serv: VehiclesService) { }

  ngOnInit(): void {
    this.serv.getAllTypes().subscribe(tps => {
      this.types = tps;
      this.initForm()
    })
  }

  initForm() {
    this.form = new FormGroup({
      "name": new FormControl(null),
      "manufacturer": new FormControl(null),
      "kmDriven": new FormControl(null),
      "pricePerDay": new FormControl(null),
      "vehicleTypeId": new FormControl(null)
    })
  }

  search() {
    this.emitter.emit(this.form.value)
  }

  clearInputs() {
    this.initForm();
    this.clear.emit();
  }

  emitSort(e: any) {
    this.sort.emit(e.target.value);
  }
}
