var storyController;
var storyView;
var storyService;
var pageUtils;

function StoryController() {

    this.debugOn = true;

    storyController = this;

    storyView = new StoryView(this);
    storyView.init();

    storyService = new StoryService();
    storyService.init();

    pageUtils = new PageUtils();
    pageUtils.init();

    this.currentStory = new StoryModel();
    this.editMode = false;

    this.debug = function (msg) {
        if (this.debugOn) {
            console.log("[DEBUG StoryController] " + msg);
        }
    }

    this.loadStory = function () {

        this.debug("> loadStory");
        var issueKey = pageUtils.getIssueKey();
        var projectKey = pageUtils.getProjectKey();
        storyService.find(projectKey, issueKey,
            function (story) {
                storyView.debug("> loadStory.callback, story - " + story);
                if (story != undefined) {
//                    storyView.showStoryButton(story);
//                    storyView.showStoryReportButtons(story); // TODO
                    storyController.currentStory = story;
                    storyController.showStoryHandler();
                } else {
                    storyController.debug("no story found for project - " + projectKey + ", issue - " + issueKey);
                    storyView.showAddStory();
                }
            }
        );
        this.debug("# loadStory");
    }

    this.addStoryHandler = function (event) {

        this.debug("> addStoryHandler");

        var projectKey = pageUtils.getProjectKey();
        storyService.fetchNewStoryTemplate(projectKey, function (story) {
            storyController.debug("> addStoryHandler.callback");

            story.issueKey = pageUtils.getIssueKey();
            story.path = pageUtils.getIssueKey() + ".story";
            storyView.editStory(story);
            storyController.currentStory = story;

            storyController.debug("# addStoryHandler.callback");
        });

        event.preventDefault();
        this.debug("# addStoryHandler");
    }

    this.showStoryHandler = function () {

        this.debug("> showStoryHandler");
        storyView.showStory(this.currentStory, this.editMode);
        this.debug("# showStoryHandler");
    }

    this.showStoryReport = function (environment) {

        this.debug("> showStoryReport");
        this.debug("environment - " + environment);

        // find the report for environment
        var reportForEnvironment = undefined;
        for (var i = 0; i < this.currentStory.storyReports.length; i++) {
            var storyReport = this.currentStory.storyReports[i];
            if (storyReport.environment == environment) {
                reportForEnvironment = storyReport;
                break;
            }
        }
        storyView.showStoryReport(reportForEnvironment, this.currentStory.version);

        this.debug("# showStoryReport");
    }

//    this.addStory = function () {
//
//        this.debug("> addStory");
//
//        var story = new StoryModel();
//        story.projectKey = pageUtils.getProjectKey();
//        story.issueKey = pageUtils.getIssueKey();
//
//        var newStoryAsString = "narrative:";
//        newStoryAsString += "\nIn order to ";
//        newStoryAsString += "\nAs a ";
//        newStoryAsString += "\nI want to ";
//        newStoryAsString += "\n\nScenario: test scenario";
//        newStoryAsString += "\nGiven something none existent";
//        story.asString = newStoryAsString;
//
//        // TODO - temp
//        story.meta = new Object();
//        story.meta.properties = [];
//        var metaField = new Object();
//        metaField.name = "metaName";
//        metaField.value = "metaValue";
//        story.meta.properties.push(metaField);
//        var metaField2 = new Object();
//        metaField2.name = "meta2Name";
//        metaField2.value = "meta2Value";
//        story.meta.properties.push(metaField2);
//
//        storyService.saveOrUpdateStory(story,
//            function (story) {
//                storyController.currentStory = story;
//                storyView.showStory(story, storyController.editMode);
//                storyView.showStoryReportButtons(story);
//                // TODO remove the add story button from the menu
//            });
//
//        this.debug("# addStory");
//    }

    this.editStoryHandler = function () {

        this.debug("> editStoryHandler");
        this.debug("current story as string - " + this.currentStory.asString);
        this.editMode = true;
        storyView.showStory(this.currentStory, this.editMode);
        this.debug("# editStoryHandler");
    }

    this.clearStoryTests = function () {

        this.debug("> clearStoryTests");

        var projectKey = pageUtils.getProjectKey();
        var issueKey = pageUtils.getIssueKey();

        storyService.deleteStoryReports(projectKey, issueKey,
            function () {
                storyController.debug("story reports successfully deleted");
                // TODO remove the delete story button from the menu
                storyController.currentStory.storyReports = [];
                storyView.showStory(storyController.currentStory);
                storyView.showStoryReportButtons(storyController.currentStory);
            });

        this.debug("# clearStoryTests");
    }

    this.deleteStory = function () {

        this.debug("> deleteStory");

        var projectKey = pageUtils.getProjectKey();
        var issueKey = pageUtils.getIssueKey();

        storyService.deleteStory(projectKey, issueKey,
            function () {
                storyController.debug("story successfully deleted");
                // TODO remove the delete story button from the menu
                storyView.removeStory();
            });

        this.debug("# deleteStory");
    }

    this.saveStoryAsModel = function () {

        this.debug("> saveStoryAsModel");

        var storyPayload = JSON.stringify(this.currentStory);

        storyService.saveOrUpdateStory(storyPayload, function (savedStory) {
            storyController.debug("> saveStory.saveOrUpdateStory callback");
            storyController.editMode = false;
            storyView.showStory(savedStory, storyController.editMode);
            storyView.showStoryReportButtons(savedStory);
            storyController.currentStory = savedStory;
            storyController.debug("# saveStory.saveOrUpdateStory callback");
        });

        this.debug("# saveStoryAsModel");
    }

//    this.saveStory = function (event) {
//
//        this.debug("> saveStory");
//        event.preventDefault();
//
//        var model = new StoryModel();
//        var issueKey = pageUtils.getIssueKey();
//        model.issueKey = issueKey;
//        var projectKey = pageUtils.getProjectKey();
//        model.projectKey = projectKey;
//        var storyInput = storyView.getStoryInputAsString();
//        model.asString = storyInput;
//        model.version = this.currentStory.version;
//
//        storyService.saveOrUpdateStory(model, function (savedStory) {
//            storyController.debug("> saveStory.saveOrUpdateStory callback");
//            storyController.editMode = false;
//            storyView.showStory(savedStory, storyController.editMode);
//            storyView.showStoryReportButtons(savedStory);
//            storyController.currentStory = savedStory;
//            storyController.debug("# saveStory.saveOrUpdateStory callback");
//        });
//
//        this.debug("# saveStory");
//    }

    this.cancelEditingStory = function (event) {

        this.debug("> cancelEditingStory");
        event.preventDefault();

        this.editMode = false;
        storyView.showStory(this.currentStory, this.editMode);

        this.debug("# cancelEditingStory");
    }

    this.showAutoCompleteHandler = function () {

        this.debug("> showAutoComplete");

        var storyInputAsString = storyView.getStoryInputAsString();
        var caretPosition = storyView.getStoryInputCaretPosition();
        this.debug("caretPosition - " + caretPosition);
        var substring = storyInputAsString.substr(0, caretPosition);
        this.debug("substring - " + substring);

//        var lines = substring.split("\n");
        var projectKey = pageUtils.getProjectKey();
        storyService.autoComplete(projectKey, substring,
            function (data) {
                var entries = data.entries;
                storyView.showAutoComplete(entries);
            }
        );

        this.debug("# showAutoComplete");
    }
}

