import { QuestionType } from '../enums/questions-types';

export interface Question {
  id: number;
  text: string;
  type: QuestionType;
  options?: string[];
}
