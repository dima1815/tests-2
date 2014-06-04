function StoryService() {

    this.debugOn = false;

    var pathBase = "/jira/rest/story-res/1.0/";
    var pathNewStoryTemplate = pathBase + "crud/newstorytemplate/";
    var pathSave = pathBase + "crud/save/";
    var pathFind = pathBase + "find/for-issue/";
    var pathDelete = pathBase + "crud/delete/";
    var pathDeleteReports = pathBase + "story-test/delete/";
    var pathAutoComplete = pathBase + "autocomplete/";

    this.debug = function (msg) {
        if (this.debugOn) {
            console.log("[DEBUG StoryService] " + msg);
        }
    }


    this.init = function () {

        storyService.debug("> init");
        storyService.debug("urlPathBase - " + pathBase);
        storyService.debug("saveBaseUrl - " + pathSave);
        storyService.debug("# init");
    }

    this.fetchNewStoryTemplate = function (projectKey, callBack) {

        storyService.debug("> fetchNewStoryTemplate");

        var successCallback = function (data, status, xhr) {
            storyService.debug("> fetchNewStoryTemplate.successCallback");
            storyService.debug("status - " + status);
            storyService.debug("xhr.status - " + xhr.status);
            storyService.debug("data - " + data);
            var jsonData = JSON.stringify(data);
            storyService.debug("jsonData - " + jsonData);
            callBack(data);
            storyService.debug("# fetchNewStoryTemplate.successCallback");
        }

        var fetchTemplateUrl = pathNewStoryTemplate + projectKey;
        storyService.debug("fetchTemplateUrl - " + fetchTemplateUrl);

        AJS.$.ajax({
            type: "GET",
            url: fetchTemplateUrl,
            contentType: "text/plain; charset=utf-8",
            success: successCallback,
            dataType: "json"
        });

        storyService.debug("# fetchNewStoryTemplate");
    }

    this.saveOrUpdateStory = function (story, callBack) {

        storyService.debug("> saveOrUpdateStory");
        storyService.debug("story - " + story);
        var storyAsString = story.asString;
        storyService.debug("storyAsString - " + storyAsString);

        var successCallback = function (data, status, xhr) {
            storyService.debug("> StoryService.saveOrUpdateStory.successCallback");
            storyService.debug("status - " + status);
            storyService.debug("xhr.status - " + xhr.status);
            storyService.debug("data - " + data);
            var jsonData = JSON.stringify(data);
            storyService.debug("jsonData - " + jsonData);
            callBack(data);
            storyService.debug("# StoryService.saveOrUpdateStory.successCallback");
        }

        var saveUrl = pathSave + story.projectKey + "/" + story.issueKey;
        if (story.version != undefined && story.version != "") {
            saveUrl += "?version=" + story.version;
        }
        storyService.debug("saveUrl - " + saveUrl);

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

        storyService.debug("# StoryService.saveOrUpdateStory");
    }

    this.find = function (projectKey, issueKey, callBack) {

        storyService.debug("> StoryService.find");
        storyService.debug("project key " + projectKey + ", issue key = " + issueKey);
        var urlString = pathFind + projectKey + "/" + issueKey;
        var jqxhr = AJS.$.getJSON(urlString);

        var successCallback = function (data, status, xhr) {
            storyService.debug("> StoryService.find.successCallback");
            storyService.debug("status - " + status);
            storyService.debug("xhr.status - " + xhr.status);
            storyService.debug("data - " + data);
            callBack(data);
            storyService.debug("# StoryService.find.successCallback");
        }
        jqxhr.done(successCallback);

        jqxhr.fail(function (data, status, xhr) {
            console.error("fail, received data - " + data);
            console.error("xhr.status - " + xhr.status);
        });
        storyService.debug("# StoryService.find");
    }

    this.deleteStory = function (projectKey, issueKey, callBack) {

        storyService.debug("> StoryService.deleteStory");
        storyService.debug("haha");
        storyService.debug("projectKey - " + projectKey);
        storyService.debug("issueKey - " + issueKey);

        var urlString = pathDelete + projectKey + "/" + issueKey;
        storyService.debug("urlString - " + urlString);

        var successCallBack = function () {
            storyService.debug("Story deleted successfully");
            callBack();
        };

        AJS.$.ajax({
            type: "DELETE",
            url: urlString,
            success: successCallBack
        });

        storyService.debug("# StoryService.deleteStory");
    }

    this.deleteStoryReports = function (projectKey, issueKey, callBack) {

        storyService.debug("> StoryService.deleteStoryReports");
        storyService.debug("projectKey - " + projectKey);
        storyService.debug("issueKey - " + issueKey);

        var urlString = pathDeleteReports + projectKey + "/" + issueKey;
        storyService.debug("urlString - " + urlString);

        var successCallback = function () {
            storyService.debug("story reports deleted successfully");
            callBack();
        };

        AJS.$.ajax({
            type: "DELETE",
            url: urlString,
            success: successCallback
        });

        storyService.debug("# StoryService.deleteStoryReports");
    }

    this.autoComplete = function (projectKey, input, callBack) {

        storyService.debug("> StoryService.autoComplete");
        storyService.debug("projectKey - " + projectKey);
        storyService.debug("input - " + input);

        var urlString = pathAutoComplete + projectKey;
        storyService.debug("urlString - " + urlString);

        var successCallback = function (data, status, xhr) {
            storyService.debug("> StoryService.autoComplete.successCallback");
            storyService.debug("status - " + status);
            storyService.debug("xhr.status - " + xhr.status);
            storyService.debug("data - " + data);
            callBack(data);
            storyService.debug("# StoryService.autoComplete.successCallback");
        };

        AJS.$.ajax({
            type: "POST",
            url: urlString,
            contentType: "text/plain; charset=utf-8",
            success: successCallback,
            data: input,
            dataType: "json"
        });

        storyService.debug("# StoryService.autoComplete");
    }


}