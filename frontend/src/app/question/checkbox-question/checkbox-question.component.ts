import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-checkbox-question',
  templateUrl: './checkbox-question.component.html',
  styleUrls: ['./checkbox-question.component.scss']
})
export class CheckboxQuestionComponent implements OnInit {

  @Input() text: string = "";

  @Input() options: string[] = [];

  choices: boolean[] = [];

  constructor() { }

  ngOnInit(): void {
    this.choices = this.options.map(() => false);
  }

}
