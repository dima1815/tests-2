Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/temp.html
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/temp.html	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/temp.html	(revision )
@@ -1,116 +1,27 @@
-<div class="element-content-container story-meta">
-    <div class="story-meta-keyword">Meta:</div>
-    <div class="story-meta-properties">
-        <div class="story-element-container">
+<div class="story-element-container">
-            <div class="element-operations-container" style="display: none;"><a class="remove-element-link" href="#"
-                                                                                onclick="editButtonHandler.removeElement(event, 'metaField', '1')"><span
-                    class="aui-icon aui-icon-small aui-iconfont-close-dialog"></span></a></div>
-            <div class="story-meta-property element-content-container"><span class="meta-property-start-symbol">@</span><span
-                    class="meta-property-name"><input class="text story-editor-field story-element-field-short"
-                                                      name="meta.properties[0].name" title="meta.properties[0].name" type="text"
-                                                      placeholder="enter 'Property key' here" value="property1"></span>&nbsp;<span
-                    class="meta-preperty-value"><input class="text story-editor-field story-element-field-short"
-                                                       name="meta.properties[0].value" title="meta.properties[0].value" type="text"
-                                                       placeholder="enter 'Property value' here" value="property1_value"></span>
-
-                <div></div>
-                <div class="story-element-container">
-                    <div class="element-operations-container" style="display: none;"><a class="remove-element-link" href="#"
-                                                                                        onclick="editButtonHandler.removeElement(event, 'metaField', '1')"><span
-                            class="aui-icon aui-icon-small aui-iconfont-close-dialog"></span></a></div>
-                    <div class="story-meta-property element-content-container"><span class="meta-property-start-symbol">@</span><span
-                            class="meta-property-name"><input class="text story-editor-field story-element-field-short"
-                                                              name="meta.properties[1].name" title="meta.properties[1].name" type="text"
-                                                              placeholder="enter 'Property key' here" value="property2"></span>&nbsp;<span
-                            class="meta-preperty-value"><input class="text story-editor-field story-element-field-short"
-                                                               name="meta.properties[1].value" title="meta.properties[1].value" type="text"
-                                                               placeholder="enter 'Property value' here" value="property2_value"></span>
-
-                        <div></div>
-                        <div class="story-element-container">
-                            <div class="element-operations-container" style="display: none;"><a class="remove-element-link" href="#"
-                                                                                                onclick="editButtonHandler.removeElement(event, 'metaField', '1')"><span
-                                    class="aui-icon aui-icon-small aui-iconfont-close-dialog"></span></a></div>
-                            <div class="story-meta-property element-content-container"><span
-                                    class="meta-property-start-symbol">@</span><span class="meta-property-name"><input
-                                    class="text story-editor-field story-element-field-short" name="meta.properties[2].name"
-                                    title="meta.properties[2].name" type="text" placeholder="enter 'Property key' here"
-                                    value="property3"></span>&nbsp;<span class="meta-preperty-value"><input
-                                    class="text story-editor-field story-element-field-short" name="meta.properties[2].value"
-                                    title="meta.properties[2].value" type="text" placeholder="enter 'Property value' here"
-                                    value="property3_value"></span>
-
-                                <div></div>
-                                <div>
-                                    <div id="insertLinkContainerMetaProperty">
-                                        <div class="insert-element-link-div">&nbsp;<a aria-controls="dropdown2-more"
-                                                                                      href="#insertDropdownDivMetaProperty"
-                                                                                      aria-owns="insertDropdownDivMetaProperty"
-                                                                                      aria-haspopup="true"
-                                                                                      class="aui-dropdown2-trigger aui-style-default aui-dropdown2-trigger-arrowless insert-element-link"
-                                                                                      style="display: none;" pressed="false"><span
-                                                class="aui-icon aui-icon-small aui-iconfont-add insert-element-icon"></span></a>
-
-                                            <div id="insertDropdownDivMetaProperty" trigger-div-id="insertTriggerDivMetaProperty"
-                                                 class="aui-dropdown2 aui-style-default insert-dropdown-content" aria-hidden="true"
-                                                 data-dropdown2-alignment="left" style="left: 280.6px; top: 801.3px; display: none;">
-                                                <ul class="aui-list-truncate">
-                                                    <li><a href="#" onclick="editButtonHandler.insertElement(event, 'metaField')">New meta
-                                                        field</a></li>
-                                                </ul>
+    <div class="element-operations-container"
+         style="display: none">
+        <a class="remove-element-link" href="#"
+           onclick="editButtonHandler.removeElement(event, 'metaField', '{index($metaProperty)}')">
+            <span class="aui-icon aui-icon-small aui-iconfont-close-dialog"></span>
+        </a>
-                                            </div>
+    </div>
-                                        </div>
-                                    </div>
-                                </div>
-                            </div>
-                        </div>
-                        <div id="insertLinkContainerBeforeNarrative"></div>
-                        <div class="narrative">
-                            <div class="title">Narrative:</div>
-                            <div class="inOrderTo">
-                                <table>
-                                    <tbody>
-                                    <tr>
-                                        <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;"><span
-                                                class="keyword">In order to</span></td>
-                                        <td style="text-align: left;"><input
-                                                class="text story-editor-field long-field story-element-field-long"
-                                                name="narrative.inOrderTo" title="narrative.inOrderTo" type="text"
-                                                placeholder="enter 'In order to' here"></td>
-                                    </tr>
-                                    </tbody>
-                                </table>
-                            </div>
-                            <div class="asA">
-                                <table>
-                                    <tbody>
-                                    <tr>
-                                        <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;"><span
-                                                class="keyword">As a</span></td>
-                                        <td style="text-align: left;"><input
-                                                class="text story-editor-field long-field story-element-field-long" name="narrative.asA"
-                                                title="narrative.asA" type="text" placeholder="enter 'As a' here"></td>
-                                    </tr>
-                                    </tbody>
-                                </table>
-                            </div>
-                            <div class="iWantTo">
-                                <table>
-                                    <tbody>
-                                    <tr>
-                                        <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;"><span
-                                                class="keyword">I want to</span></td>
-                                        <td style="text-align: left;"><input
-                                                class="text story-editor-field long-field story-element-field-long" name="narrative.iWantTo"
-                                                title="narrative.iWantTo" type="text" placeholder="enter 'I Want to' here"></td>
-                                    </tr>
-                                    </tbody>
-                                </table>
-                            </div>
-                        </div>
-                    </div>
-                </div>
-            </div>
-        </div>
+    <div class="story-meta-property element-content-container">
+        <span class="meta-property-start-symbol">@</span>
+                                <span class="meta-property-name">
+                                    {call .renderShortSingleLineField}
+                                        {param fieldName: 'meta.properties['+ index($metaProperty) + '].name' /}
+                                        {param displayValue: 'Property key' /}
+                                        {param fieldValue: $metaProperty.name /}
+                                    {/call}
+                                </span>
+        &nbsp;
+                                <span class="meta-preperty-value">
+                                    {call .renderShortSingleLineField}
+                                        {param fieldName: 'meta.properties['+ index($metaProperty) + '].value' /}
+                                        {param displayValue: 'Property value' /}
+                                        {param fieldValue: $metaProperty.value /}
+                                    {/call}
+                                </span>
     </div>
 </div>
