package Cellar.Model;

import Cellar.Model.Rooms.EmptyRoom;

import java.util.ArrayList;
import java.util.Random;

import static Cellar.Model.Model.*;

public class LevelGenerator {
    Level gen=new Level(levelSize, levelSize);
    Random rand = new Random();
    RoomGenerator roomGenerator=new RoomGenerator();
    int numberOfRooms=0;
    Room[][] rooms=new Room[levelSize][levelSize]; //rooms[m][n] -- m-row, n-column
    ArrayList<Room> roomList=new ArrayList<>();

    public Level levelGenerate(){

        //inserting rooms
        for(int i=0; i<levelSize; i++){ //by row
            for (int j=0; j<levelSize; j++){ //by column
                rooms[i][j]=new EmptyRoom();
            }
        }

        while(numberOfRooms<=5){
            for(int i=0; i<levelSize; i++){ //by row
                for (int j=0; j<levelSize; j++){ //by column
                    if(rand.nextInt(100)<40 && rooms[i][j].equals(new EmptyRoom())){
                        rooms[i][j]=roomGenerator.nextRoom();
                        rooms[i][j].id=numberOfRooms;
                        rooms[i][j].connected.add(numberOfRooms);
                        roomList.add(rooms[i][j]);
                        numberOfRooms++;
                    }
                }
            }
        }

        //connecting with corridors
        while(numberOfRooms>roomList.get(0).connected.size()){
            int first=rand.nextInt(numberOfRooms);
            int second=rand.nextInt(numberOfRooms);
            while(second==first){ second=rand.nextInt(numberOfRooms);}
            int firstX=-1, firstY=-1, secondX=-1, secondY=-1;

            //finding coordinates
            for(int i=0; i<levelSize; i++){ //by row
                for (int j=0; j<levelSize; j++){ //by column
                    if(rooms[i][j].id==first){
                        firstY=i;
                        firstX=j;
                    }
                    if(rooms[i][j].id==second){
                        secondY=i;
                        secondX=j;
                    }
                }
            }

            generateCorridors(first, firstX, firstY, second, secondX, secondY);
            roomList.get(first).connect(roomList.get(second));

        }

        //fuse to array
        gen.fuse(rooms);
        for(int i=0; i<77; i++){
            for(int j=0; j<77; j++){
                if(gen.field[i][j].getType()==Field.TypeOfField.wall){System.out.print(" ");}
                else{System.out.print("X");}
            }
            System.out.println(" ");
        }
        return gen;
    }

