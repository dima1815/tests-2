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
                    storyView.showStory(story, storyController.editMode);
                    storyView.showStoryReportButtons(story);
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

        var newStoryAsString = "Narrative:";
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

    this.editStory = function () {

        console.log("> StoryController.editStory");
        console.log("current story as string - " + this.currentStory.asString);
        this.editMode = true;
        storyView.showStory(this.currentStory, this.editMode);
        console.log("# StoryController.editStory");
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
}

AJS.$(function () {
    var ctr = new StoryController()
    ctr.loadStory();
})



