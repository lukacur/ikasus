import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { RequestsAndContractsComponent } from './requests-and-contracts/requests-and-contracts.component';
import { VehiclesComponent } from './vehicles/vehicles.component';
import { VehicleDetailsComponent } from './vehicles/vehicle-details/vehicle-details.component';
import { DatepickerComponent } from './datepicker/datepicker.component';
import { VehicleTypesComponent } from './vehicle-types/vehicle-types.component';
import { ContractsComponent } from './contracts/contracts.component';
import { CustomerContractsComponent } from './customer-contracts/customer-contracts.component';

const routes: Routes = [
  { path: "", component: DatepickerComponent},
  { path: "rent", component: HomepageComponent},
  { path: "login", component: LoginComponent },
  { path: "types", component: VehicleTypesComponent },
  { path: "contracts", component: ContractsComponent },
  { path: "my-contracts", component: CustomerContractsComponent },
  { path: "vehicles", component: VehiclesComponent },
  { path: "vehicles/:id", component: VehicleDetailsComponent },
  { path: "contracts-and-requests", component: RequestsAndContractsComponent },
  { path: "profile", loadChildren: () => import(`./profile/profile.module`).then(m => m.ProfileModule) },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
