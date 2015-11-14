// Copyright (C) 2014 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package charter3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class TicTacToe {
    private static Logger log = LoggerFactory.getLogger(TicTacToe.class);

    private class Line {
        private int[] markLine;
        private int maxIndex;
        private int numberOfX = 0;
        private int numberOfO = 0;

        Line(int size) {
            markLine = new int[size];
            maxIndex = -1;
        }

        private void addMark(int row, int column, int player) {
            maxIndex++;
            markLine[maxIndex] = player;
            if (player == X) {
                numberOfX++;
            }
            if (player == O) {
                numberOfO++;
            }
        }

        public int whoSuccessOnThisLine() {
            if (numberOfX >= numberOfMarksInlineToWin || numberOfO >= numberOfMarksInlineToWin) {
                int continueXNumber = 0;
                int continueONumber = 0;
                for (int i = 0; i <= maxIndex; i++) {
                    if (markLine[i] == X) {
                        continueXNumber++;
                        if (continueXNumber == numberOfMarksInlineToWin) {
                            return X;
                        }
                        continueONumber = 0;
                        continue;
                    }
                    if (markLine[i] == O) {
                        continueONumber++;
                        if (continueONumber == numberOfMarksInlineToWin) {
                            return O;
                        }
                        continueXNumber = 0;
                        continue;
                    }

                    continueXNumber = 0;
                    continueONumber = 0;
                }
            }
            return None;
        }
    }

    public static final int X = -1;
    public static final int O = 1;
    public static final int None = 0;

    private static String playerOf(int playerId) {
        return playerId == None ? " " : playerId == X ? "X" : "O";
    }

    private final int size;
    private final int numberOfMarksInlineToWin;
    private int[][] board;

    private int currentPlayer = O;//the first player will be O
    private int currentMarkRowIndex;
    private int currentMarkColumnIndex;

    private String drawALine() {
        StringBuilder re = new StringBuilder();
        re.append("\n----");
        for (int i = 0; i < size; i++) {
            re.append("----");
        }
        re.append("----\n");
        return re.toString();
    }

    private String drawColumnNumber() {
        StringBuilder re = new StringBuilder();
        re.append("   | ");
        for (int i = 0; i < size; i++) {
            re.append(i + 1).append(" | ");
        }
        return re.toString();
    }

    private int getStartMarkIndexToWeightWin(int currentIndexOfRowOrColumn) {
        return Math.max(0, currentIndexOfRowOrColumn - numberOfMarksInlineToWin - 1);
    }

    private int getEndMarkIndexToWeightWin(int currentIndexOfRowOrColumn) {
        return Math.min(size - 1, currentIndexOfRowOrColumn + numberOfMarksInlineToWin - 1);
    }

    private int getValidDiagonalMarks(int givenRowIndex, int givenColumnIndex) {
        return Math.min(Math.abs(currentMarkRowIndex - givenRowIndex), Math.abs(currentMarkColumnIndex - givenColumnIndex));
    }

    public TicTacToe(int size, int numberOfMarksInlineToWin) {
        if (numberOfMarksInlineToWin < 3 || numberOfMarksInlineToWin > size) {
            throw new IllegalArgumentException("The Success Line Length should  be bigger than 2 and less than the size ");
        }
        this.numberOfMarksInlineToWin = numberOfMarksInlineToWin;
        this.size = size;
        board = new int[size][size];
        clean();
    }

    public int whoWin() {
        /*
        image the play board as:
        *  column number from left to right
        *  row number from top to down
        *         1  2  3 
        *      1 
        *      2 
        *      3
         */
        int rowLineStartMarkColumnIndex, rowLineEndMarkColumnIndex;
        int columnLineStartMarkRowIndex, columnLineEndMarkRowIndex;
        Line line;
        int playerId;


        //check row line
        columnLineStartMarkRowIndex = getStartMarkIndexToWeightWin(currentMarkColumnIndex);
        columnLineEndMarkRowIndex = getEndMarkIndexToWeightWin(currentMarkColumnIndex);
        line = new Line(columnLineEndMarkRowIndex - columnLineStartMarkRowIndex + 1);
        for (int j = columnLineStartMarkRowIndex; j <= columnLineEndMarkRowIndex; j++) {
            line.addMark(currentMarkRowIndex, j, board[currentMarkRowIndex][j]);
        }
        playerId = line.whoSuccessOnThisLine();
        if (playerId != None) {
            return playerId;
        }
        //check column line
        rowLineStartMarkColumnIndex = getStartMarkIndexToWeightWin(currentMarkRowIndex);
        rowLineEndMarkColumnIndex = getEndMarkIndexToWeightWin(currentMarkRowIndex);
        line = new Line(rowLineEndMarkColumnIndex - rowLineStartMarkColumnIndex + 1);
        for (int i = rowLineStartMarkColumnIndex; i <= rowLineEndMarkColumnIndex; i++) {
            line.addMark(i, currentMarkColumnIndex, board[i][currentMarkColumnIndex]);
        }
        playerId = line.whoSuccessOnThisLine();
        if (playerId != None) {
            return playerId;
        }

        //check diagonal
        int diagonalSize;
        int leftMarksNumber;
        int rightMarksNumber;

        // check left top-> right down diagonal
        leftMarksNumber = getValidDiagonalMarks(columnLineStartMarkRowIndex, rowLineStartMarkColumnIndex);
        rightMarksNumber = getValidDiagonalMarks(columnLineEndMarkRowIndex, rowLineEndMarkColumnIndex);
        diagonalSize = leftMarksNumber + rightMarksNumber + 1;
        if (diagonalSize >= numberOfMarksInlineToWin) {
            line = new Line(diagonalSize);
            int i = currentMarkRowIndex - leftMarksNumber;
            int j = currentMarkColumnIndex - leftMarksNumber;
            for (; i <= currentMarkRowIndex + rightMarksNumber; ) {
                line.addMark(i, j, board[i][j]);
                i++;
                j++;
            }
            playerId = line.whoSuccessOnThisLine();
            if (playerId != None) {
                return playerId;
            }
        }
        //check right top-> left down diagonal
        leftMarksNumber = getValidDiagonalMarks(columnLineEndMarkRowIndex, rowLineStartMarkColumnIndex);
        rightMarksNumber = getValidDiagonalMarks(columnLineStartMarkRowIndex, rowLineEndMarkColumnIndex);
        diagonalSize = leftMarksNumber + rightMarksNumber + 1;
        if (diagonalSize >= numberOfMarksInlineToWin) {
            line = new Line(diagonalSize);
            int i = currentMarkRowIndex + leftMarksNumber;
            int j = currentMarkColumnIndex - leftMarksNumber;
            for (; i >= currentMarkRowIndex - rightMarksNumber; ) {
                line.addMark(i, j, board[i][j]);
                i--;
                j++;
            }
            playerId = line.whoSuccessOnThisLine();
            if (playerId != None) {
                return playerId;
            }
        }
        return None;
    }

    public int nextPlayer() {
        currentPlayer = -currentPlayer;
        return currentPlayer;
    }

    public int currentPlayer() {
        return currentPlayer;
    }

    public void clean() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = None;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder re = new StringBuilder();
        re.append(drawColumnNumber());
        re.append(drawALine());
        for (int i = 0; i < size; i++) {
            re.append(" ").append(i + 1).append(" |");
            for (int j = 0; j < size; j++) {
                re.append(" ").append(playerOf(board[i][j])).append(" ");
                re.append("|");

            }
            re.append(" ").append(i + 1);
            re.append(drawALine());
        }
        re.append(drawColumnNumber());
        return re.toString();
    }

    /*
     *  used for user
     */
    public void mark(int i, int j) {
        i--;
        j--;
        if (i < size && i >= 0 && j < size && j >= 0 && board[i][j] == 0) {
            board[i][j] = currentPlayer;
            currentMarkRowIndex = i;
            currentMarkColumnIndex = j;
            return;
        }
        throw new IndexOutOfBoundsException("The location is not right");
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        TicTacToe game;

        while (true) {
            try {
                System.out.println("Please input the size of the board and the number of marks in line to win, e.g. 5 3)");
                game = new TicTacToe(in.nextInt(), in.nextInt());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Please repeat again.");
                continue;
            }
        }

        System.out.println(game.toString());
        System.out.println("Start play.");
        while (true) {
            System.out.println("Next, Where will you will player? (input the line and column in format like 2 2 )");

            try {
                game.mark(in.nextInt(), in.nextInt());
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
                System.out.println("Please repeat again.");
                continue;
            }

            System.out.println(game.toString());
            game.nextPlayer();
            int playId = game.whoWin();
            if (playId != None) {
                System.out.println(String.format("\n ***** Game is over and winner is %s *****", playerOf(playId)));
                System.out.println("Play again? (input y/Y if yes)");
                if (in.next().equalsIgnoreCase("y")) {
                    game.clean();
                    System.out.println(game.toString());
                    continue;
                }
                System.out.println("Bye. Guys");
                break;
            }
        }
    }
}
