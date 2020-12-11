package fr.marissel.adventofcode.days;


import java.util.Arrays;

public class Issue11 extends Issue<Boolean[][]> {

  public Issue11() {
    super(11);
  }

  @Override
  Boolean[][] getInput(String data) {
    var input = Arrays.stream(data.split("\n"))
        .map(s -> {
          var charArray = s.toCharArray();
          var boolArray = new Boolean[charArray.length];
          for (int i = 0; i < charArray.length; i++) {
            boolArray[i] = (charArray[i] == 'L' ? Boolean.FALSE : null);
          }
          return boolArray;
        })
        .toArray(Boolean[][]::new);

    return input;
  }

  @Override
  String computePartOne(Boolean[][] input) {

    boolean hasChange = true;
    int iteration = 0;
    Boolean[][] current = input;

    while (hasChange) {
      hasChange = false;
      Boolean[][] next = new Boolean[input.length][input[0].length];
      for (int row = 0; row < current.length; row++) {
        for (int col = 0; col < current[row].length; col++) {
          next[row][col] = next(row, col, current);
          hasChange |= hasChange(current[row][col], next[row][col]);
        }
      }
      iteration++;
      current = next;
    }

    return countOccupied(current) + "";
  }

  @Override
  String computePartTwo(Boolean[][] input) {
    boolean hasChange = true;
    int iteration = 0;
    Boolean[][] current = input;

    while (hasChange) {
      hasChange = false;
      Boolean[][] next = new Boolean[input.length][input[0].length];
      for (int row = 0; row < current.length; row++) {
        for (int col = 0; col < current[row].length; col++) {
          next[row][col] = next2(row, col, current);
          hasChange |= hasChange(current[row][col], next[row][col]);
        }
      }
      iteration++;
      current = next;
    }

    return countOccupied(current) + "";
  }

  private Boolean next(int row, int col, Boolean[][] input) {

    Boolean current = input[row][col];
    int rows = input.length, cols = input[0].length;
    if (current != null) {
      int occupied = 0;
      for (int i = Integer.max(row - 1, 0); i <= Integer.min(row + 1, rows - 1); i++) {
        for (int j = Integer.max(col - 1, 0); j <= Integer.min(col + 1, cols - 1); j++) {
          if (i != row || j != col) {
            occupied += Boolean.TRUE.equals(input[i][j]) ? 1 : 0;
          }
        }
      }
      if (Boolean.TRUE.equals(current) && occupied >= 4) {
        return Boolean.FALSE;
      } else if (Boolean.FALSE.equals(current) && occupied == 0) {
        return Boolean.TRUE;
      }
    }
    return current;
  }

  private Boolean next2(int row, int col, Boolean[][] input) {

    Boolean current = input[row][col];
    int rows = input.length, cols = input[0].length;
    if (current != null) {
      int occupied = 0;

      // left
      for (int j = Integer.max(col - 1, 0); j >= 0; j--) {
        if (j != col && input[row][j] != null) {
          occupied += Boolean.TRUE.equals(input[row][j]) ? 1 : 0;
          break;
        }
      }

      // right
      for (int j = Integer.min(col + 1, cols - 1); j < cols; j++) {
        if (j != col && input[row][j] != null) {
          occupied += Boolean.TRUE.equals(input[row][j]) ? 1 : 0;
          break;
        }
      }

      // up
      for (int i = Integer.max(row - 1, 0); i >= 0; i--) {
        if (i != row && input[i][col] != null) {
          occupied += Boolean.TRUE.equals(input[i][col]) ? 1 : 0;
          break;
        }
      }

      // down
      for (int i = Integer.min(row + 1, cols - 1); i < rows; i++) {
        if (i != row && input[i][col] != null) {
          occupied += Boolean.TRUE.equals(input[i][col]) ? 1 : 0;
          break;
        }
      }

      int diag = 1;
      while (row + diag < rows && col + diag < cols) {
        if (input[row + diag][col + diag] != null) {
          occupied += Boolean.TRUE.equals(input[row + diag][col + diag]) ? 1 : 0;
          break;
        }
        diag++;
      }

      diag = 1;
      while (row - diag >= 0 && col - diag >= 0) {
        if (input[row - diag][col - diag] != null) {
          occupied += Boolean.TRUE.equals(input[row - diag][col - diag]) ? 1 : 0;
          break;
        }
        diag++;
      }

      diag = 1;
      while (row + diag < rows && col - diag >= 0) {
        if (input[row + diag][col - diag] != null) {
          occupied += Boolean.TRUE.equals(input[row + diag][col - diag]) ? 1 : 0;
          break;
        }
        diag++;
      }

      diag = 1;
      while (row - diag >= 0 && col + diag < cols) {
        if (input[row - diag][col + diag] != null) {
          occupied += Boolean.TRUE.equals(input[row - diag][col + diag]) ? 1 : 0;
          break;
        }
        diag++;
      }

      if (Boolean.TRUE.equals(current) && occupied >= 5) {
        return Boolean.FALSE;
      } else if (Boolean.FALSE.equals(current) && occupied == 0) {
        return Boolean.TRUE;
      }
    }
    return current;
  }

  private boolean hasChange(Boolean current, Boolean next) {
    return current != null && next != null && !current.equals(next);
  }

  private int countOccupied(Boolean[][] input) {
    int count = 0;
    for (int row = 0; row < input.length; row++) {
      for (int col = 0; col < input[row].length; col++) {
        if (Boolean.TRUE.equals(input[row][col])) {
          count += 1;
        }
      }
    }
    return count;
  }
}
