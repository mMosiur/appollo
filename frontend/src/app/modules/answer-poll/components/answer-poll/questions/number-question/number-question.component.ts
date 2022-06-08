import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-number-question',
  templateUrl: './number-question.component.html',
  styleUrls: ['./number-question.component.scss']
})
export class NumberQuestionComponent {

  @Input() text: string = '';
  @Input() answer: string = '';
  @Output() answerChange = new EventEmitter<string>();

  constructor() { }

  onAnswerInputChange(event: string) : void {
    this.answer = event;
    this.answerChange.emit(this.answer?.toString() ?? '');
  }

}
