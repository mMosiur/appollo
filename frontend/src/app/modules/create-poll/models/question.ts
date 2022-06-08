import { QuestionType } from "src/app/shared/enums/questions-types";

export interface Question {
  type: QuestionType;
  text: string;
  options?: string[];
}
