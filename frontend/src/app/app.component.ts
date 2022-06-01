import { Component } from '@angular/core';
import { Poll } from './poll';
import data from './mock';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'apPOLLo';
  poll: Poll = data;

  onClick() {
    console.log(this.poll)
  }
}
