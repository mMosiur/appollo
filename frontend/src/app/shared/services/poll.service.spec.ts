import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { PollService } from './poll.service';

describe('PollService', () => {
  let service: PollService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientModule ],
      providers: [ PollService ]
    });
    service = TestBed.inject(PollService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
