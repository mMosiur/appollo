import { QuestionType } from './questions-types';

export interface Question {
  id: number;
  text: string;
  type: QuestionType;
  options?: string[];
}
