import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Vehicle } from '../vehicles/vehicle.models';

@Component({
  selector: 'app-card-list',
  templateUrl: './card-list.component.html',
  styleUrls: ['./card-list.component.scss']
})
export class CardListComponent implements OnInit {
  @Output() clickEmitter = new EventEmitter<number>();
  @Input() cars: Vehicle[] = [];

  constructor() { }

  ngOnInit(): void {
  }

  emitCard(id: number) {
    this.clickEmitter.emit(id)
  }

}
