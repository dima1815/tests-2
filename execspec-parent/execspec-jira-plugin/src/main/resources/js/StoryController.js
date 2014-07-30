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

        AJS.$(function () {
            var storyService = new StoryService();
            var projectKey = new PageUtils().getProjectKey();
            storyService.fetchStepDocs(projectKey, function (foundStepDocs) {
                storyController.stepDocs = foundStepDocs;
            });
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

        this.loadStory();

        storyController.debug("# init");
    }

    this.onEditorChangeHandler = function () {
        storyController.debug("> onEditorChangeHandler");

        if (storyController.storyChanged == false) {
            var saveCancelMsg = execspec.viewissuepage.showstory.renderSaveCancelMsg();
            storyController.showWarningMessage(saveCancelMsg);
        }
        storyController.storyChanged = true;

        storyController.updateEditedStepStyle();

        storyController.debug("# onEditorChangeHandler");
    }

    this.updateEditedStepStyle = function () {

        storyController.debug("> updateEditedStepStyle");

        var editor = storyController.editor;
        var cursor = editor.getCursor();
        var line = cursor.line;
        storyController.debug("line being edited - " + line);

        var tokenAt = editor.getTokenAt(cursor, true);
        storyController.debug("tokenAt.type - " + tokenAt.type);

//        if (tokenAt.type == "step-body") {
        if (tokenAt.state.inStepBody) {
            storyController.debug("### modifying step ...");
            var doc = editor.getDoc();
            var stepBody = tokenAt.state.stepBodySoFar;
            storyController.debug("stepBodySoFar - " + stepBody);
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
                        stepBody = nextToken.state.stepBodySoFar;
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
                                    var beforeParam = step.substring(0, pgi.startIndex);
                                    var numOfLinesInBefore = beforeParam.split(/\n/).length;
                                    var startAtLine = lastStepStartedAt + (numOfLinesInBefore - 1);
                                    pgi.startAtLine = startAtLine;
                                    var lastLineBreakPos = beforeParam.lastIndexOf("\n");
                                    if (lastLineBreakPos == -1) {
                                        lastLineBreakPos = 0;
                                    }
                                    pgi.startAtLineCh = pgi.startIndex - lastLineBreakPos;
                                    // end position
                                    var includingParam = step.substring(0, (pgi.startIndex + matchedGroup.length));
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
        this.editor.off("change", storyController.onEditorChangeHandler);
        this.editor.setValue(storyModel.asString);
        this.editor.on("change", storyController.onEditorChangeHandler);
        storyController.storyChanged = false;

        this.editor.setOption("readOnly", false);

        this.debug("# showStory");

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
        templateParam.currentStoryVersion = this.currentStory.version;

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
