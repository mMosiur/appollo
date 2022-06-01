import { Question } from "./question";

export interface Poll {
  name: string,
  questions: Question[]
}
