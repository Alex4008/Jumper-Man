package alex;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sounds {

	private MusicEffect music = null;
	private Clip runningClip = null;

	public void PlaySound(SoundEffect effect) {
		if(Game.soundsToggle == false) return;

		try {
			// Open an audio input stream.
			URL url = this.getClass().getClassLoader().getResource(effect.GetFileName());
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// Get a sound clip resource.
			Clip clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();
		} catch (Exception e) { System.out.println("Sound device not found!"); }
	}
	
	public void PlayMusic(MusicEffect effect) {
		try {
			// Open an audio input stream.
			URL url = this.getClass().getClassLoader().getResource(effect.GetFileName());
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// Get a sound clip resource.
			Clip clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();
			stopMusic();
			if(effect.isRepeated() == true) {
				runningClip = clip;
				music = effect;
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			
			if(Game.musicToggle == false) clip.stop();
		} catch (Exception e) { System.out.println("Sound device not found!"); }
	}
	
	public void stopMusic() {
		if(runningClip == null) return;
		runningClip.stop();
	}
	
	public void resumeMusic() {
		if(runningClip == null) return;
		runningClip.start();
	}
	
	public boolean isRunning() {
		if(runningClip != null) return true;
		else return false;
	}
	
	public void restartMusic()  {
		if(music != null) PlayMusic(music);
	}
}
