import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit {
  @Input() isActive: boolean = false;
  @Input() title: string = "";
  @Input() actionButtonTitle: string = "";
  @Input() action2ButtonTitle: string = "";
  @Output() action = new EventEmitter<void>();
  @Output() action2 = new EventEmitter<void>();
  @Output() close = new EventEmitter<void>();

  constructor() { }

  ngOnInit(): void {
  }

  closeModal() {
    this.close.emit()
  }

  emitAction() {
    this.action.emit()
  }

  emitAction2() {
    this.action2.emit()
  }

}
