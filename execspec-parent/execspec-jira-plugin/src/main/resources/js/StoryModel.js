function StoryModel() {

    this.projectKey = null;
    this.issueKey = null;
    this.path = null;
    this.version = null;

    this.description = null;
    this.meta = new Object();
    this.meta.keyword = null;
    this.meta.properties = null;

    this.narrative = new Object();
    this.narrative.inOrderTo = new Object();
    this.narrative.inOrderTo.keyword = "";
    this.narrative.inOrderTo.value = "";

    this.asString = "";
    this.asHTML = "";
//    this.storyReports = [];

}