import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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

  constructor(
    private accountService: AccountService,
    private route: ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  onSubmit(event: SubmitEvent): void {
    event.preventDefault();
    this.accountService.login(this.username, this.password).pipe(observable => {
      this.status = '';
      this.waiting = true;
      return observable;
    }).subscribe({
      next: data => {
        this.status = 'Login successful';
        this.waiting = false;
        const returnUrl = this.route.snapshot.queryParamMap.get('returnUrl');
        if (returnUrl) {
          this.router.navigate([returnUrl]);
        } else {
          this.router.navigate(['/account']);
        }
      },
      error: err => {
        this.status = 'Login failed';
        this.waiting = false;
      }
    });
  }

}
