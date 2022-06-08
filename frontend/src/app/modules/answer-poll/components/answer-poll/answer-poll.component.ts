import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Poll } from 'src/app/shared/models/poll';
import { PollService } from 'src/app/shared/services/poll.service';
import { PollAnswer } from '../../models/poll-answer';

@Component({
  selector: 'app-answer-poll',
  templateUrl: './answer-poll.component.html',
  styleUrls: ['./answer-poll.component.scss']
})
export class AnswerPollComponent implements OnInit {

  submitted: boolean = false;

  status: string = '';

  @Input() id: number | null = null;

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
          question_id: question.id,
          answer_json: answer
        }
      });
  }

  constructor(
    private pollService: PollService,
    private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.id = parseInt(this.route.snapshot.paramMap.get('id') ?? '');
    this.pollService.getPollById(this.id).subscribe(data => {
      this.poll = data;
      this.answers = this.poll!.questions.map(_ => '');
    });
  }

  onAnswerChange(index: number, value: string) {
    this.route
    this.answers[index] = value;
  }

  public get canSubmit() : boolean {
    return !this.submitted && this.isPollFilledIn();
  }

  submit() {
    if (this.submitted) {
      throw new Error('Answers have already been submitted');
    }
    if (!this.poll) {
      throw new Error('Poll is not loaded yet');
    }
    console.log('submit');
    this.pollService.sendPollAnswers(this.poll.id, this.getPollAnswers()).subscribe({
      next: (data) => {
        this.submitted = true;
        this.status = 'Poll answers submitted successfully!';
        console.log('ok');
      },
      error: (err) => {
        console.error('error');
        this.status = `Error during answers submit: ${err}`;
      }
    });
  }

}
