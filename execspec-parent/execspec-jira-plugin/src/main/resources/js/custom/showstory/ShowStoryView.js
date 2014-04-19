function setStoryTabFunction(tabName) {
    alert("inside setStoryTabFunction, tabName - " + tabName);
}

function ShowStoryView(controller) {

    var $this = this;
    var $c = controller;

    this.init = function () {
        console.log("initializing ShowStoryView");
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

    this.showStory = function (storyPayload) {

        console.log("showing story: \n" + JSON.stringify(storyPayload));

        AJS.$("#story-container").html(execspec.viewissuepage.showstory.renderStoryPanel(storyPayload));

    }
}



