function StoryService() {

    var pathBase = "/jira/rest/story-res/1.0/";
    var pathSave = pathBase + "crud/save/";
    var pathFind = pathBase + "find/for-issue/";
    var pathDelete = pathBase + "crud/delete/";
    var pathDeleteReports = pathBase + "story-test/delete/";
    var pathAutoComplete = pathBase + "autocomplete/";

    this.init = function () {

        console.log("> init");
        console.log("urlPathBase - " + pathBase);
        console.log("saveBaseUrl - " + pathSave);
        console.log("# init");
    }

    this.saveOrUpdateStory = function (story, callBack) {

        console.log("> StoryService.saveOrUpdateStory");
        console.log("story - " + story);
        var storyAsString = story.asString;
        console.log("storyAsString - " + storyAsString);

        var successCallback = function (data, status, xhr) {
            console.log("> StoryService.saveOrUpdateStory.successCallback");
            console.log("status - " + status);
            console.log("xhr.status - " + xhr.status);
            console.log("data - " + data);
            var jsonData = JSON.stringify(data);
            console.log("jsonData - " + jsonData);
            callBack(data);
            console.log("# StoryService.saveOrUpdateStory.successCallback");
        }

        var saveUrl = pathSave + story.projectKey + "/" + story.issueKey;
        if (story.version != undefined && story.version != "") {
            saveUrl += "?version=" + story.version;
        }
        console.log("saveUrl - " + saveUrl);

        AJS.$.ajax({
            type: "POST",
            url: saveUrl,
            contentType: "text/plain; charset=utf-8",
            success: successCallback,
            data: storyAsString,
            dataType: "json"
        });

//        jqxhr.done(successCallback);
//        jqxhr.fail(function (data, status, xhr) {
//            console.error("fail, received data - " + data);
//            console.error("xhr.status - " + xhr.status);
//        });

//        jqxhr.always(function (data, status, xhr) {
//            console.error("always, received data - " + data);
//            console.error("xhr.status - " + xhr.status);
//        });

        console.log("# StoryService.saveOrUpdateStory");
    }

    this.find = function (projectKey, issueKey, callBack) {

        console.log("> StoryService.find");
        console.log("project key " + projectKey + ", issue key = " + issueKey);
        var urlString = pathFind + projectKey + "/" + issueKey;
        var jqxhr = AJS.$.getJSON(urlString);

        var successCallback = function (data, status, xhr) {
            console.log("> StoryService.find.successCallback");
            console.log("status - " + status);
            console.log("xhr.status - " + xhr.status);
            console.log("data - " + data);
            callBack(data);
            console.log("# StoryService.find.successCallback");
        }
        jqxhr.done(successCallback);

        jqxhr.fail(function (data, status, xhr) {
            console.error("fail, received data - " + data);
            console.error("xhr.status - " + xhr.status);
        });
        console.log("# StoryService.find");
    }

    this.deleteStory = function (projectKey, issueKey, callBack) {

        console.log("> StoryService.deleteStory");
        console.log("haha");
        console.log("projectKey - " + projectKey);
        console.log("issueKey - " + issueKey);

        var urlString = pathDelete + projectKey + "/" + issueKey;
        console.log("urlString - " + urlString);

        var successCallBack = function () {
            console.log("Story deleted successfully");
            callBack();
        };

        AJS.$.ajax({
            type: "DELETE",
            url: urlString,
            success: successCallBack
        });

        console.log("# StoryService.deleteStory");
    }

    this.deleteStoryReports = function (projectKey, issueKey, callBack) {

        console.log("> StoryService.deleteStoryReports");
        console.log("projectKey - " + projectKey);
        console.log("issueKey - " + issueKey);

        var urlString = pathDeleteReports + projectKey + "/" + issueKey;
        console.log("urlString - " + urlString);

        var successCallback = function () {
            console.log("story reports deleted successfully");
            callBack();
        };

        AJS.$.ajax({
            type: "DELETE",
            url: urlString,
            success: successCallback
        });

        console.log("# StoryService.deleteStoryReports");
    }

    this.autoComplete = function (projectKey, input, callBack) {

        console.log("> StoryService.autoComplete");
        console.log("projectKey - " + projectKey);
        console.log("input - " + input);

        var urlString = pathAutoComplete + projectKey;
        console.log("urlString - " + urlString);

        var successCallback = function (data, status, xhr) {
            console.log("> StoryService.autoComplete.successCallback");
            console.log("status - " + status);
            console.log("xhr.status - " + xhr.status);
            console.log("data - " + data);
            callBack(data);
            console.log("# StoryService.autoComplete.successCallback");
        };

        AJS.$.ajax({
            type: "POST",
            url: urlString,
            contentType: "text/plain; charset=utf-8",
            success: successCallback,
            data: input,
            dataType: "json"
        });

        console.log("# StoryService.autoComplete");
    }


}