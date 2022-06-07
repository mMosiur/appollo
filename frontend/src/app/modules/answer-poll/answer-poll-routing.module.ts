import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AnswerPollComponent } from './components/answer-poll/answer-poll.component';
import { PollListComponent } from './components/poll-list/poll-list.component';

const routes: Routes = [
  { path: '', component: PollListComponent },
  { path: ':id', component: AnswerPollComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AnswerPollRoutingModule { }
