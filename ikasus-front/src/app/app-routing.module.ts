import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { RequestsAndContractsComponent } from './requests-and-contracts/requests-and-contracts.component';

const routes: Routes = [
  { path: "", component: HomepageComponent},
  { path: "login", component: LoginComponent },
  { path: "contracts-and-requests", component: RequestsAndContractsComponent },
  { path: "profile", loadChildren: () => import(`./profile/profile.module`).then(m => m.ProfileModule) },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
