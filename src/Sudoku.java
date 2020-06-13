

public class Sudoku {

    private final int[][] _values = new int[9][9];
    
    /** set value for a specific sudoku cell */
    public void SetValue(int row, int column, int value) {
        
        if (row < 0 || row > 8
            || column < 0 || column > 8
            || value < 0 || value > 9) {
                return;
            }

        _values[row][column] = value;
    }

    /** get value of a specific sudoku cell */
    public int GetValue(int row, int column) {

        if (row < 0 || row > 8
            || column < 0 || column > 8) {
                return 0;
            }
        
        return _values[row][column];
    }
}