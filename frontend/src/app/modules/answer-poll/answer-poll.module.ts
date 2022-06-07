import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { CheckboxQuestionComponent } from './question/checkbox-question/checkbox-question.component';
import { DatetimeQuestionComponent } from './question/datetime-question/datetime-question.component';
import { EmailQuestionComponent } from './question/email-question/email-question.component';
import { NumberQuestionComponent } from './question/number-question/number-question.component';
import { QuestionComponent } from './question/question.component';
import { RadioQuestionComponent } from './question/radio-question/radio-question.component';
import { TextQuestionComponent } from './question/text-question/text-question.component';
import { AnswerPollComponent } from './components/answer-poll/answer-poll.component';
import { PollService } from 'src/app/shared/services/poll.service';
import { PollListComponent } from './components/poll-list/poll-list.component';
import { AnswerPollRoutingModule } from './answer-poll-routing.module';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
  declarations: [
    AnswerPollComponent,
    QuestionComponent,
    TextQuestionComponent,
    RadioQuestionComponent,
    CheckboxQuestionComponent,
    EmailQuestionComponent,
    DatetimeQuestionComponent,
    NumberQuestionComponent,
    PollListComponent,
  ],
  imports: [
    CommonModule,
    AnswerPollRoutingModule,
    FormsModule,
    MatInputModule,
    MatInputModule,
    MatRadioModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatGridListModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    MatListModule,
    MatIconModule,
  ],
  providers: [
    PollService
  ],
})
export class AnswerPollModule { }
