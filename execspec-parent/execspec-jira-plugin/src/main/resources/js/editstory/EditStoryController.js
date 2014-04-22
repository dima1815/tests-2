function EditStoryController() {

    var $this = this;
    this.view = new EditStoryView($this);
    this.storyService = new StoryService();
    this.utils = new PageUtils();

    this.init = function () {
        console.log("-> EditStoryController.init");
        this.view.init();
        this.storyService.init();
        console.log("<- EditStoryController.init");
    }

    this.saveStoryInput = function () {

        console.log("-> saveStoryInput ");

        var narrative = this.view.getNarrative();
        console.log("Saving narrative - " + narrative);

        var scenarios = this.view.getScenarios();
        console.log("Saving scenarios - " + scenarios);

        var issueKey = this.utils.getIssueKey();
        console.log("issueKey - " + issueKey);
        var projectKey = this.utils.getProjectKey();
        console.log("projectKey - " + projectKey);

        var keyVal = AJS.$("#key-val").text();
        console.log("keyVal - " + keyVal);

        var model = new StoryModel();
        model.narrative = narrative;
        model.scenarios = scenarios;
        model.issueKey = issueKey;
        model.projectKey = projectKey;

        this.storyService.createNewStory(model);

        this.view.hideDialog();
    }

}

AJS.$(function () {
    var ctr = new EditStoryController()
    ctr.init();
});