\ No newline at end of file
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/customfields/StoryStatusField.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/customfields/StoryStatusField.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/customfields/StoryStatusField.java	(revision )
@@ -7,7 +7,7 @@
 import com.atlassian.jira.issue.fields.config.FieldConfig;
 import com.atlassian.jira.issue.fields.config.FieldConfigItem;
 import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
-import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
+import com.mycomp.execspec.jiraplugin.dto.story.StoryDTO;
 import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
 import com.mycomp.execspec.jiraplugin.dto.testreport.TestStatus;
 import com.mycomp.execspec.jiraplugin.service.StepDocsSerivce;
\ No newline at end of file
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesEditStoryRich.soy
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesEditStoryRich.soy	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesEditStoryRich.soy	(revision )
@@ -198,8 +198,7 @@
                 <div class="story-meta-properties">
                     {foreach $metaProperty in $meta.properties}
                         <div class="story-element-container">
-                            <div
-                                class="element-operations-container"
+                            <div class="element-operations-container"
                                 style="display: none">
                                 <a class="remove-element-link"href="#"
                                       onclick="editButtonHandler.removeElement(event, 'metaField', '{index($metaProperty)}')">
@@ -226,95 +225,122 @@
                             </div>
                         </div>
                     {/foreach}
-                <div>
+                </div>
             {/if}
             <div id="insertLinkContainerMetaProperty"/>
         </div>
     </div>
 {/template}
 
-/**
- * Renders story.
- * @param story
- */
-{template .renderRichEditStoryContent}
-<div id="richStoryEditContent">
-    {let $clickHere: '[click here to edit]' /}
 
-    <div id="storyDescriptionContainer">
-        {if $story.description != null}
-            {call .renderStoryDescriptionField}
-                {param description: $story.description /}
-            {/call}
-        {/if}
-    </div>
-
-    <div id="insertLinkContainerBeforeMeta"/>
-
-    <div id="storyMetaContainer">
-        {if $story.meta != null}
-            {call .renderStoryMeta}
-                {param meta: $story.meta /}
-            {/call}
-        {/if}
-    </div>
-
-    <div id="insertLinkContainerBeforeNarrative"/>
-
-    {if $story.narrative != null}
+/** Renders narrative
+ * @param narrative
+ */
+{template .renderNarrative}
-        <div class="narrative">
+    <div class="narrative">
-            <div class="title">{$story.narrative.keyword}</div>
+        <div class="title">{$narrative.keyword}</div>
-            <div class="inOrderTo">
-                <table>
-                    <tr>
-                        <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;">
+        <div class="inOrderTo">
+            <table>
+                <tr>
+                    <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;">
-                            <span class="keyword">{$story.narrative.inOrderTo.keyword}</span>
+                        <span class="keyword">{$narrative.inOrderTo.keyword}</span>
-                        </td>
-                        <td style="text-align: left;">
-                            {call .renderLongSingleLineField}
-                                {param fieldName: 'narrative.inOrderTo.value' /}
-                                {param displayValue: 'In order to' /}
+                    </td>
+                    <td style="text-align: left;">
+                        {call .renderLongSingleLineField}
+                            {param fieldName: 'narrative.inOrderTo.value' /}
+                            {param displayValue: 'In order to' /}
-                                {param fieldValue: $story.narrative.inOrderTo.value /}
+                            {param fieldValue: $narrative.inOrderTo.value /}
-                            {/call}
-                        </td>
-                    </tr>
-                </table>
-            </div>
-            <div class="asA">
-                <table>
-                    <tr>
-                        <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;">
+                        {/call}
+                    </td>
+                </tr>
+            </table>
+        </div>
+        <div class="asA">
+            <table>
+                <tr>
+                    <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;">
-                            <span class="keyword">{$story.narrative.asA.keyword}</span>
+                        <span class="keyword">{$narrative.asA.keyword}</span>
-                        </td>
-                        <td style="text-align: left;">
-                            {call .renderLongSingleLineField}
-                                {param fieldName: 'narrative.asA.value' /}
-                                {param displayValue: 'As a' /}
+                    </td>
+                    <td style="text-align: left;">
+                        {call .renderLongSingleLineField}
+                            {param fieldName: 'narrative.asA.value' /}
+                            {param displayValue: 'As a' /}
-                                {param fieldValue: $story.narrative.asA.value /}
+                            {param fieldValue: $narrative.asA.value /}
-                            {/call}
-                        </td>
-                    </tr>
-                </table>
-            </div>
-            <div class="iWantTo">
-                <table>
-                    <tr>
-                        <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;">
+                        {/call}
+                    </td>
+                </tr>
+            </table>
+        </div>
+        <div class="iWantTo">
+            <table>
+                <tr>
+                    <td style="vertical-align: top; text-align: left; width: 1%; white-space:nowrap;">
-                            <span class="keyword">{$story.narrative.iWantTo.keyword}</span>
+                        <span class="keyword">{$narrative.iWantTo.keyword}</span>
-                        </td>
-                        <td style="text-align: left;">
-                            {call .renderLongSingleLineField}
-                                {param fieldName: 'narrative.iWantTo.value' /}
-                                {param displayValue: 'I Want to' /}
+                    </td>
+                    <td style="text-align: left;">
+                        {call .renderLongSingleLineField}
+                            {param fieldName: 'narrative.iWantTo.value' /}
+                            {param displayValue: 'I Want to' /}
-                                {param fieldValue: $story.narrative.iWantTo.value /}
+                            {param fieldValue: $narrative.iWantTo.value /}
-                            {/call}
-                        </td>
-                    </tr>
-                </table>
-            </div>
-        </div>
+                        {/call}
+                    </td>
+                </tr>
+            </table>
+        </div>
+    </div>
+{/template}
+
+/** Renders scenarios
+ * @param scenarios
+ */
+{template .renderScenarios}
+     <div class="story-scenarios">
+         {foreach $scenario in $scenarios}
+             <div class="story-scenario">
+                 <span class="story-scenario-keyword">{$scenario.keyword}</span>
+             </div>
+         {/foreach}
+     </div>
+{/template}
+
+/**
+ * Renders story.
+ * @param story
+ */
+{template .renderRichEditStoryContent}
+<div id="richStoryEditContent">
+    {let $clickHere: '[click here to edit]' /}
+
+    <div id="storyDescriptionContainer">
+        {if $story.description != null}
+            {call .renderStoryDescriptionField}
+                {param description: $story.description /}
+            {/call}
-    {/if}
+        {/if}
+    </div>
 
+    <div id="insertLinkContainerBeforeMeta"/>
+
+    <div id="storyMetaContainer">
+        {if $story.meta != null}
+            {call .renderStoryMeta}
+                {param meta: $story.meta /}
+            {/call}
+        {/if}
+    </div>
+
+    <div id="insertLinkContainerBeforeNarrative"/>
+
+    <div id="storyNarrativeContainer">
+        {if $story.narrative != null}
+            {call .renderNarrative}
+                {param narrative: $story.narrative /}
+            {/call}
+        {/if}
+    </div>
+
+    <div id="insertLinkContainerBeforeGiventStories"/>
+
     {if $story.givenStories != null}
         <div class="story-given-stories">
             <div class="story-given-stories-keyword">
@@ -322,6 +348,9 @@
             </div>
         </div>
     {/if}
