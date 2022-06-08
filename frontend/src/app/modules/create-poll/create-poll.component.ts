import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CreatePollRequest } from 'src/app/shared/contracts/create-poll-request';
import { QuestionType } from 'src/app/shared/enums/questions-types';
import { PollService } from 'src/app/shared/services/poll.service';
import { AddQuestionDialogComponent } from './components/add-question-dialog/add-question-dialog.component';
import { Poll } from './models/poll';

@Component({
  selector: 'app-create-poll',
  templateUrl: './create-poll.component.html',
  styleUrls: ['./create-poll.component.scss']
})
export class CreatePollComponent {
  poll: Poll = {
    name: '',
    questions: []
  };

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.poll.questions, event.previousIndex, event.currentIndex);
  }

  constructor(
    public dialog: MatDialog,
    private pollService: PollService,
    private router: Router
  ) { }

  public get canCreate(): boolean {
    return !!this.poll.name && !!this.poll.questions;
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(AddQuestionDialogComponent, {
      width: '250px',
      data: { type: QuestionType.Text, text: '', options: undefined },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.poll.questions.push(result);
      }
    });
  }

  createPoll(): void {
    const request: CreatePollRequest = this.poll;
    this.pollService.createPoll(request).subscribe({
      next: (poll) => {
        this.router.navigate([`/poll/${poll.id}`]);
      },
      error: (err) => { console.log(err) },
    });
  }

}
