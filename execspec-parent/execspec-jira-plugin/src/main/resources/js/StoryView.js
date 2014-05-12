//var storyView;
//var storyController;

//function showStoryEditHelp(event) {
//    var buttonEvent = event || window.event;
//    alert("showing story help!");
////    jBehaveStoryView.showStory(buttonEvent);
//    buttonEvent.preventDefault();

function StoryView(storyController) {

    this.init = function () {

        console.log("> StoryView.init");

        console.log("rendering story panel");
        AJS.$("#story-panel").html(execspec.viewissuepage.showstory.renderStoryPanel());

        AJS.$("#add-jbehave-story-link").click(
            function (event) {
                event.preventDefault();
                console.log("> add-jbehave-story-link clicked");
                storyController.addStory();
                console.log("# add-jbehave-story-link clicked");
            }
        );

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
        var asString = AJS.$("#story-edit-text-area").val();
        return asString;
    }

    this.processStoryPayload = function (storyPayload) {

        console.log("processing story payload...");

        console.log("processing story payload: \n" + JSON.stringify(storyPayload));
        this.$storyPayload = storyPayload;

        AJS.$("#story-panel").html(execspec.viewissuepage.showstory.renderStoryPanel(storyPayload));
//        AJS.tabs.setup();
        this.showStory();

        AJS.InlineDialog(AJS.$("#showStoryEditHelp"), 1,
            function (content, trigger, showPopup) {
                content.css({"padding": "20px"}).html('<h2>Inline dialog</h2><p>The inline dialog is a wrapper for secondary content/controls to be displayed on user request. Consider this component as displayed in context to the triggering control with the dialog overlaying the page content.</p><button class="aui-button">Done</button></form>');
                showPopup();
                return false;
            }
        );

    }

    this.updateSelectedButton = function (event) {
        console.log("> StoryView.updateSelectedButton");
        if (event != undefined) {
            var eveTarget = event.target;
            AJS.$(".story-container-button").removeClass("selected-story-container-button");
            console.log("eveTarget.id - " + eveTarget.id);
            AJS.$("#" + eveTarget.id).addClass("selected-story-container-button");
        }
        console.log("# StoryView.updateSelectedButton");
    }

    this.removeStory = function () {
        AJS.$("#story-buttons").html("");
        AJS.$("#story-report-buttons").html("");
        AJS.$("#story-view-container").html("");
    }

    this.removeStoryTests = function () {
        AJS.$("#story-buttons").html("");
        AJS.$("#story-report-buttons").html("");
    }

    this.showStory = function (story) {

        console.log("> StoryView.showStory");
        console.log("story.asString - " + story.asString);

        // add the story button link
        var storyButtonHtml = execspec.viewissuepage.showstory.renderStoryButton(story);
        AJS.$("#story-buttons").html(storyButtonHtml);
        // set story button onClick handler
        AJS.$("#show-story-button").click(
            function (event) {
                console.log("> show-story-button clicked");
                event.preventDefault();
                AJS.$("#story-view-container").html(story.asHTML);
                storyView.updateSelectedButton(event);
                console.log("# show-story-button clicked");
            }
        );

        // add the story as html
        AJS.$("#story-view-container").html(story.asHTML);
//        this.updateSelectedButton(event);

        // add the story reports
        var storyReportButtons = execspec.viewissuepage.showstory.renderStoryReportButtons(story);
        AJS.$("#story-report-buttons").html(storyReportButtons);
        // set the story report button onClick handlers
        var storyReports = story.storyReports;
        for (var i = 0; i < storyReports.length; i++) {
            var storyReport = storyReports[i];
            var linkId = "show-story-report-" + storyReport.environment;
            AJS.$("#" + linkId).click(
                function (event) {
                    console.log("> " + linkId + " clicked");
                    event.preventDefault();
                    console.log("showing story report for environment - " + storyReport.environment);
                    var reportToShow = storyReport.htmlReport;
                    AJS.$("#story-view-container").html(reportToShow);
                    storyView.updateSelectedButton(event);
                    console.log("# " + linkId + " clicked");
                }
            );
        }

        console.log("# StoryView.showStory");
    }

    this.editStory = function (event) {

        console.log("editing story...");

        var currentStory = this.$storyPayload.story;

        var lines = this.$storyPayload.story.asString.split("\n");
        var lineCount = lines.length;
        this.$storyPayload.lineCount = lineCount;

        AJS.$("#story-container").html(execspec.viewissuepage.showstory.renderStoryAsString(this.$storyPayload));

        AJS.$("#story-edit-text-area").autocomplete({
            source: [
                "ActionScript",
                "AppleScript",
                "Asp"]
        });

//        AJS.$("#story-container").html(currentStory.asString);
        this.updateSelectedButton(event);
    }

    this.showStoryReport = function (event, environment) {

        console.log("showing story report...");

        var storyReports = this.$storyPayload.storyReports;
        var recordForEnv = undefined;
        for (var i = 0; i < storyReports.length; i++) {
            var storyReport = storyReports[i];
            if (storyReport.environment == environment) {
                recordForEnv = storyReport;
                break;
            }
        }

        if (recordForEnv == undefined) {
            console.error("Failed to find story report for environment - " + environment);
        } else {
            console.log("showing story report: \n" + JSON.stringify(recordForEnv));
            AJS.$("#story-container").html(recordForEnv.htmlReport);
            this.updateSelectedButton(event);
        }
    }
}



