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

    this.init  = function () {

        var storyPanelContent = execspec.viewissuepage.showstory.renderStoryPanel();
        AJS.$("#story-panel").html(storyPanelContent);

        CodeMirror.commands.autocomplete = function (cm) {
            cm.showHint({hint: CodeMirror.hint.jbehave});
        }
        var editor = CodeMirror.fromTextArea(document.getElementById("storyTextArea"), {
            mode: "jbehave",
//            lineComment: "!--",
            extraKeys: {
                "Ctrl-Space": "autocomplete",

                // commenting
                "Ctrl-/": function(cm) {


                    console.log("commenting!");

                    var startOfSelection = cm.getCursor(true);
                    var endOfSelection = cm.getCursor(false);

                    var curLine = cm.getLine(startOfSelection.line);
                    console.log("curLine - " + curLine);

                    var from = {line: startOfSelection.line, ch: startOfSelection.ch};
                    var to = {line: endOfSelection.line, ch: endOfSelection.ch};
                    var options = new Object();

                    if (curLine.substring(0,1) == "|") {
                        options.lineComment = "|--";
                        options.uncommentFrom = 1;
                        options.uncommentTo = 3;
                        options.commentFrom = 1;
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
    }

    this.onEditorChangeHandler = function () {
        storyController.debug("> onEditorChangeHandler");

        if (storyController.storyChanged == false) {
            var saveCancelMsg = execspec.viewissuepage.showstory.renderSaveCancelMsg();
            storyController.showWarningMessage(saveCancelMsg);
        }

        storyController.storyChanged = true;

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
