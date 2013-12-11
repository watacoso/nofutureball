package json;


public class GeneralSettings {
	public Integer sound,music;
	public Integer width,height;
	public Boolean fullScreen,vSync;
	
	public GeneralSettings(int sound, int music, int width, int height, boolean fullScreen, boolean vSync){
		this.sound=sound;
		this.music=music;
		this.width=width;
		this.height=height;
		
		this.fullScreen=fullScreen;
		this.vSync=vSync;
	}
	
	
}