+
+    <div id="insertLinkContainerBeforeLifecycle"/>
+
     {if $story.lifecycle != null}
         <div class="story-lifecycle">
             <div class="story-lifecycle-keyword">
@@ -330,6 +359,16 @@
         </div>
     {/if}
 
+    <div id="insertLinkContainerAfterScenarios"/>
+
+    <div id="storyScenariosContainer">
+        {if $story.scenarios != null}
+            {call .renderScenarios}
+                {param scenarios: $story.scenarios /}
+            {/call}
+        {/if}
+    </div>
+
 //    {call .renderInsertLinkDiv}
 //        {param triggerDivId: 'anotherTriggerDivId' /}
 //        {param dropdownDivId: 'anotherDropdownDivId' /}
@@ -392,15 +431,7 @@
 //            {/call}
 //    {/if}
 
-    {if $story.scenarios.length != 0}
-        <div class="story-scenarios">
-            {foreach $scenario in $story.scenarios}
-                <div class="story-scenario">
-                    <span class="story-scenario-keyword">{$scenario.keyword}</span>
-                </div>
-            {/foreach}
-        </div>
-    {/if}
+
 
 </div>
 {/template}
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesStoryToolbar.soy
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesStoryToolbar.soy	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesStoryToolbar.soy	(revision )
@@ -10,13 +10,13 @@
                 <div id="storyButtons" class="aui-buttons"></div>
             </div>
             <div class="aui-toolbar2-secondary">
-                <div id="storyReportButtons" class="aui-buttons"></div>
+                <div id="storyTestReportButtons" class="aui-buttons"></div>
             </div>
         </div>
     </div>
     <div id="storyContainer" class="story-container" style="display: none;"></div>
-    <div id="storyReportContainer" class="story-container" style="display: none;"></div>
     <div id="storyEditContainer" style="display: none;"></div>
+    <div id="storyReportContainer" class="story-container" style="display: none;"></div>
 {/template}
 
 /**
@@ -30,12 +30,12 @@
 
 /**
  * Render story button
- * @param path
+ * @param story
  */
 {template .renderButtonsForStory}
     <button id="showStoryButton"
         onclick="storyController.showStoryHandler(event)"
-        class="aui-button story-container-button selected-story-container-button">{$path}</button>
+        class="aui-button story-container-button selected-story-container-button">{$story.path}</button>
 //    <button id="edit-story-button"
 //            class="aui-button aui-button-subtle story-container-button selected-story-container-button">{$path}</button>
 //aria-pressed="true"
@@ -48,4 +48,28 @@
 //        {param iconClass: 'aui-icon-small aui-iconfont-edit' /}
 //        {param iconText: 'View' /}
 //    {/call}
+{/template}
+
+/**
+ * Render story test report buttons
+ * @param testReports
+ */
+{template .renderButtonsForTestReports}
+    {foreach $storyReport in $testReports}
+//        <button
+//            onclick="storyController.showStoryReport(event, '{$testReport.environment}')"
+//            class="aui-button story-report-button"
+//            environment="{$testReport.environment}"
+//            >{$testReport.environment}</button>
+        <button id="show-story-report-{$storyReport.environment}"
+                environment="{$storyReport.environment}"
+                onclick="storyController.showStoryReport(event, '{$storyReport.environment}')"
+                class="aui-button story-report-button">{$storyReport.environment}{if $storyReport.totalScenariosPassed > 0}<span
+                class="aui-badge passed">{$storyReport.totalScenariosPassed}</span>{/if}{if $storyReport.totalScenariosFailed > 0}<span
+                class="aui-badge failed">{$storyReport.totalScenariosFailed}</span>{/if}{if $storyReport.totalScenariosPending > 0}<span
+                class="aui-badge pending">{$storyReport.totalScenariosPending}{/if}{if $storyReport.totalScenariosSkipped > 0}<span
+                class="aui-badge skipped">{$storyReport.totalScenariosSkipped }{/if}{if $storyReport.totalScenariosNotPerformed > 0}<span
+                class="aui-badge not-performed">{$storyReport.totalScenariosNotPerformed}{/if}</span>
+        </button>
+    {/foreach}
 {/template}
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/TableRow.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/TableRow.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/TableRowDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
@@ -10,14 +10,14 @@
  */
 @XmlRootElement
 @XmlAccessorType(XmlAccessType.FIELD)
