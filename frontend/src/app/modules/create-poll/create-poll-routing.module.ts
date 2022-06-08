import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreatePollComponent } from './create-poll.component';

const routes: Routes = [
  { path: '', component: CreatePollComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CreatePollRoutingModule { }
