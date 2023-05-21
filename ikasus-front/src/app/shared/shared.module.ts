import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { NotificationDropdownComponent } from './notification-dropdown/notification-dropdown.component';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from '../app-routing.module';
import { ModalComponent } from './modal/modal.component';



@NgModule({
  declarations: [
    HeaderComponent,
    NotificationDropdownComponent,
    ModalComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule
  ],
  exports: [
    HeaderComponent,
    ModalComponent
  ]
})
export class SharedModule { }
