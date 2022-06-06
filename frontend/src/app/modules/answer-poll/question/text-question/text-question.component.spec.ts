import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';

import { TextQuestionComponent } from './text-question.component';

describe('TextQuestionComponent', () => {
  let component: TextQuestionComponent;
  let fixture: ComponentFixture<TextQuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TextQuestionComponent ],
      imports: [ FormsModule ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TextQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
