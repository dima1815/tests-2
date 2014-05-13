//var storyView;
//var storyController;

//function showStoryEditHelp(event) {
//    var buttonEvent = event || window.event;
//    alert("showing story help!");
////    jBehaveStoryView.showStory(buttonEvent);
//    buttonEvent.preventDefault();

function StoryView(storyController) {

    this.editStoryView = undefined;

    this.init = function () {

        console.log("> StoryView.init");

        console.log("rendering story panel");
        AJS.$("#story-panel").html(execspec.viewissuepage.showstory.renderStoryPanel());

        // update button menu links
        AJS.$("#add-jbehave-story-link").click(
            function (event) {
                event.preventDefault();
                console.log("> add-jbehave-story-link clicked");
                storyController.addStory();
                console.log("# add-jbehave-story-link clicked");
            }
        );
        AJS.$("#edit-jbehave-story-link").click(
            function (event) {
                event.preventDefault();
                console.log("> edit-jbehave-story-link clicked");
                storyController.editStory();
                console.log("# edit-jbehave-story-link clicked");
            }
        )
        AJS.$("#delete-jbehave-story-link").click(
            function (event) {
                event.preventDefault();
                console.log("> delete-jbehave-story-link clicked");
                storyController.deleteStory();
                console.log("# delete-jbehave-story-link clicked");
            }
        );
        AJS.$("#clear-jbehave-story-tests-link").click(
            function (event) {
                event.preventDefault();
                console.log("> clear-jbehave-story-tests-link clicked");
                storyController.clearStoryTests();
                console.log("# clear-jbehave-story-tests-link clicked");
            }
        );

        console.log("# StoryView.init");
    }

    this.getStoryInputAsString = function () {

        console.log("> StoryView.getStoryInputAsString");

        var asString = AJS.$("#story-edit-text-area").val();
        console.log("asString - " + asString);
        return asString;

        console.log("# StoryView.getStoryInputAsString");
    }

    this.updateSelectedButton = function (clickedElementId) {
        console.log("> StoryView.updateSelectedButton");
        AJS.$(".story-container-button").removeClass("selected-story-container-button");
        AJS.$("#" + clickedElementId).addClass("selected-story-container-button");
        console.log("# StoryView.updateSelectedButton");
    }

    this.removeStory = function () {
        AJS.$("#storyButtons").html("");
        AJS.$("#storyReportButtons").html("");
        AJS.$("#storyViewContainer").html("");
    }

    this.showStory = function (story, editMode) {

        console.log("> StoryView.showStory");
        console.log("story.asString - " + story.asString);
        console.log("editMode - " + editMode);

        AJS.$("#storyMessageContainer").html("");

        // add the story button link
        var storyButtonHtml = execspec.viewissuepage.showstory.renderStoryButton(story);
        AJS.$("#storyButtons").html(storyButtonHtml);
        // set story button onClick handler
        AJS.$("#show-story-button").click(
            function (event) {
                event.preventDefault();
                storyController.showStoryHandler();
            }
        );

        if (editMode != undefined && editMode == true) {

            var lines = story.asString.split("\n");
            var lineCount = lines.length;
            var templateObject = new Object();
            templateObject.story = story;
            templateObject.lineCount = lineCount;
            var storyEdit = execspec.viewissuepage.showstory.renderEditStory(templateObject);
            AJS.$("#storyViewContainer").html(storyEdit);

        } else {
            AJS.$("#storyViewContainer").html(story.asHTML);
        }

        this.updateSelectedButton("show-story-button");

        console.log("# StoryView.showStory");
    }

    this.showStoryReportButtons = function (story) {

        console.log("> StoryView.showStoryReportButtons");

        var storyVersion = story.version;
        console.log("storyVersion - " + storyVersion);

        // add the story reports
        var storyReportButtons = execspec.viewissuepage.showstory.renderStoryReportButtons(story);
        AJS.$("#storyReportButtons").html(storyReportButtons);
        // set the story report button onClick handlers
        var storyReports = story.storyReports;
        for (var i = 0; i < storyReports.length; i++) {
            var storyReport = storyReports[i];
            var linkId = "show-story-report-" + storyReport.environment;
            AJS.$("#" + linkId).click(
                function (event) {

                    console.log("> show story report button clicked");
                    event.preventDefault();

                    var attributes = event.target.attributes;
                    var environmentNode = attributes["environment"];
                    var environment = environmentNode.nodeValue;
                    storyController.showStoryReport(environment);

                    console.log("# show story report button clicked");
                }
            );
        }

        console.log("# StoryView.showStoryReportButtons");
    }

    this.showStoryReport = function (storyReport, storyVersion) {

        console.log("> StoryView.showStoryReport");
        console.log("storyReport.environment - " + storyReport.environment);

        AJS.$("#storyMessageContainer").html("");
        var reportToShowTemplateModel = new Object();
        reportToShowTemplateModel.storyReport = storyReport;
        var storyReportHTML = execspec.viewissuepage.showstory.renderStoryReport(reportToShowTemplateModel);
        AJS.$("#storyViewContainer").html(storyReportHTML);

        if (storyVersion > storyReport.storyVersion) {
            AJS.messages.generic("#storyMessageContainer", {
                title: "Story has been modified since last run",
                closeable: false
            });
        }

        this.updateSelectedButton("show-story-report-" + storyReport.environment);

        console.log("# StoryView.showStoryReport");
    }

//    this.editStory = function (event) {
//
//        console.log("editing story...");
//
//        var currentStory = this.$storyPayload.story;
//
//        var lines = this.$storyPayload.story.asString.split("\n");
//        var lineCount = lines.length;
//        this.$storyPayload.lineCount = lineCount;
//
//        AJS.$("#story-container").html(execspec.viewissuepage.showstory.renderStoryAsString(this.$storyPayload));
//
//        AJS.$("#story-edit-text-area").autocomplete({
//            source: [
//                "ActionScript",
//                "AppleScript",
//                "Asp"]
//        });
//
////        AJS.$("#story-container").html(currentStory.asString);
////        this.updateSelectedButton(event);
//    }

//    this.showStoryReport = function (event, environment) {
//
//        console.log("showing story report...");
//
//        var storyReports = this.$storyPayload.storyReports;
//        var recordForEnv = undefined;
//        for (var i = 0; i < storyReports.length; i++) {
//            var storyReport = storyReports[i];
//            if (storyReport.environment == environment) {
//                recordForEnv = storyReport;
//                break;
//            }
//        }
//
//        if (recordForEnv == undefined) {
//            console.error("Failed to find story report for environment - " + environment);
//        } else {
//            console.log("showing story report: \n" + JSON.stringify(recordForEnv));
//            AJS.$("#story-container").html(recordForEnv.htmlReport);
//            this.updateSelectedButton(event);
//        }
//    }
}



