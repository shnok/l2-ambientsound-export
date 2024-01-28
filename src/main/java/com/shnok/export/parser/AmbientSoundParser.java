package com.shnok.export.parser;

import acmi.l2.clientmod.io.UnrealPackage;
import acmi.l2.clientmod.unreal.UnrealSerializerFactory;
import acmi.l2.clientmod.unreal.core.Object;
import acmi.l2.clientmod.unreal.properties.L2Property;
import com.shnok.export.model.AmbientSound;
import com.shnok.export.model.Vector3;

import java.util.List;

public class AmbientSoundParser {

    public static AmbientSound parse(UnrealPackage up, UnrealPackage.ExportEntry entry, UnrealSerializerFactory serializerFactory) {
        Object ambientSoundObject = serializerFactory.getOrCreateObject(entry);
        List<L2Property> properties = ambientSoundObject.properties;

        AmbientSound ambientSound = new AmbientSound();
        ambientSound.setName(entry.getObjectFullName());

        for(L2Property property : properties) {
            java.lang.Object value = property.getAt(0);

            if(property.getName().contains("AmbientSoundType")) {
                ambientSound.setAmbientSoundType(parseAmbientSoundType((int) value));
            } else if(property.getName().contains("Location")) {
                List<L2Property> pos = (List<L2Property>) value;
                ambientSound.setPosition(new Vector3((float) pos.get(0).getAt(0), (float) pos.get(1).getAt(0), (float) pos.get(2).getAt(0)));
            } else if(property.getName().equals("AmbientSound")) {
                UnrealPackage.Entry ambientEntry = up.objectReference((int) value);
                ambientSound.setAmbientSoundName(ambientEntry.getObjectFullName());
            } else if(property.getName().contains("AmbientSoundStartTime")) {
                ambientSound.setAmbientSoundStartTime((float) value);
            } else if(property.getName().contains("AmbientRandom")) {
                ambientSound.setAmbientRandom((int) value);
            } else if(property.getName().contains("SoundRadius")) {
                ambientSound.setSoundRadius((float) value);
            } else if(property.getName().contains("SoundVolume")) {
                ambientSound.setSoundVolume((int) value);
            } else if(property.getName().contains("SoundPitch")) {
                ambientSound.setSoundPitch((int) value);
            } else if(property.getName().equals("Group")) {
                ambientSound.setGroupName(up.nameReference((int) value));
            }
        }

        if(ambientSound.getAmbientSoundType() == null) {
            ambientSound.setAmbientSoundType(parseAmbientSoundType(0));
        }

        if(ambientSound.getGroupName() == null) {
            ambientSound.setGroupName("None");
        }

        System.out.println(ambientSound);

        return ambientSound;
    }

    public static String parseAmbientSoundType(int value) {
        if(value == 0) {
            return "AST1_Always";
        } else if(value == 1) {
            return "AST1_Day";
        } else if(value == 2) {
            return "AST1_Night";
        } else if(value == 3) {
            return "AST1_Water";
        } else {
            return "AST1_RangeTime";
        }
    }
}
