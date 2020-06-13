# Sudoku_Solver_GUI

## graphical user interface

![solver gui](images/solver_gui.png)
![solver gui - invalid input](images/solver_gui_invalid_input.png)
![solver gui - solved sudoku](images/solver_gui_solved.png)

## sudoku solver

- uses values from filled in cells to eliminate possible values from empty cells on the same row, column or block
- fill in missing values in a row, column or block by checking in which cells the missing values can be filled in
- when no more values can be filled in using the above methods backtracking is used