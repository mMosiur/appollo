import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatRadioChange } from '@angular/material/radio';

@Component({
  selector: 'app-radio-question',
  templateUrl: './radio-question.component.html',
  styleUrls: ['./radio-question.component.scss']
})
export class RadioQuestionComponent {

  @Input() text: string = '';
  @Input() answer: string = '';
  @Output() answerChange = new EventEmitter<string>();
  @Input() options: string[] = [];

  constructor() { }

  onAnswerChange(event: MatRadioChange): void {
    this.answer = event.value;
    this.answerChange.emit(this.answer);
  }

}
