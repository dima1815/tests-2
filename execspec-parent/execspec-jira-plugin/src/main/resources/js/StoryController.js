var storyController;
//var storyView;
var storyService;
var pageUtils;

function StoryController() {

    this.debugOn = true;

    this.autoAlignTables = true;

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
//            lineNumbers: true,
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

//        AJS.$('#storyEditorSettingsTrigger').fadeToggle();
//        AJS.$("#storyEditorHeader").mouseenter(function (event) {
//            AJS.$('#storyEditorSettingsTrigger').fadeToggle(300);
//        });
//        AJS.$("#storyEditorHeader").mouseleave(function (event) {
//            AJS.$('#storyEditorSettingsTrigger').fadeToggle(300);
//        });

        AJS.$("#showLineNumbersTrigger").click(function (event) {
            var target = event.target;
            if (AJS.$(target).hasClass("checked")) {
                storyController.debug("hiding line numbers");
                storyController.editor.setOption("lineNumbers", false);
            } else {
                storyController.debug("showing line numbers");
                storyController.editor.setOption("lineNumbers", true);
            }

        });

        AJS.$("#autoAlignTableParamsTrigger").click(function (event) {
            var target = event.target;
            if (AJS.$(target).hasClass("checked")) {
                storyController.debug("auto align table parameters -  Off");
                storyController.autoAlignTables = false;
            } else {
                storyController.debug("auto align table parameters -  On");
                storyController.autoAlignTables = true;
                storyController.alignTablesInWholeDoc();
            }

        });

        AJS.$("#autoInsertTabularParametersTrigger").click(function (event) {
            var target = event.target;
            if (AJS.$(target).hasClass("checked")) {
                storyController.debug("auto insert tabular parameters -  Off");
            } else {
                storyController.debug("auto insert tabular parameters -  On");
            }

        });

        this.loadStory();

        storyController.debug("# init");
    }

    this.alignTableBetween = function (tableStartLine, tableEndLine) {

        storyController.debug("> alignTableBetween");
        storyController.debug("tableStartLine - " + tableStartLine + ", tableEndLine - " + tableEndLine);

        // get max width for columns
        var maxColumnWidths = [];
        this.editor.getDoc().eachLine(tableStartLine, tableEndLine + 1, function (lineHandle) {
                var lineText = lineHandle.text;
                lineText = lineText.replace(/\s+$/g, ''); // trim any trailing spaces
                if (lineText.substring("|--".length) == "|--") {
                    // ignore table comment line
                } else if (lineText.length == 0) {
                    // ignore empty lines
                } else {
                    var tokens = lineText.split("|");
                    for (var i = 0; i < tokens.length; i++) {
                        var token = tokens[i];
                        var currentMax = maxColumnWidths[i];
                        if (currentMax == null || token.length > currentMax) {
                            var newMax = token.length;
                            maxColumnWidths[i] = newMax;
                        }
                    }
                }
            }
        );

        // align columns
        this.editor.getDoc().eachLine(tableStartLine, tableEndLine + 1, function (lineHandle) {
                var lineText = lineHandle.text;
                lineText = lineText.replace(/\s+$/g, ''); // trim any trailing spaces
                var currentLine = lineHandle.lineNo();
                if (lineText.substring("|--".length) == "|--") {
                    // ignore table comment line
                } else if (lineText.length == 0) {
                    // ignore empty lines
                } else {
                    var tokens = lineText.split("|");
                    var pos = 0;
                    for (var i = 0; i < tokens.length; i++) {
                        var token = tokens[i];
                        if (token != "") {
                            pos++; // for '|'
                        }
                        pos += token.length;
                        var difference = maxColumnWidths[i] - token.length;
                        if (difference > 0 && token.length > 0) {

                            var spaces = "";
                            while (difference > 0) {
                                spaces = spaces + " ";
                                difference--;
                            }

                            var tokenEndCh = pos;
                            // replace old token with new
                            storyController.editor.getDoc().replaceRange(spaces,
                                {line: currentLine, ch: tokenEndCh},
                                {line: currentLine, ch: tokenEndCh});

                            pos += spaces.length;
                        }
                    }
                }
            }
        );

        storyController.debug("# alignTableBetween");
    }

    this.alignTablesInWholeDoc = function () {

        storyController.debug("> alignTablesInWholeDoc");

        var tableStartLine = null;
        var currentLine;
        var previousLine;
        this.editor.getDoc().eachLine(function (lineHandle) {
            if (currentLine != null) {
                previousLine = currentLine;
            }
            currentLine = lineHandle.lineNo();
            var lineText = lineHandle.text;
            lineText = lineText.replace(/\s+$/g, ''); // trim any trailing spaces
            if (lineText.substr(0, 1) == "|") {
                // inside table line
                if (tableStartLine == null) {
                    tableStartLine = lineHandle.lineNo();
                }
            } else if (tableStartLine != null) {
                // we were already in the table before
                var tableEndLine = previousLine;
                if (tableEndLine > tableStartLine) {
                    storyController.alignTableBetween(tableStartLine, tableEndLine);
                }
                tableStartLine = null;
            }
        });

        storyController.debug("# alignTablesInWholeDoc");
    }

    this.lineStartsWithStepKeyword = function (lineNumber) {

        var lineHandle = this.editor.getLineHandle(lineNumber);
        if (lineHandle == null) {
            return false;
        } else {
            var lineText = lineHandle.text;
            var regExpPattern = new RegExp("^(Given|When|Then|And)\\s+");
            var matchedResult = regExpPattern.exec(lineText);
            if (matchedResult != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    this.lineStartsWithAndKeyword = function (lineNumber) {

        var lineHandle = this.editor.getLineHandle(lineNumber);
        var lineText = lineHandle.text;

        var regExpPattern = new RegExp("^(And)\\s+");
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

    this.findStepStartingLineInSameScenarioBefore = function (lineNumber) {
        var previousLine = lineNumber - 1;
        while (previousLine != -1) {
            if (this.lineStartsWithStepKeyword(previousLine)) {
                return previousLine;
            } else if (this.lineStartsWithScenario(previousLine)) {
                return -1;
            } else {
                previousLine--;
            }
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

    this.findTabularParameterStartingLineAfter = function (lineNumber, butNotAtOrAfter) {
        var nextLineNumber = lineNumber + 1;
        while (nextLineNumber < butNotAtOrAfter) {
            var nextLineHandle = storyController.editor.getLineHandle(nextLineNumber);
            if (nextLineHandle == null) {
                return -1;
            } else {
                if (nextLineHandle.text.substring(0, 1) == "|" && nextLineHandle.text.substring(0, 3) != "|--") {
                    return nextLineNumber;
                }
                nextLineNumber++;
            }
        }
        return -1;
    }

    this.findStepStartingLineInSameScenarioAfter = function (lineNumber) {
        var nextLine = lineNumber + 1;
        var totalLines = this.editor.lineCount();
        while (nextLine < totalLines) {
            if (this.lineStartsWithStepKeyword(nextLine)) {
                return nextLine;
            } else if (this.lineStartsWithScenarioOrExamples(nextLine)) {
                return -1;
            }
            nextLine++;
        }
        return -1;
    }

    this.lineStartsWithScenario = function (lineNumber) {

        var lineHandle = this.editor.getLineHandle(lineNumber);
        var lineText = lineHandle.text;
        var regExpPattern = new RegExp("^(Scenario:)\\s+");
        var matchedResult = regExpPattern.exec(lineText);
        if (matchedResult != null) {
            return true;
        } else {
            return false;
        }
    }

    this.lineStartsWithScenarioOrExamples = function (lineNumber) {

        var lineHandle = this.editor.getLineHandle(lineNumber);
        var lineText = lineHandle.text;
        var regExpPattern = new RegExp("^(Examples:|Scenario:)\\s+");
        var matchedResult = regExpPattern.exec(lineText);
        if (matchedResult != null) {
            return true;
        } else {
            return false;
        }
    }

    this.findTabularParameterEndingLineAfter = function (tableStartLine, notAtOrAfter) {
        var nextLineNumber = tableStartLine + 1;
        var lastStepLine = tableStartLine;
        while (nextLineNumber < notAtOrAfter) {
            var nextLineHandle = storyController.editor.getLineHandle(nextLineNumber);
            if (nextLineHandle == null) {
                return lastStepLine;
            } else {
                if (nextLineHandle.text.substring(0, 1) != "|") {
                    break;
                } else {
                    lastStepLine = nextLineNumber;
                    nextLineNumber++;
                }
            }
        }
        return lastStepLine;
    }

    this.findLastStepLineFrom = function (lineNumber) {

        var nextLineNumber = lineNumber + 1;
        var lineCount = this.editor.getDoc().lineCount();
        var lastStepLine = lineNumber;
        while (nextLineNumber < lineCount) {
            if (this.lineStartsWithStepKeyword(nextLineNumber)
                || this.lineStartsWithScenarioOrExamples(nextLineNumber)) {
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
                    var replacePattern = new RegExp("\\(\\.\\*\\)", "g");
                    regExpStr = regExpStr.replace(replacePattern, "([\\s\\S]*)");
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
            console.log("Failed to match step against expected pattern, step - " + step + ", pattern - " + regExpPattern);
            return;
        }

        var lookingAtLine = stepStartLine;
        while (keyword == "And") {
            // replace it with the previous steps keyword
            var previousStepStartLine = this.findStepStartingLineInSameScenarioBefore(lookingAtLine);

            if (previousStepStartLine != -1) {
                var previousStepFirstLine = this.editor.getLineHandle(previousStepStartLine).text;
                var result = regExpPattern.exec(previousStepFirstLine);
                keyword = result[2];
                lookingAtLine = previousStepStartLine;
            } else {
                // there is no previous step in this same scenario
                return;
            }
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
                            parameterStartLineCh = pgi.startIndex - lastLineBreakInBefore - 1;
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
                            parameterEndLineCh = pgi.startIndex + pgi.text.length - lastLineBreakInIncludingParam - 1;
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
                    lineOffset += (linesInGroup - 1);
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


    this.realignStepTableParameters = function (stepStartLine, stepEndLine) {

        storyController.debug("> realignStepTableParameters");

        var firstTableLine = storyController.findTabularParameterStartingLineAfter(stepStartLine, stepEndLine + 1);
        if (firstTableLine != -1) {
            var lastTableLine = storyController.findTabularParameterEndingLineAfter(stepStartLine, stepEndLine + 1);
            storyController.alignTableBetween(firstTableLine, lastTableLine);
        }

        storyController.debug("# realignStepTableParameters");
    }

    this.remarkStepBetween = function (stepStartLine, stepEndLine) {

        storyController.debug("> remarkStepBetween");
        storyController.debug("stepStartLine - " + stepStartLine + ", stepEndLine - " + stepEndLine);

        var step = "";
        this.editor.getDoc().eachLine(stepStartLine, stepEndLine + 1, function (lineHandle) {
            step += lineHandle.text + "\n";
        });

        this.remarkStep(stepStartLine, stepEndLine, step);

        this.realignStepTableParameters(stepStartLine, stepEndLine);

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
            if (this.lineStartsWithStepKeyword(stepStartLine)) {
                this.remarkStepBetween(stepStartLine, stepEndLine);
            }
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
        } else {
            // we are not inside the step, so ignore event
            scanStartLine = fromLine;
        }

        if (scanStartLine != null) {
            // find scanEndLine
            var scanEndLine = this.findLastStepLineFrom(toLine);

            var nextStepStartLine = this.findStepStartingLineInSameScenarioAfter(scanEndLine);
            if (nextStepStartLine != -1) {
                var startsWithAnd = this.lineStartsWithAndKeyword(nextStepStartLine);
                while (nextStepStartLine != -1 && startsWithAnd) {
                    scanEndLine = this.findLastStepLineFrom(nextStepStartLine);
                    nextStepStartLine = this.findStepStartingLineInSameScenarioAfter(scanEndLine);
                    if (nextStepStartLine != -1) {
                        startsWithAnd = this.lineStartsWithAndKeyword(nextStepStartLine);
                    }
                }
            }

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

        this.editor.off("change", storyController.onEditorChangeHandler);
        this.editor.setValue(storyModel.asString);
        this.editor.on("change", storyController.onEditorChangeHandler);

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
