

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import java.io.File;

// import static org.junit.jupiter.api.Assertions.assertTrue;

// public class testRecorder {

//     private Recorder recorder;

//     @BeforeEach
//     public void setUp() {
//         recorder = new Recorder();
//     }

//     @AfterEach
//     public void tearDown() {
//         recorder = null;
//     }

//     @Test
//     public void testStartAndStopRecording() {
//         recorder.startRecording();
//         //record something for 2 secs
//         try {
//             Thread.sleep(4000); 
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//         recorder.stopRecording();

//         File recordingFile = recorder.getRecordingFile();
//         assertTrue(recordingFile.exists(), "Recording file should not be null");
//         assertTrue(recordingFile.length() > 0, "Recording file should have a non-zero size");
//     }
// }