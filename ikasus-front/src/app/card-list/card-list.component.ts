import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-card-list',
  templateUrl: './card-list.component.html',
  styleUrls: ['./card-list.component.scss']
})
export class CardListComponent implements OnInit {
  @Output() clickEmitter = new EventEmitter<void>();
  cars = [0, 1, 2, 3, 4]

  constructor() { }

  ngOnInit(): void {
  }

  emitCard() {
    this.clickEmitter.emit()
  }

}
