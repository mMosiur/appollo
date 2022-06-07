import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/user';
import { AccountService } from 'src/app/shared/services/account.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

  constructor(private account: AccountService) { }

  public get user(): User | null {
    return this.account.user;
  }

  ngOnInit(): void {
  }

  onLogoutClick(): void {
    this.account.logout();
  }

}
