Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/util/ByLineStoryParser.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/util/ByLineStoryParser.java	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/util/ByLineStoryParser.java	(revision )
@@ -5,9 +5,7 @@
 import org.jbehave.core.i18n.LocalizedKeywords;
 
 import java.util.ArrayList;
-import java.util.HashMap;
 import java.util.List;
-import java.util.Map;
 
 /**
  * Created by Dmytro on 5/20/2014.
@@ -275,9 +273,9 @@
                         }
                     } else if (line.startsWith("@") && storyDTO.getMeta() != null
                             && lastElement == CompositeElement.meta) {
-                        Map properties = storyDTO.getMeta().getProperties();
+                        List<MetaEntryDTO> properties = storyDTO.getMeta().getProperties();
                         if (properties == null) {
-                            properties = new HashMap();
+                            properties = new ArrayList<MetaEntryDTO>();
                             storyDTO.getMeta().setProperties(properties);
                         }
                         if (line.length() == 0) {
@@ -288,10 +286,12 @@
                             if (firstSpaceIndex != -1) {
                                 String key = withoutAtChar.substring(0, firstSpaceIndex);
                                 String value = withoutAtChar.substring(firstSpaceIndex + 1);
-                                properties.put(key, value);
+                                MetaEntryDTO metaEntryDTO = new MetaEntryDTO(key, value);
+                                properties.add(metaEntryDTO);
                             } else {
                                 // some properties don't have value, e.g. @skip
-                                properties.put(withoutAtChar, null);
+                                MetaEntryDTO metaEntryDTO = new MetaEntryDTO(withoutAtChar, null);
+                                properties.add(metaEntryDTO);
                             }
                         }
                     } else {
Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesEditStory.soy
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesEditStory.soy	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesEditStory.soy	(revision )
@@ -29,12 +29,16 @@
             </div>
             <div class="aui-toolbar2-secondary">
                 <div id="saveOrCancelButtons" class="aui-buttons">
-//                    <button id="saveStoryButton" class="aui-button aui-button-subtle">
-//                        <span class="aui-icon aui-icon-small aui-iconfont-success">Save </span> Save
-//                    </button>
-//                    <button id="cancelEditButton" class="aui-button aui-button-subtle">
-//                        <span class="aui-icon aui-icon-small aui-iconfont-close-dialog">Cancel </span> Cancel
-//                    </button>
+                    <span class="aui-buttons">
+                        <button id="saveStoryButton" onclick="editButtonHandler.saveStory(event)" class="aui-button">
+                            <span class="aui-icon aui-icon-small aui-iconfont-success">Save </span> Save
+                        </button>
+                    </span>
+                    <span class="aui-buttons">
+                        <button id="cancelEditButton" onclick="editButtonHandler.cancelEdit(event)" class="aui-button">
+                            <span class="aui-icon aui-icon-small aui-iconfont-close-dialog">Cancel </span> Cancel
+                        </button>
+                    </span>
                 </div>
             </div>
         </div>
Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryView.js
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryView.js	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryView.js	(revision )
@@ -240,19 +240,6 @@
         var templateObj = new Object();
         templateObj.story = story;
 
-
-        templateObj.insertGivenStoriesLinkInfo = new Object();
-        templateObj.insertGivenStoriesLinkInfo.text = "GivenStories";
-        templateObj.insertGivenStoriesLinkInfo.onClickFunction = "insertGivenStories";
-
-        templateObj.insertLifecycleLinkInfo = new Object();
-        templateObj.insertLifecycleLinkInfo.text = "Lifecycle";
-        templateObj.insertLifecycleLinkInfo.onClickFunction = "insertLifecycle";
-
-        templateObj.insertScenarioLinkInfo = new Object();
-        templateObj.insertScenarioLinkInfo.text = "Scenario";
-        templateObj.insertScenarioLinkInfo.onClickFunction = "insertScenario";
-
         var richEditStoryContent = execspec.viewissuepage.editstory.rich.renderRichEditStoryContent(templateObj);
         AJS.$("#richEditStoryContainer").html(richEditStoryContent);
 
@@ -371,47 +358,52 @@
         storyView.debug("# StoryView.showStoryButton");
     }
 
-    this.showStory = function (story, editMode) {
+    this.showStory = function(story) {
+        AJS.$("#storyContainer").html("This issue has a story");
+        AJS.$("#storyContainer").show();
+    }
 
-        storyView.debug("> StoryView.showStory");
-        storyView.debug("story.asString - " + story.asString);
-        storyView.debug("editMode - " + editMode);
+//    this.showStory = function (story, editMode) {
+//
+//        storyView.debug("> StoryView.showStory");
+//        storyView.debug("story.asString - " + story.asString);
+//        storyView.debug("editMode - " + editMode);
+//
+//        if (editMode != undefined && editMode == true) {
+//
+//            var lines = story.asString.split("\n");
+//            var lineCount = lines.length;
+//            var storyAsString = story.asString;
+//            var storyAsHTML = story.asHTML;
+//
+//            // hide story and story reports view
+//            AJS.$("#storyContainer").hide();
+//            AJS.$("#storyReportContainer").hide();
+//
+//            // show edit area
+////            AJS.$("#story-edit-text-area").val(storyAsString);
+////            AJS.$("#story-edit-text-area").attr("rows", lineCount);
+//
+//
+////            AJS.$("#storyRichTextEditArea").html(storyAsString);
+////            var editorContainer = this.editor.one('storyRichTextEditArea');
+////            editorContainer.html(storyAsString);
+//            this.editor.execCommand("setContent", storyAsHTML);
+//
+//            var height = AJS.$("iframe").contents().height() + 40;
+//            storyView.debug("setting editor height to - " + height);
+//            AJS.$("#storyEditContainer").height(height);
+//
+//            AJS.$("#storyEditContainer").show();
+//
+//            var height = AJS.$("iframe").contents().height() + 40;
+//            storyView.debug("setting editor height to - " + height);
+//            AJS.$("#storyEditContainer").height(height);
+//
+//            AJS.$("#testMouseOver").mouseover(function () {
+//                storyView.debug("Mouse over event fired on test container!");
+//            });
 
-        if (editMode != undefined && editMode == true) {
-
-            var lines = story.asString.split("\n");
-            var lineCount = lines.length;
-            var storyAsString = story.asString;
-            var storyAsHTML = story.asHTML;
-
-            // hide story and story reports view
-            AJS.$("#storyContainer").hide();
-            AJS.$("#storyReportContainer").hide();
-
-            // show edit area
-//            AJS.$("#story-edit-text-area").val(storyAsString);
-//            AJS.$("#story-edit-text-area").attr("rows", lineCount);
-
-
-//            AJS.$("#storyRichTextEditArea").html(storyAsString);
-//            var editorContainer = this.editor.one('storyRichTextEditArea');
-//            editorContainer.html(storyAsString);
-            this.editor.execCommand("setContent", storyAsHTML);
-
-            var height = AJS.$("iframe").contents().height() + 40;
-            storyView.debug("setting editor height to - " + height);
-            AJS.$("#storyEditContainer").height(height);
-
-            AJS.$("#storyEditContainer").show();
-
-            var height = AJS.$("iframe").contents().height() + 40;
-            storyView.debug("setting editor height to - " + height);
-            AJS.$("#storyEditContainer").height(height);
-
-            AJS.$("#testMouseOver").mouseover(function () {
-                storyView.debug("Mouse over event fired on test container!");
-            });
-
 //            AJS.$("#inOrderTo").mouseover(function() {
 //                storyView.debug("Mouse over event fired on inOrderTo!");
 //            });
@@ -473,37 +465,37 @@
 //                at: "left+" + 100 + "px top+" + 100 + "px"
 //            });
 
-        } else {
+//        } else {
+//
+//            AJS.$("#storyReportContainer").hide();
+//            AJS.$("#storyEditContainer").hide();
+//
+////            AJS.$("#storyContainer").html(story.asHTML);
+//            var templateObject = new Object();
+//            templateObject.story = story;
+//            templateObject.story.asJson = JSON.stringify(story);
+//            var editStoryContent = execspec.viewissuepage.editstory.renderEditStory(templateObject);
+//            AJS.$("#storyContainer").html(editStoryContent);
+//
+//            AJS.$("#storyContainer").show();
+//
+//            AJS.$(".beforeNarrative").mouseover(function () {
+//                storyView.debug("mouse over on - beforeNarrative")
+//                AJS.$(".beforeNarrativeHint").show();
+//            });
+//
+//            AJS.$(".beforeNarrative").mouseout(function () {
+//                storyView.debug("mouse out on - beforeNarrative")
+//                AJS.$(".beforeNarrativeHint").hide();
+//            });
+//
+//        }
+//
+//        this.updateSelectedButton("show-story-button");
+//
+//        storyView.debug("# StoryView.showStory");
+//    }
 
-            AJS.$("#storyReportContainer").hide();
-            AJS.$("#storyEditContainer").hide();
-
-//            AJS.$("#storyContainer").html(story.asHTML);
-            var templateObject = new Object();
-            templateObject.story = story;
-            templateObject.story.asJson = JSON.stringify(story);
-            var editStoryContent = execspec.viewissuepage.editstory.renderEditStory(templateObject);
-            AJS.$("#storyContainer").html(editStoryContent);
-
-            AJS.$("#storyContainer").show();
-
-            AJS.$(".beforeNarrative").mouseover(function () {
-                storyView.debug("mouse over on - beforeNarrative")
-                AJS.$(".beforeNarrativeHint").show();
-            });
-
-            AJS.$(".beforeNarrative").mouseout(function () {
-                storyView.debug("mouse out on - beforeNarrative")
-                AJS.$(".beforeNarrativeHint").hide();
-            });
-
-        }
-
-        this.updateSelectedButton("show-story-button");
-
-        storyView.debug("# StoryView.showStory");
-    }
-
     this.showStoryReportButtons = function (story) {
 
         storyView.debug("> StoryView.showStoryReportButtons");
@@ -538,30 +530,30 @@
         storyView.debug("# StoryView.showStoryReportButtons");
     }
 
-    this.showStoryReport = function (storyReport, storyVersion) {
-
-        storyView.debug("> StoryView.showStoryReport");
-        storyView.debug("storyReport.environment - " + storyReport.environment);
-
-        var reportToShowTemplateModel = new Object();
-        reportToShowTemplateModel.storyReport = storyReport;
-        var storyReportHTML = execspec.viewissuepage.showstory.renderStoryReport(reportToShowTemplateModel);
-        AJS.$("#storyReportContainer").html(storyReportHTML);
-
-        if (storyVersion > storyReport.storyVersion) {
-            AJS.messages.generic("#reportMessageContainer", {
-                title: "Story has been modified since last run",
-                closeable: false
-            });
-        }
-
-        AJS.$("#storyContainer").hide();
-        AJS.$("#storyEditContainer").hide();
-        AJS.$("#storyReportContainer").show();
-
-        this.updateSelectedButton("show-story-report-" + storyReport.environment);
-        storyView.debug("# StoryView.showStoryReport");
-    }
+//    this.showStoryReport = function (storyReport, storyVersion) {
+//
+//        storyView.debug("> StoryView.showStoryReport");
+//        storyView.debug("storyReport.environment - " + storyReport.environment);
+//
+//        var reportToShowTemplateModel = new Object();
+//        reportToShowTemplateModel.storyReport = storyReport;
+//        var storyReportHTML = execspec.viewissuepage.showstory.renderStoryReport(reportToShowTemplateModel);
+//        AJS.$("#storyReportContainer").html(storyReportHTML);
+//
+//        if (storyVersion > storyReport.storyVersion) {
+//            AJS.messages.generic("#reportMessageContainer", {
+//                title: "Story has been modified since last run",
+//                closeable: false
+//            });
+//        }
+//
+//        AJS.$("#storyContainer").hide();
+//        AJS.$("#storyEditContainer").hide();
+//        AJS.$("#storyReportContainer").show();
+//
+//        this.updateSelectedButton("show-story-report-" + storyReport.environment);
+//        storyView.debug("# StoryView.showStoryReport");
+//    }
 
     this.showAutoComplete = function (entries) {
 
Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/temp.html
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/temp.html	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/temp.html	(revision )
@@ -1,80 +1,116 @@
-<div id="storyDescriptionContainer" class="story-element-container">
+<div class="element-content-container story-meta">
+    <div class="story-meta-keyword">Meta:</div>
+    <div class="story-meta-properties">
+        <div class="story-element-container">
-    <div class="element-operations-container" style="display: none;"><a class="remove-element-link" href="#"
+            <div class="element-operations-container" style="display: none;"><a class="remove-element-link" href="#"
-                                                                        onclick="editButtonHandler.removeElement(event, 'description')"><span
+                                                                                onclick="editButtonHandler.removeElement(event, 'metaField', '1')"><span
-            class="aui-icon aui-icon-small aui-iconfont-close-dialog"></span></a></div>
+                    class="aui-icon aui-icon-small aui-iconfont-close-dialog"></span></a></div>
-    <div class="element-content-container story-description"><textarea class="textarea rich-story-editor-field"
-                                                                       name="comment" rows="1" wrap="off"
-                                                                       placeholder="enter 'Description' here"></textarea>
-    </div>
-</div>
-<div id="insertLinkContainerBeforeMeta" class="insert-element-link-div"></div>
-<div id="storyMetaContainer"></div>
-<div id="insertLinkContainerBeforeNarrative" class="insert-element-link-div">&nbsp;<a aria-controls="dropdown2-more"
-                                                                                      href="#insertBeforeNarrativeDropdownDiv"
-                                                                                      aria-owns="insertBeforeNarrativeDropdownDiv"
+            <div class="story-meta-property element-content-container"><span class="meta-property-start-symbol">@</span><span
+                    class="meta-property-name"><input class="text story-editor-field story-element-field-short"
+                                                      name="meta.properties[0].name" title="meta.properties[0].name" type="text"
+                                                      placeholder="enter 'Property key' here" value="property1"></span>&nbsp;<span
+                    class="meta-preperty-value"><input class="text story-editor-field story-element-field-short"
+                                                       name="meta.properties[0].value" title="meta.properties[0].value" type="text"
+                                                       placeholder="enter 'Property value' here" value="property1_value"></span>
+
+                <div></div>
+                <div class="story-element-container">
+                    <div class="element-operations-container" style="display: none;"><a class="remove-element-link" href="#"
+                                                                                        onclick="editButtonHandler.removeElement(event, 'metaField', '1')"><span
+                            class="aui-icon aui-icon-small aui-iconfont-close-dialog"></span></a></div>
+                    <div class="story-meta-property element-content-container"><span class="meta-property-start-symbol">@</span><span
+                            class="meta-property-name"><input class="text story-editor-field story-element-field-short"
+                                                              name="meta.properties[1].name" title="meta.properties[1].name" type="text"
+                                                              placeholder="enter 'Property key' here" value="property2"></span>&nbsp;<span
+                            class="meta-preperty-value"><input class="text story-editor-field story-element-field-short"
+                                                               name="meta.properties[1].value" title="meta.properties[1].value" type="text"
+                                                               placeholder="enter 'Property value' here" value="property2_value"></span>
+
+                        <div></div>
+                        <div class="story-element-container">
+                            <div class="element-operations-container" style="display: none;"><a class="remove-element-link" href="#"
+                                                                                                onclick="editButtonHandler.removeElement(event, 'metaField', '1')"><span
+                                    class="aui-icon aui-icon-small aui-iconfont-close-dialog"></span></a></div>
+                            <div class="story-meta-property element-content-container"><span
+                                    class="meta-property-start-symbol">@</span><span class="meta-property-name"><input
+                                    class="text story-editor-field story-element-field-short" name="meta.properties[2].name"
+                                    title="meta.properties[2].name" type="text" placeholder="enter 'Property key' here"
+                                    value="property3"></span>&nbsp;<span class="meta-preperty-value"><input
+                                    class="text story-editor-field story-element-field-short" name="meta.properties[2].value"
+                                    title="meta.properties[2].value" type="text" placeholder="enter 'Property value' here"
+                                    value="property3_value"></span>
+
+                                <div></div>
+                                <div>
+                                    <div id="insertLinkContainerMetaProperty">
+                                        <div class="insert-element-link-div">&nbsp;<a aria-controls="dropdown2-more"
+                                                                                      href="#insertDropdownDivMetaProperty"
+                                                                                      aria-owns="insertDropdownDivMetaProperty"
                                                                                       aria-haspopup="true"
                                                                                       class="aui-dropdown2-trigger aui-style-default aui-dropdown2-trigger-arrowless insert-element-link"
-                                                                                      style="display: none;"
-                                                                                      pressed="false"><span
+                                                                                      style="display: none;" pressed="false"><span
-        class="aui-icon aui-icon-small aui-iconfont-add insert-element-icon"></span></a>
+                                                class="aui-icon aui-icon-small aui-iconfont-add insert-element-icon"></span></a>
 
-    <div id="insertBeforeNarrativeDropdownDiv" trigger-div-id="insertBeforeNarrativeTriggerDiv"
+                                            <div id="insertDropdownDivMetaProperty" trigger-div-id="insertTriggerDivMetaProperty"
-         class="aui-dropdown2 aui-style-default insert-dropdown-content" aria-hidden="true"
-         data-dropdown2-alignment="left" style="left: 280.6px; top: 801.3px; display: none;">
-        <ul class="aui-list-truncate">
+                                                 class="aui-dropdown2 aui-style-default insert-dropdown-content" aria-hidden="true"
+                                                 data-dropdown2-alignment="left" style="left: 280.6px; top: 801.3px; display: none;">
+                                                <ul class="aui-list-truncate">
-            <li><a href="#" onclick="editButtonHandler.insertMeta(event)">Meta</a></li>
+                                                    <li><a href="#" onclick="editButtonHandler.insertElement(event, 'metaField')">New meta
+                                                        field</a></li>
-        </ul>
-    </div>
+                                                </ul>
+                                            </div>
-    <div id="insertBeforeNarrativeDropdownDiv" trigger-div-id="insertBeforeNarrativeTriggerDiv"
-         class="aui-dropdown2 aui-style-default insert-dropdown-content" aria-hidden="true"
-         data-dropdown2-alignment="left" style="left: 57.55px; top: 564px; display: none;">
-        <ul class="aui-list-truncate">
-            <li><a class="" href="#" onclick="editButtonHandler.insertDescription(event)">Description</a></li>
-            <li><a class="" href="#" onclick="editButtonHandler.insertMeta(event)">Meta</a></li>
-        </ul>
-    </div>
-</div>
+                                        </div>
+                                    </div>
+                                </div>
+                            </div>
+                        </div>
+                        <div id="insertLinkContainerBeforeNarrative"></div>
-<div class="narrative">
-    <div class="title">Narrative:</div>
-    <div class="inOrderTo">
-        <table>
-            <tbody>
-            <tr>
+                        <div class="narrative">
+                            <div class="title">Narrative:</div>
+                            <div class="inOrderTo">
+                                <table>
+                                    <tbody>
+                                    <tr>
-                <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;"><span class="keyword">In order to</span>
-                </td>
-                <td style="text-align: left;"><input style="width: 100%;"
-                                                     class="text long-field rich-story-editor-field" name="In order to"
-                                                     title="In order to" placeholder="enter 'In order to' here"
-                                                     type="text"></td>
+                                        <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;"><span
+                                                class="keyword">In order to</span></td>
+                                        <td style="text-align: left;"><input
+                                                class="text story-editor-field long-field story-element-field-long"
+                                                name="narrative.inOrderTo" title="narrative.inOrderTo" type="text"
+                                                placeholder="enter 'In order to' here"></td>
-            </tr>
-            </tbody>
-        </table>
-    </div>
-    <div class="asA">
-        <table>
-            <tbody>
-            <tr>
+                                    </tr>
+                                    </tbody>
+                                </table>
+                            </div>
+                            <div class="asA">
+                                <table>
+                                    <tbody>
+                                    <tr>
-                <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;"><span class="keyword">As a</span>
-                </td>
-                <td style="text-align: left;"><input style="width: 100%;"
-                                                     class="text long-field rich-story-editor-field" name="As a"
-                                                     title="As a" placeholder="enter 'As a' here" type="text"></td>
+                                        <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;"><span
+                                                class="keyword">As a</span></td>
+                                        <td style="text-align: left;"><input
+                                                class="text story-editor-field long-field story-element-field-long" name="narrative.asA"
+                                                title="narrative.asA" type="text" placeholder="enter 'As a' here"></td>
-            </tr>
-            </tbody>
-        </table>
-    </div>
+                                    </tr>
+                                    </tbody>
+                                </table>
+                            </div>
-    <div class="iWantTo"><span class="keyword">I want to</span><span class="value"> [click here to edit]</span></div>
-</div>&nbsp;<a aria-controls="dropdown2-more" href="#insertAfterNarrativeDropdownDiv"
-               aria-owns="insertAfterNarrativeDropdownDiv" aria-haspopup="true"
-               class="aui-dropdown2-trigger aui-style-default aui-dropdown2-trigger-arrowless insert-element-link"
-               style="display: none" pressed="false"><span
-        class="aui-icon aui-icon-small aui-iconfont-add insert-element-icon"></span></a>
-<div id="insertAfterNarrativeDropdownDiv" trigger-div-id="insertAfterNarrativeTriggerDiv"
-     class="aui-dropdown2 aui-style-default insert-dropdown-content" aria-hidden="true" data-dropdown2-alignment="left"
-     style="left: 280.6px; top: 801.3px; display: none;">
-    <ul class="aui-list-truncate">
-        <li><a href="#" onclick="editButtonHandler.insertGivenStories(event)">GivenStories</a></li>
-        <li><a href="#" onclick="editButtonHandler.insertLifecycle(event)">Lifecycle</a></li>
-        <li><a href="#" onclick="editButtonHandler.insertScenario(event)">Scenario</a></li>
-    </ul>
+                            <div class="iWantTo">
+                                <table>
+                                    <tbody>
+                                    <tr>
+                                        <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;"><span
+                                                class="keyword">I want to</span></td>
+                                        <td style="text-align: left;"><input
+                                                class="text story-editor-field long-field story-element-field-long" name="narrative.iWantTo"
+                                                title="narrative.iWantTo" type="text" placeholder="enter 'I Want to' here"></td>
+                                    </tr>
+                                    </tbody>
+                                </table>
+                            </div>
+                        </div>
+                    </div>
+                </div>
+            </div>
+        </div>
+    </div>
 </div>
\ No newline at end of file
Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryService.js
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryService.js	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryService.js	(revision )
@@ -54,12 +54,12 @@
         storyService.debug("# fetchNewStoryTemplate");
     }
 
-    this.saveOrUpdateStory = function (story, callBack) {
+    this.saveOrUpdateStory = function (storyPayload, callBack) {
 
         storyService.debug("> saveOrUpdateStory");
-        storyService.debug("story - " + story);
-        var storyAsString = story.asString;
-        storyService.debug("storyAsString - " + storyAsString);
+        storyService.debug("storyPayload - " + storyPayload);
+//        var storyAsString = storyPayload.asString;
+//        storyService.debug("storyAsString - " + storyAsString);
 
         var successCallback = function (data, status, xhr) {
             storyService.debug("> StoryService.saveOrUpdateStory.successCallback");
@@ -72,9 +72,9 @@
             storyService.debug("# StoryService.saveOrUpdateStory.successCallback");
         }
 
-        var saveUrl = pathSave + story.projectKey + "/" + story.issueKey;
-        if (story.version != undefined && story.version != "") {
-            saveUrl += "?version=" + story.version;
+        var saveUrl = pathSave + storyPayload.projectKey + "/" + storyPayload.issueKey;
+        if (storyPayload.version != undefined && storyPayload.version != "") {
+            saveUrl += "?version=" + storyPayload.version;
         }
         storyService.debug("saveUrl - " + saveUrl);
 
@@ -83,7 +83,7 @@
             url: saveUrl,
             contentType: "text/plain; charset=utf-8",
             success: successCallback,
-            data: storyAsString,
+            data: storyPayload,
             dataType: "json"
         });
 
\ No newline at end of file
Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/service/StoryServiceImpl.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/service/StoryServiceImpl.java	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/service/StoryServiceImpl.java	(revision )
@@ -7,10 +7,8 @@
 import com.mycomp.execspec.jiraplugin.ao.story.StoryDao;
 import com.mycomp.execspec.jiraplugin.ao.testreport.StoryHtmlReport;
 import com.mycomp.execspec.jiraplugin.ao.testreport.StoryReportDao;
-import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
 import com.mycomp.execspec.jiraplugin.dto.story.StoryDTOUtils;
 import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
-import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
 import org.apache.commons.lang.Validate;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
@@ -84,7 +82,17 @@
         story.setVersion(1L);
         story.setIssueKey(storyDTO.getIssueKey());
         story.setProjectKey(storyDTO.getProjectKey());
-        story.setAsString(storyDTO.getAsString());
+
+
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
 
@@ -118,8 +126,7 @@
 
             Story story = byIssueKey.get(0);
 
-            List<StepDocDTO> stepDocs = stepDocsSerivce.findForProject(projectKey);
-            StoryDTO storyDTO = StoryDTOUtils.toDTO(story, stepDocs);
+            StoryDTO storyDTO = StoryDTOUtils.toDTO(story);
             return storyDTO;
         }
     }
@@ -127,13 +134,11 @@
     @Override
     public List<StoryDTO> findByProjectKey(String projectKey) {
 
-        List<StepDocDTO> stepDocs = stepDocsSerivce.findForProject(projectKey);
-
         List<Story> stories = storyDao.findAll();
 
         List<StoryDTO> storyDTOs = new ArrayList<StoryDTO>(stories.size());
         for (Story story : stories) {
-            StoryDTO storyDTO = StoryDTOUtils.toDTO(story, stepDocs);
+            StoryDTO storyDTO = StoryDTOUtils.toDTO(story);
             storyDTOs.add(storyDTO);
         }
 
@@ -145,9 +150,7 @@
 
         Story story = storyDao.get(storyId);
 
-        List<StepDocDTO> stepDocs = stepDocsSerivce.findForProject(story.getProjectKey());
-        List<StoryHtmlReportDTO> storyReports = storyReportService.findStoryReports(story.getProjectKey(), story.getIssueKey());
-        StoryDTO storyModel = StoryDTOUtils.toDTO(story, stepDocs);
+        StoryDTO storyModel = StoryDTOUtils.toDTO(story);
         return storyModel;
     }
 
Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryEditHandler.js
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryEditHandler.js	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryEditHandler.js	(revision )
@@ -12,7 +12,7 @@
         }
     }
 
-    this.init = function () {
+    this.init = function ( ) {
         this.debug("initialized");
     }
 
@@ -325,6 +325,18 @@
         this.debug("# rawTextEditorClicked");
     }
 
+    this.getArrayIndexFromPath = function (str) {
+        this.debug("> getArrayIndexFromPath");
+        var regExp = new RegExp('\\[(\\d+)\\]$');
+        var match = regExp.exec(str);
+        if (match != null) {
+            return match[1];
+        } else {
+            return null;
+        }
+        this.debug("# getArrayIndexFromPath");
+    }
+
     this.bindInputElementsToModel = function () {
 
         this.debug("> bindInputElementsToModel");
@@ -338,17 +350,25 @@
                 var fieldValue = AJS.$(this).attr("value");
                 editButtonHandler.debug("fieldName = " + fieldName + ", value = " + fieldValue);
 
-//                editButtonHandler.debug("convert indexes to properties");
-//                editButtonHandler.debug("fieldName before - " + fieldName);
-//                fieldName = fieldName.replace(/\[(\w+)\]/g, '.$1');
-//                editButtonHandler.debug("fieldName after - " + fieldName);
-
                 var path = fieldName.split('.');
                 var obj = storyController.currentStory;
                 for (var i = 0; i < path.length - 1; i++) {
-                    obj[path[i]] = {};
-                    obj = obj[path[i]];
+                    var pathPart = path[i];
+//                    if (obj[ pathPart] == null) {
+//                        obj[pathPart] = {};
+//                    }
+                    editButtonHandler.debug("### checking if fieldName part ends in array index - " + pathPart);
+                    var arrayIndexFromPath = editButtonHandler.getArrayIndexFromPath(pathPart);
+                    if (arrayIndexFromPath != null) {
+                        editButtonHandler.debug("### fieldName part ends in array index - " + pathPart);
+                        var partWithoutIndex = pathPart.substr(0, pathPart.length - (arrayIndexFromPath.length + 2));
+                        editButtonHandler.debug("### partWithoutIndex " + partWithoutIndex);
+                        obj = obj[partWithoutIndex][arrayIndexFromPath];
+                    } else {
+                        obj = obj[pathPart];
-                }
+                    }
+
+                }
                 obj[path[path.length - 1]] = fieldValue;
 
                 //console.log("s before - '" + s + "'");
@@ -395,27 +415,19 @@
         this.debug("# insertElement");
     }
 
-    this.insertDescription = function () {
+    this.removeElement = function (event, elementName, index) {
 
-        this.debug("> insertDescription");
-
-        storyController.currentStory.description = "";
-
-        var descriptionHtml = execspec.viewissuepage.editstory.rich.renderStoryDescriptionField(storyController.currentStory);
-        AJS.$("#storyDescriptionContainer").html(descriptionHtml);
-
-        this.debug("# insertDescription");
-    }
-
-    this.removeElement = function (event, elementName) {
-
         this.debug("> removeElement");
         this.debug("elementName - " + elementName);
 
+        this.bindInputElementsToModel();
+
         if (elementName == "description") {
             this.removeDescription();
         } else if (elementName == "meta") {
             this.removeMeta();
+        } else if (elementName == "metaField") {
+            this.removeMetaField(index);
         }
 
         this.assignRichEditorHandlers(storyController.currentStory);
@@ -425,6 +437,18 @@
         this.debug("# removeElement");
     }
 
+    this.insertDescription = function () {
+
+        this.debug("> insertDescription");
+
+        storyController.currentStory.description = "";
+
+        var descriptionHtml = execspec.viewissuepage.editstory.rich.renderStoryDescriptionField(storyController.currentStory);
+        AJS.$("#storyDescriptionContainer").html(descriptionHtml);
+
+        this.debug("# insertDescription");
+    }
+
     this.removeMeta = function () {
 
         this.debug("> removeMeta");
@@ -434,6 +458,24 @@
         this.debug("# removeMeta");
     }
 
+    this.removeMetaField = function (index) {
+
+        this.debug("> removeMetaField");
+
+        this.debug("story before removing property at index - " + index
+            + ":\n" + JSON.stringify(storyController.currentStory, null, "\t"));
+
+        storyController.currentStory.meta.properties.splice(index, 1);
+
+        this.debug("story after removing property at index - " + index
+            + ":\n" + JSON.stringify(storyController.currentStory, null, "\t"));
+
+        var metaHtml = execspec.viewissuepage.editstory.rich.renderStoryMeta(storyController.currentStory);
+        AJS.$("#storyMetaContainer").html(metaHtml);
+        this.debug("# removeMetaField");
+    }
+
+
     this.removeDescription = function () {
 
         this.debug("> removeDescription");
@@ -525,6 +567,18 @@
 
         event.preventDefault();
         this.debug("# insertScenario");
+    }
+
+    this.saveStory = function (event) {
+
+        this.debug("> saveStory");
+
+        this.bindInputElementsToModel();
+
+        storyController.saveStoryAsModel();
+
+        event.preventDefault();
+        this.debug("# saveStory");
     }
 
 }
\ No newline at end of file
Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/rest/StoryResourceCrud.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/rest/StoryResourceCrud.java	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/rest/StoryResourceCrud.java	(revision )
@@ -4,9 +4,9 @@
 import com.atlassian.jira.security.JiraAuthenticationContext;
 import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
 import com.mycomp.execspec.jiraplugin.dto.story.output.*;
-import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
 import com.mycomp.execspec.jiraplugin.service.StoryService;
 import org.apache.commons.lang.Validate;
+import org.codehaus.jackson.map.ObjectMapper;
 import org.jbehave.core.i18n.LocalizedKeywords;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
@@ -14,8 +14,7 @@
 import javax.ws.rs.*;
 import javax.ws.rs.core.MediaType;
 import javax.ws.rs.core.Response;
-import java.util.ArrayList;
-import java.util.List;
+import java.io.IOException;
 
 /**
  * Contains rest api methods related to processing of Story objects.
@@ -47,11 +46,11 @@
     public StoryDTO save(@PathParam("projectKey") String projectKey,
                          @PathParam("issueKey") String issueKey,
                          @QueryParam("version") String versionParam,
-                         String asString) {
+                         String storyPayload) {
 
         Validate.notNull(projectKey);
         Validate.notNull(issueKey);
-        Validate.notEmpty(asString, "story asString parameter was empty");
+//        Validate.notEmpty(asString, "story asString parameter was empty");
 
         Long version;
         if (versionParam != null && !versionParam.isEmpty()) {
@@ -59,16 +58,21 @@
         } else {
             version = null;
         }
-        List<StoryHtmlReportDTO> storyReports = new ArrayList<StoryHtmlReportDTO>();
-
         // TODO - decide what to do about the null parameters below?
+        ObjectMapper mapper = new ObjectMapper();
         StoryDTO storyDTO = null;
+        try {
+            storyDTO = mapper.readValue(storyPayload, StoryDTO.class);
+        } catch (IOException e) {
+            throw new RuntimeException(e);
+        }
 //                new StoryDTO(
 //                projectKey, issueKey, version, asString, null, storyReports,
 //                null, null, null, null, null, null);
         log.debug("saving story:\n" + storyDTO);
 
         StoryDTO savedStoryDTO = storyService.saveOrUpdate(storyDTO);
+        Validate.notNull(savedStoryDTO.getVersion());
         return savedStoryDTO;
     }
 
@@ -100,6 +104,16 @@
         LocalizedKeywords keywords = new LocalizedKeywords();
 
         StoryDTO storyDTO = new StoryDTO();
+
+//        MetaDTO meta = new MetaDTO();
+//        meta.setKeyword("Meta:");
+//        List<MetaEntryDTO> properties = new ArrayList<MetaEntryDTO>();
+//        properties.add(new MetaEntryDTO("property1", "property1_value"));
+//        properties.add(new MetaEntryDTO("property2", "property2_value"));
+//        properties.add(new MetaEntryDTO("property3", "property3_value"));
+//        meta.setProperties(properties);
+//        storyDTO.setMeta(meta);
+
         NarrativeDTO narrative = new NarrativeDTO(keywords.narrative());
         narrative.setInOrderTo(new InOrderToDTO(keywords.inOrderTo(), null));
         narrative.setAsA(new AsADTO(keywords.asA(), null));
Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/StoryDTOUtils.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/StoryDTOUtils.java	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/StoryDTOUtils.java	(revision )
@@ -1,9 +1,9 @@
 package com.mycomp.execspec.jiraplugin.dto.story;
 
 import com.mycomp.execspec.jiraplugin.ao.story.Story;
-import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
-import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
+import com.mycomp.execspec.jiraplugin.dto.story.output.*;
 import com.mycomp.execspec.jiraplugin.util.ByLineStoryParser;
+import org.apache.commons.lang.Validate;
 import org.jbehave.core.steps.StepCreator;
 
 import java.util.List;
@@ -14,13 +14,18 @@
  */
 public class StoryDTOUtils {
 
-    public static StoryDTO toDTO(Story story, List<StepDocDTO> stepDocs) {
+    public static StoryDTO toDTO(Story story) {
 
         String issueKey = story.getIssueKey();
         String storyAsString = story.getAsString();
 
         ByLineStoryParser parser = new ByLineStoryParser();
         StoryDTO storyDTO = parser.parseStory(storyAsString, issueKey + ".story");
+
+        storyDTO.setProjectKey(story.getProjectKey());
+        storyDTO.setIssueKey(story.getIssueKey());
+        storyDTO.setVersion(story.getVersion());
+
         return storyDTO;
     }
 
@@ -132,4 +137,101 @@
         }
     }
 
