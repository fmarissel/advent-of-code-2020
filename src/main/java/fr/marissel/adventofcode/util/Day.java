package fr.marissel.adventofcode.util;

import fr.marissel.adventofcode.days.Issue;
import fr.marissel.adventofcode.days.Issue1;
import fr.marissel.adventofcode.days.Issue2;
import fr.marissel.adventofcode.days.Issue3;
import fr.marissel.adventofcode.days.Issue4;

public enum Day {

  D1(new Issue1()),
  D2(new Issue2()),
  D3(new Issue3()),
  D4(new Issue4());
  /*D5(new Issue5()),
  D6(new Issue6()),
  D7(new Issue_7()),
  D8(new Issue()),
  D9(new Issue9()),
  D10(new Issue10());*/

  Day(Issue issue) {
    this.issue = issue;
  }

  private Issue issue;

  public String compute(Part part) {
    return issue.compute(part);
  }
}