-public class TableRow {
+public class TableRowDTO {
 
     private List<String> values;
 
-    public TableRow() {
+    public TableRowDTO() {
     }
 
-    public TableRow(List<String> values) {
+    public TableRowDTO(List<String> values) {
         this.values = values;
     }
 
@@ -41,7 +41,7 @@
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
 
-        TableRow tableRow = (TableRow) o;
+        TableRowDTO tableRow = (TableRowDTO) o;
 
         if (values != null ? !values.equals(tableRow.values) : tableRow.values != null) return false;
 
Index: execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/JiraHtmlOutput.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/JiraHtmlOutput.java	(date 1402907572000)
+++ execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/JiraHtmlOutput.java	(revision )
@@ -1,10 +1,10 @@
 package com.mycomp.execspec;
 
-import com.mycomp.execspec.jiraplugin.dto.story.BytesListPrintStream;
-import com.mycomp.execspec.jiraplugin.dto.story.DefaultHTMLFormatPatterns;
 import com.mycomp.execspec.jiraplugin.dto.story.StoryDTOUtils;
 import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
 import com.mycomp.execspec.jiraplugin.dto.testreport.TestStatus;
+import com.mycomp.execspec.util.BytesListPrintStream;
+import com.mycomp.execspec.util.DefaultHTMLFormatPatterns;
 import com.sun.jersey.api.client.Client;
 import com.sun.jersey.api.client.WebResource;
 import org.apache.commons.lang.Validate;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/CustomHTMLOutput.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/CustomHTMLOutput.java	(date 1402907572000)
+++ execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/util/CustomHTMLOutput.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story;
+package com.mycomp.execspec.util;
 
 import org.jbehave.core.configuration.Keywords;
 import org.jbehave.core.reporters.HtmlOutput;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/BytesListPrintStream.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/BytesListPrintStream.java	(date 1402907572000)
+++ execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/util/BytesListPrintStream.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story;
+package com.mycomp.execspec.util;
 
 import java.io.IOException;
 import java.io.OutputStream;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/IWantToDTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/IWantToDTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/IWantToDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/InOrderToDTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/InOrderToDTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/InOrderToDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/service/StoryServiceImpl.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/service/StoryServiceImpl.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/service/StoryServiceImpl.java	(revision )
@@ -8,7 +8,7 @@
 import com.mycomp.execspec.jiraplugin.ao.testreport.StoryHtmlReport;
 import com.mycomp.execspec.jiraplugin.ao.testreport.StoryReportDao;
 import com.mycomp.execspec.jiraplugin.dto.story.StoryDTOUtils;
-import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
+import com.mycomp.execspec.jiraplugin.dto.story.StoryDTO;
 import org.apache.commons.lang.Validate;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/StoriesPayload.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/StoriesPayload.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/StoriesPayload.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/GivenStoriesDTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/GivenStoriesDTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/GivenStoriesDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/rest/StoryResourceCrud.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/rest/StoryResourceCrud.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/rest/StoryResourceCrud.java	(revision )
@@ -3,7 +3,7 @@
 import com.atlassian.jira.bc.issue.search.SearchService;
 import com.atlassian.jira.security.JiraAuthenticationContext;
 import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
-import com.mycomp.execspec.jiraplugin.dto.story.output.*;
+import com.mycomp.execspec.jiraplugin.dto.story.*;
 import com.mycomp.execspec.jiraplugin.service.StoryService;
 import org.apache.commons.lang.Validate;
 import org.codehaus.jackson.map.ObjectMapper;
@@ -30,9 +30,11 @@
     private final StoryService storyService;
 
     private SearchService searchService;
+
     private JiraAuthenticationContext authenticationContext;
 
-    public StoryResourceCrud(StoryService storyService, SearchService searchService, JiraAuthenticationContext authenticationContext) {
+    public StoryResourceCrud(StoryService storyService, SearchService searchService,
+                             JiraAuthenticationContext authenticationContext) {
         this.storyService = storyService;
         this.searchService = searchService;
         this.authenticationContext = authenticationContext;
@@ -62,6 +64,7 @@
         log.debug("saving story:\n" + storyDTO);
 
         StoryDTO savedStoryDTO = storyService.saveOrUpdate(storyDTO);
+
         Validate.notNull(savedStoryDTO.getVersion());
         return savedStoryDTO;
     }
Index: execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/JiraStoryFinder.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/JiraStoryFinder.java	(date 1402907572000)
+++ execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/JiraStoryFinder.java	(revision )
@@ -1,6 +1,6 @@
 package com.mycomp.execspec;
 
-import com.mycomp.execspec.jiraplugin.dto.story.output.StoryPathsDTO;
+import com.mycomp.execspec.jiraplugin.dto.story.StoryPathsDTO;
 import com.sun.jersey.api.client.Client;
 import com.sun.jersey.api.client.ClientResponse;
 import com.sun.jersey.api.client.WebResource;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/StoryPathsDTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/StoryPathsDTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/StoryPathsDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/SoThatDTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/SoThatDTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/SoThatDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/StoryDTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/StoryDTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/StoryDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
@@ -125,4 +125,5 @@
     public void setAsString(String asString) {
         this.asString = asString;
     }
+
 }
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryView.js
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryView.js	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryView.js	(revision )
@@ -231,26 +231,25 @@
 
         this.debug("story:\n" + JSON.stringify(story, null, "\t"));
 
-        var buttonsForStory = execspec.viewissuepage.storytoolbar.renderButtonsForStory(story);
-        AJS.$("#storyButtons").html(buttonsForStory);
-        AJS.$("#editStoryButton").attr("aria-pressed", "true");
+//        var buttonsForStory = execspec.viewissuepage.storytoolbar.renderButtonsForStory(story);
+//        AJS.$("#storyButtons").html(buttonsForStory);
 
         var editContainerContent = execspec.viewissuepage.editstory.renderEditStoryContainer();
         AJS.$("#storyEditContainer").html(editContainerContent);
-
         // set content for rich editor as well as raw editor
         var templateObj = new Object();
         templateObj.story = story;
-
         var richEditStoryContent = execspec.viewissuepage.editstory.rich.renderRichEditStoryContent(templateObj);
-        AJS.$("#richEditStoryContainer").html(richEditStoryContent);
 
+        AJS.$("#richEditStoryContainer").html(richEditStoryContent);
         editButtonHandler.assignRichEditorHandlers(story);
-
         AJS.$("#richTextEditorButton").click();
 
-        AJS.$("#storyContainer").hide();
-        AJS.$("#storyEditContainer").show();
+        this.updateStoryTabButton("edit", null);
+//        AJS.$("#editStoryButton").attr("aria-pressed", "true");
+//        AJS.$("#storyContainer").hide();
+//        AJS.$("#storyEditContainer").show();
+
         this.debug("# editStory");
     }
 
@@ -353,27 +352,113 @@
         storyView.debug("# StoryView.showStoryButton");
     }
 
-    this.showStory = function (story) {
+    this.showStoryButtons = function(storyPayload) {
 
-        storyView.debug("> StoryView.showStory");
+        storyView.debug("> showStoryButtons");
 
-        var buttonsForStory = execspec.viewissuepage.storytoolbar.renderButtonsForStory(story);
+        var buttonsForStory = execspec.viewissuepage.storytoolbar.renderButtonsForStory(storyPayload);
         AJS.$("#storyButtons").html(buttonsForStory);
-        AJS.$("#editStoryButton").attr("aria-pressed", "false");
-        AJS.$("#showStoryButton").attr("aria-pressed", "true");
 
+        var buttonsForTestReports = execspec.viewissuepage.storytoolbar.renderButtonsForTestReports(storyPayload);
+        AJS.$("#storyTestReportButtons").html(buttonsForTestReports);
+
+        storyView.debug("# showStoryButtons");
+    }
+
+//    this.showStoryReportButtons = function (story) {
+//
+//        storyView.debug("> showStoryReportButtons");
+//
+//        var buttonsForTestReports = execspec.viewissuepage.storytoolbar.renderButtonsForTestReports(story);
+//        AJS.$("#storyTestReportButtons").html(buttonsForTestReports);
+//
+//        storyView.debug("# showStoryReportButtons");
+//
+//    }
+
+    this.showStoryReport = function (storyReport, currentStoryVersion) {
+
+        storyView.debug("> showStoryReport");
+        storyView.debug("currentStoryVersion - " + currentStoryVersion);
+        storyView.debug("storyReport:\n" + JSON.stringify(storyReport, null, "\t"));
+
         var templateObj = new Object();
+        templateObj.storyReport = storyReport;
+        templateObj.storyVersion = currentStoryVersion;
+        var reportHtml = execspec.viewissuepage.showstoryreports.renderStoryReport(templateObj);
+        AJS.$("#storyReportContainer").html(reportHtml);
+
+        this.updateStoryTabButton("report", storyReport.environment);
+
+        storyView.debug("# showStoryReport");
+    }
+
+    this.showStory = function (story) {
+
+        storyView.debug("> showStory");
+
+        var templateObj = new Object();
         templateObj.story = story;
         var storyHtml = execspec.viewissuepage.showstory.renderStoryAsString(templateObj);
 
         AJS.$("#storyContainer").html(storyHtml);
+
+        this.updateStoryTabButton("view", null);
+
+        storyView.debug("# showStory");
+    }
+
+    this.updateStoryTabButton = function (mode, reportForEnv) {
+
+        if (mode == "view") {
+
+            AJS.$(".story-report-button").attr("aria-pressed", "false");
+            AJS.$("#storyReportContainer").hide();
+
+            AJS.$("#editStoryButton").attr("aria-pressed", "false");
+            AJS.$("#storyEditContainer").hide();
+
+            AJS.$("#showStoryButton").attr("aria-pressed", "true");
-        AJS.$("#storyContainer").show();
+            AJS.$("#storyContainer").show();
 
+        } else if (mode == "edit") {
+
+            AJS.$(".story-report-button").attr("aria-pressed", "false");
+            AJS.$("#storyReportContainer").hide();
+
+            AJS.$("#editStoryButton").attr("aria-pressed", "true");
+            AJS.$("#storyEditContainer").show();
+
+            AJS.$("#showStoryButton").attr("aria-pressed", "false");
+            AJS.$("#storyContainer").hide();
+
+        } else if (mode == "report") {
+
+            AJS.$("#editStoryButton").attr("aria-pressed", "false");
-        AJS.$("#storyEditContainer").hide();
+            AJS.$("#storyEditContainer").hide();
 
-        storyView.debug("# StoryView.showStory");
+            AJS.$("#showStoryButton").attr("aria-pressed", "false");
+            AJS.$("#storyContainer").hide();
+
+            AJS.$(".story-report-button").each(
+                function (index, element) {
+                    var buttonReportEnv = AJS.$(element).attr("environment");
+                    if (buttonReportEnv == reportForEnv) {
+                        AJS.$(element).attr("aria-pressed", "true");
+                    } else {
+                        AJS.$(element).attr("aria-pressed", "false");
-    }
+                    }
+                }
+            );
+//            AJS.$(".story-report-button").attr("aria-pressed", "false");
 
+            AJS.$("#storyReportContainer").show();
+
+        } else {
+            console.error("Unsupported mode - " + mode);
+        }
+    }
+
 //    this.showStory = function (story, editMode) {
 //
 //        storyView.debug("> StoryView.showStory");
@@ -507,39 +592,39 @@
 //        storyView.debug("# StoryView.showStory");
 //    }
 
-    this.showStoryReportButtons = function (story) {
-
-        storyView.debug("> StoryView.showStoryReportButtons");
-
-        var storyVersion = story.version;
-        storyView.debug("storyVersion - " + storyVersion);
-
-        // add the story reports
-        var storyReportButtons = execspec.viewissuepage.showstory.renderStoryReportButtons(story);
-        AJS.$("#storyReportButtons").html(storyReportButtons);
-        // set the story report button onClick handlers
-        var storyReports = story.storyReports;
-        for (var i = 0; i < storyReports.length; i++) {
-            var storyReport = storyReports[i];
-            var linkId = "show-story-report-" + storyReport.environment;
-            AJS.$("#" + linkId).click(
-                function (event) {
-
-                    storyView.debug("> show story report button clicked");
-                    event.preventDefault();
-
-                    var attributes = event.target.attributes;
-                    var environmentNode = attributes["environment"];
-                    var environment = environmentNode.nodeValue;
-                    storyController.showStoryReport(environment);
-
-                    storyView.debug("# show story report button clicked");
-                }
-            );
-        }
-
-        storyView.debug("# StoryView.showStoryReportButtons");
-    }
+//    this.showStoryReportButtons = function (story) {
+//
+//        storyView.debug("> StoryView.showStoryReportButtons");
+//
+//        var storyVersion = story.version;
+//        storyView.debug("storyVersion - " + storyVersion);
+//
+//        // add the story reports
+//        var storyReportButtons = execspec.viewissuepage.showstory.renderStoryReportButtons(story);
+//        AJS.$("#storyReportButtons").html(storyReportButtons);
+//        // set the story report button onClick handlers
+//        var storyReports = story.storyReports;
+//        for (var i = 0; i < storyReports.length; i++) {
+//            var storyReport = storyReports[i];
+//            var linkId = "show-story-report-" + storyReport.environment;
+//            AJS.$("#" + linkId).click(
+//                function (event) {
+//
+//                    storyView.debug("> show story report button clicked");
+//                    event.preventDefault();
+//
+//                    var attributes = event.target.attributes;
+//                    var environmentNode = attributes["environment"];
+//                    var environment = environmentNode.nodeValue;
+//                    storyController.showStoryReport(environment);
+//
+//                    storyView.debug("# show story report button clicked");
+//                }
+//            );
+//        }
+//
+//        storyView.debug("# StoryView.showStoryReportButtons");
+//    }
 
 //    this.showStoryReport = function (storyReport, storyVersion) {
 //
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryEditHandler.js
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryEditHandler.js	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryEditHandler.js	(revision )
@@ -12,7 +12,7 @@
         }
     }
 
