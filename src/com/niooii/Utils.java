public class Utils {
    public void printGrid(boolean[][] grid){
        char x = 'X';
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(j == 0)
                    System.out.println("| ");
                if(grid[i][j])
                    x = '⬤';
                else
                    x = 'X';
            }
        }
    }
}
