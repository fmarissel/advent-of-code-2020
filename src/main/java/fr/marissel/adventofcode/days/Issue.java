package fr.marissel.adventofcode.days;

import fr.marissel.adventofcode.util.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class Issue<T> {

  private int num;

  Issue(int num) {
    this.num = num;
  }

  protected T getInput() {
    try {
      return this.getInput(getData());
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  abstract T getInput(String data);

  abstract String computePartOne(T input);

  abstract String computePartTwo(T input);

  public String compute(Part part) {
    return (part == Part.ONE ? computePartOne(getInput()) : computePartTwo(getInput()));
  }

  private String getData() throws IOException {
    String path = "/input/day" + num + ".txt";
    InputStream inputStream = getClass().getResourceAsStream(path);
    return readFromInputStream(inputStream);
  }

  private String readFromInputStream(InputStream inputStream) throws IOException {
    StringBuilder resultStringBuilder = new StringBuilder();
    try (BufferedReader br
        = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        resultStringBuilder.append(line).append("\n");
      }
    }
    return resultStringBuilder.toString();
  }
}
