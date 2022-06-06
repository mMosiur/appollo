import { Component, OnInit } from '@angular/core';
import { AccountService } from 'src/app/shared/services/account.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';
  waiting: boolean = false;
  status: string = '';

  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
  }

  onSubmit(event: SubmitEvent): void {
    event.preventDefault();
    this.accountService.login(this.username, this.password).pipe(u => {
      this.status = '';
      this.waiting = true;
      return u;
    }).subscribe({
      next: data => {
        this.status = 'Login successful';
        this.waiting = false;
      },
      error: err => {
        this.status = 'Login failed';
        this.waiting = false;
      }
    }
    )
    // console.log({username: this.username, password: this.password});
  }

}
