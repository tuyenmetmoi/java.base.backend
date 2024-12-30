package com.tuyendv.web.backend.api.common;

public enum GlobalEnum {

    /************ Start Table/Prefix SYSTEM **************/

    // table FILE_MNG
    TABLE_FILE_MNG("FILE_MNG"),
    PREFIX_FILE_MNG("FIM"),

    /************ End Table/Prefix SYSTEM **************/


    /************ Start Table/Prefix Domain **************/
    /************ End Table/Prefix Domain **************/

    //
    AnonymousUser_String("anonymousUser"),

    // Ip address
    INTERNAL_IP("Internal"),
    EXTERNAL_IP("External");

    private Object value;

    private GlobalEnum(Object s) {
        value = s;
    }

    public <T> T getValue() {
        return (T) value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
