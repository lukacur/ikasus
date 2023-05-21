import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OverviewComponent } from './overview/overview.component';
import { PendingComponent } from './pending/pending.component';
import { ActiveComponent } from './active/active.component';
import { HistoryComponent } from './history/history.component';
import { ProfileRoutingModule } from './profile-routing.module';
import { ProfileComponent } from './profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [
    OverviewComponent,
    PendingComponent,
    ActiveComponent,
    HistoryComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ProfileRoutingModule,
    ReactiveFormsModule,
  ],
  exports: [
    ProfileComponent
  ]
})
export class ProfileModule { }
