import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PollAnswer } from 'src/app/modules/answer-poll/models/poll-answer';
import { environment } from 'src/environments/environment';
import { CreatePollRequest } from '../contracts/create-poll-request';
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
    return this.http.get<PollLabel[]>(`${this.baseUrl}/polls`);
  }

  public createPoll(poll: CreatePollRequest): Observable<Poll> {
    return this.http.post<Poll>(`${this.baseUrl}/polls`, poll);
  }
}
