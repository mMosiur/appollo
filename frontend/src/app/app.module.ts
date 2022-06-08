import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AccountService } from './shared/services/account.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './shared/helpers/jwt.interceptor';
import { AuthGuard } from './shared/helpers/auth.guard';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [
    AuthGuard,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    AccountService,
  ],
  bootstrap: [
    AppComponent,
  ]
})
export class AppModule { }
