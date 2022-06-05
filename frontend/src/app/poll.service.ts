import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Poll } from './poll';
import PollAnswer from './poll-answer';

@Injectable()
export class PollService {

  private baseUrl = environment.apiUrl;

  // A constructor that get an HttpClient
  constructor(private http: HttpClient) { }

  public getPollById(id: number): Observable<Poll> {
    return this.http.get<Poll>(`${this.baseUrl}/polls/${id}`);
  }

  public sendPollAnswers(id: number, answers: PollAnswer[]) : Observable<any> {
    return this.http.post(`${this.baseUrl}/polls/${id}/answers`, answers);
  }
}
