import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './homepage/homepage.component';
import { SharedModule } from './shared/shared.module';
import { CardComponent } from './card/card.component';
import { CardListComponent } from './card-list/card-list.component';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { LoginComponent } from './login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule  } from '@angular/common/http';
import { RequestsAndContractsComponent } from './requests-and-contracts/requests-and-contracts.component';
import { AuthInterceptor } from './auth.interceptor';
import { VehiclesComponent } from './vehicles/vehicles.component';
import { VehicleDetailsComponent } from './vehicles/vehicle-details/vehicle-details.component';
import { VehicleTypesComponent } from './vehicle-types/vehicle-types.component';
import { DatepickerComponent } from './datepicker/datepicker.component';
import { ContractsComponent } from './contracts/contracts.component';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    CardComponent,
    CardListComponent,
    SearchBarComponent,
    LoginComponent,
    RequestsAndContractsComponent,
    VehicleDetailsComponent,
    VehiclesComponent,
    VehicleTypesComponent,
    DatepickerComponent,
    ContractsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    CommonModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
