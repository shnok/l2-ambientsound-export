package com.shnok.export;

import acmi.l2.clientmod.io.UnrealPackage;
import acmi.l2.clientmod.unreal.UnrealSerializerFactory;
import com.shnok.export.model.AmbientSound;
import com.shnok.export.parser.AmbientSoundParser;

import java.util.ArrayList;
import java.util.List;

public class AmbientSoundExporter {
    public static List<AmbientSound> exportAmbientSoundList(
            UnrealPackage up, UnrealSerializerFactory serializerFactory, String allNames) {
        String[] ambientSoundNames;
        if(allNames.contains(",")) {
            ambientSoundNames = allNames.split(",");
        } else {
            ambientSoundNames = new String[] { allNames };
        }

        List<AmbientSound> ambientSounds = new ArrayList<>();
        List<UnrealPackage.ExportEntry> exportEntries = up.getExportTable();
        for (int i = 0; i < exportEntries.size(); i++) {
            for(int b = 0; b < ambientSoundNames.length; b++) {
                if (exportEntries.get(i).getObjectInnerFullName().equals(ambientSoundNames[b])) {
                    UnrealPackage.ExportEntry brushEntry = (UnrealPackage.ExportEntry) up.getAt(i + 1);
                    AmbientSound ambientSound = exportAmbientSound(up, serializerFactory, brushEntry);
                    if(ambientSound == null) {
                        continue;
                    }

                    ambientSounds.add(ambientSound);
                }
            }
        }

        return ambientSounds;
    }

    public static List<AmbientSound> exportAllAmbientSounds(UnrealPackage up, UnrealSerializerFactory serializerFactory) {
        List<AmbientSound> ambientSounds = new ArrayList<>();
        List<UnrealPackage.ExportEntry> exportEntries = up.getExportTable();
        for (int i = 0; i < exportEntries.size(); i++) {
            if (exportEntries.get(i).getObjectInnerFullName().contains("AmbientSoundObject")) {
                UnrealPackage.ExportEntry brushEntry = (UnrealPackage.ExportEntry) up.getAt(i + 1);
                AmbientSound brush = exportAmbientSound(up, serializerFactory, brushEntry);
                if(brush == null) {
                    continue;
                }

                ambientSounds.add(brush);
            }
        }

        return ambientSounds;
    }

    public static AmbientSound exportAmbientSound(UnrealPackage up, UnrealSerializerFactory serializerFactory, UnrealPackage.ExportEntry brushEntry) {
        AmbientSound ambientSound = AmbientSoundParser.parse(up, brushEntry, serializerFactory);
        if(ambientSound.getAmbientSoundName() == null) {
            System.out.println("ERROR: AmbientSound object is empty.");
            return null;
        }

        return ambientSound;
    }


}
