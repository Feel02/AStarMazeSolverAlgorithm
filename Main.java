/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{   
            Maze mazeFromText = new Maze("./maze.txt");
            Maze mazeFromText2 = new Maze("./maze2.txt");
            
            //BFS_Solver bfs = new BFS_Solver(mazeFromText);
            //DFS_Solver dfs = new DFS_Solver(mazeFromText);
            AStarSolver astar = new AStarSolver(mazeFromText, true);
            AStarSolver astar_euclid = new AStarSolver(mazeFromText, false);
            
            //BFS_Solver bfs2 = new BFS_Solver(mazeFromText2);
            //DFS_Solver dfs2 = new DFS_Solver(mazeFromText2);
            AStarSolver astar2 = new AStarSolver(mazeFromText2, true);
            AStarSolver astar2_euclid = new AStarSolver(mazeFromText2, false);
                
            //String bfs_res = bfs.solve();
            //String dfs_res = dfs.solve();
            String astar_res = astar.solve();
            String astar_res_euclid = astar_euclid.solve();
            
            //String bfs_res2 = bfs2.solve();
            //String dfs_res2 = dfs2.solve();
            String astar_res2 = astar2.solve();
            String astar_res2_euclid = astar2_euclid.solve();
                
            //Path bfs_path = Paths.get("./bfs.txt");
            //Path dfs_path = Paths.get("./dfs.txt");
            Path astar_path = Paths.get("./astar.txt");
            Path astar_path_euclid = Paths.get("./astar_euclid.txt");
            
            //Path bfs_path2 = Paths.get("./bfs2.txt");
            //Path dfs_path2 = Paths.get("./dfs2.txt");
            Path astar_path2 = Paths.get("./astar2.txt");
            Path astar_path2_euclid = Paths.get("./astar2_euclid.txt");
            
            try 
            {
                // Now calling Files.writeString() method
                // with path , content & standard charsets
                //Files.writeString(bfs_path, bfs_res, StandardCharsets.UTF_8);
                //Files.writeString(dfs_path, dfs_res, StandardCharsets.UTF_8);
                Files.writeString(astar_path, astar_res, StandardCharsets.UTF_8);
                Files.writeString(astar_path_euclid, astar_res_euclid, StandardCharsets.UTF_8);
                
                //Files.writeString(bfs_path2, bfs_res2, StandardCharsets.UTF_8);
                //Files.writeString(dfs_path2, dfs_res2, StandardCharsets.UTF_8);
                Files.writeString(astar_path2, astar_res2, StandardCharsets.UTF_8);
                Files.writeString(astar_path2_euclid, astar_res2_euclid, StandardCharsets.UTF_8);
            }            
            // Catch block to handle the exception
            catch (IOException ex) 
            {
                // Print messqage exception occurred as
                // invalid. directory local path is passed
                System.out.print("Invalid Path");
            }
            
            System.out.println("Completed!");
	}
}

