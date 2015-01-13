package com.yan.glengine.assets.font;

import com.yan.glengine.EngineWrapper;
import com.yan.glengine.assets.YANAssetDescriptor;
import com.yan.glengine.assets.YANAssetLoader;
import com.yan.glengine.util.YANTextResourceReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yan-Home on 1/10/2015.
 */
public class YANFontLoader implements YANAssetLoader<YANFont> {
    @Override
    public YANFont loadAsset(YANAssetDescriptor assetDescriptor) {
        //load font file descriptor as a string
        String jsonAtlasString = YANTextResourceReader.readTextFileFromAssets(EngineWrapper.getContext(), assetDescriptor.getPathToAsset() + assetDescriptor.getAssetName() + "." + assetDescriptor.getAssetExtension());

        //separate by lines
        String[] lines = jsonAtlasString.split("\\n");

        //extract general data
        String face = extractFaceName(lines[0]);
        int lineHeight = extractLineHeight(lines[1]);
        int base = extractLineBase(lines[1]);
        int scaleWidth = extractScaleWidth(lines[1]);
        int scaleHeight = extractScaleHeight(lines[1]);
        String textureFile = assetDescriptor.getPathToAsset() + extractTextureFileName(lines[2]);

        //extract char data
        List<YANFontChar> charList = extractCharList(lines);
        Map<Integer,YANFontChar> charMap = new HashMap<>();

        for (YANFontChar aChar : charList) {
            charMap.put(aChar.getID(),aChar);
        }

        List<YANFontKerning> kerningList = extractKerningList(lines);

        //create font
        YANFont font = new YANFont(face, lineHeight, base, scaleWidth, scaleHeight, textureFile, charMap, kerningList);

        //assign back reference to the font object
        for (YANFontChar fontChar : font.getCharsMap().values()) {
            fontChar.setFont(font);
        }

        return font;
    }

    private List<YANFontKerning> extractKerningList(String[] lines) {
        List<YANFontKerning> fontKernings = new ArrayList<>();

        for (int i = 4; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith("kerning ")) {
                fontKernings.add(loadKerningFromLine(line));
            }
        }

        return fontKernings;
    }

    private YANFontKerning loadKerningFromLine(String line) {
        String[] entries = line.split("\\s+");
        int firstCharId = Integer.parseInt((entries[1].split("="))[1]);
        int secondCharId = Integer.parseInt((entries[2].split("="))[1]);
        int amount = Integer.parseInt((entries[3].split("="))[1]);
        return new YANFontKerning(firstCharId, secondCharId, amount);
    }

    private List<YANFontChar> extractCharList(String[] lines) {

        List<YANFontChar> charList = new ArrayList<>();

        for (int i = 4; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith("char ")) {
                charList.add(loadCharFromLine(line));
            }
        }

        return charList;
    }

    private YANFontChar loadCharFromLine(String line) {
        String[] entries = line.split("\\s+");

        int id = Integer.parseInt((entries[1].split("="))[1]);
        int x = Integer.parseInt((entries[2].split("="))[1]);
        int y = Integer.parseInt((entries[3].split("="))[1]);
        int width = Integer.parseInt((entries[4].split("="))[1]);
        int height = Integer.parseInt((entries[5].split("="))[1]);
        int xOffset = Integer.parseInt((entries[6].split("="))[1]);
        int yOffset = Integer.parseInt((entries[7].split("="))[1]);
        int xAdvance = Integer.parseInt((entries[8].split("="))[1]);

        //create a char
        return new YANFontChar(id, x, y, width, height, xOffset, yOffset, xAdvance);
    }

    private String extractTextureFileName(String line) {
        String[] entries = line.split("\\s+");
        String[] splicedEntry = entries[2].split("=");
        String tringWithQuotes = splicedEntry[1];
        return tringWithQuotes.replace("\"","");
    }

    private int extractScaleHeight(String line) {
        String[] entries = line.split("\\s+");
        String[] splicedEntry = entries[4].split("=");
        return Integer.parseInt(splicedEntry[1]);
    }

    private int extractScaleWidth(String line) {
        String[] entries = line.split("\\s+");
        String[] splicedEntry = entries[3].split("=");
        return Integer.parseInt(splicedEntry[1]);
    }

    private int extractLineBase(String line) {
        String[] entries = line.split("\\s+");
        String[] splicedEntry = entries[2].split("=");
        return Integer.parseInt(splicedEntry[1]);
    }

    private int extractLineHeight(String line) {
        String[] entries = line.split("\\s+");
        String[] splicedEntry = entries[1].split("=");
        return Integer.parseInt(splicedEntry[1]);
    }

    private String extractFaceName(String line) {
        String[] entries = line.split("\\s+");
        String[] splicedEntry = entries[1].split("=");
        return splicedEntry[1];
    }
}
