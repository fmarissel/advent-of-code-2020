package fr.marissel.adventofcode.days;

import fr.marissel.adventofcode.util.Day;
import fr.marissel.adventofcode.util.Part;
import org.junit.jupiter.api.Test;


public class IssueTest {

  @Test
  public void issueTest() {

    Day day = Day.D14;
    System.out.println("Part 1 : " + day.compute(Part.ONE));
    System.out.println("Part 2 : " + day.compute(Part.TWO));
  }
}
