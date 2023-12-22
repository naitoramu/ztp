import Violation from "./Violation";

export default class Problem {
  title: string;
  details: string;
  violations: Violation[] | undefined;
}