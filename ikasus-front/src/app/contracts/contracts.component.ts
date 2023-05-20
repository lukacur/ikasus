import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { VehiclesService } from '../vehicles/vehicles.service';
import { RentalsService } from '../rentals.service';

@Component({
  selector: 'app-contracts',
  templateUrl: './contracts.component.html',
  styleUrls: ['./contracts.component.scss']
})
export class ContractsComponent implements OnInit {
  contracts: any[] = []
  modalActive = false;
  contractForm: FormGroup | undefined;
  add = false;
  sub = new Subscription();

  constructor(private serv: VehiclesService, private rentServ: RentalsService) { }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.sub.add(
      this.serv.getAllContracts().subscribe(tp => {
        this.contracts = tp;
      })
    )
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe()
    }
  }

  openModal(contract: any) {
    this.modalActive = true;

    if (contract) {
      this.rentServ.getContract(contract.id).subscribe(cnt => {
        this.contractForm = new FormGroup({
          "id": new FormControl(cnt.id, Validators.required),
          "title": new FormControl(cnt.title, Validators.required),
          "contractTag": new FormControl(cnt.contractTag, Validators.required),
          "content": new FormControl(cnt.content, Validators.required),
          "signed": new FormControl(cnt.signed),
          "signedOn": new FormControl(cnt.signedOn),
          "signaturePath": new FormControl(cnt.signaturePath),
          "price": new FormControl(cnt.price, Validators.required),
          "rentalRequestId": new FormControl(cnt.rentalRequestId),
        })
      })
    }

    else {
      this.add = true;
      this.contractForm = new FormGroup({
        "id": new FormControl(null, Validators.required),
        "title": new FormControl(null, Validators.required),
        "contractTag": new FormControl(null, Validators.required),
        "content": new FormControl(null, Validators.required),
        "price": new FormControl(null, Validators.required),
        "rentalRequestId": new FormControl(null)
      })
    }
  }

  closeModal() {
    this.contractForm = undefined;
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
      this.serv.addType(this.contractForm?.value).subscribe(_ => {
        this.getData();
        this.closeModal()
      })
    }
    else {
      this.serv.updateType(this.contractForm?.value).subscribe(_ => {
        this.getData();
        this.closeModal()
      })
    }
  }

}
