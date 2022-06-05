import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule) },
  // { path: 'poll', loadChildren: () => import('./modules/answer-poll/answer-poll.module').then(m => m.AnswerPollModule) },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
