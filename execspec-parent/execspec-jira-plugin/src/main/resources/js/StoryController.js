var storyController;
var storyView;
var storyService;
var pageUtils;

function StoryController() {

    storyController = this;

    storyView = new StoryView(this);
    storyView.init();

    storyService = new StoryService();
    storyService.init();

    pageUtils = new PageUtils();
    pageUtils.init();

    this.currentStory = new StoryModel();
    this.editMode = false;

    this.loadStory = function () {

        console.log("> StoryController.showStory");
        var issueKey = pageUtils.getIssueKey();
        var projectKey = pageUtils.getProjectKey();
        storyService.find(projectKey, issueKey,
            function (story) {
                if (story != undefined) {
                    storyView.showStoryButton(story);
                    storyView.showStoryReportButtons(story);
                    storyView.showStory(story, storyController.editMode);
                    storyController.currentStory = story;
                } else {
                    console.log("no story found for project - " + projectKey + ", issue - " + issueKey);
                }
            }
        );
        console.log("# StoryController.showStory");
    }

    this.showStoryHandler = function () {

        console.log("> StoryController.showStoryHandler");
        storyView.showStory(this.currentStory, this.editMode);
        console.log("# StoryController.showStoryHandler");
    }

    this.showStoryReport = function (environment) {

        console.log("> StoryController.showStoryReport");
        console.log("environment - " + environment);

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

        console.log("# StoryController.showStoryReport");
    }

    this.addStory = function () {

        console.log("> StoryController.addStory");

        var story = new StoryModel();
        story.projectKey = pageUtils.getProjectKey();
        story.issueKey = pageUtils.getIssueKey();

        var newStoryAsString = "narrative:";
        newStoryAsString += "\nIn order to ";
        newStoryAsString += "\nAs a ";
        newStoryAsString += "\nI want to ";
        newStoryAsString += "\n\nScenario: test scenario";
        newStoryAsString += "\nGiven something none existent";
        story.asString = newStoryAsString;

        storyService.saveOrUpdateStory(story,
            function (story) {
                storyController.currentStory = story;
                storyView.showStory(story, storyController.editMode);
                storyView.showStoryReportButtons(story);
                // TODO remove the add story button from the menu
            });

        console.log("# StoryController.addStory");
    }

    this.editStoryHandler = function () {

        console.log("> StoryController.editStoryHandler");
        console.log("current story as string - " + this.currentStory.asString);
        this.editMode = true;
        storyView.showStory(this.currentStory, this.editMode);
        console.log("# StoryController.editStoryHandler");
    }

    this.clearStoryTests = function () {

        console.log("> StoryController.clearStoryTests");

        var projectKey = pageUtils.getProjectKey();
        var issueKey = pageUtils.getIssueKey();

        storyService.deleteStoryReports(projectKey, issueKey,
            function () {
                console.log("story reports successfully deleted");
                // TODO remove the delete story button from the menu
                storyController.currentStory.storyReports = [];
                storyView.showStory(storyController.currentStory);
                storyView.showStoryReportButtons(storyController.currentStory);
            });

        console.log("# StoryController.clearStoryTests");
    }

    this.deleteStory = function () {

        console.log("> StoryController.deleteStory");

        var projectKey = pageUtils.getProjectKey();
        var issueKey = pageUtils.getIssueKey();

        storyService.deleteStory(projectKey, issueKey,
            function () {
                console.log("story successfully deleted");
                // TODO remove the delete story button from the menu
                storyView.removeStory();
            });

        console.log("# StoryController.deleteStory");
    }

    this.saveStory = function (event) {

        console.log("> StoryController.saveStory");
        event.preventDefault();

        var model = new StoryModel();
        var issueKey = pageUtils.getIssueKey();
        model.issueKey = issueKey;
        var projectKey = pageUtils.getProjectKey();
        model.projectKey = projectKey;
        var storyInput = storyView.getStoryInputAsString();
        model.asString = storyInput;
        model.version = this.currentStory.version;

        storyService.saveOrUpdateStory(model, function (savedStory) {
            console.log("> StoryController.saveStory.saveOrUpdateStory callback");
            storyController.editMode = false;
            storyView.showStory(savedStory, storyController.editMode);
            storyView.showStoryReportButtons(savedStory);
            storyController.currentStory = savedStory;
            console.log("# StoryController.saveStory.saveOrUpdateStory callback");
        });

        console.log("# StoryController.saveStory");
    }

    this.cancelEditingStory = function (event) {

        console.log("> StoryController.cancelEditingStory");
        event.preventDefault();

        this.editMode = false;
        storyView.showStory(this.currentStory, this.editMode);

        console.log("# StoryController.cancelEditingStory");
    }

    this.showAutoCompleteHandler = function () {

        console.log("> StoryController.showAutoComplete");

        var storyInputAsString = storyView.getStoryInputAsString();
        var caretPosition = storyView.getStoryInputCaretPosition();
        console.log("caretPosition - " + caretPosition);
        var substring = storyInputAsString.substr(0, caretPosition);
        console.log("substring - " + substring);

//        var lines = substring.split("\n");
        var projectKey = pageUtils.getProjectKey();
        storyService.autoComplete(projectKey, substring,
            function (data) {
                var entries = data.entries;
                storyView.showAutoComplete(entries);
            }
        );

        console.log("# StoryController.showAutoComplete");
    }
}

//AJS.$(function () {
//    AJS.$.fn.caret = function () {
//
//        var target = this[0];
//        var isContentEditable = target.contentEditable === 'true';
//        //get
//        if (arguments.length == 0) {
//            //HTML5
//            if (window.getSelection) {
//                //contenteditable
//                if (isContentEditable) {
//                    target.focus();
//                    var range1 = window.getSelection().getRangeAt(0),
//                        range2 = range1.cloneRange();
//                    range2.selectNodeContents(target);
//                    range2.setEnd(range1.endContainer, range1.endOffset);
//                    return range2.toString().length;
//                }
//                //textarea
//                return target.selectionStart;
//            }
//            //IE<9
//            if (document.selection) {
//                target.focus();
//                //contenteditable
//                if (isContentEditable) {
//                    var range1 = document.selection.createRange(),
//                        range2 = document.body.createTextRange();
//                    range2.moveToElementText(target);
//                    range2.setEndPoint('EndToEnd', range1);
//                    return range2.text.length;
//                }
//                //textarea
//                var pos = 0,
//                    range = target.createTextRange(),
//                    range2 = document.selection.createRange().duplicate(),
//                    bookmark = range2.getBookmark();
//                range.moveToBookmark(bookmark);
//                while (range.moveStart('character', -1) !== 0) pos++;
//                return pos;
//            }
//            //not supported
//            return 0;
//        }
//        //set
//        if (pos == -1)
//            pos = this[isContentEditable ? 'text' : 'val']().length;
//        //HTML5
//        if (window.getSelection) {
//            //contenteditable
//            if (isContentEditable) {
//                target.focus();
//                window.getSelection().collapse(target.firstChild, pos);
//            }
//            //textarea
//            else
//                target.setSelectionRange(pos, pos);
//        }
//        //IE<9
//        else if (document.body.createTextRange) {
//            var range = document.body.createTextRange();
//            range.moveToElementText(target);
//            range.moveStart('character', pos);
//            range.collapse(true);
//            range.select();
//        }
//        if (!isContentEditable)
//            target.focus();
//        return pos;
//
//    };
//});

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
//            console.log("setting editor height to - " + height);
//            AJS.$("#yuiEditorPanel").height(height);
//        });
//
//        //Rendering the Editor.
//        editor.render('#yuiEditorPanel');
//
//    });
//});


