package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 * Main Class.
 * @author Miles Engelbrecht.
 *
 * <br>
 *     <br>
 *
 * Logical/Runtime Error:
 * The AddProductController and ModifyProductController both have a RemovePartFromTable method, they're very similar but slightly different.
 * The RemovePartFromTable method, which I'll now just refer to as Remove method, in the AddProductController doesn't call the deleteAssociatedPart method.
 * I figured this was not needed, as nothing needs to be associated until the new Product being created is saved.
 * However, the main purpose of the Remove method in the ModifyProductController is to get rid of the Associated Part, that way the user can
 * delete the Product if they choose to.  So while I was programming the Remove method for the ModifyProductController I had to call deleteAssociatedPart
 * from the Products class.  Because of this addition + the code that was already in place, I was essentially deleting the same thing twice.  This was causing an
 * IndexOutOfBounds Exception, even though the program was running as intended.  After debugging, I figured out that I didn't need the line that contained,
 * "addedPartObList.remove(index);" (See ModifyProductController's Remove method to see where the line was).  I also had to change the getData method, and add a
 * setData method in order for the Remove method within ModifyProductController to work properly.  Thankfully, this error, while it made me scratch my head because the
 * program was still working as intended, and I wasn't sure where the indexOutOfBounds Exception was coming from, didn't take too long to find and squash.
 *
 * <br>
 *     <br>
 *
 * Lastly, a compatible feature I would add if I were further experimenting with this project.
 * I think I would automatically sort Parts in the Add and Modify Products TableView, to be based upon Parts that were last accessed.
 * For example, if I have 3 Parts, let's call them p1, p2, p3, and in my first product I created I used p3.  The very next time I go to
 * create a new product, p3 would be at the top of the parts table.
 * This would give the TableView a Temporal Locality feature to it, and would help users be more efficient.
 * <br>
 *     <br>
 * Thanks!
 *
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("SoftwareI Project");  //Setting the title.
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    /**
     * This is the main, which launches the program.
     * Some of the comments here are for some old data that I was testing when I initially made the program.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
        //DataArray dataArray = new DataArray();
        //dataArray.partLL.add()
        //Testing data here ^^^
    }
}
