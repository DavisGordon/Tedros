package tmp;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Tmp {
    // record duration, in milliseconds
   // static final long RECORD_TIME = 7200000;//60000;  // 1 minute
	static final long RECORD_TIME = 1800000 ; 
    String pattern = "MMddyyyy_HHmmss";

 // Create an instance of SimpleDateFormat used for formatting 
 // the string representation of date according to the chosen pattern
 DateFormat df = new SimpleDateFormat(pattern);

 // Get the today date using Calendar object.
 Date today = Calendar.getInstance().getTime();        
 // Using DateFormat format method we can create a string 
 // representation of a date with the defined format.
 String todayAsString = df.format(today);
    
    // path of the wav file
    File wavFile = new File("C:/tmp/googledrive/nov/"+todayAsString+".wav");
 
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    TargetDataLine line;
 
    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
 
    /**
     * Captures the sound and record into a WAV file
     */
    void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing
 
            System.out.println("Start capturing...");
 
            AudioInputStream ais = new AudioInputStream(line);
 
            System.out.println("Start recording...");
 
            // start recording
            AudioSystem.write(ais, fileType, wavFile);
 
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
 
    /**
     * Closes the target data line to finish capturing and recording
     */
    void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }
 
    /**
     * Entry to run the program
     */
    public static void main(String[] args) {
        
    	/*for (int i = 0; i < 2; i++) {  
            new Thread1().start();  
        } */
    	
    	final Tmp recorder = new Tmp();
 
        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });
 
        stopper.start();
 
        // start 
        recorder.start();
    }
    
    static class Thread1 extends Thread {  
        @Override  
        public void run() {  
            final Tmp recorder = new Tmp();
                synchronized(Thread1.class) {  //this ensures that all the threads use the same lock
                	System.out.println("Thread-" + this.getId()+" start");
                	recorder.start();
                      
                }

                //for the sake of illustration, we sleep to ensure some other thread goes next
                try {
                    Thread.sleep(RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish(); 
                System.out.println("Thread-" + this.getId()+" stop"); 
            
        }  
    }
}
