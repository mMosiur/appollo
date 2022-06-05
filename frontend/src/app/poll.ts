import { Question } from './question';

export interface Poll {
  id: number;
  is_active: boolean;
  name: string;
  questions: Question[];
}
