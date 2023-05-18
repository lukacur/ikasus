export interface Customer {
  email: string;
  password: string;
  name: string;
  surname: string;
  id: number;
  phoneNumber: string;
}

export interface Employee {
  name: string;
  surname: string;
  id: number;
  oib: string;
  idLocation: number;
  workAddress: string;
}

export interface Manager {
  name: string;
  surname: string;
  id: number;
  oib: string;
  idLocation: number;
  workAddress: string;
  username: string;
  managerFrom: Date;
  managerTo: Date;
}
