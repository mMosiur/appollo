import { QuestionType } from "../enums/questions-types";

export interface CreatePollRequest {
  name: string;
  questions: {
    type: QuestionType;
    text: string;
    options?: string[];
  }[];
}
