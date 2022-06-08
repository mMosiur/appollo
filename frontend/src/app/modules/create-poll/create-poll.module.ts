import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreatePollRoutingModule } from './create-poll-routing.module';
import { CreatePollComponent } from './create-poll.component';
import { AddQuestionDialogComponent } from './components/add-question-dialog/add-question-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { PollService } from 'src/app/shared/services/poll.service';
import { MatListModule } from '@angular/material/list';

@NgModule({
  declarations: [
    CreatePollComponent,
    AddQuestionDialogComponent,
  ],
  imports: [
    CommonModule,
    CreatePollRoutingModule,
    MatDialogModule,
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    DragDropModule,
    MatGridListModule,
    MatListModule,
    MatSelectModule,
    MatIconModule,
  ],
  providers: [
    PollService,
  ],
})
export class CreatePollModule { }