    void generateCorridors(int first, int firstX, int firstY, int second, int secondX, int secondY){
        int fieldX, fieldY, roomX, roomY;
        roomX=firstX; roomY=firstY;
        fieldX=(roomSize+1)/2; fieldY=(roomSize+1)/2;
        Room currentRoom=roomList.get(first);

        //if second is on the left
        if(firstX>secondX){
            for(int i=0; i<roomSize; i++){
                if(currentRoom.fields[(roomSize+1)/2][i]!=null && currentRoom.fields[(roomSize+1)/2][i].getType()!=Field.TypeOfField.wall){
                    fieldY=(roomSize+1)/2;
                    fieldX=i;
                    break;
                }
            }
            //fill room with corridor
            while(fieldX>0){
                fieldX--;
                currentRoom.fields[fieldY][fieldX]=new Field(Field.TypeOfField.corridor);
            }

            //going left
            while(roomX!=secondX){
                roomX--;
                currentRoom=rooms[roomY][roomX];
                //if met room on the way
                if(!currentRoom.equals(new EmptyRoom())){
                    fieldX=roomSize-1;
                    while(currentRoom.fields[fieldY][fieldX]==null || currentRoom.fields[fieldY][fieldX].getType()==Field.TypeOfField.wall){
                        currentRoom.fields[fieldY][fieldX]=new Field(Field.TypeOfField.corridor);
                        fieldX--;
                    }
                    roomList.get(first).connect(currentRoom);
                    return;
                }
                //if theres no room
                for(int i=0; i<roomSize; i++){
                    currentRoom.fields[fieldY][i]=new Field(Field.TypeOfField.corridor);
                }
            }

            //filling to center
            currentRoom=rooms[roomY][roomX];
            //if met room on the way
            if(!currentRoom.equals(new EmptyRoom())){
                fieldX=roomSize-1;
                while(currentRoom.fields[fieldY][fieldX]==null || currentRoom.fields[fieldY][fieldX].getType()==Field.TypeOfField.wall){
                    currentRoom.fields[fieldY][fieldX]=new Field(Field.TypeOfField.corridor);
                    fieldX--;
                }
                roomList.get(first).connect(currentRoom);
                return;
            }
            for(int i=roomSize-1; i<=fieldY; i--){
                currentRoom.fields[fieldY][i]=new Field(Field.TypeOfField.corridor);
            }
        }

        //if second is on the right
        if(firstX<secondX){
            for(int i=roomSize-1; i>=0; i--){
                if(currentRoom.fields[(roomSize+1)/2][i]!=null && currentRoom.fields[(roomSize+1)/2][i].getType()!=Field.TypeOfField.wall){
                    fieldY=(roomSize+1)/2;
                    fieldX=i;
                    break;
                }
            }
            //fill room with corridor
            while(fieldX<roomSize-1){
                fieldX++;
                currentRoom.fields[fieldY][fieldX]=new Field(Field.TypeOfField.corridor);
            }

            //going right
            while(roomX!=secondX){
                roomX++;
                currentRoom=rooms[roomY][roomX];
                //if met room on the way
                if(!currentRoom.equals(new EmptyRoom())){
                    fieldX=0;
                    while(currentRoom.fields[fieldY][fieldX]==null || currentRoom.fields[fieldY][fieldX].getType()==Field.TypeOfField.wall){
                        currentRoom.fields[fieldY][fieldX]=new Field(Field.TypeOfField.corridor);
                        fieldX++;
                    }
                    roomList.get(first).connect(currentRoom);
                    return;
                }
                //if theres no room
                for(int i=0; i<roomSize; i++){
                    currentRoom.fields[fieldY][i]=new Field(Field.TypeOfField.corridor);
                }
            }

            //filling to center
            currentRoom=rooms[roomY][roomX];
            //if met room on the way
            if(!currentRoom.equals(new EmptyRoom())){
                fieldX=0;
                while(currentRoom.fields[fieldY][fieldX]==null || currentRoom.fields[fieldY][fieldX].getType()==Field.TypeOfField.wall){
                    currentRoom.fields[fieldY][fieldX]=new Field(Field.TypeOfField.corridor);
                    fieldX++;
                }
                roomList.get(first).connect(currentRoom);
                return;
            }
            for(int i=0; i<=fieldY; i++){
                currentRoom.fields[fieldY][i]=new Field(Field.TypeOfField.corridor);
            }
        }

        //if second is in the same column
        if(firstX==secondX){
            //if second is below
            if(firstY<secondY){
                for(int i=roomSize-1; i>=0; i--){
                    if(currentRoom.fields[i][fieldX]!=null && currentRoom.fields[i][fieldX].getType()!=Field.TypeOfField.wall){
                        fieldX=(roomSize+1)/2;
                        fieldY=i;
                        break;
                    }
                }
                //fill room with corridor
                while(fieldY<roomSize-1){
                    fieldY++;
                    currentRoom.fields[fieldY][fieldX]=new Field(Field.TypeOfField.corridor);
                }
            }

            //if second is above
            if(firstY>secondY){
                for(int i=0; i<roomSize; i++){
                    if(currentRoom.fields[i][fieldX]!=null && currentRoom.fields[i][fieldX].getType()!=Field.TypeOfField.wall){
                        fieldX=(roomSize+1)/2;
                        fieldY=i;
                        break;
                    }
                }
                //fill room with corridor
                while(fieldY>0){
                    fieldY--;
                    currentRoom.fields[fieldY][fieldX]=new Field(Field.TypeOfField.corridor);
                }
            }
        }

        //if second is below
        if(firstY<secondY){

        }

        //if second is above
        if(firstY>secondY){

        }
    }
}
