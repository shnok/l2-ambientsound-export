package com.shnok.export;

import acmi.l2.clientmod.io.*;
import acmi.l2.clientmod.unreal.Environment;
import acmi.l2.clientmod.unreal.UnrealSerializerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shnok.export.model.AmbientSound;
import com.shnok.export.model.DataContainer;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String l2Folder;
        String mapName;
        String ambientSoundParams = ""; //46 53
        if(args.length >= 2) {
            l2Folder = args[0];
            mapName = args[1];
            if(args.length > 2) {
                ambientSoundParams = args[2];
            }
        } else {
            System.out.println("Error: Invalid arguments");
            return;
        }

        Path systemFolder = FileSystems.getDefault().getPath(l2Folder, "system");
        Environment environment = Environment.fromIni(new File(systemFolder.toString(), "l2.ini"));
        UnrealSerializerFactory serializerFactory = new UnrealSerializerFactory(environment);

        Path mapsFolder = FileSystems.getDefault().getPath(l2Folder, "maps");
        UnrealPackage up = new UnrealPackage(new File(mapsFolder.toString(), mapName + ".unr"), true);

        ObjectMapper objectMapper = new ObjectMapper();
        String json;

        List<AmbientSound> ambientSounds;
        if(ambientSoundParams.isEmpty()) {
            ambientSounds = AmbientSoundExporter.exportAllAmbientSounds(up, serializerFactory);
        } else {
            ambientSounds = AmbientSoundExporter.exportAmbientSoundList(up, serializerFactory, ambientSoundParams);
        }

        DataContainer dataContainer = new DataContainer(ambientSounds);
        json = objectMapper.writeValueAsString(dataContainer);

        FileWriter fw = new FileWriter(mapName + "_AmbientSounds.json", false);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(json);
        writer.close();
    }
}



