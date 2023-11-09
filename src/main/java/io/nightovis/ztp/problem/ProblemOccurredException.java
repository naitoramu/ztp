package io.nightovis.ztp.problem;

public class ProblemOccurredException extends RuntimeException {
	private final Problem problem;

	public ProblemOccurredException(Problem problem) {
		this.problem = problem;
	}

	public Problem problem() {
		return problem;
	}
}
