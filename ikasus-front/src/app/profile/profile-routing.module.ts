import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OverviewComponent } from './overview/overview.component';
import { ActiveComponent } from './active/active.component';
import { HistoryComponent } from './history/history.component';
import { PendingComponent } from './pending/pending.component';
import { ProfileComponent } from './profile.component';


const routes: Routes = [
  { path: "", component: ProfileComponent, children: [
    { path: "overview", component: OverviewComponent },
    { path: "active", component: ActiveComponent },
    { path: "pending", component: PendingComponent },
    { path: "history", component: HistoryComponent },
    { path: '', redirectTo: 'overview', pathMatch: "full"},
  ] },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }
