import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { PollService } from 'src/app/shared/services/poll.service';

import { AnswerPollComponent } from './answer-poll.component';

describe('AnswerPollComponent', () => {
  let component: AnswerPollComponent;
  let fixture: ComponentFixture<AnswerPollComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnswerPollComponent ],
      imports: [
        HttpClientModule,
        RouterTestingModule
      ],
      providers: [ PollService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnswerPollComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