-    this.init = function ( ) {
+    this.init = function () {
         this.debug("initialized");
     }
 
@@ -29,14 +29,18 @@
 
         this.assignAutoHeightForTextAreas(story);
 
-        this.autoHeightTextAreas(story);
+        this.setSizeForTextAreas(story);
 
         this.debug("# assignRichEditorHandlers");
     }
 
-    this.autoHeightTextAreas = function (story) {
+    this.setSizeForTextAreas = function (story) {
 
-        story-multiline-edit-field
+        AJS.$('.story-multiline-edit-field').each(function (element, textArea) {
+            editButtonHandler.debug('> setSizeForTextAreas, textArea - ' + textArea);
+            editButtonHandler.resizeTextArea(textArea);
+            editButtonHandler.debug('# setSizeForTextAreas');
+        });
     }
 
     this.assignAutoHeightForTextAreas = function (story) {
@@ -48,43 +52,50 @@
             editButtonHandler.debug('keydown, event.which - ' + key);
             if (key == 13) {
                 editButtonHandler.debug('return pressed, resizing text area');
-                editButtonHandler.resizeTextArea(event);
+                editButtonHandler.resizeTextArea(event.target);
             }
         });
 
         AJS.$('.story-editor-field').keyup(function (event) {
-            editButtonHandler.resizeTextArea(event);
+            editButtonHandler.resizeTextArea(event.target);
         });
 
-        this.resizeTextArea = function (event) {
+        // add border on focus
+        AJS.$('.story-editor-field').focus(function (event) {
+            editButtonHandler.debug('focused, event.target - ' + event.target);
+            AJS.$(event.target).addClass("focused");
 
-            var textArea = event.target;
+        });
+        AJS.$('.story-editor-field').blur(function (event) {
+            editButtonHandler.debug('blurred, event.target - ' + event.target);
+            AJS.$(event.target).removeClass("focused");
+        });
 
-            editButtonHandler.debug('keypressed, event.target - ' + textArea);
-            var value = AJS.$(event.target).val();
+    }
+
+    this.resizeTextArea = function (textArea) {
+
+        editButtonHandler.debug("> resizeTextArea");
+
+        editButtonHandler.debug('textArea - ' + textArea);
+        var value = AJS.$(textArea).val();
+
+        if (value != null) {
             editButtonHandler.debug('value - ' + value)
             var lines = value.split(/\r*\n/);
             editButtonHandler.debug('lines - ' + lines)
             var lineCount = lines.length;
             editButtonHandler.debug('lineCount - ' + lineCount);
-            var currentRows = AJS.$(event.target).attr("rows");
+            var currentRows = AJS.$(textArea).attr("rows");
             editButtonHandler.debug('currentRows - ' + currentRows);
             if (Number(currentRows) != lineCount) {
-                AJS.$(event.target).attr("rows", lineCount);
+                AJS.$(textArea).attr("rows", lineCount);
             }
+        } else {
+            editButtonHandler.debug('not resizing text area as the value is null');
         }
 
-        // add border on focus
-        AJS.$('.story-editor-field').focus(function (event) {
-            editButtonHandler.debug('focused, event.target - ' + event.target);
-            AJS.$(event.target).addClass("focused");
-
-        });
-        AJS.$('.story-editor-field').blur(function (event) {
-            editButtonHandler.debug('blurred, event.target - ' + event.target);
-            AJS.$(event.target).removeClass("focused");
-        });
-
+        editButtonHandler.debug("# resizeTextArea");
     }
 
     this.assignInsertLinkHandlers = function (story) {
@@ -295,9 +306,37 @@
             }
         }
 
