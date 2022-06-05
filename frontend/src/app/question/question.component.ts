import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Question } from '../question';
import QuestionType from '../questions-types';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {
  textType = QuestionType.Text
  radioType = QuestionType.Radio
  checkboxType = QuestionType.Checkbox
  emailType = QuestionType.Email
  datetimeType = QuestionType.Datetime
  numberType = QuestionType.Number

  @Input() question : Question = { id: 0, text: '', type: QuestionType.Text }

  answer: string = ''
  @Output() answerChange = new EventEmitter<string>();

  constructor() { }

  getOptions(): string[] {
    if(this.question.options === undefined)
      throw new Error('Options are not defined');
    return this.question.options;
  }

  onAnswerChange(value: string) {
    this.answer = value;
    this.answerChange.emit(value);
  }

  ngOnInit(): void {
  }

}
