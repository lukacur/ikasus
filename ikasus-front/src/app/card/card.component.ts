import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Vehicle } from '../vehicles/vehicle.models';
import { Routes } from '../routes';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss']
})
export class CardComponent implements OnInit, OnChanges {
  @Output() clickEmitter = new EventEmitter<number>();
  @Input() vehicle: Vehicle | undefined;

  constructor() { }

  ngOnInit(): void {
  }

  emit(id: number) {
    this.clickEmitter.emit(id)
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.vehicle && this.vehicle.imagePath && !this.vehicle.imagePath.startsWith("https")
    && !this.vehicle.imagePath.startsWith("http")) {
      console.log("Here");
      console.log(this.vehicle.imagePath);
      this.vehicle.imagePath = Routes.ORIGIN + this.vehicle.imagePath.substring(1)
    }
  }

}
