package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Miles Engelbrecht
 * Figured I'd throw my name in at the Main.java class, just for an easier to read JavaDoc.
 *
 * I also wanted to add a couple of notes here, for the grader who reads through this.
 * Firstly, the choice to give each scene its own FXML document, and controller was mostly for organization, I could have had quite a few less files
 * And less code, but I really don't mind the copy/paste of some methods, and it kept things a lot more organized for my brain to keep track of.
 *
 * Next, the descriptive logic runtime error can be found over the method goToModifyProducts.  There are also quite a few bugs I had to squash
 * and I've added comments in some of those places as well, but that was the one I decided to do more of a deep dive on explaining.
 *
 * Lastly, a compatible feature I would add if I were further experimenting with this project.
 * I think I would automatically sort Parts in the Add and Modify Products TableView, to be based upon Parts that were last accessed.
 * This would give the TableView a Temporal Locality feature to it, and would help users be more efficient.
 *
 * Thanks!
 *
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Main");  //Setting the title.
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }




    public static void main(String[] args) {
        launch(args);
        //DataArray dataArray = new DataArray();
        //dataArray.partLL.add()
        //Testing data here ^^^
    }
}
