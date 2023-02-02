package lk.ijse.jewellery.util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationController {


    public static void loginSuccessful() {
        Notifications notificationBuilder = Notifications.create()
                .title("Login Successfully.!")
                .text("Your Details Validated Successfully.")
                .graphic(new ImageView(new Image("lk/ijse/jewellery/assets/check.png")))
                .hideAfter(Duration.seconds(6))
                .position(Pos.BASELINE_CENTER);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public static void loginUnSuccessful() {
        Notifications notificationBuilder = Notifications.create()
                .title("Login UnSuccessful.!")
                .text("Your Details Validation Rejected.")
                .graphic(new ImageView(new Image("lk/ijse/jewellery/assets/error.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }


    public static void AddedDetailsSuccessFully() {
        Notifications notificationBuilder = Notifications.create()
                .title("Data added !")
                .text("details saved successfully ! .")
                .graphic(new ImageView(new Image("lk/ijse/jewellery/assets/check.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public static void cantAddedDetailsSuccessFully() {
        Notifications notificationBuilder = Notifications.create()
                .title("\t\t\t☠ ACCESS DENIED..!")
                .text("you can't hit the database !")
                .graphic(new ImageView(new Image("lk/ijse/jewellery/assets/error.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public static void detailsRemoved() {
        Notifications notificationBuilder = Notifications.create()
                .title(" Details Removed Successfully !")
                .text("table row deleted successfully !")
                .graphic(new ImageView(new Image("lk/ijse/jewellery/assets/error.png")))
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_CENTER);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public static void searchResultNotFound() {
        Notifications notificationBuilder = Notifications.create()
                .title("Search Result Not Found..!")
                .text("can't find result !")
                .graphic(new ImageView(new Image("lk/ijse/jewellery/assets/error.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    public static void searchResultFound(){
        Notifications notificationBuilder = Notifications.create()
                .title("Search Result Found !")
                .text("matching successful your details is here  ! .")
                .graphic(new ImageView(new Image("lk/ijse/jewellery/assets/check.png")))
                .hideAfter(Duration.seconds(8))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

}
