//package com.tuyendv.web.backend.api.config.security.webMVC.characterEscapes;
//
//import com.fasterxml.jackson.core.SerializableString;
//import com.fasterxml.jackson.core.io.CharacterEscapes;
//import com.fasterxml.jackson.core.io.SerializedString;
//import org.apache.commons.text.translate.AggregateTranslator;
//import org.apache.commons.text.translate.CharSequenceTranslator;
//import org.apache.commons.text.translate.EntityArrays;
//import org.apache.commons.text.translate.LookupTranslator;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//public class HTMLCharacterEscapes extends CharacterEscapes {
//
//    private final int[] asciiEscapes;
//
//    private final CharSequenceTranslator translator;
//
//    public HTMLCharacterEscapes() {
//        Map<CharSequence, CharSequence> customMap = new HashMap<>();
//        customMap.put("(", "&#40;");
//        customMap.put(")", "&#41;");
//        customMap.put("<", "&lt;");
//        customMap.put(">", "&gt;");
//        customMap.put("&", "&amp;");
//
//        //Collections.unmodifiableMap: create immutable map, can't add, update or delete
//        Map<CharSequence, CharSequence> CUSTOM_ESCAPE = Collections.unmodifiableMap(customMap);
//
//        // Specify special characters to prevent XSS
//        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
//        asciiEscapes[ '<' ] = CharacterEscapes.ESCAPE_CUSTOM;
//        asciiEscapes[ '>' ] = CharacterEscapes.ESCAPE_CUSTOM;
//        asciiEscapes[ '&' ] = CharacterEscapes.ESCAPE_CUSTOM;
//        asciiEscapes[ '(' ] = CharacterEscapes.ESCAPE_CUSTOM;
//        asciiEscapes[ ')' ] = CharacterEscapes.ESCAPE_CUSTOM;
//
//        // Handle prevent XSS: encode specify special characters
//        translator = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE),
//                // <, >, &, " are included here
//                new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE),
//                new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE), new LookupTranslator(CUSTOM_ESCAPE));
//    }
//
//    @Override
//    public int[] getEscapeCodesForAscii() {
//        return asciiEscapes;
//    }
//
//    @Override
//    public SerializableString getEscapeSequence(int ch) {
//        return new SerializedString(translator.translate(Character.toString((char) ch)));
//        // if no custom => user default by Apache Commons Text as below
//        // return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char) ch)));
//    }
//
//}