+        // after scenarios
+        {
+            var templateObj = new Object();
+            templateObj.triggerDivId = "insertAfterScenariosTriggerDiv";
+            templateObj.dropdownDivId = "insertAfterScenariosDropdownDiv";
 
+            var insertScenarioLinkInfo = new Object();
+            insertScenarioLinkInfo.text = "Scenario";
+            insertScenarioLinkInfo.onClickFunction = "insertElement";
+            insertScenarioLinkInfo.elementName = "scenario";
+
+//            var insertMetaLinkInfo = new Object();
+//            insertMetaLinkInfo.text = "Meta";
+//            insertMetaLinkInfo.onClickFunction = "insertElement";
+//            insertMetaLinkInfo.elementName = "meta";
+
+            templateObj.dropdownItems = [];
+//            if (story.description == null && story.meta == null) {
+//                templateObj.dropdownItems.push(insertScenarioLinkInfo);
+//                templateObj.dropdownItems.push(insertMetaLinkInfo);
+//            } else if (story.meta == null) {
+//                templateObj.dropdownItems.push(insertMetaLinkInfo);
+//            }
+            templateObj.dropdownItems.push(insertScenarioLinkInfo);
+
+            var insertAfterScenariosHtml = execspec.viewissuepage.editstory.rich.renderInsertLinkDiv(templateObj);
+            AJS.$('#insertLinkContainerAfterScenarios').html(insertAfterScenariosHtml);
-    }
+        }
 
+    }
+
     this.richTextEditorClicked = function (event) {
 
         this.debug("> richTextEditorClicked");
@@ -412,7 +451,11 @@
         } else if (elementName == "meta") {
             this.insertMeta();
         } else if (elementName == "metaField") {
-            this.insertMetaField();
+            this.insertMetaField(null, null);
+        } else if (elementName == "scenario") {
+            this.insertScenario();
+        } else {
+            console.error("Attempting to insert unsupported element - " + elementName);
         }
 
         this.assignRichEditorHandlers(storyController.currentStory);
@@ -513,16 +556,8 @@
 
         var meta = storyController.currentStory.meta;
         var newEntry = new Object();
-        if (fieldName == null) {
-            newEntry.name = null;
-        } else {
-            newEntry.name = fieldName;
+        newEntry.name = fieldName;
-        }
-        if (fieldValue == null) {
-            newEntry.value = null;
-        } else {
-            newEntry.value = fieldValue;
+        newEntry.value = fieldValue;
-        }
         meta.properties.push(newEntry);
 
         var metaHtml = execspec.viewissuepage.editstory.rich.renderStoryMeta(storyController.currentStory);
@@ -559,20 +594,20 @@
         this.debug("# insertLifecycle");
     }
 
-    this.insertScenario = function (event) {
+    this.insertScenario = function () {
 
         this.debug("> insertScenario");
 
         var scenario = new Object();
         scenario.keyword = "Scenario:";
-        if (storyController.currentStory.scenarios == null) {
-            storyController.currentStory.scenarios = [];
-        }
         storyController.currentStory.scenarios.push(scenario);
 
-        storyView.editStory(storyController.currentStory);
+        var jsonStory = JSON.stringify(storyController.currentStory, null, "\t");
+        this.debug("after insert of new scenario into story model:\n" + jsonStory);
 
-        event.preventDefault();
+        var scenarioHtml = execspec.viewissuepage.editstory.rich.renderScenarios(storyController.currentStory);
+        AJS.$("#storyScenariosContainer").html(scenarioHtml);
+
         this.debug("# insertScenario");
     }
 
\ No newline at end of file
Index: execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/JiraStoryLoader.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/JiraStoryLoader.java	(date 1402907572000)
+++ execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/JiraStoryLoader.java	(revision )
@@ -1,9 +1,9 @@
 package com.mycomp.execspec;
 
-import com.mycomp.execspec.jiraplugin.dto.story.BytesListPrintStream;
-import com.mycomp.execspec.jiraplugin.dto.story.ReportingStoryWalker;
+import com.mycomp.execspec.util.BytesListPrintStream;
+import com.mycomp.execspec.util.ReportingStoryWalker;
 import com.mycomp.execspec.jiraplugin.dto.story.StoryDTOUtils;
-import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
+import com.mycomp.execspec.jiraplugin.dto.story.StoryDTO;
 import com.sun.jersey.api.client.Client;
 import com.sun.jersey.api.client.ClientResponse;
 import com.sun.jersey.api.client.WebResource;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryModel.js
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryModel.js	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryModel.js	(revision )
@@ -15,8 +15,10 @@
     this.narrative.inOrderTo.keyword = "";
     this.narrative.inOrderTo.value = "";
 
+    this.scenarios = [];
+
     this.asString = "";
     this.asHTML = "";
-//    this.storyReports = [];
+    this.testReports = [];
 
 }
\ No newline at end of file
Index: execspec/tests-2-master/execspec-parent/execspec-maven-plugin/src/main/java/com/mycomp/execspec/mavenplugin/DownloadStoriesFromJira.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-maven-plugin/src/main/java/com/mycomp/execspec/mavenplugin/DownloadStoriesFromJira.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-maven-plugin/src/main/java/com/mycomp/execspec/mavenplugin/DownloadStoriesFromJira.java	(revision )
@@ -8,8 +8,8 @@
  */
 
 
-import com.mycomp.execspec.jiraplugin.dto.story.output.StoriesPayload;
-import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
+import com.mycomp.execspec.jiraplugin.dto.story.StoriesPayload;
+import com.mycomp.execspec.jiraplugin.dto.story.StoryDTO;
 import com.sun.jersey.api.client.Client;
 import com.sun.jersey.api.client.ClientResponse;
 import com.sun.jersey.api.client.WebResource;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesShowStoryReports.soy
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesShowStoryReports.soy	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/TemplatesShowStoryReports.soy	(revision )
@@ -22,10 +22,16 @@
 /**
  * Render story report
  * @param storyReport
+ * @param storyVersion
  */
 {template .renderStoryReport autoescape="false"}
-    <div id="reportMessageContainer"></div>
+    <div id="reportMessageContainer">Story version is - {$storyVersion}</div>
     <div class="story-report">
