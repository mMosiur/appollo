import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { QuestionType } from 'src/app/shared/enums/questions-types';
import { Question } from '../../models/question';

@Component({
  selector: 'app-add-question-dialog',
  templateUrl: './add-question-dialog.component.html',
  styleUrls: ['./add-question-dialog.component.scss']
})
export class AddQuestionDialogComponent {
  questionTypes: string[] = Object.values(QuestionType);
  newOption: string = '';

  constructor(
    public dialogRef: MatDialogRef<AddQuestionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public question: Question
  ) { }

  removeOption(index: number): void {
    if (!this.question.options) {
      throw new Error('Options are not initialized');
    }
    this.question.options.splice(index, 1);
  }

  onDiscardClick(): void {
    this.dialogRef.close();
  }

  onAddOptionClick(): void {
    if (!this.newOption) {
      return;
    }
    if (!this.question.options) {
      this.question.options = [];
    }
    this.question.options.push(this.newOption);
    this.newOption = '';
  }

  buildQuestion(): Question {
    return {
      text: this.question.text,
      type: this.question.type,
      options: this.hasOptions ? this.question.options : undefined
    };
  }

  public get canAdd(): boolean {
    return !!this.question.text && !!this.question.type && (!this.hasOptions || !!this.question.options);
  }

  public get hasOptions(): boolean {
    return [QuestionType.Radio, QuestionType.Checkbox].includes(this.question.type);
  }

}
