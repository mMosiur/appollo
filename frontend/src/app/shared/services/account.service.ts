import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../models/user';
import { environment } from 'src/environments/environment';
import { LoginResponse } from '../contracts/login-response';
import { RegisterRequest } from '../contracts/register-request';

@Injectable()
export class AccountService {

  constructor(
    private router: Router,
    private http: HttpClient
  ) { }

  public get user(): User | null {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  login(username: string, password: string): Observable<User> {
    //mock
    const user = {
      username: username,
      token: `token${username}${password}`
    };
    localStorage.setItem('user', JSON.stringify(user));
    return of(user);
    // return this.http.post<LoginResponse>(`${environment.apiUrl}/users/login`, { username, password })
    //   .pipe(map(response => {
    //     // store user details and jwt token in local storage to keep user logged in between page refreshes
    //     const user = {
    //       username: username,
    //       token: response.value
    //     }
    //     localStorage.setItem('user', JSON.stringify(user));
    //     return user;
    //   }));
  }

  logout() {
    // remove user from local storage and set current user to null
    localStorage.removeItem('user');
    this.router.navigate(['/account/login']);
  }

  register(request: RegisterRequest) {
    return this.http.post(`${environment.apiUrl}/users`, request);
  }

  // getAll() {
  //   return this.http.get<User[]>(`${environment.apiUrl}/users`);
  // }

  // getById(id: string) {
  //   return this.http.get<User>(`${environment.apiUrl}/users/${id}`);
  // }

  // update(id, params) {
  //   return this.http.put(`${environment.apiUrl}/users/${id}`, params)
  //     .pipe(map(x => {
  //       // update stored user if the logged in user updated their own record
  //       if (id == this.userValue.id) {
  //         // update local storage
  //         const user = { ...this.userValue, ...params };
  //         localStorage.setItem('user', JSON.stringify(user));

  //         // publish updated user to subscribers
  //         this.userSubject.next(user);
  //       }
  //       return x;
  //     }));
  // }

  // delete(id: string) {
  //   return this.http.delete(`${environment.apiUrl}/users/${id}`)
  //     .pipe(map(x => {
  //       // auto logout if the logged in user deleted their own record
  //       if (id == this.userValue.id) {
  //         this.logout();
  //       }
  //       return x;
  //     }));
  // }
}
