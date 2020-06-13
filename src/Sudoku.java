import java.util.ArrayList;

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

    /** check for all rows if all nonzero values are distinct */
    private boolean AreRowsValid() {

        for (int y = 0; y < 9; y++) {            
            boolean[] values = new boolean[9];

            for (int x = 0; x < 9; x++) {
                int value = GetValue(x, y);

                if (value > 0 && value <= 9) {
                    if (values[value - 1]) {
                        return false;
                    }

                    values[value - 1] = true;
                }
            }
        }

        return true;
    }

    /** check for all columns if all nonzero values are distinct */
    private boolean AreColumnsValid() {

        for (int x = 0; x < 9; x++) {            
            boolean[] values = new boolean[9];

            for (int y = 0; y < 9; y++) {
                int value = GetValue(x, y);

                if (value > 0 && value <= 9) {
                    if (values[value - 1]) {
                        return false;
                    }

                    values[value - 1] = true;
                }
            }
        }

        return true;
    }

    /** check for all blocks if all nonzero values are distinct */
    private boolean AreBlocksValid() {

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {

                boolean[] values = new boolean[9];

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {

                        int value = GetValue(3 * x + i, 3 * y + j);

                        if (value > 0 && value <= 9) {
                            if (values[value - 1]) {
                                return false;
                            }

                            values[value - 1] = true;
                        }
                    }
                }
            }
        }

        return true;
    }

    /** check if nonzero values on the same row, column or block are distinct */
    private boolean IsSudokuValid() {

        return AreRowsValid() && AreColumnsValid() && AreBlocksValid();
    }

    /** remove value from possible values in all cells and return the number of updates */
    private int UpdatePossibleValues(ArrayList<SudokuCell> cells, int value) {

        int updates = 0;

        for (int i = 0; i < cells.size(); i++) {

            SudokuCell cell = cells.get(i);

            updates += cell.RemoveFromPossibleValues(value);
        }

        return updates;
    }

    /** fill in missing values if possible and return the number of updates */
    private int FillInMissingValues(ArrayList<SudokuCell> cells) {

        int updates = 0;

        // find all missing values
        ArrayList<Integer> missingValues = new ArrayList<Integer>();
        for (int i = 1; i <= 9; i++) { missingValues.add(i); }

        cells.forEach(cell -> {
            missingValues.removeIf(v -> v == cell.GetValue());
        });

        // find possible values for missing values
        for (int i = 0; i < missingValues.size(); i++) {

            int value = missingValues.get(i);

            ArrayList<SudokuCell> possibleCells = new ArrayList<SudokuCell>();

            cells.forEach(cell -> {

                if (cell.IsValuePossible(value)) {
                    possibleCells.add(cell);
                }
            });

            if (possibleCells.size() == 1) {
                possibleCells.get(0).SetValue(value);
                updates++;
            }
        }

        return updates;
    }

    /** try to solve the sudoku */
    public boolean Solve() {

        if (!IsSudokuValid()) {
            return false;
        }

        ArrayList<ArrayList<SudokuCell>> rows = new ArrayList<ArrayList<SudokuCell>>(9);
        ArrayList<ArrayList<SudokuCell>> columns = new ArrayList<ArrayList<SudokuCell>>(9);
        ArrayList<ArrayList<SudokuCell>> blocks = new ArrayList<ArrayList<SudokuCell>>(9);

        ArrayList<SudokuCell> fixedCells = new ArrayList<SudokuCell>();
        ArrayList<SudokuCell> emptyCells = new ArrayList<SudokuCell>();

        for (int i = 0; i < 9; i++) {
            rows.add(new ArrayList<SudokuCell>());
            columns.add(new ArrayList<SudokuCell>());
            blocks.add(new ArrayList<SudokuCell>());
        }

        // add cells to rows, columns, blocks and to empty or fixed cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                SudokuCell cell = new SudokuCell(i, j, GetValue(i, j));

                rows.get(cell.Row).add(cell);
                columns.get(cell.Column).add(cell);
                blocks.get(cell.Block).add(cell);

                if (GetValue(i, j) == 0) {
                    emptyCells.add(cell);
                }
                else {
                    fixedCells.add(cell);
                }
            }
        }

        // eliminate fixed cell values from possible values in cells on same rows, columns or blocks
        fixedCells.forEach(fixedCell -> {

            UpdatePossibleValues(rows.get(fixedCell.Row), fixedCell.GetValue());
            UpdatePossibleValues(columns.get(fixedCell.Column), fixedCell.GetValue());
            UpdatePossibleValues(blocks.get(fixedCell.Block), fixedCell.GetValue());
        });

        while (emptyCells.size() > 0) {

            int updates = 0;

            // get all cells from empty cells that now have a value
            for (int i = 0; i < emptyCells.size(); i++) {

                SudokuCell cell = emptyCells.get(i);

                if (cell.GetValue() != 0) {
                    fixedCells.add(cell);
                    SetValue(cell.Row, cell.Column, cell.GetValue());
                    updates += UpdatePossibleValues(rows.get(cell.Row), cell.GetValue());
                    updates += UpdatePossibleValues(columns.get(cell.Column), cell.GetValue());
                    updates += UpdatePossibleValues(blocks.get(cell.Block), cell.GetValue());
                }
            }

            emptyCells.removeIf(c -> fixedCells.contains(c));

            // fill in missing values in rows, columns and blocks
            for (int i = 0; i < 9; i++) {

                updates += FillInMissingValues(rows.get(i));
                updates += FillInMissingValues(columns.get(i));
                updates += FillInMissingValues(blocks.get(i));
            }

            if (updates == 0) {
                break;
            }
        }

        // check if sudoku is solved
        if (emptyCells.size() == 0) {
            return IsSudokuValid();
        }
        else {
            //TODO: find the empty cell with the least number of possible values
            return false;
        }
    }
}