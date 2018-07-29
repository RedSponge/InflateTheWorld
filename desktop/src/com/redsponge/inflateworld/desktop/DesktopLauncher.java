package com.redsponge.inflateworld.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.redsponge.inflateworld.InflateTheWorld;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 256*3;
		config.height = 256*2;
		config.addIcon("icon/16.png", FileType.Internal);
		config.addIcon("icon/32.png", FileType.Internal);
		config.addIcon("icon/128.png", FileType.Internal);
		new LwjglApplication(new InflateTheWorld(), config);
	}
}
