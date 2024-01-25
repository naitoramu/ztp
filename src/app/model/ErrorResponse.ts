export class ErrorResponse {
  constructor(
    public status: number,
    public error: string,
    public details: string,
    public violations: Violation[]
  ) {}
}

class Violation {
  constructor(
    field: string,
    message: string
  ) {}
}
