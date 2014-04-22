function PageUtils() {

    this.init = function () {

        console.log("initializing PageUtils");
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