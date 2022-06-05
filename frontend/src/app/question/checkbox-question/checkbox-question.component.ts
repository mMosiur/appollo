import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';

@Component({
  selector: 'app-checkbox-question',
  templateUrl: './checkbox-question.component.html',
  styleUrls: ['./checkbox-question.component.scss']
})
export class CheckboxQuestionComponent implements OnInit {

  @Input() text: string = '';

  @Input() options: string[] = [];

  @Output() answerChange = new EventEmitter<string>();

  choices: boolean[] = [];

  constructor() { }

  getAnswer(): string {
    return JSON.stringify(this.options.filter((_, index) => this.choices[index]));
  }

  ngOnInit(): void {
    this.choices = this.options.map(() => false);
    this.answerChange.emit(this.getAnswer());
  }

  onChange(index: number, data: MatCheckboxChange): void {
    this.choices[index] = data.checked;
    this.answerChange.emit(this.getAnswer());
  }

}
