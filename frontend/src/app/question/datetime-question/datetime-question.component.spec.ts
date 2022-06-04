import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DatetimeQuestionComponent } from './datetime-question.component';

describe('DatetimeQuestionComponent', () => {
  let component: DatetimeQuestionComponent;
  let fixture: ComponentFixture<DatetimeQuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DatetimeQuestionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DatetimeQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
