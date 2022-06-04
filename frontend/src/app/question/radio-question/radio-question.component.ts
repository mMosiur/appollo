import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-radio-question',
  templateUrl: './radio-question.component.html',
  styleUrls: ['./radio-question.component.scss']
})
export class RadioQuestionComponent {

  @Input() text: string = "";
  @Input() answer: string = "";
  @Output() answerChange = new EventEmitter<string>();
  @Input() options: string[] = [];

  constructor() { }

  onAnswerInputChange(event: string) : void {
    this.answer = event;
    this.answerChange.emit(this.answer);
  }

}
