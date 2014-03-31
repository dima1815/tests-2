function DeleteStoryController() {

    var $this = this;
    var $storyService = new StoryService();
    this.utils = new PageUtils();

    this.init = function () {
        console.log("-> DeleteStoryController.init");
        $storyService.init();

        // Add dialog trigger to toolbar button
        AJS.$("a.delete-story-button").click(function () {
            var storyId = AJS.$("#story-id-container").attr("storyId");
            console.log("deleting story with id - " + storyId);
            $storyService.deleteStory(storyId);
            return false;
        });

        console.log("<- DeleteStoryController.init");
    }

}

AJS.$(function () {
    var ctr = new DeleteStoryController()
    ctr.init();
});



