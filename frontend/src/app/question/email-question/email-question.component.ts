import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-email-question',
  templateUrl: './email-question.component.html',
  styleUrls: ['./email-question.component.scss']
})
export class EmailQuestionComponent {

  @Input() text: string = "";
  @Input() email: string = "";
  @Output() emailChange = new EventEmitter<string>();

  constructor() { }

  onEmailInputChange(event: string) : void {
    this.email = event;
    this.emailChange.emit(this.email);
  }

}
