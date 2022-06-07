import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import PollAnswer from 'src/app/modules/answer-poll/models/poll-answer';
import { environment } from 'src/environments/environment';
import { Poll } from '../models/poll';
import { PollLabel } from '../models/poll-label';

@Injectable()
export class PollService {

  private baseUrl = environment.apiUrl;

  // A constructor that get an HttpClient
  constructor(private http: HttpClient) { }

  public getPollById(id: number): Observable<Poll> {
    return this.http.get<Poll>(`${this.baseUrl}/polls/${id}`);
  }

  public sendPollAnswers(id: number, answers: PollAnswer[]): Observable<any> {
    return this.http.post(`${this.baseUrl}/polls/${id}/answers`, answers);
  }

  public getPolls(): Observable<PollLabel[]> {
    return of([
      {
        id: 1,
        name: 'Poll 1'
      },
      {
        id: 2,
        name: 'Poll 2'
      }
    ]);
    // return this.http.get<PollLabel[]>(`${this.baseUrl}/polls`);
  }
}
