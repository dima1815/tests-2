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

    var keywords = [
        {text: "Meta:", stateField: "allowMeta", stateTokenType: "meta-title"},
        {text: "Narrative:", stateField: "allowNarrative", stateTokenType: "narrative-title"},
        {text: "Scenario:", stateField: "allowScenario", stateTokenType: "scenario-keyword"},
        {text: "Given ", stateField: "allowSteps", stateTokenType: "step-keyword"},
        {text: "When ", stateField: "allowSteps", stateTokenType: "step-keyword"},
        {text: "Then ", stateField: "allowSteps", stateTokenType: "step-keyword"},
        {text: "And ", stateField: "allowAndStep", stateTokenType: "step-keyword-and"},
    ];

    CodeMirror.registerHelper("hint", "jbehave", function (editor, options) {

        console.log("########################## in autocomplete");

        var wordPattern = /[\w$]+/;
        var cur = editor.getCursor();

        var curLine = editor.getLine(cur.line);
        console.log("curLine - " + curLine);

        var cursorPos = cur.ch;
        var currentText = curLine.slice(0, cursorPos);
        console.log("currentText - " + currentText);

        var list = [];

        var token = editor.getTokenAt(cur);
        console.log("token.string - " + token.string);
        console.log("token.type - " + token.type);
        var state = token.state;

        // hint on keywords that must be at start of line
        for (var i = 0; i < keywords.length; i++) {
            var keyword = keywords[i]
            if ((state[keyword.stateField] || token.type == keyword.stateTokenType) && (cursorPos == 0 || keyword.text.indexOf(currentText) == 0)) {
                list.push(keyword.text);
            }
        }

        // hint on steps
        var stepHints = ["Given something", "Given something else"];

        if (state.lastStepType == "Given") {
            for (var i = 0; i < stepHints.length; i++) {
                var stepHint = stepHints[i];
                list.push(stepHint);
            }
        }
//        storyService.test();

        return {list: list, from: CodeMirror.Pos(cur.line, 0), to: CodeMirror.Pos(cur.line, cur.ch)};
    });
});
