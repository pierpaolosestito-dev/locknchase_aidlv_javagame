
package application.algorithm;
import application.model.Coordinates;
import java.util.LinkedList;
import java.util.Queue;
class QItem {
  int row;
  int col;
  int dist;
  public QItem(int row, int col, int dist)
  {
    this.row = row;
    this.col = col;
    this.dist = dist;
  }
}
public class BFSCalculator {
    public static int minDistance(char[][] grid,char destinazione,Coordinates sourceCoordinates){
        
    QItem source = new QItem(sourceCoordinates.getX(), sourceCoordinates.getY(), 0);
     
    // applying BFS on matrix cells starting from source
    Queue<QItem> queue = new LinkedList<>();
    queue.add(new QItem(source.row, source.col, 0));
 
    boolean[][] visited
      = new boolean[grid.length][grid[0].length];
    visited[source.row][source.col] = true;
 
    while (queue.isEmpty() == false) {
      QItem p = queue.remove();
       
      // Destination found;
      if (grid[p.row][p.col] == destinazione)
        return p.dist;
 
      // moving up
      if (isValid(p.row - 1, p.col, grid, visited)) {
        queue.add(new QItem(p.row - 1, p.col,
                            p.dist + 1));
        visited[p.row - 1][p.col] = true;
      }
 
      // moving down
      if (isValid(p.row + 1, p.col, grid, visited)) {
        queue.add(new QItem(p.row + 1, p.col,
                            p.dist + 1));
        visited[p.row + 1][p.col] = true;
      }
 
      // moving left
      if (isValid(p.row, p.col - 1, grid, visited)) {
        queue.add(new QItem(p.row, p.col - 1,
                            p.dist + 1));
        visited[p.row][p.col - 1] = true;
      }
 
      // moving right
      if (isValid(p.row, p.col + 1, grid,
                  visited)) {
        queue.add(new QItem(p.row, p.col + 1,
                            p.dist + 1));
        visited[p.row][p.col + 1] = true;
      }
    }
    return -1;
  }
    public static int minDistanceWithDestinationCoordinates(char[][] grid,Coordinates destinazione,Coordinates sourceCoordinates){
        
        QItem source = new QItem(sourceCoordinates.getX(), sourceCoordinates.getY(), 0);
         
        // applying BFS on matrix cells starting from source
        Queue<QItem> queue = new LinkedList<>();
        queue.add(new QItem(source.row, source.col, 0));
     
        boolean[][] visited
          = new boolean[grid.length][grid[0].length];
        visited[source.row][source.col] = true;
     
        while (queue.isEmpty() == false) {
          QItem p = queue.remove();
           
          // Destination found;
          if (p.row == destinazione.getX() && p.col == destinazione.getX())
            return p.dist;
     
          // moving up
          if (isValid(p.row - 1, p.col, grid, visited)) {
            queue.add(new QItem(p.row - 1, p.col,
                                p.dist + 1));
            visited[p.row - 1][p.col] = true;
          }
     
          // moving down
          if (isValid(p.row + 1, p.col, grid, visited)) {
            queue.add(new QItem(p.row + 1, p.col,
                                p.dist + 1));
            visited[p.row + 1][p.col] = true;
          }
     
          // moving left
          if (isValid(p.row, p.col - 1, grid, visited)) {
            queue.add(new QItem(p.row, p.col - 1,
                                p.dist + 1));
            visited[p.row][p.col - 1] = true;
          }
     
          // moving right
          if (isValid(p.row, p.col + 1, grid,
                      visited)) {
            queue.add(new QItem(p.row, p.col + 1,
                                p.dist + 1));
            visited[p.row][p.col + 1] = true;
          }
        }
        return -1;
      }
     private static boolean isValid(int x, int y,
                                 char[][] grid,
                                 boolean[][] visited)
  {
    if (x >= 0 && y >= 0 && x < grid.length
        && y < grid[0].length && grid[x][y] != 'W'
        && visited[x][y] == false) {
      return true;
    }
    return false;
  }
}
