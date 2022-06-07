import { Component, OnInit } from '@angular/core';
import { PollLabel } from 'src/app/shared/models/poll-label';
import { PollService } from 'src/app/shared/services/poll.service';

@Component({
  selector: 'app-poll-list',
  templateUrl: './poll-list.component.html',
  styleUrls: ['./poll-list.component.scss']
})
export class PollListComponent implements OnInit {

  pollLabels: PollLabel[] = [];

  constructor(private pollService: PollService) { }

  ngOnInit(): void {
    this.pollService.getPolls().subscribe({
      next: data => this.pollLabels = data,
      error: err => console.error(err)
    });
  }

}
