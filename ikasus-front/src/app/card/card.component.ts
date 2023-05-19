import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Vehicle } from '../vehicles/vehicle.models';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss']
})
export class CardComponent implements OnInit {
  @Output() clickEmitter = new EventEmitter<number>();
  @Input() vehicle: Vehicle | undefined;
  type!: string;

  constructor() { }

  ngOnInit(): void {
  }

  emit(id: number) {
    this.clickEmitter.emit(id)
  }

}
