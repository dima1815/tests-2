package com.mycomp.execspec.jiraplugin.dto.model.step;

/**
 * Created by Dmytro on 3/3/14.
 */
public enum TokenKind {

    /*e.g. 'In order to', 'Given', etc.*/
    keyword,
    /*regular text*/
    text,
    /*e.g. step parameter*/
    parameter,
    //e.g. !--
    comment,
    //e.g. |value|etc.|
    table,

}
