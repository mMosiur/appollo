import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';

@Component({
  selector: 'app-datetime-question',
  templateUrl: './datetime-question.component.html',
  styleUrls: ['./datetime-question.component.scss']
})
export class DatetimeQuestionComponent {

  @Input() text: string = "";
  @Input() answer: string = "";
  @Output() answerChange = new EventEmitter<string>();

  constructor() { }

  onDateChange(event: MatDatepickerInputEvent<any, any>) : void {
    this.answer = event.value;
    this.answerChange.emit(this.answer);
  }

}
