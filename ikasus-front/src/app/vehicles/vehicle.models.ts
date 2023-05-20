export interface Vehicle {
  id: number;
  registration: string;
  name: string;
  manufacturer: string;
  kmDriven: number;
  pricePerDay: number;
  imagePath: string;
  vehicleTypeId: string;
  locationId: number;
}

export interface Contract {
  id: number;
  title: string;
}

export interface VehicleDetails {
  id: number;
  registration: string;
  name: string;
  manufacturer: string;
  kmDriven: number;
  pricePerDay: number;
  imagePath: string;
  vehicleTypeId: string;
  locationId: number;
  rentals: Rental[];
}

export interface Rental {
  id: number;
  timeFrom: string;
  timeTo: string;
  kmDriven: number;
  active: boolean;
  vehicleId: number;
  contractId: number;
}

export interface VehicleType {
  id: string;
  typeName: string;
  category: string;
}

export interface Location {
  id: number;
  address: string;
  zipCode: number;
  city: string;
  country: string;
}
