import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-text-question',
  templateUrl: './text-question.component.html',
  styleUrls: ['./text-question.component.scss']
})
export class TextQuestionComponent {

  @Input() text: string = "";
  @Input() answer: string = "";
  @Output() answerChange = new EventEmitter<string>();

  constructor() { }

  onAnswerInputChange(event: string) : void {
    this.answer = event;
    this.answerChange.emit(this.answer);
  }

}
