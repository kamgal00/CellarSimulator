package Cellar.Model;

import Cellar.Model.Rooms.EmptyRoom;

import java.util.ArrayList;
import java.util.Random;

import static Cellar.Model.Model.levelSize;
import static Cellar.Model.Model.roomSize;

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
                    if(rand.nextInt(100)<40 && rooms[i][j].equal(new EmptyRoom())){
                        rooms[i][j]=roomGenerator.nextRoom();
                        rooms[i][j].id=numberOfRooms;
                        rooms[i][j].connected.add(numberOfRooms);
                        roomList.add(rooms[i][j]);
                        numberOfRooms++;
                    }
                }
            }
        }

        //generating entrance and exit
        int entranceId=rand.nextInt(numberOfRooms);
        while(!roomList.get(entranceId).exitable){
            entranceId=rand.nextInt(numberOfRooms);
        }
        int exitId=rand.nextInt(numberOfRooms);
        Room curr=roomList.get(entranceId);
        while(!roomList.get(exitId).exitable){
            exitId=rand.nextInt(numberOfRooms);
        }
        while(exitId==entranceId){exitId=rand.nextInt(numberOfRooms);}

        //finding coordinates
        for(int i=0; i<levelSize; i++){ //by row
            for (int j=0; j<levelSize; j++){ //by column
                if(rooms[i][j].id==entranceId){
                    gen.entranceY=i;
                    gen.entranceX=j;
                }
                if(rooms[i][j].id==exitId){
                    gen.exitY=i;
                    gen.exitX=j;
                }
            }
        }

        //entrance
        boolean found=false;
        while(!found){
            found=true;
            int enx=rand.nextInt(roomSize-2)+1;
            int eny=rand.nextInt(roomSize-2)+1;
            //checking 3x3 square around
            for(int i=eny-1; i<=eny+1; i++){
                for(int j=enx-1; j<=enx+1; j++){
                    if(curr.fields[i][j]==null || curr.fields[i][j].getType()== Field.TypeOfField.wall){
                        found=false;
                    }
                }
            }
            if(found){
                gen.entranceY=gen.entranceY*roomSize+eny;
                gen.entranceX=gen.entranceX*roomSize+enx;
            }
        }

        //exit
        found=false;
        curr=roomList.get(exitId);
        while(!found){
            found=true;
            int enx=rand.nextInt(roomSize-2)+1;
            int eny=rand.nextInt(roomSize-2)+1;
            //checking 3x3 square around
            for(int i=eny-1; i<=eny+1; i++){
                for(int j=enx-1; j<=enx+1; j++){
                    if(curr.fields[i][j]==null || curr.fields[i][j].getType()== Field.TypeOfField.wall){
                        found=false;
                    }
                }
            }
            if(found){
                gen.exitY=gen.exitY*roomSize+eny;
                gen.exitX=gen.exitX*roomSize+enx;
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

            //System.out.println("first id:" + first + " " +firstY + " " +firstX+" second id:"+ second +" "+ secondY+" "+secondX);
            generateCorridors(first, firstX, firstY, second, secondX, secondY);
        }

        //fuse to array
        gen.fuse(rooms);
        gen.field[gen.entranceY][gen.entranceX]=new Field(Field.TypeOfField.entrance);
        gen.field[gen.exitY][gen.exitX]=new Field(Field.TypeOfField.exit);
        gen.playerX=gen.entranceX;
        gen.playerY=gen.entranceY;

        //printing in console
        for(int i=0; i<roomSize*levelSize; i++){
            for(int j=0; j<roomSize*levelSize; j++){
                if(gen.field[i][j].getType()== Field.TypeOfField.wall){System.out.print(" ");}
                else if(gen.field[i][j].getType()== Field.TypeOfField.corridor){System.out.print("O");}
                else if(gen.field[i][j].getType()== Field.TypeOfField.entrance){System.out.print(".");}
                else if(gen.field[i][j].getType()== Field.TypeOfField.exit){System.out.print("'");}
                else{System.out.print("X");}
            }
            System.out.println(" ");
        }
        return gen;
    }

    void generateCorridors(int first, int firstX, int firstY, int second, int secondX, int secondY){
        int fieldX, fieldY, roomX, roomY;
        roomX=firstX; roomY=firstY;
        fieldX=(roomSize)/2; fieldY=(roomSize)/2;
        Room currentRoom=roomList.get(first);

        //if second is on the left
        if(firstX>secondX){
            for(int i=0; i<roomSize; i++){
                if(currentRoom.fields[fieldY][i]!=null && currentRoom.fields[fieldY][i].getType()!= Field.TypeOfField.wall){
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
            roomX--;
            while(roomX!=secondX){
                currentRoom=rooms[roomY][roomX];
                //if met room on the way
                if(!currentRoom.equal(new EmptyRoom())){
                    fieldX=roomSize-1;
                    while(currentRoom.fields[fieldY][fieldX]==null || currentRoom.fields[fieldY][fieldX].getType()== Field.TypeOfField.wall){
                        currentRoom.fields[fieldY][fieldX]=new Field(Field.TypeOfField.corridor);
                        fieldX--;
                    }
                    connect(roomList.get(first), currentRoom);
                    return;
                }
                //if theres no room
                for(int i=0; i<roomSize; i++){
                    currentRoom.fields[fieldY][i]=new Field(Field.TypeOfField.corridor);
                }
                roomX--;
            }

            //filling to center
            currentRoom=rooms[roomY][roomX];
            //if met room on the way
            if(!currentRoom.equal(new EmptyRoom())){
                fieldX=roomSize-1;
                while(currentRoom.fields[fieldY][fieldX]==null || currentRoom.fields[fieldY][fieldX].getType()== Field.TypeOfField.wall){
                    currentRoom.fields[fieldY][fieldX]=new Field(Field.TypeOfField.corridor);
                    fieldX--;
                }
                connect(roomList.get(first), currentRoom);
                return;
            }
            for(int i=roomSize-1; i>=fieldY; i--){
                currentRoom.fields[fieldY][i]=new Field(Field.TypeOfField.corridor);
            }
            fieldX=roomSize/2;
            //if second is below
            if(firstY<secondY){
                for(int i=roomSize-1; i>fieldX; i--){
                    currentRoom.fields[i][fieldX]=new Field(Field.TypeOfField.corridor);
                }
            }

            //if second is above
            if(firstY>secondY){
                for(int i=0; i<fieldX; i++){
                    currentRoom.fields[i][fieldX]=new Field(Field.TypeOfField.corridor);
                }
            }
        }

        //if second is on the right
        if(firstX<secondX){
            for(int i=roomSize-1; i>=0; i--){
                if(currentRoom.fields[fieldY][i]!=null && currentRoom.fields[fieldY][i].getType()!= Field.TypeOfField.wall){
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
            roomX++;
            while(roomX!=secondX){
                currentRoom=rooms[roomY][roomX];
                //if met room on the way
                if(!currentRoom.equal(new EmptyRoom())){
                    fieldX=0;
                    while(currentRoom.fields[fieldY][fieldX]==null || currentRoom.fields[fieldY][fieldX].getType()== Field.TypeOfField.wall){
                        currentRoom.fields[fieldY][fieldX]=new Field(Field.TypeOfField.corridor);
                        fieldX++;
                    }
                    connect(roomList.get(first), currentRoom);
                    return;
                }
                //if theres no room
                for(int i=0; i<roomSize; i++){
                    currentRoom.fields[fieldY][i]=new Field(Field.TypeOfField.corridor);
                }
                roomX++;
            }

            //filling to center
            currentRoom=rooms[roomY][roomX];
            //if met room on the way
            if(!currentRoom.equal(new EmptyRoom())){
                fieldX=0;
                while(currentRoom.fields[fieldY][fieldX]==null || currentRoom.fields[fieldY][fieldX].getType()== Field.TypeOfField.wall){
                    currentRoom.fields[fieldY][fieldX]=new Field(Field.TypeOfField.corridor);
                    fieldX++;
                }
                connect(roomList.get(first), currentRoom);
                return;
            }
            for(int i=0; i<=fieldY; i++){
                currentRoom.fields[fieldY][i]=new Field(Field.TypeOfField.corridor);
            }
            fieldX=roomSize/2;
            //if second is below
            if(firstY<secondY){
                for(int i=roomSize-1; i>fieldX; i--){
                    currentRoom.fields[i][fieldX]=new Field(Field.TypeOfField.corridor);
                }
            }

            //if second is above
            if(firstY>secondY){
                for(int i=0; i<fieldX; i++){
                    currentRoom.fields[i][fieldX]=new Field(Field.TypeOfField.corridor);
                }
            }
        }

        //if second is in the same column
        if(firstX==secondX){
            //if second is below
            if(firstY<secondY){
                for(int i=roomSize-1; i>=0; i--){
                    if(currentRoom.fields[i][fieldX]!=null && currentRoom.fields[i][fieldX].getType()!= Field.TypeOfField.wall){
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
                    if(currentRoom.fields[i][fieldX]!=null && currentRoom.fields[i][fieldX].getType()!= Field.TypeOfField.wall){
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

        fieldX=roomSize/2;

        //if second is below
        if(firstY<secondY){
            roomY++;
            while(true){
                currentRoom=rooms[roomY][roomX];

                //if met room on the way
                if(currentRoom.id!=-1){
                    for(int i=0; i<roomSize; i++){
                        if(currentRoom.fields[i][fieldX]!=null && currentRoom.fields[i][fieldX].getType()!= Field.TypeOfField.wall){
                            break;
                        }
                        currentRoom.fields[i][fieldX]=new Field(Field.TypeOfField.corridor);
                    }

                    connect(roomList.get(first), currentRoom);
                    return;
                }
                for(int i=0; i<roomSize; i++){
                    currentRoom.fields[i][fieldX]=new Field(Field.TypeOfField.corridor);
                }
                roomY++;
            }
        }

        //if second is above
        if(firstY>secondY){
            roomY--;
            while(true){
                currentRoom=rooms[roomY][roomX];

                //if met room on the way
                if(currentRoom.id!=-1){
                    for(int i=roomSize-1; i>=0; i--){
                        if(currentRoom.fields[i][fieldX]!=null && currentRoom.fields[i][fieldX].getType()!= Field.TypeOfField.wall){
                            break;
                        }
                        currentRoom.fields[i][fieldX]=new Field(Field.TypeOfField.corridor);
                    }

                    connect(roomList.get(first), currentRoom);
                    return;
                }
                for(int i=0; i<roomSize; i++){
                    currentRoom.fields[i][fieldX]=new Field(Field.TypeOfField.corridor);
                }
                roomY--;
            }
        }
    }

    public boolean connect(Room room1, Room room2){
        if(room1.connected.contains(room2.connected.get(0))){
            return true;
        }
        ArrayList<Integer> temp=new ArrayList<>();
        temp.addAll(room1.connected);
        //if werent connected
        for(Integer id: room1.connected){
            if(id!=room1.id){
                roomList.get(id).connected.addAll(room2.connected);
            }
        }
        room1.connected.addAll(room2.connected);
        for(Integer id: room2.connected){
            if(id!=room2.id){
                roomList.get(id).connected.addAll(temp);
            }
        }
        room2.connected.addAll(temp);
        return false;
    }
}
