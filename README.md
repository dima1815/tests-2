Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesEditStoryRich.soy
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesEditStoryRich.soy	(date 1402994274000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesEditStoryRich.soy	(revision )
@@ -34,6 +34,7 @@
  * @param triggerDivId
  * @param dropdownDivId
  * @param dropdownItems
+ * @param additionalParams
  */
 {template .renderInsertLinkDiv}
     {let $addIconClass: 'aui-icon aui-icon-small aui-iconfont-add' /}
@@ -53,14 +54,58 @@
             <ul class="aui-list-truncate">
                 {foreach $item in $dropdownItems}
                     <li><a href="#"
-                        onclick="editButtonHandler.{$item.onClickFunction}(event, '{$item.elementName}')">{$item.text}</a></li>
+                        onclick="editButtonHandler.{$item.onClickFunction}(event, '{$item.elementName}'
+                        {if $additionalParams != null}
+                            {foreach $param in $additionalParams}
+                                , '{$param}'
-                {/foreach}
+                            {/foreach}
+                        {/if}
+                        )">{$item.text}</a></li>
+                {/foreach}
             </ul>
         </div>
     </div>
 {/template}
 
 /**
+ * Renders the '+' insert icon link to add scenario steps.
+ * @param triggerDivId
+ * @param dropdownDivId
+ * @param dropdownItems
+ * @param additionalParams
+ */
+{template .renderInsertStepLinkDiv}
+    {let $addIconClass: 'aui-icon aui-icon-small aui-iconfont-add' /}
+    <div class="insert-element-link-div">
+        &nbsp;
+        <a aria-controls="dropdown2-more" href="#{$dropdownDivId}" aria-owns="{$dropdownDivId}"
+            aria-haspopup="true"
+            class="aui-dropdown2-trigger aui-style-default aui-dropdown2-trigger-arrowless insert-element-link"
+            style="display: none"
+            pressed="false">
+            <span class="{$addIconClass} insert-element-icon"></span>
+        </a>
+        <div id="{$dropdownDivId}" trigger-div-id="{$triggerDivId}"
+            class="aui-dropdown2 aui-style-default insert-dropdown-content"
+            aria-hidden="true" data-dropdown2-alignment="left"
+            style="left: 280.6px; top: 801.3px; display: none;">
+            <ul class="aui-list-truncate dropdown-items">
+                {foreach $item in $dropdownItems}
+                    <li><a href="#"
+                        onclick="editButtonHandler.insertStep(event, '{$item.elementName}'
+                        {if $additionalParams != null}
+                            {foreach $param in $additionalParams}
+                                , '{$param}'
+                            {/foreach}
+                        {/if}
+                        )">{$item.text}</a></li>
+                {/foreach}
+            </ul>
+        </div>
+    </div>
+{/template}
+
+/**
  * Renders the text area type editable field.
  * @param fieldName
  * @param displayValue
@@ -294,13 +339,41 @@
  * @param scenarios
  */
 {template .renderScenarios}
