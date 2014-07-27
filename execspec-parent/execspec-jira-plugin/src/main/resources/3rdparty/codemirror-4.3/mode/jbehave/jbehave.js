// CodeMirror, copyright (c) by Marijn Haverbeke and others
// Distributed under an MIT license: http://codemirror.net/LICENSE

/*
 Gherkin mode - http://www.cukes.info/
 Report bugs/issues here: https://github.com/marijnh/CodeMirror/issues
 */

// Following Objs from Brackets implementation: https://github.com/tregusti/brackets-gherkin/blob/master/main.js
//var Quotes = {
//  SINGLE: 1,
//  DOUBLE: 2
//};

//var regex = {
//  keywords: /(Feature| {2}(Scenario|In order to|As|I)| {4}(Given|When|Then|And))/
//};

(function (mod) {
    if (typeof exports == "object" && typeof module == "object") // CommonJS
        mod(require("../../lib/codemirror"));
    else if (typeof define == "function" && define.amd) // AMD
        define(["../../lib/codemirror"], mod);
    else // Plain browser env
        mod(CodeMirror);
})(function (CodeMirror) {
    "use strict";

    CodeMirror.defineMode("jbehave", function () {
        return {
            lineComment: "!--",
            tableLineComment: "|--",
            startState: function () {
                return {
                    lineNumber: 0,

                    allowDescription: true,
                    allowMeta: true,
                    allowMetaField: false,
                    allowNarrative: true,
                    allowNarrativeInOrderTo: false,
                    allowNarrativeAsA: false,
                    allowNarrativeIWantTo: false,

                    allowScenario: false,
                    allowSteps: false,

                    tableHeaderLine: false,
                    inMultilineString: false,
                    inMultilineTable: false,

                    lastStepType: null
                };
            },
            token: function (stream, state) {
                if (stream.sol()) {
                    state.lineNumber++;

                    state.inScenarioTitleLine = false;
                    state.inNarrativeField = false;
                }

                // LINE COMMENT
                if (stream.sol() && stream.match(/!--.*/)) {
                    return "comment";

                    // TABLE COMMENT
                } else if (stream.sol() && stream.match(/\|--.*/)) {
                    return "comment";

                    // META title
                } else if (state.allowMeta && stream.sol() && stream.match(/(Meta):/)) {

                    state.allowDescription = false;
                    state.allowMeta = false;
                    state.allowMetaField = true;
                    return "meta-title";

                    // META field
                } else if (state.allowMetaField && stream.sol() && stream.match(/@.*/)) {

                    return "meta-field"

                    // Narrative - title
                } else if (state.allowNarrative && stream.sol() && stream.match(/Narrative:/)) {

                    state.allowDescription = false;
                    state.allowMeta = false;
                    state.allowMetaField = false;
                    state.allowNarrative = false;
                    state.allowNarrativeInOrderTo = true;

                    state.allowScenario = true;
                    state.allowSteps = false;
                    return "narrative-title";

                    // Narrative - In order to - keyword
                } else if (state.allowNarrativeInOrderTo && stream.sol() && stream.match(/(In order to )/)) {

                    state.allowNarrativeInOrderTo = false;
                    state.allowNarrativeAsA = true;
                    state.inNarrativeField = true;

                    return "narrative-field-keyword";

                    // Narrative - As a - keyword
                } else if (state.allowNarrativeAsA && stream.sol() && stream.match(/(As a )/)) {

                    state.allowNarrativeAsA = false;
                    state.allowNarrativeIWantTo = true;
                    state.inNarrativeField = true;

                    return "narrative-field-keyword";

                    // Narrative - I want to - keyword
                } else if (state.allowNarrativeIWantTo && stream.sol() && stream.match(/(I want to )/)) {

                    state.allowNarrativeIWantTo = false;
                    state.inNarrativeField = true;

                    return "narrative-field-keyword";

                    // Narrative - field value
                } else if (state.inNarrativeField && stream.match(/.*/)) {

                    return "narrative-field-value";

                    // SCENARIO keyword
                } else if (state.allowScenario && stream.sol() && stream.match(/(Scenario):/)) {
                    state.allowSteps = true;
                    state.allowDescription = false;
                    state.inScenarioTitleLine = true;
                    state.lastStepType = null;
                    state.allowAndStep = false;
                    return "scenario-keyword";

                    // SCENARIO title
                } else if (state.inScenarioTitleLine && stream.match(/.*/)) {
                    return "scenario-title";

                    // GIVEN
                } else if (state.allowSteps && stream.sol() && stream.match(/(Given )/)) {
                    state.lastStepType = "Given";
                    state.allowAndStep = true;
                    return "step-keyword";

                    // WHEN
                } else if (state.allowSteps && stream.sol() && stream.match(/(When )/)) {
                    state.lastStepType = "When";
                    state.allowAndStep = true;
                    return "step-keyword";

                    // THEN
                } else if (state.allowSteps && stream.sol() && stream.match(/(Then )/)) {
                    state.lastStepType = "Then";
                    state.allowAndStep = true;
                    return "step-keyword";

                    // AND
                } else if (state.allowAndStep && stream.sol() && stream.match(/(And )/)) {
                    return "step-keyword-and";

                    // Description
                } else if (stream.sol() && state.allowDescription && stream.match(/(.*)/)) {

                    return "description-line";

                    // Fall through
                } else {
                    stream.next();
//                    stream.eatWhile(/[^@"<#]/);
                    return null;
                }
            }
        };
    });

    CodeMirror.defineMIME("text/x-feature", "gherkin");

});
