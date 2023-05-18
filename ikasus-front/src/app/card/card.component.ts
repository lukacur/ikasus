import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss']
})
export class CardComponent implements OnInit {
  @Output() clickEmitter = new EventEmitter<void>();

  constructor() { }

  ngOnInit(): void {
  }

  emit() {
    this.clickEmitter.emit()
  }

}
