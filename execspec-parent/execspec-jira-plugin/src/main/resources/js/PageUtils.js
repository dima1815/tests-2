function PageUtils() {

    this.debugOn = false;

    this.debug = function (msg) {
        if (this.debugOn) {
            console.log("[DEBUG StoryController] " + msg);
        }
    }

    this.init = function () {

        if (this.debugOn) {
            console.log("initializing PageUtils");
        }
    }

    this.getIssueKey = function () {
        return AJS.$.trim(AJS.$("#key-val").text());
    };

//    this.getIssueKey = function () {
//        if (JIRA.IssueNavigator.isNavigator()) {
//            return JIRA.IssueNavigator.getSelectedIssueKey();
//        } else {
//            return AJS.$.trim(AJS.$("#key-val").text());
//        }
//    };

    // Function for getting the project key of the Issue being edited.
    this.getProjectKey = function () {
        var issueKey = this.getIssueKey();
        if (issueKey) {
            return issueKey.match("[A-Z]*")[0];
        }
    };

}

//AJS.$(function () {
//    JIRA.bind(JIRA.Events.NEW_CONTENT_ADDED, function (e, context, reason) {
//        var $context = AJS.$(context);
//
//        // Find our web panel. Handles the pageLoad and panelRefreshed reasons.
//        var $webPanel = $context.find("*").andSelf().filter("#story-panel");
//        if ($webPanel.length > 0) {
//            console.log("processing - NEW_CONTENT_ADDED event for jbehave panel");
//            jBehaveStoryView.processStoryPayload(jBehaveStoryView.$storyPayload);
//        }
//
//        // Find our custom fields. There may be multiple!
////        $context.find(".my-custom-field").each(function() {
////            var $customField = AJS.$(this);
////            // ...
////        });
//    });
//});