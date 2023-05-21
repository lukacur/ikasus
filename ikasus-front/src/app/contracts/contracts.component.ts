import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { VehiclesService } from '../vehicles/vehicles.service';
import { RentalRequest, RentalsService } from '../rentals.service';

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
  requests: RentalRequest[] = []

  constructor(private serv: VehiclesService, private rentServ: RentalsService) { }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.sub.add(
      this.rentServ.getAllRentalRequests().subscribe(rqs => {

        this.requests = rqs;

        this.sub.add(
          this.serv.getAllContracts().subscribe(cnts => {
            this.contracts = cnts.sort((a, b) => (a.id > b.id) ? 1 : -1);
          })
        )
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
    this.contractForm = undefined;

    if (contract) {
      this.rentServ.getContract(contract.id).subscribe(cnt => {
        this.contractForm = new FormGroup({
          "id": new FormControl(contract.id, Validators.required),
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

  deleteType(id: number) {
    this.rentServ.deleteContract(id).subscribe(_ => {
      this.getData();
    })
  }

  updateType() {
    if (this.add) {
      this.rentServ.addContract(this.contractForm?.value).subscribe(_ => {
        this.getData();
        this.closeModal()
      })
    }
    else {
      const id = this.contractForm!.get("id")?.value;
      this.contractForm?.removeControl("id");
      this.rentServ.updateContract(this.contractForm?.value, id).subscribe(_ => {
        this.getData();
        this.closeModal()
      })
    }
  }

}
