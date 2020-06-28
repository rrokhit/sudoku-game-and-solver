package sudoku.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SudokuGame {

	public SudokuGame() {

	}

	public int[][] createSolution(int[][] board, int index) {

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
		return isSafeX(board, indexY, number) && isSafeY(board, indexX, number) && isSafeBox(board, indexX, indexY, number);
	}

	private boolean isSafeBox(int[][] board, int indexX, int indexY, int number) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isSafeY(int[][] board, int indexX, int number) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isSafeX(int[][] board, int indexY, int number) {
		for(int x = 0 ; x<9;x++) {
			if(board[indexY][x] == number) {
				return false;
			}
		}
		return true;
	}

}
