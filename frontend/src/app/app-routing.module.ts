import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './shared/helpers/auth.guard';

const homeModule = () => import('./modules/home/home.module').then(m => m.HomeModule);
const answerPollModule = () => import('./modules/answer-poll/answer-poll.module').then(m => m.AnswerPollModule);
const accountModule = () => import('./modules/account/account.module').then(m => m.AccountModule);

const routes: Routes = [
  { path: '', loadChildren: homeModule },
  { path: 'poll', loadChildren: answerPollModule, canActivate: [AuthGuard] },
  { path: 'account', loadChildren: accountModule },
  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
