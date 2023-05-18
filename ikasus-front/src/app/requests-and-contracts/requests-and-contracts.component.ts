import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-requests-and-contracts',
  templateUrl: './requests-and-contracts.component.html',
  styleUrls: ['./requests-and-contracts.component.scss']
})
export class RequestsAndContractsComponent implements OnInit {
  requests = [0, 1, 2, 3,1,1,1,1,1,1,1,1,1,1];
  contracts = [0, 1, 2, 3];

  constructor() { }

  ngOnInit(): void {
  }

}
