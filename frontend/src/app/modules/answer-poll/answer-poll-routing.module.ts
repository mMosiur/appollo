import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AnswerPollComponent } from './answer-poll.component';

const routes: Routes = [
  { path: ':id', component: AnswerPollComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AnswerPollRoutingModule { }
