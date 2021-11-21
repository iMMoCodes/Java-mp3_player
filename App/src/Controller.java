import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Controller implements Initializable{
// -------------------- VARIABLES -------------------- \\
    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton, pauseButton, resetButton, prevButton, nextButton;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;
    // Get media & mediaPlayer
    private Media media;
    private MediaPlayer mediaPlayer;
    // Get files and create array for files to play
    private File directory;
    private File[] files;
    private ArrayList<File> songs;
    // Track song number
    private int songNumber;
    // Track speed option
    private int[] speeds = {25,50,75,100,125,150,175,200};
    // Timer for the music
    private Timer timer;
    private TimerTask task;
    // Is the music running
    private boolean running;
// -------------------- END OF VARIABLES -------------------- \\

// -------------------- INITIALIZE -------------------- \\
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Create songs array
        songs = new ArrayList<File>();
        // Get directory
        directory = new File("music");
        // Apply files from directory
        files = directory.listFiles();
        // Check that there are files and add them to songs array
        if(files != null) {
            for(File file : files) {
                songs.add(file);
            }
        }
        // Get songs name and apply it to the screen
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songs.get(songNumber).getName());
        // Populate play speed box with values
        for(int i = 0; i < speeds.length; i++) {
            speedBox.getItems().add(Integer.toString(speeds[i])+"%");
        }
        // Reference changeSpeed method
        speedBox.setOnAction(this::changeSpeed);
    }
// -------------------- END OF INITIALIZE -------------------- \\

// -------------------- METHODS -------------------- \\
    public void playMedia() {
        // To make speed stay the same if song is switched
        changeSpeed(null);
        // Play music
        mediaPlayer.play();
    }

    public void pauseMedia() {
        // Pause music
        mediaPlayer.pause();
    }

    public void resetMedia() {
        // Reset music -> Set duration back to 0
        mediaPlayer.seek(Duration.seconds(0));
    }

    public void prevMedia() {
        // Check that not first song on the list
        if(songNumber > 0) {
            // Decrease index
            songNumber--;

            mediaPlayer.stop();
            // Get songs name and apply it to the screen
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            // Start previous song
            playMedia();
        }
        // If first song on the list
        else {
            // Set to last song on the list
            songNumber = songs.size() -1;

            mediaPlayer.stop();
            // Get songs name and apply it to the screen
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());

            // Start previous song
            playMedia();
        }        
    }

    public void nextMedia() {
        // Check that not last song on the list
        if(songNumber < songs.size() -1) {
            // Increase index
            songNumber++;

            mediaPlayer.stop();
            // Get songs name and apply it to the screen
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            // Start next song
            playMedia();
        }
        // If last song on the list
        else {
            // Set to first song
            songNumber = 0;

            mediaPlayer.stop();
            // Get songs name and apply it to the screen
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());

            // Start next song
            playMedia();
        }
    }

    public void changeSpeed(ActionEvent event) {
        // Check if no value set and set it to 1
        if(speedBox.getValue() == null) {
            mediaPlayer.setRate(1);
        } else {
            // Get value -> regular speed (100) is 1 so got to divide value by 100
            //mediaPlayer.setRate(Integer.parseInt(speedBox.getValue()) * 0.01);
            // Upper causes crash because % added after number -> Remove the %
            mediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01);
        }
    }

    public void beginTimer() {
        
    }

    public void cancelTimer() {
        
    }
    // -------------------- END OF METHODS -------------------- \\
}