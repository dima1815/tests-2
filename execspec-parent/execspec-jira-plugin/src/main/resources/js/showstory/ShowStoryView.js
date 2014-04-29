var jBehaveStoryView;

function showStory(event) {
    var buttonEvent = event || window.event;
//    alert("showing story");
    jBehaveStoryView.showStory(buttonEvent);
}

function showStoryReport(event, environment) {
    var buttonEvent = event || window.event;
//    alert("showing story report for - " + environment);
    jBehaveStoryView.showStoryReport(buttonEvent, environment);
}

function ShowStoryView(controller) {

    var $this = this;
    var $c = controller;

    var $storyPayload;

    jBehaveStoryView = this;

    this.init = function () {
        console.log("initializing ShowStoryView");
    }

    this.hello = function hello() {
        return "hello";
    }

    /**
     * Used to set/mark things like starting key words.
     * @param story
     * @returns {*}
     */
    this.preprocess = function (story) {

        var narrative = story.narrative;
        var lines = narrative.splitlines();

        lines = string.split(narrative, '\n');
        console.log("lines:\n" + lines);

        for (var line in lines) {
            console.log("line - " + line);
        }

        for (var line in lines) {
            console.log("index - " + line.indexOf("In order to"));
            if (line.indexOf("In order to") == 0) {
                console.log("HURRAY!");
            }
        }

        return story;
    }

    this.processStoryPayload = function (storyPayload) {

        console.log("processing story payload: \n" + JSON.stringify(storyPayload));
        this.$storyPayload = storyPayload;

        AJS.$("#story-panel").html(execspec.viewissuepage.showstory.renderStoryPanel(storyPayload));
//        AJS.tabs.setup();
        this.showStory();

    }

    this.updateSelectedButton = function (event) {
        if (event != undefined) {
            var eveTarget = event.target;
            AJS.$(".story-container-button").removeClass("selected-story-container-button");
            console.log("eveTarget.id - " + eveTarget.id);
            AJS.$("#" + eveTarget.id).addClass("selected-story-container-button");
        }
    }

    this.showStory = function (event) {

        var currentStory = this.$storyPayload.story;
        console.log("showing story: \n" + JSON.stringify(currentStory));
//        AJS.$("#story-panel-content").html(execspec.viewissuepage.showstory.renderStoryAsHTML(currentStory));
        AJS.$("#story-container").html(currentStory.asHTML);
        this.updateSelectedButton(event);
    }

    this.showStoryReport = function (event, environment) {

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



