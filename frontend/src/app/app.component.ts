import { Component } from '@angular/core';
import { Poll } from './poll';
import PollAnswer from './poll-answer';
import { PollService } from './poll.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'apPOLLo';
  poll?: Poll;
  answers: string[] = [];

  isPollFilledIn(): boolean {
    return this.answers.every(value => !!value);
  }

  getPollAnswers(): PollAnswer[] {
    if (!this.poll) {
      throw new Error('Poll is not loaded yet');
    }
    return this.poll.questions
      .map((question, i) => {
        const answer = this.answers[i];
        return {
          questionId: question.id,
          answerJson: answer
        }
      });
  }

  constructor(private pollService: PollService) {
    this.pollService.getPollById(1).subscribe(data => {
      this.poll = data;
      this.answers = this.poll.questions.map(_ => '');
    });
  }

  onAnswerChange(index: number, value: string) {
    this.answers[index] = value;
  }

  submit() {
    if (!this.poll) {
      throw new Error('Poll is not loaded yet');
    }
    this.pollService.sendPollAnswers(this.poll.id, this.getPollAnswers()).subscribe({
      next: (data) => {console.log('success')},
      error: () => {console.error('error')}
    });
  }
}
