import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { QuestionComponent } from './question/question.component';
import { TextQuestionComponent } from './question/text-question/text-question.component';
import { RadioQuestionComponent } from './question/radio-question/radio-question.component';
import { CheckboxQuestionComponent } from './question/checkbox-question/checkbox-question.component';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatRadioModule } from '@angular/material/radio';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { EmailQuestionComponent } from './question/email-question/email-question.component';
import { DatetimeQuestionComponent } from './question/datetime-question/datetime-question.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { HttpClientModule } from '@angular/common/http';
import { PollService } from './poll.service';
import { MatGridListModule } from '@angular/material/grid-list';
import { NumberQuestionComponent } from './question/number-question/number-question.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
@NgModule({
  declarations: [
    AppComponent,
    QuestionComponent,
    TextQuestionComponent,
    RadioQuestionComponent,
    CheckboxQuestionComponent,
    EmailQuestionComponent,
    DatetimeQuestionComponent,
    NumberQuestionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatRadioModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatGridListModule,
    HttpClientModule,
    MatProgressSpinnerModule,
    MatButtonModule
  ],
  providers: [
    MatDatepickerModule,
    PollService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
