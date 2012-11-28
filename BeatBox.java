package MusicBoard;

import java.awt.*;  
import javax.swing.*;  
import javax.sound.midi.*;  
import java.util.*;  
import java.awt.event.*;  
   
public class BeatBox implements MetaEventListener {  
    
    JPanel mainPanel;  
    ArrayList checkboxList;  
    int bpm=120;  
    Sequencer sequencer;  
    Sequence sequence;  
    Track track;  
    JFrame theFrame;  
      
    String[] instrumentNames= {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",  
            "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Congo", "Cowbell", "Vibraslap",  
            "Lowmid Tom", "High Agogo", "Open Hi Conga"};  
      
    int[] instruments={35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};  
      
   public static void main (String [] args) {  
       new BeatBox().buildGUI();  
   }  
          
        public void buildGUI() {  
              
            theFrame=new JFrame("Cyber Beatbox");  
            BorderLayout layout=new BorderLayout();  
            JPanel background= new JPanel(layout);  
            background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));  
              
            checkboxList = new ArrayList();  
            Box buttonBox=new Box(BoxLayout.Y_AXIS);  
              
            JButton start=new JButton("Start");  
            start.addActionListener(new MyStartListener());  
            buttonBox.add(start);  
              
            JButton stop=new JButton("Stop");  
            stop.addActionListener(new MyStopListener());  
             buttonBox.add(stop);  
               
             JButton upTempo=new JButton("Tempo Up");  
            upTempo.addActionListener(new MyUpTempoListener());  
            buttonBox.add(upTempo);  
              
            JButton downTempo=new JButton("Tempo Down");  
            downTempo.addActionListener(new MyDownTempoListener());  
             buttonBox.add(downTempo);  
               
             Box nameBox= new Box (BoxLayout.Y_AXIS);  
             for (int i=0; i<16; i++)  {  
                 nameBox.add(new Label(instrumentNames[i]));  
              
        } //close for loop  
          
         background.add(BorderLayout.EAST, buttonBox);  
         background.add(BorderLayout.WEST, nameBox);  
           
         theFrame.getContentPane().add(background);  
           
         GridLayout grid = new GridLayout (16,16);  
         grid.setVgap(1);  
         grid.setHgap(2);  
         mainPanel=new JPanel(grid);  
         background.add(BorderLayout.CENTER,mainPanel);  
           
          for (int i=0; i<256; i++)  {  
             JCheckBox c= new JCheckBox();  
             c.setSelected(false);  
             checkboxList.add(c);  
             mainPanel.add(c);  
               
          } //end loop  
               
         setUpMidi();  
           
         theFrame.setBounds(50,50,300,300);  
         theFrame.pack();  
         theFrame.setVisible(true);  
    } // closes method  
        
    public void setUpMidi() {  
        try {  
            sequencer=MidiSystem.getSequencer();  
            sequencer.open();  
            sequencer.addMetaEventListener(this);  
            sequence=new Sequence(Sequence.PPQ,4);  
            track= sequence.createTrack();  
            sequencer.setTempoInBPM(bpm);  
              
        } catch(Exception e) {e.printStackTrace();}  
    } // closes method  
      
    public void buildTrackAndStart() {  
        int[] trackList=null;  
          
         
         sequence.deleteTrack(track);  
        track=sequence.createTrack();  
          
          
         
          
        for (int i=0;i<16;i++) {  
            trackList=new int[16];  
              
            int key=instruments[i];  
              for (int j=0;j<16;j++) {  
                JCheckBox jc=(JCheckBox) checkboxList.get(j + (16*i));  
                  
                if (jc.isSelected()) {  
                    trackList[j]=key;  
                    } else{   
                    trackList[j]=0;  
                    }  
              } //closes inner for loop   
              
            makeTracks(trackList);  
              
        } // closes outer for loop  
          
        track.add(makeEvent(192,9,1,0,15));  
          
        try {   
              
            sequencer.setSequence(sequence);  
            sequencer.start();  
            sequencer.setTempoInBPM(bpm); 
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        }  catch (Exception e) {e.printStackTrace();}  
          
    } // closes buildTrackAndStart method  
      
    public class MyStartListener implements ActionListener {  
        public void actionPerformed(ActionEvent a) {  
     
            buildTrackAndStart();  
        }  
    } // closes inner class  
      
     public class MyStopListener implements ActionListener {  
        public void actionPerformed(ActionEvent b) {  
            sequencer.stop();  
        }  
    } // closes inner class  
       
      public class MyUpTempoListener implements ActionListener {  
        public void actionPerformed(ActionEvent c) {  
            bpm+=3;  
        }  
    } // closes inner class  
        
        public class MyDownTempoListener implements ActionListener {  
        public void actionPerformed(ActionEvent d) {  
            bpm-=3;  
        }  
    } // closes inner class  
          
        public void makeTracks(int[] list) {  
              
            for (int i=0; i<16; i++) {  
                int key=list[i];  
                  
                if (key !=0) {  
                    track.add(makeEvent(144,9,key,100,i));  
                    track.add(makeEvent(128,9,key,100,i+1));  
                }  
            }  
        }  
          
        public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick ) {  
            MidiEvent event = null;  
              
            try {  
                ShortMessage a = new ShortMessage();  
                a.setMessage(comd, chan, one, two);  
                event = new MidiEvent(a, tick);  
                  
            } catch (Exception e) {e.printStackTrace();}  
            return event;  
        }  
          
        public void meta(MetaMessage message) {  
            if (message.getType()==47) {  
                sequencer.start();  
                sequencer.setTempoInBPM(bpm);  
            }  
        }  
} 
