package sudoku.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SudokuGame {
	
	private int[][] solutionBoard;
	private int[][] gameBoard;

	public SudokuGame() {
		newGame();
	}
	
	private void newGame() {
		this.solutionBoard = createSolution(new int[9][9], 0);
		this.gameBoard = createGame(deepCopy(this.solutionBoard));
	}

	private int[][] createSolution(int[][] board, int index) {

		if (index > 80) {
			return board;
		} else {
			int indexX = index % 9;
			int indexY = index / 9;

			List<Integer> options = new ArrayList<Integer>();

			for (int z = 1; z <= 9; z++) {
				options.add(z);
			}
			Collections.shuffle(options);

			while (options.size() > 0) {
				int number = nextNumber(board, indexX, indexY, options);

				if (number != -1) {
					board[indexY][indexX] = number;
					int[][] newBoard = createSolution(board, index++);
					if (newBoard != null) {
						return newBoard;
					}
					board[indexY][indexX] = 0;
				} else {
					return null;
				}
			}

		}
		return null;
	}

	private int nextNumber(int[][] board, int indexX, int indexY, List<Integer> options) {
		while (options.size() > 0) {
			int number = options.remove(0);
			if (isSafe(number, indexX, indexY, board)) {
				return number;
			}
		}

		return -1;
	}

	private boolean isSafe(int number, int indexX, int indexY, int[][] board) {
		return isSafeX(board, indexY, number) && isSafeY(board, indexX, number)
				&& isSafeBox(board, indexX, indexY, number);
	}

	private boolean isSafeBox(int[][] board, int indexX, int indexY, int number) {
		int x1, y1;

		if (indexX < 3) {
			x1 = 0;
		} else if (indexX < 6) {
			x1 = 3;
		} else {
			x1 = 6;
		}

		if (indexY < 3) {
			y1 = 0;
		} else if (indexY < 6) {
			y1 = 3;
		} else {
			y1 = 6;
		}

		for (int yy = y1; yy < y1 + 3; yy++) {
			for (int xx = x1; xx < x1 + 3; xx++) {
				if (board[yy][xx] == number) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isSafeY(int[][] board, int indexX, int number) {
		for (int y = 0; y < 9; y++) {
			if (board[y][indexX] == number) {
				return false;
			}
		}
		return true;
	}

	private boolean isSafeX(int[][] board, int indexY, int number) {
		for (int x = 0; x < 9; x++) {
			if (board[indexY][x] == number) {
				return false;
			}
		}
		return true;
	}

	private int[][] createGame(int[][] board) {
		List<Integer> positions = new ArrayList<Integer>();
		for (int i = 0; i < 81; i++) {
			positions.add(i);
		}
		Collections.shuffle(positions);

		return createGameBackend(board, positions);
	}

	private int[][] createGameBackend(int[][] board, List<Integer> positions) {
		while (positions.size() > 0) {
			int position = positions.remove(0);
			int indexX = position % 9;
			int indexY = position / 9;

			int savedValue = board[indexY][indexX];
			board[indexY][indexX] = 0;

			if (!isValid(board)) {
				board[indexY][indexX] = savedValue;
			}
		}

		return board;
	}

	private boolean isValid(int[][] board) {
		return isValidBackend(board, 0, new int[] { 0 });
	}

	private boolean isValidBackend(int[][] board, int index, int[] numberOfSolutions) {
		if (index > 80) {
			return ++numberOfSolutions[0] == 1;
		} else {
			int indexX = index % 9;
			int indexY = index / 9;

			if (board[indexY][indexX] == 0) {
				List<Integer> numbers = new ArrayList<Integer>();
				for (int i = 1; i <= 9; i++) {
					numbers.add(i);
				}

				while (numbers.size() > 0) {
					int number = nextNumber(board, indexX, indexY, numbers);

					if (number == -1) {
						break;
					} else {
						board[indexY][indexX] = number;
					}

					if (!isValidBackend(board, index + 1, numberOfSolutions)) {
						board[indexY][indexX] = 0;
						return false;
					} else {
						board[indexY][indexX] = 0;
					}

				}
			}else if(!isValidBackend(board, index + 1, numberOfSolutions)) {
				return false;
			}
		}
		
		return true;
	}
	
	private int[][] deepCopy(int[][] array){
		int[][] copy = new int[array.length][array[0].length];
		
		for(int y=0;y<9;y++) {
			for(int x =0;x<9;x++) {
				copy[y][x] = array[y][x];
			}
		}
		
		return copy;
	}

}
