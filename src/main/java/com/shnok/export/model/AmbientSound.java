package com.shnok.export.model;

import lombok.Data;

@Data
public class AmbientSound {
    private String name;
    private String ambientSoundName;
    private String ambientSoundType;
    private float ambientSoundStartTime;
    private int ambientRandom;
    private float soundRadius;
    private int soundVolume;
    private int soundPitch;
    private Vector3 position;
    private String groupName;
}
