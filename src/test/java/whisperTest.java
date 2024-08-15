
// import java.io.*;
// import java.net.*;
// import org.json.*;
// import org.junit.jupiter.api.Test;

// import javax.sound.sampled.*;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertThrows;

// public class whisperTest {
//     @Test
//     void testTranscribeWorking() throws InterruptedException{
//         mockWhisper w = new mockWhisper();
//         Recorder r = new Recorder();
//         File f = new File("recording.wav");
//         r.startRecording();
//         Thread.sleep(5000);
//         r.stopRecording();
//         String s = "";
//         try {
//             s = w.transcribe(f);
//         } catch (JSONException | IOException | URISyntaxException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         }
//         assertTrue(s.equals("Working"));
//     }
// }