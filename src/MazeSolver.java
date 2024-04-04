/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam and Annie Virsik
 * @version 03/10/2023
 *
 * 4/3/24
 */

import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * The parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        ArrayList<MazeCell> cellArrayList = new ArrayList<MazeCell>();
        Stack<MazeCell> cellStack = new Stack<MazeCell>();
        // Keeps track of current cell and adds it to the stack
        MazeCell current = maze.getEndCell();
        cellStack.push(current);
        // While current cell is not equal to start cell, set current cell equal to parent and add the current cell to the stack
        while (current != maze.getStartCell()) {
            current = current.getParent();
            cellStack.add(current);
        }
        // While there are still values in the stack, add the last thing to the ArrayList
        while (!cellStack.isEmpty()) {
            cellArrayList.add(cellStack.pop());
        }
        return cellArrayList;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        Stack<MazeCell> cellsVisited = new Stack<MazeCell>();
        MazeCell current = maze.getStartCell();
        current.setExplored(true);
        while (current != maze.getEndCell()) {
            // North
            moveCell(current.getRow() - 1, current.getCol(), cellsVisited, current);
            // East
            moveCell(current.getRow(), current.getCol() + 1, cellsVisited, current);
            // South
            moveCell(current.getRow() + 1, current.getCol(), cellsVisited, current);
            // West
            moveCell(current.getRow(), current.getCol() - 1, cellsVisited, current);
            // Sets the current cell equal to the new cell
            current = cellsVisited.pop();
        }
        return getSolution();
    }

    /**
     * If there is a valid cell North, East, South, or West,
     * Update parents, add to stack, and set explored
     *
     * @param row
     * @param col
     * @param cellsVisited
     * @param current
     */
    public void moveCell(int row, int col, Stack<MazeCell> cellsVisited, MazeCell current) {
        if (maze.isValidCell(row, col)) {
            MazeCell newCell = maze.getCell(row, col);
            // Current cell is the parent of the new cell
            newCell.setParent(current);
            // Adds next cell to cellsVisited stack and it is explored
            cellsVisited.push(newCell);
            newCell.setExplored(true);
        }
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Queue<MazeCell> cellsVisited = new LinkedList<MazeCell>();
        MazeCell current = maze.getStartCell();
        current.setExplored(true);
        while (current != maze.getEndCell()) {
            // North
            moveCell(current.getRow() - 1, current.getCol(), cellsVisited, current);
            // East
            moveCell(current.getRow(), current.getCol() + 1, cellsVisited, current);
            // South
            moveCell(current.getRow() + 1, current.getCol(), cellsVisited, current);
            // West
            moveCell(current.getRow(), current.getCol() - 1, cellsVisited, current);
            current = cellsVisited.remove();
        }
        return getSolution();
    }


    /**
     * If there is a valid cell North, East, South, or West,
     * Update parents, add to Queue, and set explored
     *
     * @param row row of the cell
     * @param col
     * @param cellsVisited
     * @param current
     */
    public void moveCell(int row, int col, Queue<MazeCell> cellsVisited, MazeCell current) {
        if (maze.isValidCell(row, col)) {
            MazeCell newCell = maze.getCell(row, col);
            // Current cell is a parent
            newCell.setParent(current);
            // Adds next cell to cellsVisited stack and it is explored
            cellsVisited.add(newCell);
            newCell.setExplored(true);
        }
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
