import QuestionType from "./questions-types";

export interface Question {
  text: string,
  type: QuestionType,
  options?: string[]
}