// function to obtain caret position in text area
AJS.$(function () {
    AJS.$.fn.caret = function () {

        var target = this[0];
        var isContentEditable = target.contentEditable === 'true';
        //get
        if (arguments.length == 0) {
            //HTML5
            if (window.getSelection) {
                //contenteditable
                if (isContentEditable) {
                    target.focus();
                    var range1 = window.getSelection().getRangeAt(0),
                        range2 = range1.cloneRange();
                    range2.selectNodeContents(target);
                    range2.setEnd(range1.endContainer, range1.endOffset);
                    return range2.toString().length;
                }
                //textarea
                return target.selectionStart;
            }
            //IE<9
            if (document.selection) {
                target.focus();
                //contenteditable
                if (isContentEditable) {
                    var range1 = document.selection.createRange(),
                        range2 = document.body.createTextRange();
                    range2.moveToElementText(target);
                    range2.setEndPoint('EndToEnd', range1);
                    return range2.text.length;
                }
                //textarea
                var pos = 0,
                    range = target.createTextRange(),
                    range2 = document.selection.createRange().duplicate(),
                    bookmark = range2.getBookmark();
                range.moveToBookmark(bookmark);
                while (range.moveStart('character', -1) !== 0) pos++;
                return pos;
            }
            //not supported
            return 0;
        }
        //set
        if (pos == -1)
            pos = this[isContentEditable ? 'text' : 'val']().length;
        //HTML5
        if (window.getSelection) {
            //contenteditable
            if (isContentEditable) {
                target.focus();
                window.getSelection().collapse(target.firstChild, pos);
            }
            //textarea
            else
                target.setSelectionRange(pos, pos);
        }
        //IE<9
        else if (document.body.createTextRange) {
            var range = document.body.createTextRange();
            range.moveToElementText(target);
            range.moveStart('character', pos);
            range.collapse(true);
            range.select();
        }
        if (!isContentEditable)
            target.focus();
        return pos;

    };
});

AJS.$(function () {
    var ctr = new StoryController()
    ctr.loadStory();
});

//AJS.$(function () {
//    YUI().use('editor-inline', function(Y) {
//
//        var editor = new Y.InlineEditor({
//            content: '<strong>This is <em>a test</em></strong> <strong>This is <em>a test</em></strong> '
//        });
//
//        //Add the BiDi plugin
//        editor.plug(Y.Plugin.EditorBidi);
//
//        //Focusing the Editor when ready
//        editor.on('ready', function() {
//            editor.focus();
//        });
//
//        //Rendering the Editor.
//        editor.render('#yuiEditorPanel');
//
//    });
//    YUI().use('editor', function (Y) {
//
//        var rangeObject = new Object();
//        rangeObject.range = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20];
//        var content = execspec.viewissuepage.showstory.renderRichTextArea(rangeObject);
////        var content = "test";
//
//        var editor = new Y.EditorBase({
//            content: content
//        });
//
//        //Add the BiDi plugin
//        editor.plug(Y.Plugin.EditorBidi);
//
//        //Focusing the Editor when the frame is ready..
//        editor.on('frame:ready', function () {
//            this.focus();
//            var height = AJS.$("iframe").contents().height() + 40;
//            this.debug("setting editor height to - " + height);
//            AJS.$("#yuiEditorPanel").height(height);
//        });
//
//        //Rendering the Editor.
//        editor.render('#yuiEditorPanel');
//
//    });
//});


