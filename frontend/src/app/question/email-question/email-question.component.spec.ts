import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailQuestionComponent } from './email-question.component';

describe('EmailQuestionComponent', () => {
  let component: EmailQuestionComponent;
  let fixture: ComponentFixture<EmailQuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmailQuestionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
