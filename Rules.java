// Turing Machine for CS2333
// Made By:    Blake Holt
//          &  Gregory Quinlan

// Rules.java
// This file definitely contains the hardest part of this whole project.
// This is where we keep the rules data.
// Also we were kidding, this was easy.
// Im omiting any more comments for this as the code is self explanitory.

class Rules{
   
   String CurrentState;
   String CurrentCell;
   String NewCell;
   String Direction;
   String NextState;
   
   public Rules(String CurrentCell, String CurrentState, String NewCell, String Direction, String NextState){
   
      this.CurrentState = CurrentState;
      this.CurrentCell = CurrentCell;
      this.NewCell = NewCell;
      this.Direction = Direction;
      this.NextState = NextState;
      
   }
   
   String getCurrentState(){
      return CurrentState;
   }
   
   String getCurrentCell(){
      return CurrentCell;
   }
   
   String getNewCell(){
      return NewCell;
   }
   
   String getDirection(){
      return Direction;
   }
   
   String getNextState(){
      return NextState;
   }
   
   String report(int num){
      String Message = "Rule "+ num +": Current Cell: " + CurrentCell + "; Current State: " + CurrentState + ";";
      if(NewCell.equals("-")){
         Message += " Leaving the Cell unchanged; ";
      }else if(NewCell.equals("_")){
         Message += " Erasing the Cell; ";
      }else{  
         Message += " Writing: "+ NewCell + "; ";
      }
      
      if(Direction.equals("H")){
         Message += "Halting; ";
      }else if(Direction.equals("L")){
         Message += "Moving Left; ";
      }else if(Direction.equals("R")){
         Message += "Moving Right; ";
      }else{
         Message += "Staying Still; ";
      }
      
      Message += "Next State: " + NextState + ";";
      
      return Message;
   }

}