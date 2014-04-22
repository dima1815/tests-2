function ShowStoryController() {

    var $this = this;
    var $view = new ShowStoryView($this);
    this.storyService = new StoryService();
    this.utils = new PageUtils();

    this.init = function () {
        console.log("-> ShowStoryController.init");
        $view.init();

        var issueKey = this.utils.getIssueKey();
        var projectKey = this.utils.getProjectKey();
        console.log("issueKey == " + issueKey);

        var successFunction = function (storyPayload) {
            console.log("Request submitted successfully, receivedData: \n" + JSON.stringify(storyPayload));
            if (storyPayload.story !== undefined) {
                $view.showStory(storyPayload);
            } else {
                console.log("ReceivedData did not contain a valid story");
            }
        };

        this.storyService.find(projectKey, issueKey, successFunction);

        console.log("<- ShowStoryController.init");
    }
}

AJS.$(function () {
    var ctr = new ShowStoryController()
    ctr.init();
})



