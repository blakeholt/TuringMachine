// Turing Machine for CS2333
// Made By:    Blake Holt
//          &  Gregory Quinlan

// Controller.java
// This file contains the controls for the GUI.fxml file.
// This is also where all the code for the turing machine is.
// It is started by compiling and running Start.java, and it uses data recieved from Rules.txt
// It also uses Rules.java to keep the rules given nice and sorted.

// Import statements
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.util.*;
import java.io.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

   // Declaring Global Variables
   boolean Halted = false;
   boolean Loaded = false;
   int CurrentPoint = 0;
   int CurrentRule = 0;
   String Tape;
   Stage stage;
   Rules[] listOfRules;
   int num_of_rules;
   ArrayList<String> States = new ArrayList<String>();
   
   // Declaring the things that are on the GUI
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblInput;

    @FXML
    private Button btnStep;
    
    @FXML
    private Button btnEdit;

    @FXML
    private TextField txtInput;

    @FXML
    private Button btnDone;
   
    @FXML
    private Label lblTape;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnRules;

    @FXML
    private Button btnRun;

    @FXML
    private Label lblState;


   //Methods
    @FXML
    void ButtonRun(ActionEvent event){ // Code for when the Run button is pressed
      lblStatus.setText("PROCESSING");
      while(!Halted){
         step();
      }
      if(Halted){
         lblStatus.setText("ACCEPTED");
      }else{
         lblStatus.setText("IDLE");
      }
    }

    @FXML
    void ButtonStep(ActionEvent event) { // Code for when the Step button is pressed
      lblStatus.setText("PROCESSING");
      step();
      if(Halted){
         lblStatus.setText("ACCEPTED");
      }else{
         lblStatus.setText("IDLE");
      }
    }
    
    void step(){// Code for the Step method, which is used to step once in ButtonStep, and multiple times in ButtonRun
      // This is where the bulk of the magic happens
      int sizeOfTape = Tape.length();
      int[] nextSSRule = new int[100];
      int SSRuleL = 0;
      String CurrentState = listOfRules[CurrentRule].getCurrentState();
      for(int i = 0; i < num_of_rules; i++){ // This is checking if the current state has multiple rules.
         if(i!=CurrentRule && CurrentState.equals(listOfRules[i].getCurrentState())){
            nextSSRule[SSRuleL] = i;
            SSRuleL++;
         }
      }
      int n = 0;
      boolean check = true;
      boolean flag = true;
      while(flag){ // Inside here is where the rules are evaluated.
         String t = Tape.charAt(CurrentPoint) + "";
         if(t.equals(listOfRules[CurrentRule].getCurrentCell()) && check){ // This is if there is only one rule in the state, or its the first rule of the state
            flag = false;
            if(listOfRules[CurrentRule].getNewCell().equals("-")){ // If the current point on the tape is -, then do nothing with it.
            }else if(listOfRules[CurrentRule].getNewCell().equals("_")){ // If the current point on the tape is _, then do erase it.
               String te = Tape.substring(0,CurrentPoint) + " " + Tape.substring(CurrentPoint+1,Tape.length());
               Tape = te;
               lblTape.setText(Tape);
            }else{ // Otherwise replace the thing at the current point on the tape.
               String te = Tape.substring(0,CurrentPoint) + listOfRules[CurrentRule].getNewCell() + Tape.substring(CurrentPoint+1,Tape.length());
               Tape = te;
               lblTape.setText(Tape);
            }
            if(listOfRules[CurrentRule].getDirection().equals("H")){ // If the rules direction is H then its accepted and it halts the machine.
               Halting();
               break;
            }else if(listOfRules[CurrentRule].getDirection().equals("L")){ // L means left, so move left
               if(CurrentPoint == 0){
                  Tape = " " + Tape;
                  lblTape.setText(Tape);
               }else{
                  CurrentPoint--;
               }
            }else if(listOfRules[CurrentRule].getDirection().equals("R")){ // R means Ruby, wait, no it means Right, so move right.
               CurrentPoint++;
            }
            
         }else{
            check = false;
         }
         
         if(t.equals(listOfRules[nextSSRule[n]].getCurrentCell()) && !check){ // This is where it does the rules for any additional rules in a state. The code in this is the same as the first set.
            CurrentRule = nextSSRule[n];
            flag = false;
            if(listOfRules[CurrentRule].getNewCell().equals("-")){
            }else if(listOfRules[CurrentRule].getNewCell().equals("_")){
               String te = Tape.substring(0,CurrentPoint) + " " + Tape.substring(CurrentPoint+1,Tape.length());
               Tape = te;
               lblTape.setText(Tape);
            }else{
               String te = Tape.substring(0,CurrentPoint) + listOfRules[CurrentRule].getNewCell() + Tape.substring(CurrentPoint+1,Tape.length());
               Tape = te;
               lblTape.setText(Tape);
            }
      
            if(listOfRules[CurrentRule].getDirection().equals("H")){
               Halting();
               break;
            }else if(listOfRules[CurrentRule].getDirection().equals("L")){
               if(CurrentPoint == 0){
                  Tape = " " + Tape;
                  lblTape.setText(Tape);
               }else{
                  CurrentPoint--;
               }
            }else if(listOfRules[CurrentRule].getDirection().equals("R")){
               CurrentPoint++;
            }
         }
         if(n > SSRuleL){
            flag = false;
         }else{
            n++;
         }
         for(int i = 0; i < num_of_rules; i++){
            if(listOfRules[CurrentRule].getNextState().equals(listOfRules[i].getCurrentState())){
               CurrentRule = i;
               break;
            }
         }
      }
      lblState.setText(listOfRules[CurrentRule].getCurrentState());
    }
    
    void Halting(){// Code for when the tape is accepted
      Halted = true;
      btnStep.setDisable(true);
      btnRun.setDisable(true);
      btnRules.setDisable(true);
      btnDone.setDisable(false);
      txtInput.setDisable(false);
      btnEdit.setDisable(true);
    }
    
    @FXML
    void ButtonDone(ActionEvent event) { // Code for when the Done button is pressed
      lblStatus.setText("PROCESSING");
      Halted = false;
      Tape = txtInput.getText();
      lblTape.setText(Tape);
      txtInput.setDisable(true);
      btnDone.setDisable(true);
      btnEdit.setDisable(false);
      if(Loaded){
         btnStep.setDisable(false);
         btnRun.setDisable(false);
      }
      btnRules.setDisable(false);
      lblStatus.setText("IDLE");
    }

    @FXML
    void ButtonEdit(ActionEvent event) { // Code for when the Edit button is pressed
      lblStatus.setText("PROCESSING");
      btnDone.setDisable(false);
      txtInput.setDisable(false);
      btnEdit.setDisable(true);
      btnStep.setDisable(true);
      btnRun.setDisable(true);
      btnRules.setDisable(true);
      lblStatus.setText("IDLE");
    }

    @FXML
    void ButtonRules(ActionEvent event) throws FileNotFoundException { // Code for when the Load Rules button is pressed
      // Getting rule file from filechooser
      Loaded = true;
      btnStep.setDisable(false);
      btnRun.setDisable(false);
      lblStatus.setText("PROCESSING");
      FileChooser fileChooser = new FileChooser();
      fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
      fileChooser.setTitle("Open Rules File");
      File selectedFile = fileChooser.showOpenDialog(stage);
      // Loading Rules
      Scanner kb = new Scanner(selectedFile);
      num_of_rules = kb.nextInt();
      kb.nextLine();
      
      listOfRules = new Rules[num_of_rules];
      for(int i = 0; i < num_of_rules; i++){
         String temp = kb.nextLine();
         String[] rule = temp.trim().split("\\s+"); // Split the rule input via spaces into array
         listOfRules[i] = new Rules(rule[0],rule[1],rule[2],rule[3],rule[4]);
         if(!Arrays.asList(States).contains(rule[0])){ // this will gather all of the states into an arraylist.
            States.add(rule[1]);
         }
      }
      lblStatus.setText("LOADED");
    }
   // Initialization
    @FXML
    void initialize() { // Starts with some Assertions for the FXML stuff
         assert lblInput != null : "fx:id=\"lblInput\" was not injected: check your FXML file 'GUI.fxml'.";
         assert btnStep != null : "fx:id=\"btnStep\" was not injected: check your FXML file 'GUI.fxml'.";
         assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'GUI.fxml'.";
         assert btnDone != null : "fx:id=\"btnDone\" was not injected: check your FXML file 'GUI.fxml'.";
         assert lblTape != null : "fx:id=\"lblTape\" was not injected: check your FXML file 'GUI.fxml'.";
         assert lblStatus != null : "fx:id=\"lblStatus\" was not injected: check your FXML file 'GUI.fxml'.";
         assert btnRules != null : "fx:id=\"btnRules\" was not injected: check your FXML file 'GUI.fxml'.";
         assert btnRun != null : "fx:id=\"btnRun\" was not injected: check your FXML file 'GUI.fxml'.";
         assert lblState != null : "fx:id=\"lblState\" was not injected: check your FXML file 'GUI.fxml'.";
         btnEdit.setDisable(true); // Disabling buttons so the user doesnt break anything
         btnStep.setDisable(true);
         btnRun.setDisable(true);
         btnRules.setDisable(true);
    }
}
