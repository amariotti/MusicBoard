package MusicBoard;

import javax.sound.midi.*;

public class Main {

	public static void main(String[] args) throws MidiUnavailableException {	  
	  
//	  Synthesizer synthesizer = MidiSystem.getSynthesizer();
//      synthesizer.open();
//      MidiChannel channel = synthesizer.getChannels()[0];
//	  
//	  playChord(channel,60,64,67);
//	  sleepMethod(100);
//	  playChord(channel,60,65,68);
//	  sleepMethod(100);
//	  playChord(channel,60,68,72);
//	  sleepMethod(100);
//	  playChord(channel,60,64,67);
//	  sleepMethod(100);
//	  playChord(channel,60,65,68);
//	  sleepMethod(100);
//	  playChord(channel,60,68,72);
	  
	  try {
	        Synthesizer synthesizer = MidiSystem.getSynthesizer();
	        synthesizer.open();

	        MidiChannel[] channels = synthesizer.getChannels();

	        channels[0].noteOn(60,65);
	        Thread.sleep(200);
	        channels[0].noteOff(60);
	        Thread.sleep(200);

	        synthesizer.close();
	    } catch (Exception e)
	    {
	        e.printStackTrace();
	    }
  }
  
  public static void sleepMethod(int speed)
  {
	  try {
          Thread.sleep(speed);
      }
	  catch (InterruptedException e) {
          System.out.println("Error catch");
      }
  }
  
  public static void playChord(MidiChannel channel, int n1, int n2, int n3) throws MidiUnavailableException
  {
	  int[] notes = new int[]{n1, n2, n3};

      for (int note : notes) {
          channel.noteOn(note, 70);
          channel.noteOff(note);
          System.out.println(note + " ");
      }
      //channel.allNotesOff();
  }
  
}