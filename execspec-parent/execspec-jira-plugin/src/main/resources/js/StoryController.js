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

    this.showStory = function () {

        console.log("> StoryController.showStory");
        var issueKey = pageUtils.getIssueKey();
        var projectKey = pageUtils.getProjectKey();
        storyService.find(projectKey, issueKey,
            function (story) {
                if (story != undefined) {
                    storyView.showStory(story);
                } else {
                    console.log("no story found for project - " + projectKey + ", issue - " + issueKey);
                }
            }
        );
        console.log("# StoryController.showStory");
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
                storyView.showStory(story);
                // TODO remove the add story button from the menu
            });

        console.log("# StoryController.addStory");
    }

    this.clearStoryTests = function () {

        console.log("> StoryController.clearStoryTests");

        var projectKey = pageUtils.getProjectKey();
        var issueKey = pageUtils.getIssueKey();

        storyService.deleteStoryReports(projectKey, issueKey,
            function (story) {
                console.log("story reports successfully deleted");
                // TODO remove the delete story button from the menu
                storyView.removeStoryTests();
                storyView.showStory(story);
            });

        console.log("# StoryController.clearStoryTests");
    }

    this.deleteStory = function () {

        console.log("> StoryController.deleteStory");

        var projectKey = pageUtils.getProjectKey();
        var issueKey = pageUtils.getIssueKey();

        storyService.delete(projectKey, issueKey,
            function () {
                console.log("story successfully deleted");
                // TODO remove the delete story button from the menu
                storyView.removeStory();
            });

        console.log("# StoryController.deleteStory");
    }

    this.saveStory = function () {

        console.log("> StoryController.saveStory");
        var model = new StoryModel();
        var issueKey = pageUtils.getIssueKey();
        model.issueKey = issueKey;
        var projectKey = pageUtils.getProjectKey();
        model.projectKey = projectKey;
        var storyInput = storyView.getStoryAsString();
        model.asString = storyInput;

        var savedStory = this.storyService.saveOrUpdateStory(model);

        console.log("# StoryController.saveStory");
    }
}

AJS.$(function () {
    var ctr = new StoryController()
    ctr.showStory();
})



