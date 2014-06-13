Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesShowStory.soy
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesShowStory.soy	(date 1402669135000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesShowStory.soy	(revision )
@@ -4,79 +4,9 @@
  * Renders story as html.
  * @param story
  */
-{template .renderStoryAsHtml}
-    {$story.asString}
+{template .renderStoryAsString}
+    {$story.asString|changeNewlineToBr}
 {/template}
-
-///**
-// * Renders story edit area.
-// */
-//{template .renderEditStoryArea2}
-//<div class="story-panel">
-//    Rich Text Edit Area - Default content
-//    <div class="mod-content">
-//        <div class="field-ignore-highlight editable-field active" id="description-val">
-//            <form action="#" class="ajs-dirty-warning-exempt aui" id="description-form">
-//                <div class="inline-edit-fields" tabindex="1">
-//                    <div class="field-group">
-//                        <div class="wiki-edit">
-//                            <div class="wiki-edit-content ui-front" id="storyEditTextAreaContainer">
-//                                <div id="autoCompleteContainer1"></div>
-//                                <div id="autoCompleteContainer2">
-//                                    <textarea rows="10"
-//                                          id="story-edit-text-area"
-//                                          class="textarea long-field wiki-textfield long-field full-width-field mentionable"
-//                                          name="nowrap" wrap="off"></textarea>
-//                                </div>
-//                                <div id="autoCompleteContainer3"></div>
-//                                <div class="content-inner">
-//                                </div>
-//                            </div>
-//                        </div>
-//                    </div>
-//                </div>
-//                <span class="overlay-icon throbber"></span>
-//
-//                <div class="save-options" tabindex="1">
-//                    <button title="Press Alt+s to submit this form" accesskey="s" onclick="storyController.saveStory(event)" class="aui-button submit" type="submit">
-//                        <span class="icon icon-save">Save</span></button>
-//                    <button title="Press Alt+` to cancel" accesskey="`" onclick="storyController.cancelEditingStory(event)" class="aui-button cancel" type="cancel"><span
-//                            class="icon icon-cancel">Cancel</span></button>
-//                </div>
-//            </form>
-//        </div>
-//    </div>
-//</div>
-//{/template}
-
-
-///**
-//* Render rich text area.
-//* @param range
-// */
-//{template .renderRichTextArea}
-//    <div>This is a test rich text area</div>
-//    <div>
-//        <table style="border-style: solid; border-width: 1px; border-color: blue">
-//            <tr>
-//                {foreach $i in $range}
-//                    <th>column {$i}</th>
-//                {/foreach}
-//            </tr>
-//            <tr>
-//                {foreach $i in $range}
-//                    <td>cell {$i}</td>
-//                {/foreach}
-//            </tr>
-//        </table>
-//        {foreach $i in $range}
-//            <div>
-//                test {$i}
-//            </div>
-//        {/foreach}
-//
-//    </div>
-//{/template}
 
 /**
  * Renders story as html.
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/util/ByLineStoryParser.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/util/ByLineStoryParser.java	(date 1402669135000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/util/ByLineStoryParser.java	(revision )
@@ -47,6 +47,8 @@
 
         StoryDTO storyDTO = new StoryDTO();
 
+        storyDTO.setAsString(storyAsText);
+
         if (storyPath != null && !storyPath.isEmpty()) {
             storyDTO.setPath(storyPath);
         }
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesStoryToolbar.soy
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesStoryToolbar.soy	(date 1402669135000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesStoryToolbar.soy	(revision )
@@ -34,11 +34,14 @@
  */
 {template .renderButtonsForStory}
     <button id="showStoryButton"
+        onclick="storyController.showStoryHandler(event)"
         class="aui-button story-container-button selected-story-container-button">{$path}</button>
 //    <button id="edit-story-button"
 //            class="aui-button aui-button-subtle story-container-button selected-story-container-button">{$path}</button>
 //aria-pressed="true"
-    <button id="editStoryButton" class="aui-button"><span class="aui-icon aui-icon-small aui-iconfont-edit">Edit </span></button>
+    <button id="editStoryButton"
+        onclick="storyController.editStoryHandler(event)"
+        class="aui-button"><span class="aui-icon aui-icon-small aui-iconfont-edit">Edit </span></button>
 //    {call aui.buttons.button}
 //        {param text: '' /}
 //        {param iconType: 'aui' /}
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryController.js
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryController.js	(date 1402669135000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryController.js	(revision )
@@ -34,12 +34,14 @@
         var projectKey = pageUtils.getProjectKey();
         storyService.find(projectKey, issueKey,
             function (story) {
-                storyView.debug("> loadStory.callback, story - " + story);
+                storyController.debug("> loadStory.callback");
+                var storyPayload = JSON.stringify(story, null, "\t");
+                storyController.debug("story - " + storyPayload);
                 if (story != undefined) {
 //                    storyView.showStoryButton(story);
 //                    storyView.showStoryReportButtons(story); // TODO
                     storyController.currentStory = story;
-                    storyController.showStoryHandler();
+                    storyController.showStoryHandler(null);
                 } else {
                     storyController.debug("no story found for project - " + projectKey + ", issue - " + issueKey);
                     storyView.showAddStory();
@@ -59,8 +61,9 @@
 
             story.issueKey = pageUtils.getIssueKey();
             story.path = pageUtils.getIssueKey() + ".story";
-            storyView.editStory(story);
+
             storyController.currentStory = story;
+            storyController.editStoryHandler(null);
 
             storyController.debug("# addStoryHandler.callback");
         });
@@ -69,10 +72,14 @@
         this.debug("# addStoryHandler");
     }
 
-    this.showStoryHandler = function () {
+    this.showStoryHandler = function (event) {
 
         this.debug("> showStoryHandler");
-        storyView.showStory(this.currentStory, this.editMode);
+        this.editMode = false;
+        storyView.showStory(this.currentStory);
+        if (event != null) {
+            event.preventDefault();
+        }
         this.debug("# showStoryHandler");
     }
 
@@ -134,12 +141,16 @@
 //        this.debug("# addStory");
 //    }
 
-    this.editStoryHandler = function () {
+    this.editStoryHandler = function (event) {
 
         this.debug("> editStoryHandler");
-        this.debug("current story as string - " + this.currentStory.asString);
+
         this.editMode = true;
-        storyView.showStory(this.currentStory, this.editMode);
+        storyView.editStory(this.currentStory);
+
+        if(event != null) {
+            event.preventDefault();
+        }
         this.debug("# editStoryHandler");
     }
 
@@ -186,12 +197,15 @@
         var storyPayload = JSON.stringify(this.currentStory);
 
         storyService.saveOrUpdateStory(storyPayload, function (savedStory) {
-            storyController.debug("> saveStory.saveOrUpdateStory callback");
-            storyController.editMode = false;
-            storyView.showStory(savedStory, storyController.editMode);
-            storyView.showStoryReportButtons(savedStory);
+            storyController.debug("> saveStoryAsModel.saveOrUpdateStory callback");
+//            storyView.showStoryReportButtons(savedStory);
+            var jsonStory = JSON.stringify(savedStory, null, "\t");
+            storyController.debug("saved story:\n" + jsonStory);
+
             storyController.currentStory = savedStory;
-            storyController.debug("# saveStory.saveOrUpdateStory callback");
+            storyController.editMode = false;
+            storyController.showStoryHandler(null);
+            storyController.debug("# saveStoryAsModel.saveOrUpdateStory callback");
         });
 
         this.debug("# saveStoryAsModel");
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryView.js
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryView.js	(date 1402669135000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryView.js	(revision )
@@ -247,14 +247,7 @@
 
         AJS.$("#richTextEditorButton").click();
 
-//        if (richEditor) {
-
-//        } else {
-//            var rawEditButtons = execspec.viewissuepage.editstory.renderRawEditStoryButtons();
-//            AJS.$("#editStoryButtons").html(rawEditButtons);
-//            AJS.$("#rawTextEditorButton").attr("aria-pressed", "true");
-//        }
-
+        AJS.$("#storyContainer").hide();
         AJS.$("#storyEditContainer").show();
         this.debug("# editStory");
     }
@@ -358,9 +351,25 @@
         storyView.debug("# StoryView.showStoryButton");
     }
 
-    this.showStory = function(story) {
+    this.showStory = function (story) {
-        AJS.$("#storyContainer").html("This issue has a story");
+
+        storyView.debug("> StoryView.showStory");
+
+        var buttonsForStory = execspec.viewissuepage.storytoolbar.renderButtonsForStory(story);
+        AJS.$("#storyButtons").html(buttonsForStory);
+        AJS.$("#editStoryButton").attr("aria-pressed", "false");
+        AJS.$("#showStoryButton").attr("aria-pressed", "true");
+
+        var templateObj = new Object();
+        templateObj.story = story;
+        var storyHtml = execspec.viewissuepage.showstory.renderStoryAsString(templateObj);
+
+        AJS.$("#storyContainer").html(storyHtml);
         AJS.$("#storyContainer").show();
+
+        AJS.$("#storyEditContainer").hide();
+
+        storyView.debug("# StoryView.showStory");
     }
 
 //    this.showStory = function (story, editMode) {
@@ -450,7 +459,7 @@
 ////                });
 //            });
 
-            // add the auto complete
+    // add the auto complete
 //            AJS.$("#story-edit-text-area").autocomplete({
 //                autoFocus: true,
 //                position: {
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/service/StoryServiceImpl.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/service/StoryServiceImpl.java	(date 1402669135000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/service/StoryServiceImpl.java	(revision )
@@ -106,7 +106,15 @@
         long rolledVersion = currentVersion + 1;
         story.setVersion(rolledVersion);
 
-        story.setAsString(storyDTO.getAsString());
+        String asString = storyDTO.getAsString();
+        if (asString != null) {
+            // this will be the case when the client sends the story already as a string, e.g. from the
+            // Raw editor rather than Rich editor
+        } else {
+            asString = StoryDTOUtils.asString(storyDTO);
+        }
+        story.setAsString(asString);
+
         story.setLastEditedBy(userName);
         story.save();
 
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryEditHandler.js
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryEditHandler.js	(date 1402669135000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryEditHandler.js	(revision )
@@ -575,6 +575,8 @@
 
         this.bindInputElementsToModel();
 
+        // remove the asString field as we are saving from the rich editor
+        storyController.currentStory.asString = null;
         storyController.saveStoryAsModel();
 
         event.preventDefault();
\ No newline at end of file
