var storyController;
//var storyView;
var storyService;
var pageUtils;

function StoryController() {

    this.debugOn = true;

    storyController = this;
    storyService = new StoryService();
    pageUtils = new PageUtils();

    this.debug = function (msg) {
        if (this.debugOn) {
            console.log("[DEBUG StoryController] " + msg);
        }
    }

    this.editor = null;
    this.currentStory = null;
    this.storyChanged = false;
    this.stepDocs = null;

    this.init = function () {

        storyController.debug("> init");

        var storyService = new StoryService();
        var projectKey = new PageUtils().getProjectKey();
        storyService.fetchStepDocs(projectKey, function (foundStepDocs) {
            storyController.stepDocs = foundStepDocs;
        });

        var storyPanelContent = execspec.viewissuepage.showstory.renderStoryPanel();
        AJS.$("#story-panel").html(storyPanelContent);

        CodeMirror.commands.autocomplete = function (cm) {
            cm.showHint({hint: CodeMirror.hint.jbehave});
        }
        var editor = CodeMirror.fromTextArea(document.getElementById("storyTextArea"), {
            mode: "jbehave",
//            lineComment: "!--",
            lineNumbers: true,
            extraKeys: {
                "Ctrl-Space": "autocomplete",

                // commenting
                "Ctrl-/": function (cm) {


                    console.log("commenting!");

                    var startOfSelection = cm.getCursor(true);
                    var endOfSelection = cm.getCursor(false);

                    var curLine = cm.getLine(startOfSelection.line);
                    console.log("curLine - " + curLine);

                    var from = {line: startOfSelection.line, ch: startOfSelection.ch};
                    var to = {line: endOfSelection.line, ch: endOfSelection.ch};
                    var options = new Object();

                    if (curLine.substring(0, 1) == "|") {
                        options.lineComment = "|--";
//                        options.uncommentFrom = 1;
//                        options.uncommentTo = 3;
//                        options.commentFrom = 1;
                    } else {
                        options.lineComment = "!--";
                    }

                    options.padding = "";

                    if (curLine.substring(0, 3) == options.lineComment) {
                        cm.uncomment(from, to, options);
                    } else {
                        cm.lineComment(from, to, options);
                    }

//                    storyController.editor.uncomment(from, to, options);

                }
//                "F11": function(cm) {
//                    cm.setOption("fullScreen", !cm.getOption("fullScreen"));
//                },
//                "Esc": function(cm) {
//                    if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
//                }
            }
        });
        this.editor = editor;
        editor.on("change", this.onEditorChangeHandler);
//
//        editor.on("beforeChange", function() {
//           storyController.debug("##### on beforeChange");
//        });

        editor.on("update", function (editor, param) {
            storyController.debug("##### on editor UPDATE");
//            storyController.onEditorUpdateHandler(editor);
        });

//        editor.on("keypress", function () {
//            storyController.debug("##### on editor 'keypress'");
////            this.onEditorChangeHandler
//        });
//
//        editor.on("change", function (editor, changeObj) {
//            storyController.debug("##### on editor CHANGE");
//            storyController.onEditorChangeHandler(editor, changeObj);
//            var paramStart = {line: 0, ch: 0};
//            var paramEnd = {line: 1, ch: 10};
//            var options = new Object();
//            options.className = "test-marker";
//            editor.getDoc().markText(paramStart, paramEnd, options);
//        });

        this.loadStory();

        storyController.debug("# init");
    }

    this.lineStartsWithStepKeyword = function (lineNumber) {

        var lineHandle = this.editor.getLineHandle(lineNumber);
        var lineText = lineHandle.text;

        var regExpPattern = new RegExp("^(Given|When|Then|And)\\s+");
        var matchedResult = regExpPattern.exec(lineText);
        if (matchedResult != null) {
            return true;
        } else {
            return false;
        }
    }

    this.findStepStartingLineBefore = function (lineNumber) {
        var previousLine = lineNumber - 1;
        while (previousLine != -1) {
            if (this.lineStartsWithStepKeyword(previousLine)) {
                return previousLine;
            }
            previousLine--;
        }
        return -1;
    }

    this.findStepStartingLineAfter = function (lineNumber) {
        var nextLine = lineNumber + 1;
        var totalLines = this.editor.lineCount();
        while (nextLine < totalLines) {
            if (this.lineStartsWithStepKeyword(nextLine)) {
                return nextLine;
            }
            nextLine++;
        }
        return -1;
    }

    this.lineStartsWithScenarioOrExamles = function (lineNumber) {

        var lineHandle = this.editor.getLineHandle(lineNumber);
        var lineText = lineHandle.text;

        if (lineText.match(/^Examples: /)) {

        }

        if (lineText.substring(0, "Examples: ".length) == "Examples: "
            || lineText.substring(0, "Scenario ".length) == "Scenario ") {
            return true;
        } else {
            return false;
        }

    }

    this.findLastStepLineFrom = function (lineNumber) {

        var nextLineNumber = lineNumber + 1;
        var lineCount = this.editor.getDoc().lineCount();
        var lastStepLine = lineNumber;
        while (nextLineNumber < lineCount) {
            if (this.lineStartsWithStepKeyword(nextLineNumber)) {
                break;
            } else {
                lastStepLine = nextLineNumber;
                nextLineNumber++;
            }
        }

        return lastStepLine;
    }

    this.findMatchingStepdoc = function (step, stepType) {

        storyController.debug("> findMatchingStepdoc");

        // check if step matches
        var matchedResult = null;
        var matchingStepDoc = null;
        {
            step = step.replace(/\s+$/g, ''); // trim trailing whitespace;

            for (var i = 0; i < storyController.stepDocs.length; i++) {
                var stepDoc = storyController.stepDocs[i];
                if (stepDoc.startingWord == stepType) {
                    // try to see if the step docs pattern matches step body
                    var regExpStr = stepDoc.groupedRegExpPattern;
                    // replace the (.*) with ([\s\S]*) for javascript version of dotall option
                    regExpStr = regExpStr.replace("(.*)", "([\\s\\S]*)");
                    // add start and end chars to match the string exactly
                    regExpStr = "^" + regExpStr + "$";
                    storyController.debug("Trying to match the step body against pattern - " + regExpStr);
                    var regExpPattern = new RegExp(regExpStr);
                    var matchedResult = regExpPattern.exec(step);
                    if (matchedResult != null) {
                        storyController.debug("Step pattern - " + regExpStr + " matches current step body");
                        matchingStepDoc = stepDoc;
                        return new Object({result: matchedResult, stepDoc: matchingStepDoc});
                        break;
                    }
                }
            }
        }

        storyController.debug("# findMatchingStepdoc");

        return null;
    }

    this.remarkStep = function (stepStartLine, stepEndLine, step) {

        storyController.debug("> remarkStep");
        storyController.debug("stepStartLine - " + stepStartLine + ", stepEndLine - " + stepEndLine);
        storyController.debug("step:\n" + step);


        // extract step body i.e. without the starting keyword
        var regExpPattern = new RegExp("(^(Given|When|Then|And)\\s+)([\\s\\S]*)");
        var matchedResult = regExpPattern.exec(step);
        var keyword;
        var stepBody;
        var keywordPart;
        if (matchedResult != null) {
            keywordPart = matchedResult[1];
            keyword = matchedResult[2];
            stepBody = matchedResult[3];
        } else {
            console.error("Failed to match step against expected pattern, step - " + step + ", pattern - " + regExpPattern);
            return;
        }

        var findResult = this.findMatchingStepdoc(stepBody, keyword);

        var markerStart = {line: stepStartLine, ch: 0};
        var lastStepLineHandle = this.editor.getLineHandle(stepEndLine);
        var markerEnd = {line: stepEndLine, ch: lastStepLineHandle.text.length};
        var className = "matched-step";

        // remove any matched-step markers
        var markersBefore = this.editor.getDoc().findMarks(markerStart, markerEnd);
        if (markersBefore.length > 0) {
            // always remove any existing marks, so that we include newly edited text in the marked range
            for (var m = 0; m < markersBefore.length; m++) {
                var marker = markersBefore[m];
                if (marker.className == className
                    || marker.className == "step-parameter") {
                    marker.clear();
                }
            }
        }

        if (findResult != null) {

            // set matched-step markers
            var options = new Object();
            options.className = className;
            this.editor.getDoc().markText(markerStart, markerEnd, options);

            // match any parameters
            // obtain boundaries of any parameters
            var parameterGroupsInfos = [];
            var parameterGroups = findResult.stepDoc.parameterGroups;
            if (parameterGroups.length > 0) {
                var pos = 0;
                var lineOffset = keywordPart.split("\n").length - 1;
                for (var j = 1; j < findResult.result.length; j++) {
                    var matchedGroup = findResult.result[j];
                    if (parameterGroups.indexOf(j) > -1) {
                        var pgi = new Object();
                        pgi.number = j;
                        pgi.text = matchedGroup;
                        pgi.startIndex = pos;
                        pgi.startLineOffset = lineOffset;
                        pgi.endIndex = pos + matchedGroup.length;
                        pgi.endLineOffset = lineOffset + (matchedGroup.split("\n").length - 1);

                        // obtain line number and ch position
                        // start
                        pgi.startLine = stepStartLine + pgi.startLineOffset;
                        var beforeParam = stepBody.substring(0, pgi.startIndex);
                        var lastLineBreakInBefore = beforeParam.lastIndexOf("\n");
                        var parameterStartLineCh;
                        if (lastLineBreakInBefore > -1) {
                            parameterStartLineCh = pgi.startIndex - lastLineBreakInBefore;
                        } else {
                            parameterStartLineCh = pgi.startIndex;
                        }
                        if (pgi.startLineOffset == 0) {
                            // need to add the length of starting word also if on line 1
                            parameterStartLineCh += keywordPart.length;
                        }
                        pgi.startLineCh = parameterStartLineCh;
                        // end
                        pgi.endLine = stepStartLine + pgi.endLineOffset;
                        var includingParam = stepBody.substring(0, pgi.startIndex + pgi.text.length);
                        var lastLineBreakInIncludingParam = includingParam.lastIndexOf("\n");
                        var parameterEndLineCh;
                        if (lastLineBreakInIncludingParam > -1) {
                            parameterEndLineCh = pgi.startIndex + pgi.text.length - lastLineBreakInIncludingParam;
                        } else {
                            parameterEndLineCh = pgi.startIndex + pgi.text.length;
                        }
                        if (pgi.endLineOffset == 0) {
                            // need to add the length of starting word also
                            parameterEndLineCh += keywordPart.length;
                        }
                        pgi.endLineCh = parameterEndLineCh;

                        parameterGroupsInfos.push(pgi);
                    }
                    pos += matchedGroup.length;
                    var linesInGroup = matchedGroup.split(/\n/).length;
                    if (linesInGroup > 1) {
                        lineOffset += linesInGroup;
                    }
                }

                // mark any step parameters
                for (var k = 0; k < parameterGroupsInfos.length; k++) {
                    var pgi = parameterGroupsInfos[k];
                    var paramStart = {line: pgi.startLine, ch: pgi.startLineCh};
                    var paramEnd = {line: pgi.endLine, ch: pgi.endLineCh};
                    var paramMarkerOptions = new Object();
                    paramMarkerOptions.className = "step-parameter";
                    this.editor.getDoc().markText(paramStart, paramEnd, paramMarkerOptions);
                }

            }

        }

        storyController.debug("# remarkStep");
    }


    this.remarkStepBetween = function (stepStartLine, stepEndLine) {

        storyController.debug("> remarkStepBetween");
        storyController.debug("stepStartLine - " + stepStartLine + ", stepEndLine - " + stepEndLine);

        var step = "";
        this.editor.getDoc().eachLine(stepStartLine, stepEndLine + 1, function (lineHandle) {
            step += lineHandle.text + "\n";
        });

        this.remarkStep(stepStartLine, stepEndLine, step);

        storyController.debug("# remarkStepBetween");
    }

    this.remarkStepsBetween = function (scanStartLine, scanEndLine) {

        storyController.debug("> remarkStepsBetween");
        storyController.debug("scanStartLine - " + scanStartLine + ", scanEndLine - " + scanEndLine);

        var stepStartLine = scanStartLine;
        var stepEndLine = this.findLastStepLineFrom(scanStartLine);

        this.remarkStepBetween(stepStartLine, stepEndLine);

        while (stepEndLine < scanEndLine) {
            stepStartLine = stepEndLine + 1;
            stepEndLine = this.findLastStepLineFrom(stepStartLine);
            this.remarkStepBetween(stepStartLine, stepEndLine);
        }

        storyController.debug("# remarkStepsBetween");
    }

    this.remarkStepsOnChange = function (editor, changeObj) {

        storyController.debug("> onEditorChangeHandler");

        var fromLine = changeObj.from.line;
        var toLine = changeObj.to.line;

        // update toLine if the edited text contains more than one line and the result is greater than current toLine
        var linesInChangedText = changeObj.text.length;
        var toLineAfterChange = fromLine + (linesInChangedText - 1);
        if (toLineAfterChange > toLine) {
            toLine = toLineAfterChange;
        }

        var scanStartLine;

        // find a step which starts before or at fromLine
        var previousStepStartingLine = this.findStepStartingLineBefore(fromLine);
        if (previousStepStartingLine > -1) {
            scanStartLine = previousStepStartingLine;
        } else if (this.lineStartsWithStepKeyword(fromLine)) {
            scanStartLine = fromLine;
        } else {
            // we are not inside the step, so ignore event
            scanStartLine = null;
        }

        if (scanStartLine != null) {
            // find scanEndLine
            var scanEndLine = this.findLastStepLineFrom(toLine);
            this.remarkStepsBetween(scanStartLine, scanEndLine);
        }

        storyController.debug("# onEditorChangeHandler");
    }

    this.onEditorChangeHandler = function (editor, changeObj) {
        storyController.debug("> onEditorChangeHandler");

        if (storyController.storyChanged == false) {
            var saveCancelMsg = execspec.viewissuepage.showstory.renderSaveCancelMsg();
            storyController.showWarningMessage(saveCancelMsg);
        }
        storyController.storyChanged = true;

        storyController.remarkStepsOnChange(editor, changeObj);

        storyController.debug("# onEditorChangeHandler");
    }

    this.updateEditedStepStyle = function () {

        storyController.debug("> updateEditedStepStyle");

        var editor = storyController.editor;
        var cursor = editor.getCursor();
        var lineBeingEdited = cursor.line;
        storyController.debug("line being edited - " + lineBeingEdited);
        var doc = editor.getDoc();
        var lineHandle = doc.getLineHandle(lineBeingEdited);

        var tokenAtCurrentLine = editor.getTokenAt({line: lineBeingEdited, ch: lineHandle.text.length}, true);
//        if (tokenAtCurrentLine.state.inStepBody) {
        var stepTokensToRematch = [];
        var currentStepNumber = tokenAtCurrentLine.state.stepNumber;
        // if we are at the start of the line, we should rematch any previous step also if any
        if (cursor.ch == 0) {
            var previousLine = lineBeingEdited - 1;
            var previousLineHandle = doc.getLineHandle(previousLine);
            if (previousLineHandle != null) {
                var previousLineToken = editor.getTokenAt({line: previousLine, ch: previousLineHandle.text.length}, true);
                if (previousLineToken != null && previousLineToken.state.inStepBody && previousLineToken.state.stepNumber != currentStepNumber) {
                    stepTokensToRematch.push(previousLine);
//                        storyController.rematchStepSpanningLine(previousLine);
                }
            }
        }
        stepTokensToRematch.push(lineBeingEdited);
        if (stepTokensToRematch.length == 2) {
            // use case when we will rematch two steps one after the other
            // in this case we need to clear the markers in range of lines that span the two target steps
            var stepsStartedLine = previousLineToken.state.lastStepStartedAt;
            var stepsEndedLine = lineBeingEdited;
            var nextLine = stepsEndedLine + 1;
            var nextLineHandle = doc.getLineHandle(nextLine);
            if (nextLineHandle != null) {
                var nextLineToken = editor.getTokenAt({line: nextLine, ch: nextLineHandle.text.length}, true);
                while (nextLineHandle != null && nextLineToken.state.inStepBody && nextLineToken.state.stepNumber == currentStepNumber) {
                    stepsEndedLine = nextLine;
                    nextLine++;
                    nextLineHandle = doc.getLineHandle(nextLine);
                    if (nextLineHandle != null) {
                        nextLineToken = editor.getTokenAt({line: nextLine, ch: nextLineHandle.text.length}, true);
                    }
                }
            }
            var startOfMarkers = {line: stepsStartedLine, ch: 0};
            var lastStepsLine = doc.getLineHandle(stepsEndedLine);
            var endOfMarkers = {line: stepsEndedLine, ch: lastStepsLine.text.length};
            storyController.debug("### Going to rematch two consecutive steps, " +
                "going to clear markers between [" + startOfMarkers.line + ":" + startOfMarkers.ch + "]" +
                " and [" + endOfMarkers.line + ":" + endOfMarkers.ch + "]");
            var markersBefore = doc.findMarks(startOfMarkers, endOfMarkers);
            if (markersBefore.length > 0) {
                // always remove any existing marks, so that we include newly edited text in the marked range
                for (var m = 0; m < markersBefore.length; m++) {
                    var marker = markersBefore[m];
                    if (marker.className == "cm-matched-step"
                        || marker.className == "cm-unmatched-step"
                        || marker.className == "cm-step-parameter") {
                        marker.clear();
                    }
                }
            }
        }

        if (tokenAtCurrentLine.state.inStepBody) {
            for (var i = 0; i < stepTokensToRematch.length; i++) {
                storyController.rematchStepSpanningLine(stepTokensToRematch[i]);
            }
        }

//        }

        storyController.debug("# updateEditedStepStyle");
    }

    this.rematchStepSpanningLine = function (stepLine) {

        storyController.debug("> rematchStepSpanningLine");

        var editor = storyController.editor;
        var lineBeingEdited = stepLine;

        var doc = editor.getDoc();
        var lineHandle = doc.getLineHandle(lineBeingEdited);

        var tokenAtCurrentLine = editor.getTokenAt({line: lineBeingEdited, ch: lineHandle.text.length}, true);
        storyController.debug("tokenAtCurrentLine.type - " + tokenAtCurrentLine.type);

        // work out final token of step body
        var stepBodyToken = tokenAtCurrentLine;
        {
            var currentStepNumber = tokenAtCurrentLine.state.currentStepNumber;

            var lastStepLine = lineBeingEdited;
            var nextLineHandle = doc.getLineHandle(lastStepLine + 1);

            if (nextLineHandle != null) {
                var tokenAtNextLine = editor.getTokenAt({line: lastStepLine + 1, ch: nextLineHandle.text.length}, true);
                while (tokenAtNextLine != null && tokenAtNextLine.state.inStepBody && tokenAtNextLine.state.currentStepNumber == currentStepNumber) {
                    stepBodyToken = tokenAtNextLine;
                    lastStepLine++;
                    var nextLineHandle = doc.getLineHandle(lastStepLine + 1);
                    if (nextLineHandle != null) {
                        tokenAtNextLine = editor.getTokenAt({line: lastStepLine + 1, ch: nextLineHandle.text.length}, true);
                    } else {
                        tokenAtNextLine = null;
                    }
                }
            }
        }

        // check if step matches
        var matchedResult = null;
        var matchingStepDoc = null;
        {
            var lastStepType = stepBodyToken.state.lastStepType;
            storyController.debug("lastStepType - " + lastStepType);

            var stepDocs = storyController.stepDocs;
            var stepBody = stepBodyToken.state.stepBody;
            var stepBodyTrimmed = stepBody.replace(/\s+$/g, ''); // trim trailing whitespace;

            for (var i = 0; i < stepDocs.length; i++) {
                var stepDoc = stepDocs[i];
                if (stepDoc.startingWord == lastStepType) {
                    // try to see if the step docs pattern matches step body
                    var regExpStr = stepDoc.groupedRegExpPattern;
                    // replace the (.*) with ([\s\S]*) for javascript version of dotall option
                    regExpStr = regExpStr.replace("(.*)", "([\\s\\S]*)");
                    // add start and end chars to match the string exactly
                    regExpStr = "^" + regExpStr + "$";
                    storyController.debug("Trying to match the step body against pattern - " + regExpStr);
                    var regExpPattern = new RegExp(regExpStr);
                    var matchedResult = regExpPattern.exec(stepBodyTrimmed);
                    if (matchedResult != null) {
                        storyController.debug("Step pattern - " + regExpStr + " matches current step body");
                        matchingStepDoc = stepDoc;
                        break;
                    }
                }
            }
        }

        // obtain boundaries of any parameters
        var parameterGroupsInfos = [];
        {
            if (matchedResult != null) {
                var parameterGroups = matchingStepDoc.parameterGroups;
                if (parameterGroups.length > 0) {
                    var pos = 0;
                    var lineOffset = 0;
                    for (var j = 1; j < matchedResult.length; j++) {
                        var matchedGroup = matchedResult[j];
                        if (parameterGroups.indexOf(j) > -1) {
                            var pgi = new Object();
                            pgi.number = j;
                            pgi.text = matchedGroup;
                            pgi.startIndex = pos;
                            pgi.endIndex = pos + matchedGroup.length;
                            pgi.lineOffset = lineOffset;
                            parameterGroupsInfos.push(pgi);
                        }
                        pos += matchedGroup.length;
                        var linesInGroup = matchedGroup.split(/\n/).length;
                        if (linesInGroup > 1) {
                            lineOffset += linesInGroup;
                        }
                    }
                }
            }
        }

        // obtain start and end indexes of parameters in terms of line number and ch position
        {
            if (matchedResult != null && parameterGroupsInfos.length > 0) {
                var startingWord = stepBodyToken.state.stepStartingKeyword;
                var stepStartLine = stepBodyToken.state.lastStepStartedAt;
                for (var i = 0; i < parameterGroupsInfos.length; i++) {
                    var pgi = parameterGroupsInfos[i];
                    // start
                    var parameterStartLine = stepStartLine + pgi.lineOffset;
                    var beforeParam = stepBodyToken.state.stepBody.substring(0, pgi.startIndex);
                    var lastLineBreakInBefore = beforeParam.lastIndexOf("\n");
                    var parameterStartLineCh;
                    if (lastLineBreakInBefore > -1) {
                        parameterStartLineCh = pgi.startIndex - lastLineBreakInBefore;
                    } else {
                        parameterStartLineCh = pgi.startIndex;
                    }
                    if (pgi.lineOffset == 0) {
                        // need to add the length of starting word also
                        parameterStartLineCh += startingWord.length;
                    }
                    pgi.startLine = parameterStartLine;
                    pgi.startLineCh = parameterStartLineCh;
                    // end
                    var parameterEndLine = stepStartLine + pgi.lineOffset + (pgi.text.split("\n").length - 1);
                    var includingParam = stepBodyToken.state.stepBody.substring(0, pgi.startIndex + pgi.text.length);
                    var lastLineBreakInIncludingParam = includingParam.lastIndexOf("\n");
                    var parameterEndLineCh;
                    if (lastLineBreakInIncludingParam > -1) {
                        parameterEndLineCh = pgi.startIndex + pgi.text.length - lastLineBreakInIncludingParam;
                    } else {
                        parameterEndLineCh = pgi.startIndex + pgi.text.length;
                    }
                    if (pgi.lineOffset == 0 && lastLineBreakInIncludingParam == -1) {
                        // need to add the length of starting word also
                        parameterEndLineCh += startingWord.length;
                    }
                    pgi.endLine = parameterEndLine;
                    pgi.endLineCh = parameterEndLineCh;
                }
            }
        }

        // mark the step as unmatched / matched
        var stepStartedAtLine = stepBodyToken.state.lastStepStartedAt;
        var stepEndedAtLine = stepBodyToken.state.lineNumber;
        {
            var from = {line: stepStartedAtLine, ch: 0};
            var toLineHandle = doc.getLineHandle(stepEndedAtLine);
            var to = {line: stepEndedAtLine, ch: toLineHandle.text.length};
            var options = new Object();
            var marksBefore = doc.findMarks(from, to);
            if (marksBefore.length > 0) {
                // always remove any existing marks, so that we include newly edited text in the marked range
                for (var m = 0; m < marksBefore.length; m++) {
                    var marks = marksBefore[m];
                    if (marks.className == "cm-matched-step"
                        || marks.className == "cm-unmatched-step"
                        || marks.className == "cm-step-parameter") {
                        marks.clear();
                    }
                }
            }
            if (matchedResult != null) {
                options.className = "cm-matched-step";
            } else {
                options.className = "cm-unmatched-step";
            }
            doc.markText(from, to, options);
        }

        // mark parameter boundaries
        {
            if (parameterGroupsInfos.length > 0) {
                for (var k = 0; k < parameterGroupsInfos.length; k++) {
                    var pgi = parameterGroupsInfos[k];
                    var paramStart = {line: pgi.startLine, ch: pgi.startLineCh};
                    var paramEnd = {line: pgi.endLine, ch: pgi.endLineCh};
                    options.className = "cm-step-parameter";
                    doc.markText(paramStart, paramEnd, options);
                }
            }
        }

        storyController.debug("# rematchStepSpanningLine");
    }


    this.updateEditedStepStyle_backup = function () {

        storyController.debug("> updateEditedStepStyle");

        var editor = storyController.editor;
        var cursor = editor.getCursor();
        var line = cursor.line;
        storyController.debug("line being edited - " + line);

        var doc = editor.getDoc();
        var lineHandle = doc.getLineHandle(line);

        var checkTokenPos = {line: line, ch: lineHandle.text.length};
        var tokenAt = editor.getTokenAt(checkTokenPos, true);
        storyController.debug("tokenAt.type - " + tokenAt.type);

        if (tokenAt.state.inStep) {

            storyController.debug("### modifying step ...");

            var stepBody = tokenAt.state.stepBody;
            storyController.debug("stepBody - " + stepBody);
            var inStepBody = true;
            var nextLineNum = cursor.line;
            var stepEndedAtLine = cursor.line;
            if (cursor.ch != 0) {
                // if we are in a step and also at the start of the line, i.e. case of multiline steps
                // then we do not want to yet advance to the next line since we want to include the
                // step body of this step line also
                nextLineNum++;
            } else {
                stepEndedAtLine--;
            }

            var stepStartedAtLine = tokenAt.state.lastStepStartedAt;

            while (inStepBody && nextLineNum < doc.lineCount()) {
                var lineContent = editor.getLine(nextLineNum);
                if (lineContent == undefined) {
                    // we have reached the end of the story
                    break;
                } else if (lineContent.length == 0) {
                    // empty line so simply move onto next line
                    nextLineNum++;
                    stepEndedAtLine++;
                } else {
                    var nextToken = editor.getTokenAt({line: nextLineNum, ch: 1}, true);
                    if (nextToken.type != "step-body") {
                        inStepBody = false;
                    } else {
                        stepBody = nextToken.state.stepBody;
                        nextLineNum++;
                        stepEndedAtLine++;
                    }
                }
            }

            storyController.debug("Modifying step body:\n" + stepBody);
            stepBody = stepBody.replace(/\s+$/g, ''); // trim trailing whitespace
            storyController.debug("Modifying step body after trimming:\n" + stepBody);
            var lastStepType = tokenAt.state.lastStepType;
            storyController.debug("lastStepType - " + lastStepType);

            // check if step matches
            var stepMatched = false;
            var lastStepStartedAt = tokenAt.state.lastStepStartedAt;
            var stepBodyStartedAtCh = tokenAt.state.stepBodyStartedAtCh;
            var stepDocs = storyController.stepDocs;
            var parameterGroupsInfos = [];

            for (var i = 0; i < stepDocs.length; i++) {
                var stepDoc = stepDocs[i];
                if (stepDoc.startingWord == lastStepType) {
                    // try to see if the step docs pattern matches step body
                    var regExpStr = stepDoc.groupedRegExpPattern;
                    // replace the (.*) with ([\s\S]*) for javascript version of dotall option
                    regExpStr = regExpStr.replace("(.*)", "([\\s\\S]*)");
                    // add start and end chars to match the string exactly
                    regExpStr = "^" + regExpStr + "$";
                    storyController.debug("Trying to match the step against pattern - " + regExpStr);
                    var regExpPattern = new RegExp(regExpStr);
                    var matched = regExpPattern.exec(stepBody);
                    if (matched != null) {
                        var step = lastStepType + " " + stepBody;
                        storyController.debug("Step pattern - " + regExpStr + " matches current step");
                        stepMatched = true;
                        // obtain boundaries of any parameters
                        var parameterGroups = stepDoc.parameterGroups;
                        if (parameterGroups.length > 0) {
                            var pos = stepBodyStartedAtCh;
                            for (var j = 1; j < matched.length; j++) {
                                var matchedGroup = matched[j];
                                if (parameterGroups.indexOf(j) > -1) {
                                    var pgi = new Object();
                                    pgi.number = j;
                                    pgi.text = matchedGroup;
                                    pgi.startIndex = pos;
                                    pgi.endIndex = pos + matchedGroup.length;

                                    // work out start and end boundaries in terms of line and line position
                                    // start position
                                    var beforeParam = step.substring(0 + lastStepType.length, pgi.startIndex + lastStepType.length);
                                    var numOfLinesInBefore = beforeParam.split(/\n/).length;
                                    var startAtLine = lastStepStartedAt + (numOfLinesInBefore - 1);
                                    pgi.startAtLine = startAtLine;
                                    var lastLineBreakPos = beforeParam.lastIndexOf("\n");
                                    if (lastLineBreakPos == -1) {
                                        lastLineBreakPos = 0;
                                    }
                                    pgi.startAtLineCh = pgi.startIndex - lastLineBreakPos;
                                    // end position
                                    var includingParam = step.substring(0 + lastStepType.length, (pgi.startIndex + matchedGroup.length + lastStepType.length));
                                    var numOfLinesInIncludingParam = includingParam.split(/\n/).length;
                                    var endAtLine = lastStepStartedAt + (numOfLinesInIncludingParam - 1);
                                    pgi.endAtLine = endAtLine;
                                    lastLineBreakPos = includingParam.lastIndexOf("\n");
                                    if (lastLineBreakPos == -1) {
                                        lastLineBreakPos = 0;
                                    }
                                    pgi.endAtLineCh = pgi.endIndex - lastLineBreakPos;

                                    parameterGroupsInfos.push(pgi);
                                }
                                pos += matchedGroup.length;
                            }
                        }
                        break;
                    }
                }
            }

            var from = {line: stepStartedAtLine, ch: 0};
            var to = {line: stepEndedAtLine, ch: null};
            var options = new Object();

            var marksBefore = doc.findMarks(from, to);

            if (marksBefore.length > 0) {
                // always remove any existing marks, so that we include newly edited text in the marked range
                for (var m = 0; m < marksBefore.length; m++) {
                    marksBefore[m].clear();
                }
            }

            if (stepMatched) {
                options.className = "cm-matched-step";
            } else {
                options.className = "cm-unmatched-step";
            }
            doc.markText(from, to, options);

            var marksAfter = doc.findMarks(from, to);

            // mark parameter boundaries
            if (parameterGroupsInfos.length > 0) {
                for (var k = 0; k < parameterGroupsInfos.length; k++) {
                    var pgi = parameterGroupsInfos[k];
                    var startLine = pgi.startAtLine;
                    var startCh = pgi.startAtLineCh;
                    var paramStart = {line: startLine, ch: startCh};
                    var endLine = pgi.endAtLine;
                    var endCh = pgi.endAtLineCh;
                    var paramEnd = {line: endLine, ch: endCh};
                    options.className = "cm-step-parameter";
                    doc.markText(paramStart, paramEnd, options);
                }
            }
        }

        storyController.debug("# updateEditedStepStyle");
    }

    this.showWarningMessage = function (saveCancelMsg) {
        AJS.$("#storyMsgBar").empty();
        AJS.messages.warning("#storyMsgBar", {
            title: null,
            id: "storyWarningMsg",
            body: saveCancelMsg,
            closeable: false
        });
    }

    this.showSuccessMessage = function (saveCancelMsg) {
        AJS.$("#storyMsgBar").empty();
        AJS.messages.success("#storyMsgBar", {
            title: null,
            fadeout: true,
            delay: 3000,
            body: saveCancelMsg,
            closeable: true
        });
    }

    this.loadStory = function () {

        this.debug("> loadStory");

        var issueKey = pageUtils.getIssueKey();
        var projectKey = pageUtils.getProjectKey();

        storyService.find(projectKey, issueKey,

            function (storyPayload) {

                storyController.debug("> loadStory.callback");

                if (storyPayload != undefined) {
                    storyController.debug("found storyPayload - " + JSON.stringify(storyPayload, null, "\t"));
                } else {
                    storyController.debug("no story found for project - " + projectKey + ", issue - " + issueKey);
                    storyPayload = new StoryModel();
                    storyPayload.projectKey = pageUtils.getProjectKey();
                    storyPayload.issueKey = pageUtils.getIssueKey();
                    storyPayload.asString = "";
                }

                storyController.showStory(storyPayload);

                storyController.debug("# loadStory.callback");
            }

        );

        this.debug("# loadStory");
    }

    this.showStory = function (storyModel) {

        this.debug("> showStory");

        this.currentStory = storyModel;

//        this.editor.off("change", storyController.onEditorChangeHandler);
        this.editor.setValue(storyModel.asString);
//        this.editor.on("change", storyController.onEditorChangeHandler);

        storyController.storyChanged = false;

        this.editor.setOption("readOnly", false);

        this.debug("# showStory");

        // rescan step matching
        var firstStepStartingLine = this.findStepStartingLineAfter(-1);
        if (firstStepStartingLine != -1) {
            var lastStepStartingLine = this.findStepStartingLineBefore(this.editor.lineCount());
            var lastStepEndingLine = this.findLastStepLineFrom(lastStepStartingLine);
            this.remarkStepsBetween(firstStepStartingLine, lastStepEndingLine);
        }

        if (storyModel.version != null) {
            // this is NOT a new story, so check and show any story reports
            var projectKey = storyModel.projectKey;
            var issueKey = storyModel.issueKey;
            storyService.findStoryReports(projectKey, issueKey,
                function (storyReportsPayload) {

                    storyController.debug("> findStoryReports.callback");

                    if (storyReportsPayload != undefined && storyReportsPayload.storyTestReports.length != 0) {
                        storyController.debug("found storyReportsPayload - " + JSON.stringify(storyReportsPayload, null, "\t"));
                        storyController.showStoryReports(storyReportsPayload.storyTestReports);
                    } else {
                        storyController.debug("no story reports were found for project - " + projectKey + ", issue - " + issueKey);
                    }

                    storyController.debug("# findStoryReports.callback");
                }

            );

        }
    }

    this.showStoryReports = function (storyTestReports) {

        this.debug("> showStoryReports");

        var templateParam = new Object();
        templateParam.storyTestReports = storyTestReports;
        templateParam.currentStoryVersion = storyController.currentStory.version;

        var storyReportsContent = execspec.viewissuepage.showstoryreports.renderStoryReports(templateParam);

        AJS.$('#storyReportsPanel').html(storyReportsContent);
        AJS.tabs.setup();

        this.debug("# showStoryReports");
    }


    this.saveStory = function (event) {

        this.debug("> saveStory");
        event.preventDefault();

        var storyBeingSaved = new StoryModel();
        storyBeingSaved.projectKey = this.currentStory.projectKey;
        storyBeingSaved.issueKey = this.currentStory.issueKey;
        storyBeingSaved.version = this.currentStory.version;
        var storyInputAsText = this.editor.getValue();
        storyBeingSaved.asString = storyInputAsText;

        var storyPayload = JSON.stringify(storyBeingSaved, null, "\t");
        this.debug("saving story:\n" + storyPayload);

        var waitingMsg = execspec.viewissuepage.showstory.renderWaitingMessage();
//        storyController.showWarningMessage(waitingMsg);
        AJS.$('#storyEditedMsgContainer').html(waitingMsg);
        AJS.$('.save-story-button-spinner').spin();

        this.editor.setOption("readOnly", true);

        storyService.saveOrUpdateStory(storyPayload, function (savedStory) {
            storyController.debug("> saveOrUpdateStory callback");
//            storyView.showStoryReportButtons(savedStory);
            var jsonStory = JSON.stringify(savedStory, null, "\t");
            storyController.debug("saved story:\n" + jsonStory);

            storyController.showSuccessMessage("Story was saved successfully!");
            storyController.showStory(savedStory);
            storyController.debug("# saveOrUpdateStory callback");
        });


        this.debug("# saveStory");
    }

    this.cancelEdit = function (event) {

        this.debug("> cancelEditingStory");
        event.preventDefault();

        this.showStory(this.currentStory);

        // hide story edited message
        AJS.$("#storyMsgBar").empty();

        this.debug("# cancelEditingStory");
    }

}

AJS.$(function () {
    var ctr = new StoryController();
    ctr.init();
});

AJS.$(function () {
    // handling page updates in response to inline editing of other jira fields
    JIRA.bind(JIRA.Events.NEW_CONTENT_ADDED, function (e, context, reason) {
//            console.log("reason");
        if (reason != "inlineEditStarted ") {
            var ctr = new StoryController();
            ctr.init();
        }
    });
});
