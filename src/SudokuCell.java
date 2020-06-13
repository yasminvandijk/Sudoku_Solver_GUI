import java.util.ArrayList;

public class SudokuCell {

    public final int Row;
    public final int Column;
    public final int Block;

    private int _value;
    private ArrayList<Integer> _possibleValues;

    public SudokuCell(int row, int column, int value) {
        
        Row = row;
        Column = column;
        Block = (int)(3 * Math.floor(row / 3.0) + Math.floor(column / 3.0));

        _value = value;
        _possibleValues = new ArrayList<Integer>();

        if (value == 0) {
            for (int i = 1; i <= 9; i++) {
                _possibleValues.add(i);
            }
        }
    }

    public int GetValue() {
        
        return _value;
    }

    public void SetValue(int value) {
        
        if (_possibleValues.contains(value)) {
            _value = value;

            _possibleValues.removeIf(v -> v != value);
        }
    }

    public boolean IsValuePossible(int value) {
        
        return _possibleValues.contains(value);
    }

    public int GetNrPossibleValues() {
        
        return _possibleValues.size();
    }

    public int RemoveFromPossibleValues(int value) {
        
        if (_possibleValues.contains(value)) {

            _possibleValues.removeIf(v -> v == value);

            if (_possibleValues.size() == 1) {
                _value = _possibleValues.get(0);
            }

            return 1;
        }
        else {
            return 0;
        }
    }
}