-     <div class="story-scenarios">
-         {foreach $scenario in $scenarios}
+    <div class="story-scenarios">
+        {foreach $scenario in $scenarios}
-             <div class="story-scenario">
+            // title line
+            <div class="story-element-container">
+                <div class="element-operations-container" style="display: none">
+                    <a class="remove-element-link" href="#"
+                        onclick="editButtonHandler.removeElement(event, 'scenario', '{index($scenario)}')"
+                    >
+                        <span class="aui-icon aui-icon-small aui-iconfont-close-dialog"></span>
+                    </a>
+                </div>
+                <div class="story-scenario element-content-container">
-                 <span class="story-scenario-keyword">{$scenario.keyword}</span>
+                    <span class="story-scenario-keyword">{$scenario.keyword}</span>
+                    <span class="story-scenario-title">
+                        {call .renderLongSingleLineField}
+                            {param fieldName: 'scenarios[' + index($scenario) + '].title' /}
+                            {param displayValue: 'scenario title' /}
+                            {param fieldValue: $scenario.title/}
+                        {/call}
+                    </span>
-             </div>
+                </div>
+            </div>
+            // steps
+            {if $scenario.steps != null}
+                <div class="scenario-steps">
+                    {foreach $step in $scenario.steps}
+                        <div class="scenario-step">
+                            {$step}
+                        </div>
+                    {/foreach}
+                </div>
+            {/if}
+            <div class="insert-link-container-after-scenario-steps"/>
-         {/foreach}
-     </div>
+        {/foreach}
+    </div>
 {/template}
 
 /**
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryEditHandler.js
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryEditHandler.js	(date 1402994274000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryEditHandler.js	(revision )
@@ -335,8 +335,53 @@
             AJS.$('#insertLinkContainerAfterScenarios').html(insertAfterScenariosHtml);
         }
 
+        // after scenario steps
+        {
+            AJS.$('.insert-link-container-after-scenario-steps').each(
+                function (index, element) {
+
+                    var templateObj = new Object();
+
+                    templateObj.triggerDivId = "insertAfterScenarioStepsTriggerDiv_" + index;
+                    templateObj.dropdownDivId = "insertAfterScenarioStepsDropdownDiv_" + index;
+
+                    templateObj.additionalParams = [];
+                    templateObj.additionalParams.push(index);
+
+                    templateObj.dropdownItems = [];
+
+                    var insertGivenLink = new Object();
+                    insertGivenLink.text = "Given";
+//                    insertGivenLink.onClickFunction = "insertElement";
+                    insertGivenLink.elementName = "given";
+                    templateObj.dropdownItems.push(insertGivenLink);
+
+                    var insertWhenLink = new Object();
+                    insertWhenLink.text = "When";
+//                    insertWhenLink.onClickFunction = "insertElement";
+                    insertWhenLink.elementName = "when";
+                    templateObj.dropdownItems.push(insertWhenLink);
+
+                    var insertThenLink = new Object();
+                    insertThenLink.text = "Then";
+//                    insertThenLink.onClickFunction = "insertElement";
+                    insertThenLink.elementName = "then";
+                    templateObj.dropdownItems.push(insertThenLink);
+
+                    var insertAndLink = new Object();
+                    insertAndLink.text = "And";
+//                    insertAndLink.onClickFunction = "insertElement";
+                    insertAndLink.elementName = "and";
+                    templateObj.dropdownItems.push(insertAndLink);
+
+                    var insertAfterScenariosHtml = execspec.viewissuepage.editstory.rich.renderInsertStepLinkDiv(templateObj);
+                    AJS.$(element).html(insertAfterScenariosHtml);
-    }
+                }
+            );
+        }
 
+    }
+
     this.richTextEditorClicked = function (event) {
 
         this.debug("> richTextEditorClicked");
@@ -403,12 +448,12 @@
 //                    if (obj[ pathPart] == null) {
 //                        obj[pathPart] = {};
 //                    }
-                    editButtonHandler.debug("### checking if fieldName part ends in array index - " + pathPart);
+//                    editButtonHandler.debug("### checking if fieldName part ends in array index - " + pathPart);
                     var arrayIndexFromPath = editButtonHandler.getArrayIndexFromPath(pathPart);
                     if (arrayIndexFromPath != null) {
-                        editButtonHandler.debug("### fieldName part ends in array index - " + pathPart);
+//                        editButtonHandler.debug("### fieldName part ends in array index - " + pathPart);
                         var partWithoutIndex = pathPart.substr(0, pathPart.length - (arrayIndexFromPath.length + 2));
-                        editButtonHandler.debug("### partWithoutIndex " + partWithoutIndex);
+//                        editButtonHandler.debug("### partWithoutIndex " + partWithoutIndex);
                         obj = obj[partWithoutIndex][arrayIndexFromPath];
                     } else {
                         obj = obj[pathPart];
@@ -439,7 +484,7 @@
         this.debug("# bindInputElementsToModel");
     }
 
-    this.insertElement = function (event, elementName) {
+    this.insertElement = function (event, elementName, scenarioIndex) {
 
         this.debug("> insertElement");
         this.debug("elementName = " + elementName);
@@ -478,6 +523,10 @@
             this.removeMeta();
         } else if (elementName == "metaField") {
             this.removeMetaField(index);
+        } else if (elementName == "scenario") {
+            this.removeScenario(index);
+        } else {
+            console.error("attempted to remove unsupported element - " + elementName);
         }
 
         this.assignRichEditorHandlers(storyController.currentStory);
@@ -512,8 +561,8 @@
 
         this.debug("> removeMetaField");
 
-        this.debug("story before removing property at index - " + index
-            + ":\n" + JSON.stringify(storyController.currentStory, null, "\t"));
+//        this.debug("story before removing property at index - " + index
+//            + ":\n" + JSON.stringify(storyController.currentStory, null, "\t"));
 
         storyController.currentStory.meta.properties.splice(index, 1);
 
@@ -525,7 +574,28 @@
         this.debug("# removeMetaField");
     }
 
+    this.removeScenario = function (index) {
 
+        this.debug("> removeScenario");
+        this.debug("index - " + index);
+
+        storyController.currentStory.scenarios.splice(index, 1);
+
+        this.debug("story after removing scenario at index - " + index
+            + ":\n" + JSON.stringify(storyController.currentStory, null, "\t"));
+
+        var scenariosHtml;
+        if (storyController.currentStory.scenarios.length > 0) {
+            scenariosHtml = execspec.viewissuepage.editstory.rich.renderScenarios(storyController.currentStory);
+        } else {
+            scenariosHtml = "";
+        }
+        AJS.$("#storyScenariosContainer").html(scenariosHtml);
+
+        this.debug("# removeScenario");
+    }
+
+
     this.removeDescription = function () {
 
         this.debug("> removeDescription");
@@ -609,6 +679,37 @@
         AJS.$("#storyScenariosContainer").html(scenarioHtml);
 
         this.debug("# insertScenario");
+    }
+
+    this.insertStep = function (event, elementName, scenarioIndex) {
+
+        this.debug("> insertStep");
+        this.debug("scenarioIndex - " + scenarioIndex);
+
+        this.bindInputElementsToModel();
+
+        if (elementName == "step") {
+            this.insertStep(scenarioIndex);
+        } else if (elementName == "given") {
+            this.insertGiven(scenarioIndex);
+        } else {
+            console.error("Attempting to insert unsupported step element - " + elementName);
+        }
+
+        this.assignRichEditorHandlers(storyController.currentStory);
+        this.assignShowElementOperationsOnHover(storyController.currentStory);
+        event.preventDefault();
+
+        this.debug("# insertStep");
+    }
+
+    this.insertGiven = function (scenarioIndex) {
+
+        this.debug("> insertGiven");
+        this.debug("scenarioIndex - " + scenarioIndex)
+
+
+        this.debug("# insertGiven");
     }
 
     this.saveStory = function (event) {
\ No newline at end of file
