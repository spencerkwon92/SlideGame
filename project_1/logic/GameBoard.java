package univ.major.project.assign.project_1.logic;

public class GameBoard {
    private Player[][] grid;
    private final int DIM = 5;
    private Player currentPlayer;

    public GameBoard() {
        grid = new Player[DIM][DIM];
        clear();
        currentPlayer = Player.X;
    }

    public void clear() {
        for (int i=0; i<DIM; i++) {
            for (int j=0; j<DIM; j++) {
                grid[i][j] = Player.BLANK;
            }
        }
    }

    public void submitMove(char move) {
        if (move >= '1' && move <= '5') {
            //vertical move, move stuff down
            int col = Integer.parseInt(""+move)-1;
            Player newVal = currentPlayer;
            for (int i=0; i<DIM; i++) {
                if (grid[i][col] == Player.BLANK) {
                    grid[i][col] = newVal;
                    break;
                } else {
                    Player tmp = grid[i][col];
                    grid[i][col] = newVal;
                    newVal = tmp;
                }
            }

        } else { //A-E
            //horizontal move, move stuff right
            int row = (int)(move - 'A');
            Player newVal = currentPlayer;
            for (int i=0; i<DIM; i++) {
                if (grid[row][i] == Player.BLANK) {
                    grid[row][i] = newVal;
                    break;
                } else {
                    Player tmp = grid[row][i];
                    grid[row][i] = newVal;
                    newVal = tmp;
                }
            }
        }
        if (currentPlayer == Player.X) {
            currentPlayer = Player.O;
        } else {
            currentPlayer = Player.X;
        }
    }

//    public Player aiObserber(){
//        int beforWin = DIM-2;
//
//        Player aiwin = Player.BLANK;
//        Player aiTmpWin;
//
//        //check all rows
//        for (int i=0; i<beforWin; ++i) {
//            if (grid[i][0] != Player.BLANK) {
//                aiTmpWin = grid[i][0];
//                for (int j=0; j<beforWin; ++j) {
//                    if (grid[i][j] != aiTmpWin) {
//                        aiTmpWin = Player.BLANK;
//                        break;
//                    }
//                }
//                if (aiTmpWin != Player.BLANK) {
//                    if (aiwin == Player.BLANK) {
//                        aiwin = aiTmpWin;
//                    } else {
//                        return Player.TIE;
//                    }
//                }
//            }
//        }
//
//        //check all columns
//        aiTmpWin = Player.BLANK;
//        for (int i=0; i<beforWin; ++i) {
//            if (grid[0][i] != Player.BLANK) {
//                aiTmpWin = grid[0][i];
//                for (int j=0; j<beforWin; ++j) {
//                    if (grid[j][i] != aiTmpWin) {
//                        aiTmpWin = Player.BLANK;
//                        break;
//                    }
//                }
//                if (aiTmpWin != Player.BLANK) {
//                    if (aiwin == Player.BLANK) {
//                        aiwin = aiTmpWin;
//                    } else {
//                        return Player.TIE;
//                    }
//                }
//            }
//        }
//
//        //at this point, either there's a tie, or there's not.
//        //You can't have a tie with diagonals.
//        if (aiwin != Player.BLANK) {
//            return aiwin;
//        }
//
//        //check top-left -> bottom-right diagonal
//        if (grid[0][0] != Player.BLANK) {
//            aiwin = grid[0][0];
//            for (int i=0; i<beforWin; ++i) {
//                if (grid[i][i] != aiwin) {
//                    aiwin = Player.BLANK;
//                    break;
//                }
//            }
//            if (aiwin != Player.BLANK) {
//                return aiwin; //5 in a diagonal!
//            }
//        }
//
//        //check bottom-left -> top-right diagonal
//        if (grid[beforWin-1][0] != Player.BLANK) {
//            aiwin = grid[beforWin-1][0];
//            for (int i=0; i<beforWin; ++i) {
//                if (grid[beforWin-1-i][i] != aiwin) {
//                    aiwin = Player.BLANK;
//                    break;
//                }
//            }
//            if (aiwin != Player.BLANK) {
//                return aiwin; //5 in a diagonal!
//            }
//        }
//        return aiwin;
//    }

    public Player checkForWin() {
        Player winner = Player.BLANK;
        Player tmpWinner;


        //check all rows
        for (int i=0; i<DIM; ++i) {
            if (grid[i][0] != Player.BLANK) {
                tmpWinner = grid[i][0];
                for (int j=0; j<DIM; ++j) {
                    if (grid[i][j] != tmpWinner) {
                        tmpWinner = Player.BLANK;
                        break;
                    }
                }
                if (tmpWinner != Player.BLANK) {
                    if (winner == Player.BLANK) {
                        winner = tmpWinner;
                    } else {
                        return Player.TIE;
                    }
                }
            }
        }

        //check all columns
        tmpWinner = Player.BLANK;
        for (int i=0; i<DIM; ++i) {
            if (grid[0][i] != Player.BLANK) {
                tmpWinner = grid[0][i];
                for (int j=0; j<DIM; ++j) {
                    if (grid[j][i] != tmpWinner) {
                        tmpWinner = Player.BLANK;
                        break;
                    }
                }
                if (tmpWinner != Player.BLANK) {
                    if (winner == Player.BLANK) {
                        winner = tmpWinner;
                    } else {
                        return Player.TIE;
                    }
                }
            }
        }

        //at this point, either there's a tie, or there's not.
        //You can't have a tie with diagonals.
        if (winner != Player.BLANK) {
            return winner;
        }

        //check top-left -> bottom-right diagonal
        if (grid[0][0] != Player.BLANK) {
            winner = grid[0][0];
            for (int i=0; i<DIM; ++i) {
                if (grid[i][i] != winner) {
                    winner = Player.BLANK;
                    break;
                }
            }
            if (winner != Player.BLANK) {
                return winner; //5 in a diagonal!
            }
        }

        //check bottom-left -> top-right diagonal
        if (grid[DIM-1][0] != Player.BLANK) {
            winner = grid[DIM-1][0];
            for (int i=0; i<DIM; ++i) {
                if (grid[DIM-1-i][i] != winner) {
                    winner = Player.BLANK;
                    break;
                }
            }
            if (winner != Player.BLANK) {
                return winner; //5 in a diagonal!
            }
        }

        return winner;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public char suggestNextMove() {
        //random algorithm
        char[] options = {'1', '2', '3', '4', '5', 'A', 'B', 'C', 'D', 'E'};
        int index = (int)(Math.random() * options.length);
        return options[index];
    }
}
