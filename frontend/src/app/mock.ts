import QuestionType from "./questions-types";

export default {
  name: "Favorite things poll",
  questions: [
    {
      type: "text" as QuestionType,
      text: "How was your day today?"
    },
    {
      type: "radio" as QuestionType,
      text: "What is your sex?",
      options: [
        "male",
        "female",
        "prefer not to specify"
      ]
    },
    {
      type: "checkbox" as QuestionType,
      text: "What do you like?",
      options: [
        "apples",
        "bananas",
        "oranges"
      ]
    },
    {
      type: "email" as QuestionType,
      text: "What is your email?",
      options: []
    },
    {
      type: "datetime" as QuestionType,
      text: "When were you born?",
      options: []
    },
    {
      type: "number" as QuestionType,
      text: "How old are you?",
      options: []
    }
  ]
}
