package fr.marissel.adventofcode.days;

import java.util.Arrays;

public class Issue17 extends Issue<boolean[][][]> {

  public Issue17() {
    super(17);
  }

  @Override
  boolean[][][] getInput(String data) {
    var input2d = Arrays.stream(data.split("\n"))
        .map(s -> {
          var charArray = s.toCharArray();
          var boolArray = new boolean[charArray.length];
          for (int i = 0; i < charArray.length; i++) {
            boolArray[i] = charArray[i] == '#';
          }
          return boolArray;
        })
        .toArray(boolean[][]::new);

    var input3d = new boolean[1][][];
    input3d[0] = input2d;

    return input3d;
  }

  @Override
  String computePartOne(boolean[][][] input) {

    int turn = 0;
    boolean current[][][] = input;

    while (turn != 6) {
      current = addLayer(current);
      boolean next[][][] = new boolean[current.length][current[0].length][current[0][0].length];

      for (int i = 0; i < current.length; i++) {
        for (int j = 0; j < current[i].length; j++) {
          for (int k = 0; k < current[i][j].length; k++) {
            boolean isActive = current[i][j][k];
            int actives = countActivesNeighbors(current, i, j, k);
            next[i][j][k] = (isActive && (actives == 2 || actives == 3))
                || (!isActive && actives == 3);
          }
        }
      }
      turn++;
      current = next;
    }

    return countActives(current) + "";
  }

  @Override
  String computePartTwo(boolean[][][] input) {
    var input4d = new boolean[1][][][];
    input4d[0] = input;

    int turn = 0;
    boolean current[][][][] = input4d;

    while (turn != 6) {
      current = addLayer(current);
      boolean next[][][][] = new boolean[current.length][current[0].length][current[0][0].length][current[0][0][0].length];

      for (int i = 0; i < current.length; i++) {
        for (int j = 0; j < current[i].length; j++) {
          for (int k = 0; k < current[i][j].length; k++) {
            for (int l = 0; l < current[i][j][k].length; l++) {
              boolean isActive = current[i][j][k][l];
              int actives = countActivesNeighbors(current, i, j, k, l);
              next[i][j][k][l] = (isActive && (actives == 2 || actives == 3))
                  || (!isActive && actives == 3);
            }
          }
        }
      }
      turn++;
      current = next;
    }

    return countActives(current) + "";
  }

  private boolean[][][] addLayer(boolean[][][] input) {

    boolean[][][] res = new boolean[input.length + 2][input[0].length + 2][input[0][0].length + 2];

    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length; j++) {
        for (int k = 0; k < input[i][j].length; k++) {
          res[i + 1][j + 1][k + 1] = input[i][j][k];
        }
      }
    }
    return res;
  }

  private boolean[][][][] addLayer(boolean[][][][] input) {

    boolean[][][][] res = new boolean[input.length + 2][input[0].length + 2][input[0][0].length + 2][
        input[0][0][0].length + 2];

    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length; j++) {
        for (int k = 0; k < input[i][j].length; k++) {
          for (int l = 0; l < input[i][j][k].length; l++) {
            res[i + 1][j + 1][k + 1][l + 1] = input[i][j][k][l];
          }
        }
      }
    }
    return res;
  }

  private int countActivesNeighbors(boolean[][][] input, int pi, int pj, int pk) {

    int count = 0;
    for (int i = Math.max(pi - 1, 0); i <= Math.min(pi + 1, input.length - 1); i++) {
      for (int j = Math.max(pj - 1, 0); j <= Math.min(pj + 1, input[i].length - 1); j++) {
        for (int k = Math.max(pk - 1, 0); k <= Math.min(pk + 1, input[i][j].length - 1); k++) {
          if (!(i == pi && j == pj && k == pk) && input[i][j][k]) {
            count++;
          }
        }
      }
    }

    return count;
  }

  private int countActivesNeighbors(boolean[][][][] input, int pi, int pj, int pk, int pl) {

    int count = 0;
    for (int i = Math.max(pi - 1, 0); i <= Math.min(pi + 1, input.length - 1); i++) {
      for (int j = Math.max(pj - 1, 0); j <= Math.min(pj + 1, input[i].length - 1); j++) {
        for (int k = Math.max(pk - 1, 0); k <= Math.min(pk + 1, input[i][j].length - 1); k++) {
          for (int l = Math.max(pl - 1, 0); l <= Math.min(pl + 1, input[i][j][k].length - 1); l++) {
            if (!(i == pi && j == pj && k == pk && l == pl) && input[i][j][k][l]) {
              count++;
            }
          }
        }
      }
    }

    return count;
  }

  private int countActives(boolean[][][] input) {
    int actives = 0;
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length; j++) {
        for (int k = 0; k < input[i][j].length; k++) {
          if (input[i][j][k]) {
            actives++;
          }
        }
      }
    }
    return actives;
  }

  private int countActives(boolean[][][][] input) {
    int actives = 0;
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length; j++) {
        for (int k = 0; k < input[i][j].length; k++) {
          for (int l = 0; l < input[i][j][k].length; l++) {
            if (input[i][j][k][l]) {
              actives++;
            }
          }
        }
      }
    }
    return actives;
  }
}
