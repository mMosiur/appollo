import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { QuestionComponent } from './question/question.component';
import { TextQuestionComponent } from './question/text-question/text-question.component';
import { RadioQuestionComponent } from './question/radio-question/radio-question.component';
import { CheckboxQuestionComponent } from './question/checkbox-question/checkbox-question.component';

@NgModule({
  declarations: [
    AppComponent,
    QuestionComponent,
    TextQuestionComponent,
    RadioQuestionComponent,
    CheckboxQuestionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
