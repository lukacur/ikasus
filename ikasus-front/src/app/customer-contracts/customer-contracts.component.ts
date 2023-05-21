import { Component, OnInit } from '@angular/core';
import { RentalsService } from '../rentals.service';
import { VehiclesService } from '../vehicles/vehicles.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-customer-contracts',
  templateUrl: './customer-contracts.component.html',
  styleUrls: ['./customer-contracts.component.scss']
})
export class CustomerContractsComponent implements OnInit {
  contracts: any[] = [];
  modalActive = false;
  form: FormGroup | undefined;

  constructor(private serv: RentalsService, private vehServ: VehiclesService) { }

  ngOnInit(): void {
    this.vehServ.getAllContracts().subscribe(cns => {
      cns.forEach(c => {
        this.serv.getContract(c.id).subscribe(cn => {
          Object.assign(cn, { id: c.id })
          this.contracts.push(cn)
        })
      })
    })
  }

  openModal(id: number) {
    this.modalActive = true;

    this.form = new FormGroup({
      "contractId" : new FormControl(id, Validators.required),
      "signatureBase64Encoded" : new FormControl("", Validators.required),
      "timeSigned" : new FormControl(null),
    })
  }

  closeModal() {
    this.modalActive = false
    this.form = undefined;
  }

  sign() {
    console.log(this.form?.value);

    if (this.form && this.form.valid) {
      this.serv.signContract(this.form.value).subscribe(_ => {
        window.location.reload();
      })
    }
  }

  async onFileChange(event: any) {
    if (event.target!.files.length > 0) {
      const file = event.target.files[0];
      let str = (await this.blobToBase64(file)) as string;
      str = str.slice(str.indexOf(",") + 1);

      this.form?.setControl("signatureBase64Encoded", new FormControl(str));
    }
  }

  blobToBase64(blob: Blob) {
    return new Promise((resolve, _) => {
      const reader = new FileReader();
      reader.onloadend = () => resolve(reader.result);
      reader.readAsDataURL(blob);
    });
  }

}
