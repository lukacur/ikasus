import { Component, OnDestroy, OnInit } from '@angular/core';
import { RentalRequest, RentalsService } from '../rentals.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { VehiclesService } from '../vehicles/vehicles.service';

@Component({
  selector: 'app-requests-and-contracts',
  templateUrl: './requests-and-contracts.component.html',
  styleUrls: ['./requests-and-contracts.component.scss']
})
export class RequestsAndContractsComponent implements OnInit, OnDestroy {
  requests: RentalRequest[] = [];
  request: RentalRequest | undefined;
  modalActive = false;
  contractForm: FormGroup | undefined;
  sub: Subscription = new Subscription();
  contracts: any[] = [];

  constructor(private serv: RentalsService, private vehServ: VehiclesService) { }

  ngOnInit(): void {
    this.getData()
  }

  getData() {
    this.contracts = [];

    this.sub.add(
      this.serv.getAllRentalRequests().subscribe(rqs => {

        rqs.forEach(rq => {
          rq.timeCreated =  rq.timeCreated.substring(0, 10)
        })

        this.requests = rqs.filter(rq => rq.status == "NA CEKANJU");

        this.sub.add(
          this.vehServ.getAllContracts().subscribe(cnts => {
            cnts = cnts.sort((a, b) => (a.id > b.id) ? 1 : -1);

            this.sub.add(
              this.serv.getAllRentals().subscribe(rns => {
                cnts.forEach(cnt => {
                  let cntRentals = rns.filter(r => r.contractId == cnt.id);
                  if (cntRentals.some(r => r.active == true) && cntRentals.every(r => r.timeTo < (new Date()).toISOString()))
                    this.contracts.push(cnt);
                })
              })
            )
          })
        )
      })
    )
  }

  trimDate(date: string) {
    return date.substring(0, 10)
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe()
    }
  }

  dateDiffInDays(a: Date, b: Date) {
    const _MS_PER_DAY = 1000 * 60 * 60 * 24;
    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
    const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());

    return Math.floor((utc2 - utc1) / _MS_PER_DAY);
  }

  openModal(request: RentalRequest) {
    this.modalActive = true;
    this.request = request;

    this.sub.add(
      this.vehServ.getAllVehicles().subscribe(vhs => {
        let price = 0.0;

        if (request && request.vehicleDetails && request.vehicleDetails.length > 0) {
          const ids: number[] = []
          request.vehicleDetails.forEach(v => ids.push(v.vehicleId));

          let diff = this.dateDiffInDays(new Date(request.vehicleDetails[0].rentFrom), new Date(request.vehicleDetails[0].rentTo));

          vhs.forEach(v => {
            if (ids.indexOf(v.id) != -1) {
              price = price + diff * v.pricePerDay;
            }
          })
        }

        this.contractForm = new FormGroup({
          "title": new FormControl(null, Validators.required),
          "contractTag": new FormControl(null, Validators.required),
          "content": new FormControl(null, Validators.required),
          "price": new FormControl(price, Validators.required),
          "signed": new FormControl(false),
          "rentalRequestId": new FormControl(request.id)
        })
      })
    )


  }

  closeModal() {
    this.request = undefined;
    this.contractForm = undefined;
    this.modalActive = false;
  }

  unapproveRequest() {
    if (this.request && this.request.id) {
      this.serv.deleteRequest(this.request.id).subscribe(_ => {
        this.closeModal();
        this.getData();
      })
    }
  }

  issueContract() {
    if (!this.contractForm?.valid) return;

    this.contractForm?.removeControl("rentalRequestId");

    let contract = {
      customerId: this.request?.customerRequestedId,
      rentalRequestId: this.request?.id,
      dateOfIssue: null,
      contractInfo: this.contractForm?.value
    }

    this.serv.issueContract(contract).subscribe(_ => {
      this.getData();
      this.closeModal()
    })
  }

  complete(id: number) {
    this.serv.completeContract(id).subscribe(_ => {
      this.getData();
    })
  }
}
