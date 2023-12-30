package ds.datastructprjc;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class GUIController {

    @FXML
    private TextArea TA1;
    @FXML
    private TextArea TA2;
    @FXML
    private TextField TF1;
    @FXML
    private TextField TF2;
    @FXML
    private TextField TF3;
    @FXML
    private TextField TF4;
    
    FileChooser fileChooser=new FileChooser();
    @FXML
    void showFile(MouseEvent event) {
        TA1.clear();

        File f=fileChooser.showOpenDialog(new Stage());
        String filePath = f.getPath();
        XMLReader.readFile(filePath);
        TA1.appendText(XMLReader.fileText.toString());}
    @FXML
    void ShowManual(MouseEvent event) {
        XMLReader.fileText=new StringBuffer(TA1.getText());


    }

    @FXML
    void Save(MouseEvent event) {

         File file=fileChooser.showSaveDialog(new Stage());
         file = new File(file.getAbsolutePath());
         fileWriter(file,TA2);

    }
    public void fileWriter(File savePath, TextArea textArea) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(savePath));
            bf.write(textArea.getText());
            bf.flush();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @FXML
    void Correct(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.correctError(XMLReader.fileText.toString()));
    }
    @FXML
    void Detect(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.detectError(XMLReader.fileText.toString()));


    }
    @FXML
    void Compress(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.compress(XMLReader.fileText.toString()));
    }

    @FXML
    void ConvertJSON(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.toJSON(XMLReader.fileText.toString()));
    }

    @FXML
    void Decompress(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.decompress(XMLReader.fileText.toString()));
    }

    @FXML
    void Minify(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.minify(XMLReader.fileText.toString()));
    }

    @FXML
    void Prettify(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.prettify(XMLReader.fileText.toString()));
    }
    @FXML
    void Construct(MouseEvent event) {
        Graph.constructGraph();
    }

    @FXML
    void print(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Graph.printGraph());
    }
    @FXML
    void Redo(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.redo());
    }

    @FXML
    void Undo(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.undo());

    }
    @FXML
    void Search(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.postSearch(TF1.getText()));

    }

    @FXML
    void Active(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.mostActive());
    }
    @FXML
    void Influence(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.mostInfluencer());
    }
    @FXML
    void Mutual(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.mutualFollowers(Integer.parseInt(TF3.getText()),Integer.parseInt(TF4.getText())));
        TF3.clear();
        TF4.clear();
    }
    @FXML
    void Suggest(MouseEvent event) {
        TA2.clear();
        TA2.appendText(Functions.suggestFollow(Integer.parseInt(TF2.getText())));
        TF2.clear();
    }



}
