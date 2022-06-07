import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RegisterRequest } from 'src/app/shared/contracts/register-request';
import { AccountService } from 'src/app/shared/services/account.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  username: string = '';
  password: string = '';
  firstname: string = '';
  lastname: string = '';
  email: string = '';

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
    const request: RegisterRequest = {
      username: this.username,
      password: this.password,
      firstname: this.firstname,
      lastname: this.lastname,
      email: this.email,
    };
    this.accountService.register(request).pipe(observable => {
      this.status = '';
      this.waiting = true;
      return observable;
    }).subscribe({
      next: data => {
        this.status = 'Register successful';
        this.waiting = false;
        const returnUrl = this.route.snapshot.queryParamMap.get('returnUrl');
        if (returnUrl) {
          this.router.navigate([returnUrl]);
        } else {
          this.router.navigate(['/account/login']);
        }
      },
      error: err => {
        this.status = 'Register failed';
        this.waiting = false;
      }
    });
  }

}
