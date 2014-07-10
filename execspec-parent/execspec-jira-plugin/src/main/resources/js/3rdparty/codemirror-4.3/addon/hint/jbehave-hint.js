// CodeMirror, copyright (c) by Marijn Haverbeke and others
// Distributed under an MIT license: http://codemirror.net/LICENSE

(function (mod) {
    if (typeof exports == "object" && typeof module == "object") // CommonJS
        mod(require("../../lib/codemirror"));
    else if (typeof define == "function" && define.amd) // AMD
        define(["../../lib/codemirror"], mod);
    else // Plain browser env
        mod(CodeMirror);
})(function (CodeMirror) {
    "use strict";

    var WORD = /[\w$]+/, RANGE = 500;

    CodeMirror.registerHelper("hint", "jbehave", function (editor, options) {

        console.log("########################## in autocomplete");

        var word = options && options.word || WORD;
        var range = options && options.range || RANGE;
        var cur = editor.getCursor();

        var token1 = editor.getTokenAt(cur);
        console.log("token1.string - " + token1.string);
        console.log("token1.type - " + token1.type);

        var curLine = editor.getLine(cur.line);
        console.log("curLine - " + curLine);

        var start = cur.ch;
        console.log("start before - " + start);
        var end = start;
        console.log("end before - " + end);

        while (end < curLine.length && word.test(curLine.charAt(end))) ++end;
        while (start && word.test(curLine.charAt(start - 1))) --start;

        console.log("start after - " + start);
        console.log("end after - " + end);

        var curWord = start != end && curLine.slice(start, end);
        console.log("curWord - " + curWord);

        var list = [];

        var token = editor.getTokenAt(cur);
        console.log("token.string - " + token.string);
        console.log("token.type - " + token.type);

        var state = token.state;

        // hint on keywords that must be at start of line
        if (cur.ch == 0) {
            if (state.allowMeta) {
                list.push("Meta:");
            }
            if (state.allowNarrative) {

                list.push("Narrative:");
            }
            list.push("Scenario:");
        }

//        var seen = {};
//        var re = new RegExp(word.source, "g");
//        for (var dir = -1; dir <= 1; dir += 2) {
//            var line = cur.line, endLine = Math.min(Math.max(line + dir * range, editor.firstLine()), editor.lastLine()) + dir;
//            for (; line != endLine; line += dir) {
//                var text = editor.getLine(line), m;
//                while (m = re.exec(text)) {
//                    if (line == cur.line && m[0] === curWord) continue;
//                    if ((!curWord || m[0].lastIndexOf(curWord, 0) == 0) && !Object.prototype.hasOwnProperty.call(seen, m[0])) {
//                        seen[m[0]] = true;
//                        list.push(m[0]);
//                    }
//                }
//            }
//        }
        return {list: list, from: CodeMirror.Pos(cur.line, start), to: CodeMirror.Pos(cur.line, end)};
    });
});