+        <div>
+            Report story version is - {$storyReport.storyVersion}
+        </div>
+        <div>
-        {$storyReport.htmlReport}
+            {$storyReport.htmlReport}
+        </div>
     </div>
 {/template}
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/DefaultHTMLFormatPatterns.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/DefaultHTMLFormatPatterns.java	(date 1402907572000)
+++ execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/util/DefaultHTMLFormatPatterns.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story;
+package com.mycomp.execspec.util;
 
 import java.util.Properties;
 
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/MetaDTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/MetaDTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/MetaDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/LifecycleDTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/LifecycleDTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/LifecycleDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/AsADTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/AsADTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/AsADTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
Index: execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/MyEmbedderTest.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/MyEmbedderTest.java	(date 1402907572000)
+++ execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/MyEmbedderTest.java	(revision )
@@ -33,7 +33,7 @@
                 new StoryReporterBuilder() {
                     public StoryReporter reporterFor(String storyPath, org.jbehave.core.reporters.Format format) {
                         if (format.equals(org.jbehave.core.reporters.Format.HTML)) {
-                            return new JiraHtmlOutput(jiraBaseUrl, jiraProject, "UAT");
+                            return new JiraHtmlOutput(jiraBaseUrl, jiraProject, "DEV");
                         } else {
                             return super.reporterFor(storyPath, format);
                         }
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/service/StoryService.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/service/StoryService.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/service/StoryService.java	(revision )
@@ -1,7 +1,7 @@
 package com.mycomp.execspec.jiraplugin.service;
 
 import com.atlassian.activeobjects.tx.Transactional;
-import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
+import com.mycomp.execspec.jiraplugin.dto.story.StoryDTO;
 
 import java.util.List;
 
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/rest/StoryResourceFind.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/rest/StoryResourceFind.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/rest/StoryResourceFind.java	(revision )
@@ -2,9 +2,11 @@
 
 import com.atlassian.jira.bc.issue.search.SearchService;
 import com.atlassian.jira.security.JiraAuthenticationContext;
-import com.mycomp.execspec.jiraplugin.dto.story.output.StoriesPayload;
-import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
-import com.mycomp.execspec.jiraplugin.dto.story.output.StoryPathsDTO;
+import com.mycomp.execspec.jiraplugin.dto.story.StoriesPayload;
+import com.mycomp.execspec.jiraplugin.dto.story.StoryDTO;
+import com.mycomp.execspec.jiraplugin.dto.story.StoryPathsDTO;
+import com.mycomp.execspec.jiraplugin.dto.story.StoryWithReportsPayload;
+import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
 import com.mycomp.execspec.jiraplugin.service.StepDocsSerivce;
 import com.mycomp.execspec.jiraplugin.service.StoryReportService;
 import com.mycomp.execspec.jiraplugin.service.StoryService;
@@ -85,9 +87,14 @@
             @PathParam("issueKey") String issueKey) {
 
         StoryDTO storyDTO = storyService.findByProjectAndIssueKey(projectKey, issueKey);
+
         Response response;
         if (storyDTO != null) {
-            response = Response.ok(storyDTO, MediaType.APPLICATION_JSON).build();
+
+            List<StoryHtmlReportDTO> storyReports = storyReportService.findStoryReports(projectKey, issueKey);
+            StoryWithReportsPayload payload = new StoryWithReportsPayload(storyDTO, storyReports);
+
+            response = Response.ok(payload, MediaType.APPLICATION_JSON).build();
         } else {
             response = Response.noContent().build();
         }
@@ -103,8 +110,16 @@
 
         Validate.notNull(storyPath);
         Validate.isTrue(storyPath.endsWith(".story"));
-        storyPath = storyPath.substring(0, storyPath.lastIndexOf(".story"));
+        String issueKey = storyPath.substring(0, storyPath.lastIndexOf(".story"));
 
-        return this.findForIssue(projectKey, storyPath);
+        StoryDTO storyDTO = storyService.findByProjectAndIssueKey(projectKey, issueKey);
+
+        Response response;
+        if (storyDTO != null) {
+            response = Response.ok(storyDTO, MediaType.APPLICATION_JSON).build();
+        } else {
+            response = Response.noContent().build();
+        }
+        return response;
     }
 }
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/test/java/com/mycomp/execspec/jiraplugin/util/ByLineStoryParserTest.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/test/java/com/mycomp/execspec/jiraplugin/util/ByLineStoryParserTest.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/test/java/com/mycomp/execspec/jiraplugin/util/ByLineStoryParserTest.java	(revision )
@@ -1,6 +1,6 @@
 package com.mycomp.execspec.jiraplugin.util;
 
-import com.mycomp.execspec.jiraplugin.dto.story.output.*;
+import com.mycomp.execspec.jiraplugin.dto.story.*;
 import junit.framework.Assert;
 import org.apache.commons.io.FileUtils;
 import org.codehaus.jackson.map.ObjectMapper;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/StoryWithReportsPayload.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/StoryWithReportsPayload.java	(revision )
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/StoryWithReportsPayload.java	(revision )
@@ -0,0 +1,49 @@
+package com.mycomp.execspec.jiraplugin.dto.story;
+
+import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
+
+import javax.xml.bind.annotation.XmlAccessType;
+import javax.xml.bind.annotation.XmlAccessorType;
+import javax.xml.bind.annotation.XmlRootElement;
+import java.util.List;
+
+/**
+ * TODO - add at least one line of java doc comment.
+ *
+ * @author stasyukd
+ * @since 6.0.0-SNAPSHOT
+ */
+@XmlRootElement
+@XmlAccessorType(XmlAccessType.FIELD)
+public class StoryWithReportsPayload {
+
+    private StoryDTO story;
+
+    private List<StoryHtmlReportDTO> testReports;
+
+
+    public StoryWithReportsPayload() {
+    }
+
+    public StoryWithReportsPayload(StoryDTO story, List<StoryHtmlReportDTO> testReports) {
+        this.story = story;
+        this.testReports = testReports;
+    }
+
+    public StoryDTO getStory() {
+        return story;
+    }
+
+    public void setStory(StoryDTO story) {
+        this.story = story;
+    }
+
+    public List<StoryHtmlReportDTO> getTestReports() {
+        return testReports;
+    }
+
+    public void setTestReports(List<StoryHtmlReportDTO> testReports) {
+        this.testReports = testReports;
+    }
+
+}
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/ExamplesTableDTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/ExamplesTableDTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/ExamplesTableDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
@@ -14,7 +14,7 @@
 
     private List<String> headers;
 
-    private List<TableRow> rows;
+    private List<TableRowDTO> rows;
 
     public ExamplesTableDTO() {
     }
@@ -27,11 +27,11 @@
         this.headers = headers;
     }
 
-    public List<TableRow> getRows() {
+    public List<TableRowDTO> getRows() {
         return rows;
     }
 
-    public void setRows(List<TableRow> rows) {
+    public void setRows(List<TableRowDTO> rows) {
         this.rows = rows;
     }
 
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/util/ByLineStoryParser.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/util/ByLineStoryParser.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/util/ByLineStoryParser.java	(revision )
@@ -1,6 +1,6 @@
 package com.mycomp.execspec.jiraplugin.util;
 
-import com.mycomp.execspec.jiraplugin.dto.story.output.*;
+import com.mycomp.execspec.jiraplugin.dto.story.*;
 import org.jbehave.core.configuration.Keywords;
 import org.jbehave.core.i18n.LocalizedKeywords;
 
@@ -72,7 +72,9 @@
                         throw new StoryParseException("Found an out of order " + keywords.meta()
                                 + " (meta) keyword declaration on line " + lineNumber);
                     } else {
-                        storyDTO.setMeta(new MetaDTO());
+                        MetaDTO meta = new MetaDTO();
+                        meta.setKeyword(keywords.meta());
+                        storyDTO.setMeta(meta);
                         lastElement = CompositeElement.meta;
                     }
                 }
