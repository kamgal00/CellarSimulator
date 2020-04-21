package Cellar.Model;

import Cellar.Model.Field;
import Cellar.Model.Level;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class PathFinder {
    static ArrayList<Pair<Integer,Integer>> it;
    public static void loadPathFinder()
    {
        it=new ArrayList<>();
        it.add(new Pair<>(1,0));
        it.add(new Pair<>(-1,0));
        it.add(new Pair<>(0,1));
        it.add(new Pair<>(0,-1));
        it.add(new Pair<>(1,1));
        it.add(new Pair<>(1,-1));
        it.add(new Pair<>(-1,1));
        it.add(new Pair<>(-1,-1));
    }
    static class Node
    {
        Node parent;
        Pair<Integer,Integer> coordinates;
        public Node(Node p, Pair<Integer,Integer> x)
        {
            parent=p;
            coordinates=x;
        }
    } 
    public static ArrayList<Pair<Integer,Integer>> findPath(boolean discoveredMatters,Level world,Pair<Integer,Integer> begin, Pair<Integer,Integer> end)
    {
        if(begin==null||end==null||world==null) return null;
        if(begin.getKey()<0||begin.getKey()>=Model.levelSize*Model.roomSize||begin.getValue()<0||begin.getValue()>=Model.levelSize*Model.roomSize) return null;
        if(end.getKey()<0||end.getKey()>=Model.levelSize*Model.roomSize||end.getValue()<0||end.getValue()>=Model.levelSize*Model.roomSize) return null;
        Set<Pair<Integer,Integer>> visited = new HashSet<>();
        visited.add(end);
        ArrayList<Node> q = new ArrayList<>();
        q.add(new Node(null,end));
        Node a=null;
        Node temp=null;
        loop:
        while(!q.isEmpty())
        {
            a = q.get(0);
            q.remove(0);
            if(a.coordinates.equals(begin)) break;
            //System.out.println(a.coordinates);
            for(int k=0;k<it.size();k++)
            {
                int i=it.get(k).getKey();
                int j=it.get(k).getValue();
                temp = new Node(a,new Pair<Integer,Integer>(a.coordinates.getKey()+i,a.coordinates.getValue()+j));
                if(temp.coordinates.getKey()<0||temp.coordinates.getKey()>=Model.levelSize*Model.roomSize) continue;
                if(temp.coordinates.getValue()<0||temp.coordinates.getValue()>=Model.levelSize*Model.roomSize) continue;
                if(visited.contains(temp.coordinates)) continue ;
                visited.add(temp.coordinates);
                if(world.field[temp.coordinates.getKey()][temp.coordinates.getValue()].getType()== Field.TypeOfField.wall) continue ;
                if(discoveredMatters&&!world.field[temp.coordinates.getKey()][temp.coordinates.getValue()].discovered) continue ;
                q.add(temp);
            }
        }
        if(a==null||!a.coordinates.equals(begin)) return null;
        ArrayList<Pair<Integer,Integer>> path=new ArrayList<>();
        a=a.parent;
        while(a!=null)
        {
            path.add(a.coordinates);
            a=a.parent;
        }
        return path;
    }
}
