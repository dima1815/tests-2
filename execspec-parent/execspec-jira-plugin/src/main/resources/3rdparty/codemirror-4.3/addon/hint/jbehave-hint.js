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

    // fetch step hints
    var stepDocs = null;
    AJS.$(function() {
        var storyService = new StoryService();
        var projectKey = new PageUtils().getProjectKey();
//        console.log("projectKey - " + projectKey);
        storyService.fetchStepDocs(projectKey, function (foundStepDocs) {
            stepDocs = foundStepDocs;
        });
    });

    CodeMirror.registerHelper("hint", "jbehave", function (editor, options) {

        console.log("########################## in autocomplete");

        options.completeSingle = false;
        options.closeOnUnfocus = false;

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
        for (var k = 0; k < stepDocs.length; k++) {
            var stepHint = new Object();
            stepHint.text = stepDocs[k].startingWord + " " + stepDocs[k].pattern;

            stepHint.displayText = "<span>Display:</span> " + stepHint.text;

            stepHint.render = function (element, data, self) {

                console.log("### Rendering hint");
//                var hintDiv = document.createElement("div");
                element.innerHTML = "<span style='font-weight: bold;'>span</span>: " + self.text;
//                element.appendChild(hintDiv);

            };

            list.push(stepHint);
        }

        return {
            list: list,
            from: CodeMirror.Pos(cur.line, 0),
            to: CodeMirror.Pos(cur.line, cur.ch)
        };
    });
});