@@ -98,7 +100,7 @@
                     } else {
                         InOrderToDTO inOrderTo = new InOrderToDTO();
                         inOrderTo.setKeyword(keywords.inOrderTo());
-                        String value = line.substring(keywords.inOrderTo().length());
+                        String value = line.substring(keywords.inOrderTo().length()).trim();
                         if (!value.isEmpty()) {
                             inOrderTo.setValue(value);
                         }
@@ -115,7 +117,7 @@
                     } else {
                         AsADTO asA = new AsADTO();
                         asA.setKeyword(keywords.asA());
-                        String value = line.substring(keywords.asA().length());
+                        String value = line.substring(keywords.asA().length()).trim();
                         if (!value.isEmpty()) {
                             asA.setValue(value);
                         }
@@ -132,7 +134,7 @@
                     } else {
                         IWantToDTO iWantTo = new IWantToDTO();
                         iWantTo.setKeyword(keywords.iWantTo());
-                        String value = line.substring(keywords.iWantTo().length());
+                        String value = line.substring(keywords.iWantTo().length()).trim();
                         if (!value.isEmpty()) {
                             iWantTo.setValue(value);
                         }
@@ -149,7 +151,7 @@
                     } else {
                         SoThatDTO soThat = new SoThatDTO();
                         soThat.setKeyword(keywords.soThat());
-                        String value = line.substring(keywords.soThat().length());
+                        String value = line.substring(keywords.soThat().length()).trim();
                         if (!value.isEmpty()) {
                             soThat.setValue(value);
                         }
Index: execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/resources/jira_stories/TESTING-1.story
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/resources/jira_stories/TESTING-1.story	(date 1402907572000)
+++ execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/resources/jira_stories/TESTING-1.story	(revision )
@@ -1,6 +1,6 @@
 Meta:
-@jira-version 7
+@jira-version 2
 
-Scenario: 
+Scenario: some story description
 
 
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/StoryDTOUtils.java
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/StoryDTOUtils.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/StoryDTOUtils.java	(revision )
@@ -1,7 +1,6 @@
 package com.mycomp.execspec.jiraplugin.dto.story;
 
 import com.mycomp.execspec.jiraplugin.ao.story.Story;
-import com.mycomp.execspec.jiraplugin.dto.story.output.*;
 import com.mycomp.execspec.jiraplugin.util.ByLineStoryParser;
 import org.apache.commons.lang.Validate;
 import org.jbehave.core.steps.StepCreator;
@@ -42,7 +41,7 @@
         return str;
     }
 
-    private static String markTablesInPendingStep(String step, CustomHTMLOutput reporter) {
+    private static String markTablesInPendingStep(String step) {
 
         StringBuilder sb = new StringBuilder();
         String[] lines = step.split("\\n");
@@ -177,7 +176,7 @@
                 for (MetaEntryDTO p : properties) {
                     String name = p.getName();
                     String value = p.getValue();
-                    sb.append(name);
+                    sb.append("@" + name);
                     if (value != null && !value.isEmpty()) {
                         sb.append(" " + value);
                     }
@@ -240,6 +239,20 @@
                         sb.append(" " + value);
                     }
                     sb.append(LB);
+                }
+            }
+        }
+
+        List<ScenarioDTO> scenarios = storyDTO.getScenarios();
+        if (scenarios != null && !scenarios.isEmpty()) {
+            for (ScenarioDTO scenario : scenarios) {
+                String keyword = scenario.getKeyword();
+                Validate.notEmpty(keyword);
+                sb.append(keyword);
+                String title = scenario.getTitle();
+                if (title != null && !title.isEmpty()) {
+                    sb.append(" ");
+                    sb.append(title.trim());
                 }
             }
         }
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryController.js
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryController.js	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/resources/js/StoryController.js	(revision )
@@ -19,6 +19,7 @@
     pageUtils.init();
 
     this.currentStory = new StoryModel();
+    this.currentReports = [];
     this.editMode = false;
 
     this.debug = function (msg) {
@@ -33,14 +34,15 @@
         var issueKey = pageUtils.getIssueKey();
         var projectKey = pageUtils.getProjectKey();
         storyService.find(projectKey, issueKey,
-            function (story) {
+            function (storyPayload) {
                 storyController.debug("> loadStory.callback");
-                var storyPayload = JSON.stringify(story, null, "\t");
-                storyController.debug("story - " + storyPayload);
-                if (story != undefined) {
+                storyController.debug("storyPayload - " + JSON.stringify(storyPayload, null, "\t"));
+                if (storyPayload != undefined) {
 //                    storyView.showStoryButton(story);
 //                    storyView.showStoryReportButtons(story); // TODO
-                    storyController.currentStory = story;
+                    storyView.showStoryButtons(storyPayload);
+                    storyController.currentStory = storyPayload.story;
+                    storyController.currentReports = storyPayload.testReports;
                     storyController.showStoryHandler(null);
                 } else {
                     storyController.debug("no story found for project - " + projectKey + ", issue - " + issueKey);
@@ -83,15 +85,15 @@
         this.debug("# showStoryHandler");
     }
 
-    this.showStoryReport = function (environment) {
+    this.showStoryReport = function (event, environment) {
 
         this.debug("> showStoryReport");
         this.debug("environment - " + environment);
 
         // find the report for environment
         var reportForEnvironment = undefined;
-        for (var i = 0; i < this.currentStory.storyReports.length; i++) {
-            var storyReport = this.currentStory.storyReports[i];
+        for (var i = 0; i < this.currentReports.length; i++) {
+            var storyReport = this.currentReports[i];
             if (storyReport.environment == environment) {
                 reportForEnvironment = storyReport;
                 break;
@@ -99,6 +101,7 @@
         }
         storyView.showStoryReport(reportForEnvironment, this.currentStory.version);
 
+        event.preventDefault();
         this.debug("# showStoryReport");
     }
 
@@ -148,7 +151,7 @@
         this.editMode = true;
         storyView.editStory(this.currentStory);
 
-        if(event != null) {
+        if (event != null) {
             event.preventDefault();
         }
         this.debug("# editStoryHandler");
@@ -194,7 +197,9 @@
 
         this.debug("> saveStoryAsModel");
 
-        var storyPayload = JSON.stringify(this.currentStory);
+        var storyPayload = JSON.stringify(this.currentStory, null, "\t");
+
+        this.debug("saving story:\n" + storyPayload);
 
         storyService.saveOrUpdateStory(storyPayload, function (savedStory) {
             storyController.debug("> saveStoryAsModel.saveOrUpdateStory callback");
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/ScenarioDTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/ScenarioDTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/ScenarioDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/MetaEntryDTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/MetaEntryDTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/MetaEntryDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/ReportingStoryWalker.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/ReportingStoryWalker.java	(date 1402907572000)
+++ execspec/tests-2-master/example-projects/example-run-stories-as-paths/src/test/java/com/mycomp/execspec/util/ReportingStoryWalker.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story;
+package com.mycomp.execspec.util;
 
 import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
 import org.jbehave.core.model.GivenStories;
Index: execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/NarrativeDTO.java
===================================================================
--- execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/output/NarrativeDTO.java	(date 1402907572000)
+++ execspec/tests-2-master/execspec-parent/execspec-jira-plugin/src/main/java/com/mycomp/execspec/jiraplugin/dto/story/NarrativeDTO.java	(revision )
@@ -1,4 +1,4 @@
-package com.mycomp.execspec.jiraplugin.dto.story.output;
+package com.mycomp.execspec.jiraplugin.dto.story;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