+    public static String asString(StoryDTO storyDTO) {
+
+        // walk the story model and convert to string
+
+        final String LB = "\n";
+        StringBuilder sb = new StringBuilder();
+
+        // description
+        String description = storyDTO.getDescription();
+        if (description != null && !description.trim().isEmpty()) {
+            sb.append(description.trim() + LB);
+            sb.append(LB);
+        }
+
+        // meta
+        MetaDTO meta = storyDTO.getMeta();
+        if (meta != null) {
+            String keyword = meta.getKeyword();
+            Validate.notEmpty(keyword);
+            keyword = keyword.trim();
+            Validate.notEmpty(keyword);
+            sb.append(keyword + LB);
+            List<MetaEntryDTO> properties = meta.getProperties();
+            if (properties != null && !properties.isEmpty()) {
+                for (MetaEntryDTO p : properties) {
+                    String name = p.getName();
+                    String value = p.getValue();
+                    sb.append(name);
+                    if (value != null && !value.isEmpty()) {
+                        sb.append(" " + value);
+                    }
+                    sb.append(LB);
+                }
+            }
+            sb.append(LB);
+        }
+
+        // narrative
+        NarrativeDTO narrative = storyDTO.getNarrative();
+        if (narrative != null) {
+            {
+                String keyword = narrative.getKeyword();
+                sb.append(keyword + LB);
+            }
+            {
+                InOrderToDTO inOrderTo = narrative.getInOrderTo();
+                if (inOrderTo != null) {
+                    String keyword = inOrderTo.getKeyword();
+                    sb.append(keyword);
+                    String value = inOrderTo.getValue();
+                    if (value != null && !value.isEmpty()) {
+                        sb.append(" " + value);
+                    }
+                    sb.append(LB);
+                }
+            }
+            {
+                AsADTO asA = narrative.getAsA();
+                if (asA != null) {
+                    String keyword = asA.getKeyword();
+                    sb.append(keyword);
+                    String value = asA.getValue();
+                    if (value != null && !value.isEmpty()) {
+                        sb.append(" " + value);
+                    }
+                    sb.append(LB);
+                }
+            }
+            {
+                IWantToDTO iWantTo = narrative.getiWantTo();
+                if (iWantTo != null) {
+                    String keyword = iWantTo.getKeyword();
+                    sb.append(keyword);
+                    String value = iWantTo.getValue();
+                    if (value != null && !value.isEmpty()) {
+                        sb.append(" " + value);
+                    }
+                    sb.append(LB);
+                }
+            }
+            {
+                SoThatDTO soThat = narrative.getSoThat();
+                if (soThat != null) {
+                    String keyword = soThat.getKeyword();
+                    sb.append(keyword);
+                    String value = soThat.getValue();
+                    if (value != null && !value.isEmpty()) {
+                        sb.append(" " + value);
+                    }
+                    sb.append(LB);
+                }
+            }
+        }
+
+        String asString = sb.toString();
+        return asString;
+    }
 }
Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/MetaDTO.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/MetaDTO.java	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/MetaDTO.java	(revision )
@@ -3,7 +3,7 @@
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlRootElement;
-import java.util.Map;
+import java.util.List;
 
 /**
  * Created by Dmytro on 5/28/2014.
@@ -14,13 +14,13 @@
 
     private String keyword;
 
-    private Map properties;
+    private List<MetaEntryDTO> properties;
 
-    public Map getProperties() {
+    public List<MetaEntryDTO> getProperties() {
         return properties;
     }
 
-    public void setProperties(Map properties) {
+    public void setProperties(List<MetaEntryDTO> properties) {
         this.properties = properties;
     }
 
Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesShowStory.soy
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesShowStory.soy	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesShowStory.soy	(revision )
@@ -8,12 +8,12 @@
     {$story.asString}
 {/template}
 
-/**
- * Renders story edit area.
- */
-{template .renderEditStoryArea2}
-<div class="story-panel">
-    Rich Text Edit Area - Default content
+///**
+// * Renders story edit area.
+// */
+//{template .renderEditStoryArea2}
+//<div class="story-panel">
+//    Rich Text Edit Area - Default content
 //    <div class="mod-content">
 //        <div class="field-ignore-highlight editable-field active" id="description-val">
 //            <form action="#" class="ajs-dirty-warning-exempt aui" id="description-form">
