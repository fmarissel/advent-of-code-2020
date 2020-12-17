package fr.marissel.adventofcode.util;

import fr.marissel.adventofcode.days.Issue;
import fr.marissel.adventofcode.days.Issue1;
import fr.marissel.adventofcode.days.Issue10;
import fr.marissel.adventofcode.days.Issue11;
import fr.marissel.adventofcode.days.Issue12;
import fr.marissel.adventofcode.days.Issue15;
import fr.marissel.adventofcode.days.Issue16;
import fr.marissel.adventofcode.days.Issue2;
import fr.marissel.adventofcode.days.Issue3;
import fr.marissel.adventofcode.days.Issue4;
import fr.marissel.adventofcode.days.Issue5;
import fr.marissel.adventofcode.days.Issue6;
import fr.marissel.adventofcode.days.Issue7;
import fr.marissel.adventofcode.days.Issue8;
import fr.marissel.adventofcode.days.Issue9;

public enum Day {

  D1(new Issue1()),
  D2(new Issue2()),
  D3(new Issue3()),
  D4(new Issue4()),
  D5(new Issue5()),
  D6(new Issue6()),
  D7(new Issue7()),
  D8(new Issue8()),
  D9(new Issue9()),
  D10(new Issue10()),
  D11(new Issue11()),
  D12(new Issue12()),
  D15(new Issue15()),
  D16(new Issue16());

  Day(Issue issue) {
    this.issue = issue;
  }

  private Issue issue;

  public String compute(Part part) {
    return issue.compute(part);
  }
}
