package util;

import assets.AssetManager;
import entity.TileType;

import java.util.HashMap;
import java.util.Map;

public class FootStepSoundManager {
    private final Map<TileType, PooledSoundPlayer> soundMap = new HashMap<>();

    public FootStepSoundManager() {
        AssetManager assets = AssetManager.getInstance();
        soundMap.put(TileType.GRASS, new PooledSoundPlayer(assets.getSoundPool("/sounds/footstep_grass.wav", 3)));
        soundMap.put(TileType.STONE, new PooledSoundPlayer(assets.getSoundPool("/sounds/footstep_stone.wav", 3)));
    }

    public void play(TileType type) {
        PooledSoundPlayer player = soundMap.get(type);
        if (player != null) player.play();
    }
}