@@ -46,37 +46,37 @@
 //            </form>
 //        </div>
 //    </div>
-</div>
-{/template}
+//</div>
+//{/template}
 
 
-/**
- * Render rich text area.
- * @param range
- */
-{template .renderRichTextArea}
-    <div>This is a test rich text area</div>
-    <div>
-        <table style="border-style: solid; border-width: 1px; border-color: blue">
-            <tr>
-                {foreach $i in $range}
-                    <th>column {$i}</th>
-                {/foreach}
-            </tr>
-            <tr>
-                {foreach $i in $range}
-                    <td>cell {$i}</td>
-                {/foreach}
-            </tr>
-        </table>
-        {foreach $i in $range}
-            <div>
-                test {$i}
-            </div>
-        {/foreach}
-
-    </div>
-{/template}
+///**
+//* Render rich text area.
+//* @param range
+// */
+//{template .renderRichTextArea}
+//    <div>This is a test rich text area</div>
+//    <div>
+//        <table style="border-style: solid; border-width: 1px; border-color: blue">
+//            <tr>
+//                {foreach $i in $range}
+//                    <th>column {$i}</th>
+//                {/foreach}
+//            </tr>
+//            <tr>
+//                {foreach $i in $range}
+//                    <td>cell {$i}</td>
+//                {/foreach}
+//            </tr>
+//        </table>
+//        {foreach $i in $range}
+//            <div>
+//                test {$i}
+//            </div>
+//        {/foreach}
+//
+//    </div>
+//{/template}
 
 /**
  * Renders story as html.
Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesEditStoryRich.soy
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesEditStoryRich.soy	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesEditStoryRich.soy	(revision )
@@ -197,26 +197,34 @@
             {if $meta.properties != null}
                 <div class="story-meta-properties">
                     {foreach $metaProperty in $meta.properties}
-                        <div class="story-meta-property">
-                            <span class="meta-property-start-symbol">
-                                @
-                            </span>
+                        <div class="story-element-container">
+                            <div
+                                class="element-operations-container"
+                                style="display: none">
+                                <a class="remove-element-link"href="#"
+                                      onclick="editButtonHandler.removeElement(event, 'metaField', '{index($metaProperty)}')">
+                                    <span class="aui-icon aui-icon-small aui-iconfont-close-dialog"></span>
+                                </a>
+                            </div>
+                            <div class="story-meta-property element-content-container">
+                                <span class="meta-property-start-symbol">@</span>
-                            <span class="meta-property-name">
-                                {call .renderShortSingleLineField}
+                                <span class="meta-property-name">
+                                    {call .renderShortSingleLineField}
-                                    {param fieldName: 'meta.properties[0].name' /}
+                                        {param fieldName: 'meta.properties['+ index($metaProperty) + '].name' /}
-                                    {param displayValue: 'Property key' /}
-                                    {param fieldValue: $metaProperty.name /}
-                                {/call}
-                            </span>
-                            &nbsp;
-                            <span class="meta-preperty-value">
-                                {call .renderShortSingleLineField}
+                                        {param displayValue: 'Property key' /}
+                                        {param fieldValue: $metaProperty.name /}
+                                    {/call}
+                                </span>
+                                &nbsp;
+                                <span class="meta-preperty-value">
+                                    {call .renderShortSingleLineField}
-                                    {param fieldName: 'meta.properties[0].value' /}
+                                        {param fieldName: 'meta.properties['+ index($metaProperty) + '].value' /}
-                                    {param displayValue: 'Property value' /}
-                                    {param fieldValue: $metaProperty.value /}
-                                {/call}
-                            </span>
+                                        {param displayValue: 'Property value' /}
+                                        {param fieldValue: $metaProperty.value /}
+                                    {/call}
+                                </span>
-                        <div>
+                            </div>
+                        </div>
                     {/foreach}
                 <div>
             {/if}
@@ -264,7 +272,7 @@
                         </td>
                         <td style="text-align: left;">
                             {call .renderLongSingleLineField}
-                                {param fieldName: 'narrative.inOrderTo' /}
+                                {param fieldName: 'narrative.inOrderTo.value' /}
                                 {param displayValue: 'In order to' /}
                                 {param fieldValue: $story.narrative.inOrderTo.value /}
                             {/call}
@@ -280,7 +288,7 @@
                         </td>
                         <td style="text-align: left;">
                             {call .renderLongSingleLineField}
-                                {param fieldName: 'narrative.asA' /}
+                                {param fieldName: 'narrative.asA.value' /}
                                 {param displayValue: 'As a' /}
                                 {param fieldValue: $story.narrative.asA.value /}
                             {/call}
@@ -296,7 +304,7 @@
                         </td>
                         <td style="text-align: left;">
                             {call .renderLongSingleLineField}
-                                {param fieldName: 'narrative.iWantTo' /}
+                                {param fieldName: 'narrative.iWantTo.value' /}
                                 {param displayValue: 'I Want to' /}
                                 {param fieldValue: $story.narrative.iWantTo.value /}
                             {/call}
Index: execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryController.js
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryController.js	(date 1402658903000)
+++ execspec/test-local-repo/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryController.js	(revision )
@@ -29,23 +29,24 @@
 
     this.loadStory = function () {
 
-        this.debug("> showStory");
+        this.debug("> loadStory");
         var issueKey = pageUtils.getIssueKey();
         var projectKey = pageUtils.getProjectKey();
         storyService.find(projectKey, issueKey,
             function (story) {
+                storyView.debug("> loadStory.callback, story - " + story);
                 if (story != undefined) {
-                    storyView.showStoryButton(story);
+//                    storyView.showStoryButton(story);
 //                    storyView.showStoryReportButtons(story); // TODO
-                    storyView.showStory(story, storyController.editMode);
                     storyController.currentStory = story;
+                    storyController.showStoryHandler();
                 } else {
                     storyController.debug("no story found for project - " + projectKey + ", issue - " + issueKey);
                     storyView.showAddStory();
                 }
             }
         );
-        this.debug("# showStory");
+        this.debug("# loadStory");
     }
 
     this.addStoryHandler = function (event) {
@@ -94,33 +95,45 @@
         this.debug("# showStoryReport");
     }
 
-    this.addStory = function () {
+//    this.addStory = function () {
+//
+//        this.debug("> addStory");
+//
+//        var story = new StoryModel();
+//        story.projectKey = pageUtils.getProjectKey();
+//        story.issueKey = pageUtils.getIssueKey();
+//
+//        var newStoryAsString = "narrative:";
+//        newStoryAsString += "\nIn order to ";
+//        newStoryAsString += "\nAs a ";
+//        newStoryAsString += "\nI want to ";
+//        newStoryAsString += "\n\nScenario: test scenario";
+//        newStoryAsString += "\nGiven something none existent";
+//        story.asString = newStoryAsString;
+//
+//        // TODO - temp
+//        story.meta = new Object();
+//        story.meta.properties = [];
+//        var metaField = new Object();
+//        metaField.name = "metaName";
+//        metaField.value = "metaValue";
+//        story.meta.properties.push(metaField);
+//        var metaField2 = new Object();
+//        metaField2.name = "meta2Name";
+//        metaField2.value = "meta2Value";
+//        story.meta.properties.push(metaField2);
+//
+//        storyService.saveOrUpdateStory(story,
+//            function (story) {
+//                storyController.currentStory = story;
+//                storyView.showStory(story, storyController.editMode);
+//                storyView.showStoryReportButtons(story);
+//                // TODO remove the add story button from the menu
+//            });
+//
+//        this.debug("# addStory");
+//    }
 
-        this.debug("> addStory");
-
-        var story = new StoryModel();
-        story.projectKey = pageUtils.getProjectKey();
-        story.issueKey = pageUtils.getIssueKey();
-
-        var newStoryAsString = "narrative:";
-        newStoryAsString += "\nIn order to ";
-        newStoryAsString += "\nAs a ";
-        newStoryAsString += "\nI want to ";
-        newStoryAsString += "\n\nScenario: test scenario";
-        newStoryAsString += "\nGiven something none existent";
-        story.asString = newStoryAsString;
-
-        storyService.saveOrUpdateStory(story,
-            function (story) {
-                storyController.currentStory = story;
-                storyView.showStory(story, storyController.editMode);
-                storyView.showStoryReportButtons(story);
-                // TODO remove the add story button from the menu
-            });
-
-        this.debug("# addStory");
-    }
-
     this.editStoryHandler = function () {
 
         this.debug("> editStoryHandler");
@@ -166,21 +179,13 @@
         this.debug("# deleteStory");
     }
 
-    this.saveStory = function (event) {
+    this.saveStoryAsModel = function () {
 
-        this.debug("> saveStory");
-        event.preventDefault();
+        this.debug("> saveStoryAsModel");
 
-        var model = new StoryModel();
-        var issueKey = pageUtils.getIssueKey();
-        model.issueKey = issueKey;
-        var projectKey = pageUtils.getProjectKey();
-        model.projectKey = projectKey;
-        var storyInput = storyView.getStoryInputAsString();
-        model.asString = storyInput;
-        model.version = this.currentStory.version;
+        var storyPayload = JSON.stringify(this.currentStory);
 
-        storyService.saveOrUpdateStory(model, function (savedStory) {
+        storyService.saveOrUpdateStory(storyPayload, function (savedStory) {
             storyController.debug("> saveStory.saveOrUpdateStory callback");
             storyController.editMode = false;
             storyView.showStory(savedStory, storyController.editMode);
@@ -189,8 +194,34 @@
             storyController.debug("# saveStory.saveOrUpdateStory callback");
         });
 
-        this.debug("# saveStory");
+        this.debug("# saveStoryAsModel");
     }
+
+//    this.saveStory = function (event) {
+//
+//        this.debug("> saveStory");
+//        event.preventDefault();
+//
+//        var model = new StoryModel();
+//        var issueKey = pageUtils.getIssueKey();
+//        model.issueKey = issueKey;
+//        var projectKey = pageUtils.getProjectKey();
+//        model.projectKey = projectKey;
+//        var storyInput = storyView.getStoryInputAsString();
+//        model.asString = storyInput;
+//        model.version = this.currentStory.version;
+//
+//        storyService.saveOrUpdateStory(model, function (savedStory) {
+//            storyController.debug("> saveStory.saveOrUpdateStory callback");
+//            storyController.editMode = false;
+//            storyView.showStory(savedStory, storyController.editMode);
+//            storyView.showStoryReportButtons(savedStory);
+//            storyController.currentStory = savedStory;
+//            storyController.debug("# saveStory.saveOrUpdateStory callback");
+//        });
+//
+//        this.debug("# saveStory");
+//    }
 
     this.cancelEditingStory = function (event) {
 
