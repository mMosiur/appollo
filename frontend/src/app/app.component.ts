import { Component } from '@angular/core';
import { User } from './shared/models/user';
import { AccountService } from './shared/services/account.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'apPOLLo';

  constructor(private accountService: AccountService) {}

  getUser(): User | null {
    return this.accountService.user;
  }

  onClickLogOut(): void {
    this.accountService.logout();
  }
}
