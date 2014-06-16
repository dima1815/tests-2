function StoryView(storyController) {

    this.debugOn = true;

    this.autoCompleteEntries = AutoCompleteEntryModel[0];

    this.storyEditHandler = new StoryEditHandler();
    this.storyEditHandler.init();

    this.debug = function (msg) {
        if (this.debugOn) {
            console.log("[DEBUG StoryView] " + msg);
        }
    }

    this.init = function () {

        this.debug("> StoryView.init");

        // update button menu links
        AJS.$("#add-jbehave-story-link").click(
            function (event) {
                event.preventDefault();
                storyView.debug("> add-jbehave-story-link clicked");
                storyController.addStory();
                storyView.debug("# add-jbehave-story-link clicked");
            }
        );
        AJS.$("#edit-jbehave-story-link").click(
            function (event) {
                event.preventDefault();
                storyView.debug("> edit-jbehave-story-link clicked");
                storyController.editStoryHandler();
                storyView.debug("# edit-jbehave-story-link clicked");
            }
        )
        AJS.$("#delete-jbehave-story-link").click(
            function (event) {
                event.preventDefault();
                storyView.debug("> delete-jbehave-story-link clicked");
                storyController.deleteStory();
                storyView.debug("# delete-jbehave-story-link clicked");
            }
        );
        AJS.$("#clear-jbehave-story-tests-link").click(
            function (event) {
                event.preventDefault();
                storyView.debug("> clear-jbehave-story-tests-link clicked");
                storyController.clearStoryTests();
                storyView.debug("# clear-jbehave-story-tests-link clicked");
            }
        );

        AJS.$("#story-panel").html(execspec.viewissuepage.storytoolbar.renderStoryToolbar());


//        this.editor = null;


//        AJS.$("#testMouseOver").mouseover(function() {
//            storyView.debug("Mouse over event fired!");
//        });

//        YUI().use('editor-base', function (Y) {
//
//            var content = execspec.viewissuepage.showstory.renderEditStoryArea2();
////            var extraCss = execspec.viewissuepage.editstory.renderExtraCss();
//
//            var editor = new Y.EditorBase({
//                content: content
////                extracss: extraCss
//            });
//            storyView.editor = editor;
//
//            //Add the BiDi plugin
//            editor.plug(Y.Plugin.EditorBidi);
//
//            Y.mix(Y.Plugin.ExecCommand.COMMANDS, {
//                foo: function (cmd, val) {
//                    storyView.debug('You clicked on Foo');
//                    var inst = this.getInstance();
//                    inst.one('body').setStyle('backgroundColor', 'yellow');
//                },
//                setContent: function (cmd, val) {
//                    storyView.debug('You clicked on setContent');
//                    storyView.debug('cmd - ' + cmd);
//                    storyView.debug('val - ' + val);
//                    var inst = this.getInstance();
//
//                    var editorNode = inst.one('.story-panel');
//                    editorNode.setContent(val);
////                    inst.one('#storyRichTextEditArea').html('HAHA');
////                    inst.one('body').setStyle('backgroundColor', 'yellow');
//
////                    AJS.$(".narrative").mouseover(function() {
////                        storyView.debug('mouseOver event from JQuery fired');
////                    });
//
//                    inst.one('.inOrderTo').on('mouseover', function () {
//                        storyView.debug("mouse over fired on .inOrderTo");
////                        inst.one('.scenario-plus').show();
//                        inst.one('.add-icon').show();
//
//                    });
//                    inst.one('.inOrderTo').on('mouseleave', function () {
//                        storyView.debug("mouse leave fired on .inOrderTo");
//                        var attr = inst.one('.inOrderTo').get("contentEditable");
//                        storyView.debug("attr - " + attr);
//                        inst.one('.add-icon').hide();
//                    });
//
//                    inst.one('.inOrderToSpan').on('focus', function () {
//                        storyView.debug("on focus fired on .inOrderToSpan");
//                    });
//
//                }
//            });
//
//            Y.each(Y.Frame.DOM_EVENTS, function (v, k) {
//                editor.on('dom:' + k, function (e) {
//                    var tag = e.frameTarget.get('tagName').toLowerCase();
//                    storyView.debug('Event: ' + e.type + ' on element (' + tag + ')');
//                });
//            });
//
//            //Focusing the Editor when the frame is ready..
//            editor.on('frame:ready', function () {
//                this.focus();
////                var height = AJS.$("iframe").contents().height() + 40;
////                storyView.debug("setting editor height to - " + height);
////                AJS.$("#storyEditContainer").height(height);
//            });
//
//            //Rendering the Editor.
//            editor.render('#storyEditContainer');
//
//        });

//        var storyEditArea = execspec.viewissuepage.showstory.renderEditStoryArea2();
//        AJS.$("#storyEditContainer").html(storyEditArea);

//        {
//            // keyboard events handling
//            storyView.isCtrDown = false;
//
//            AJS.$("#story-edit-text-area").keydown(function (event) {
//                storyView.debug("keydown, event.keyCode - " + event.keyCode);
//                if (event.keyCode == 17) {
//                    storyView.isCtrDown = true;
//                }
//            });
//
//            AJS.$("#story-edit-text-area").keyup(function (event) {
//                storyView.debug("keyup, event.keyCode - " + event.keyCode);
//                if (event.keyCode == 17) {
//                    storyView.isCtrDown = false;
//                } else if (event.keyCode == 32 /*space key*/ && storyView.isCtrDown) {
//                    storyController.showAutoCompleteHandler();
//                    storyView.isCtrDown = false;
//                }
//            });
//        }

        // prepare auto complete area
//        var storyPanelWidth = AJS.$("#story-panel").width();
//
//        this.autoCompleteDialog = AJS.InlineDialog(AJS.$("#story-edit-text-area"),
//            "autoCompleteDialog",
//            function (content, trigger, showPopup) {
//
//                storyView.debug("> StoryView.autoCompleteDialog.contentProvider");
//                var autoCompleteHtml = storyView.fetchAutoCompleteContent();
//                content
////                    .css({"padding-left": "10px", "padding-top": "2px", "padding-right": "10px", "padding-bottom": "2px"})
//                    .html(autoCompleteHtml);
//                showPopup();
//                storyView.debug("# StoryView.autoCompleteDialog.contentProvider");
//                return false;
//            },
//            {
//                noBind: true,
//                cacheContent: false,
//                displayShadow: false,
//                width: AJS.$("#story-panel").width() - 90,
////                width: AJS.$("#story-edit-text-area").css("width") + 75,
//                container: AJS.$("#autoCompleteContainer1"),
////                arrowOffsetX: 100,
////                arrowOffsetY: 100,
////                persistent: true,
////                gravity: 'n',
////                initCallback: function () {
//////                    alert("Hello World");
////                    AJS.$("#arrow-autoCompleteDialog").removeClass("aui-bottom-arrow");
////                },
//                calculatePositions: function getPosition(popup, targetPosition, mousePosition, opts) {
//                    return {
//                        displayAbove: true,
//                        popupCss: {
////                            left: mousePosition.x,
////                            top: mousePosition.y + 20,
////                            right: mousePosition.y + 100
//                            left: 10,
//                            top: storyView.getAutoCompleteYPos(),
//                            right: 100
//                        },
//                        arrowCss: {
//                            left: 20,
//                            top: -16
//                        }
//                    }
//                }
//            }
//        );

        storyView.debug("# StoryView.init");
    }

    this.showAddStory = function () {

        storyView.debug("> showAddStory");

        var buttonsForAddStory = execspec.viewissuepage.storytoolbar.renderButtonsForAddStory();
        AJS.$("#storyButtons").html(buttonsForAddStory);

        storyView.debug("# showAddStory");
    }

    this.editStory = function (story) {

        this.debug("> editStory");

        this.debug("story:\n" + JSON.stringify(story, null, "\t"));

        var buttonsForStory = execspec.viewissuepage.storytoolbar.renderButtonsForStory(story);
        AJS.$("#storyButtons").html(buttonsForStory);
        AJS.$("#editStoryButton").attr("aria-pressed", "true");

        var editContainerContent = execspec.viewissuepage.editstory.renderEditStoryContainer();
        AJS.$("#storyEditContainer").html(editContainerContent);

        // set content for rich editor as well as raw editor
        var templateObj = new Object();
        templateObj.story = story;

        var richEditStoryContent = execspec.viewissuepage.editstory.rich.renderRichEditStoryContent(templateObj);
        AJS.$("#richEditStoryContainer").html(richEditStoryContent);

        editButtonHandler.assignRichEditorHandlers(story);

        AJS.$("#richTextEditorButton").click();

        AJS.$("#storyContainer").hide();
        AJS.$("#storyEditContainer").show();
        this.debug("# editStory");
    }

    this.fetchAutoCompleteContent = function () {

        storyView.debug("> StoryView.fetchAutoCompleteContent");

        var templateObject = new Object();
        templateObject.entries = this.autoCompleteEntries;
        var autoCompleteHtml = execspec.viewissuepage.showstory.renderAutoComplete(templateObject);

//        var autoCompleteHtml = "<ul class='autoCompleteEntries'>";
//        for (var i = 0; i < this.autoCompleteEntries.length; i++) {
//            var entry = this.autoCompleteEntries[i];
//            autoCompleteHtml += "<li class='autoCompleteEntry'>";
//            autoCompleteHtml += entry.suggestion;
//            autoCompleteHtml += "</li>";
//        }
//        autoCompleteHtml += "</ul>";

        storyView.debug("autoCompleteHtml - " + autoCompleteHtml);
        storyView.debug("# StoryView.fetchAutoCompleteContent");

        return autoCompleteHtml;
    }

    this.getAutoCompleteYPos = function () {

        storyView.debug("> StoryView.getAutoCompleteYPos");

        var storyInputAsString = storyView.getStoryInputAsString();
        var caretPosition = storyView.getStoryInputCaretPosition();
        storyView.debug("caretPosition - " + caretPosition);
        var substring = storyInputAsString.substr(0, caretPosition);
        storyView.debug("substring - " + substring);
        var lines = substring.split("\n");
        var lineNumber = lines.length;
        storyView.debug("lineNumber - " + lineNumber);

        var scrollTop = AJS.$("#story-edit-text-area").prop("scrollTop");
        storyView.debug("scrollTop - " + scrollTop);

//        var position = 8; // with the arrow present
        var position = 2; // without the arrow present
        position += (lineNumber * 6) + lineNumber * (parseInt(AJS.$("#story-edit-text-area").css('font-size')));
        if (scrollTop != undefined && scrollTop > 0) {
            position = position - scrollTop;
        }

        storyView.debug("position - " + position);
        storyView.debug("# StoryView.getAutoCompleteYPos");

        return  position;
    }

    this.getStoryInputAsString = function () {

        storyView.debug("> StoryView.getStoryInputAsString");

        var asString = AJS.$("#story-edit-text-area").val();
        storyView.debug("asString - " + asString);
        return asString;

        storyView.debug("# StoryView.getStoryInputAsString");
    }

    this.getStoryInputCaretPosition = function () {
        var caretPos = AJS.$("#story-edit-text-area").caret();
        return caretPos;
    }

    this.updateSelectedButton = function (clickedElementId) {
        storyView.debug("> StoryView.updateSelectedButton");
        AJS.$(".story-container-button").removeClass("selected-story-container-button");
        AJS.$("#" + clickedElementId).addClass("selected-story-container-button");
        storyView.debug("# StoryView.updateSelectedButton");
    }

    this.removeStory = function () {
        AJS.$("#storyButtons").html("");
        AJS.$("#storyReportButtons").html("");
        AJS.$("#storyViewContainer").html("");
    }

    this.showStoryButton = function (story) {

        storyView.debug("> StoryView.showStoryButton");

        // add the story button link
        var storyButtonHtml = execspec.viewissuepage.showstory.renderStoryButton(story);
        AJS.$("#storyButtons").html(storyButtonHtml);
        // set story button onClick handler
        AJS.$("#show-story-button").click(
            function (event) {
                event.preventDefault();
                storyController.showStoryHandler();
            }
        );

        storyView.debug("# StoryView.showStoryButton");
    }

    this.showStory = function (story) {

        storyView.debug("> StoryView.showStory");

        var buttonsForStory = execspec.viewissuepage.storytoolbar.renderButtonsForStory(story);
        AJS.$("#storyButtons").html(buttonsForStory);
        AJS.$("#editStoryButton").attr("aria-pressed", "false");
        AJS.$("#showStoryButton").attr("aria-pressed", "true");

        var templateObj = new Object();
        templateObj.story = story;
        var storyHtml = execspec.viewissuepage.showstory.renderStoryAsString(templateObj);

        AJS.$("#storyContainer").html(storyHtml);
        AJS.$("#storyContainer").show();

        AJS.$("#storyEditContainer").hide();

        storyView.debug("# StoryView.showStory");
    }

//    this.showStory = function (story, editMode) {
//
//        storyView.debug("> StoryView.showStory");
//        storyView.debug("story.asString - " + story.asString);
//        storyView.debug("editMode - " + editMode);
//
//        if (editMode != undefined && editMode == true) {
//
//            var lines = story.asString.split("\n");
//            var lineCount = lines.length;
//            var storyAsString = story.asString;
//            var storyAsHTML = story.asHTML;
//
//            // hide story and story reports view
//            AJS.$("#storyContainer").hide();
//            AJS.$("#storyReportContainer").hide();
//
//            // show edit area
////            AJS.$("#story-edit-text-area").val(storyAsString);
////            AJS.$("#story-edit-text-area").attr("rows", lineCount);
//
//
////            AJS.$("#storyRichTextEditArea").html(storyAsString);
////            var editorContainer = this.editor.one('storyRichTextEditArea');
////            editorContainer.html(storyAsString);
//            this.editor.execCommand("setContent", storyAsHTML);
//
//            var height = AJS.$("iframe").contents().height() + 40;
//            storyView.debug("setting editor height to - " + height);
//            AJS.$("#storyEditContainer").height(height);
//
//            AJS.$("#storyEditContainer").show();
//
//            var height = AJS.$("iframe").contents().height() + 40;
//            storyView.debug("setting editor height to - " + height);
//            AJS.$("#storyEditContainer").height(height);
//
//            AJS.$("#testMouseOver").mouseover(function () {
//                storyView.debug("Mouse over event fired on test container!");
//            });

//            AJS.$("#inOrderTo").mouseover(function() {
//                storyView.debug("Mouse over event fired on inOrderTo!");
//            });

//            AJS.$("#story-edit-text-area").keyup(function (event) {
//
//                storyView.debug("keyup, event.keyCode - " + event.keyCode);
//                var caretPos = AJS.$("#story-edit-text-area").caret();
//                storyView.debug("caretPos - " + caretPos);
//
//
//                if (event.keyCode == 17) {
//                    storyView.debug("control key pressed");
////                    AJS.InlineDialog(AJS.$("#popupLink"), 1,
////                        function(content, trigger, showPopup) {
////                            content.css({"padding":"20px"}).html('<h2>Inline dialog</h2><p>The inline dialog is a wrapper for secondary content/controls to be displayed on user request. Consider this component as displayed in context to the triggering control with the dialog overlaying the page content.</p><button class="aui-button">Done</button></form>');
////                            showPopup();
////                            return false;
////                        }
////                    );
//                    var dialog = AJS.InlineDialog(AJS.$("#story-edit-text-area"),
//                        "myDialog",
//                        function (content, trigger, showPopup) {
//                            content.css({"padding": "20px"}).html('<h2>Inline dialog</h2><p>Content.</p>');
//                            showPopup();
//                            return false;
//                        },
//                        {
//                            noBind: true
//                        }
//                    );
//                    dialog.show();
//
//                }
//
////                var newY = AJS.$("#story-edit-text-area").textAreaHelper('caretPos').top
////                    + (parseInt(AJS.$("#story-edit-text-area").css('font-size'), 10) * 1.5);
////                var newX = AJS.$("#story-edit-text-area").textAreaHelper('caretPos').left;
////                var posString = "left+" + newX + "px top+" + newY + "px";
////                storyView.debug("posString - " + posString);
////                AJS.$("#story-edit-text-area").autocomplete("option", "position", {
////                    my: "left top",
////                    at: posString
////                });
//            });

    // add the auto complete
//            AJS.$("#story-edit-text-area").autocomplete({
//                autoFocus: true,
//                position: {
//                    my: "left top",
//                    at: "left+100px top+100px"
//                },
//                source: [ "c++", "java", "php", "coldfusion", "javascript", "asp", "ruby" ]
//            });

//            AJS.$("#story-edit-text-area").autocomplete("option", "position", {
//                my: "left top",
//                at: "left+" + 100 + "px top+" + 100 + "px"
//            });

//        } else {
//
//            AJS.$("#storyReportContainer").hide();
//            AJS.$("#storyEditContainer").hide();
//
////            AJS.$("#storyContainer").html(story.asHTML);
//            var templateObject = new Object();
//            templateObject.story = story;
//            templateObject.story.asJson = JSON.stringify(story);
//            var editStoryContent = execspec.viewissuepage.editstory.renderEditStory(templateObject);
//            AJS.$("#storyContainer").html(editStoryContent);
//
//            AJS.$("#storyContainer").show();
//
//            AJS.$(".beforeNarrative").mouseover(function () {
//                storyView.debug("mouse over on - beforeNarrative")
//                AJS.$(".beforeNarrativeHint").show();
//            });
//
//            AJS.$(".beforeNarrative").mouseout(function () {
//                storyView.debug("mouse out on - beforeNarrative")
//                AJS.$(".beforeNarrativeHint").hide();
//            });
//
//        }
//
//        this.updateSelectedButton("show-story-button");
//
//        storyView.debug("# StoryView.showStory");
//    }

    this.showStoryReportButtons = function (story) {

        storyView.debug("> StoryView.showStoryReportButtons");

        var storyVersion = story.version;
        storyView.debug("storyVersion - " + storyVersion);

        // add the story reports
        var storyReportButtons = execspec.viewissuepage.showstory.renderStoryReportButtons(story);
        AJS.$("#storyReportButtons").html(storyReportButtons);
        // set the story report button onClick handlers
        var storyReports = story.storyReports;
        for (var i = 0; i < storyReports.length; i++) {
            var storyReport = storyReports[i];
            var linkId = "show-story-report-" + storyReport.environment;
            AJS.$("#" + linkId).click(
                function (event) {

                    storyView.debug("> show story report button clicked");
                    event.preventDefault();

                    var attributes = event.target.attributes;
                    var environmentNode = attributes["environment"];
                    var environment = environmentNode.nodeValue;
                    storyController.showStoryReport(environment);

                    storyView.debug("# show story report button clicked");
                }
            );
        }

        storyView.debug("# StoryView.showStoryReportButtons");
    }

//    this.showStoryReport = function (storyReport, storyVersion) {
//
//        storyView.debug("> StoryView.showStoryReport");
//        storyView.debug("storyReport.environment - " + storyReport.environment);
//
//        var reportToShowTemplateModel = new Object();
//        reportToShowTemplateModel.storyReport = storyReport;
//        var storyReportHTML = execspec.viewissuepage.showstory.renderStoryReport(reportToShowTemplateModel);
//        AJS.$("#storyReportContainer").html(storyReportHTML);
//
//        if (storyVersion > storyReport.storyVersion) {
//            AJS.messages.generic("#reportMessageContainer", {
//                title: "Story has been modified since last run",
//                closeable: false
//            });
//        }
//
//        AJS.$("#storyContainer").hide();
//        AJS.$("#storyEditContainer").hide();
//        AJS.$("#storyReportContainer").show();
//
//        this.updateSelectedButton("show-story-report-" + storyReport.environment);
//        storyView.debug("# StoryView.showStoryReport");
//    }

    this.showAutoComplete = function (entries) {

        storyView.debug("> StoryView.showAutoComplete");
        storyView.debug("entries - " + entries);

        this.autoCompleteEntries = entries;

        this.autoCompleteDialog.refresh();
//        AJS.$("#arrow-autoCompleteDialog").removeClass("aui-bottom-arrow");
//        AJS.$("#arrow-autoCompleteDialog").addClass("aui-css-arrow");
        this.autoCompleteDialog.show();
//        AJS.$("#arrow-autoCompleteDialog").removeClass("aui-bottom-arrow");
//        AJS.$("#arrow-autoCompleteDialog").addClass("aui-css-arrow");
//        AJS.$("#arrow-autoCompleteDialog").addClass("aui-top-arrow");
//        AJS.$("#arrow-autoCompleteDialog").rotate(90);


        var autoCompleteHtml = storyView.autoCompleteDialog.html();
        storyView.debug("autoCompleteHtml - " + autoCompleteHtml);

//        AJS.$("#autoCompleteHtml").html(autoCompleteHtml);
//        AJS.$("#arrow-autoCompleteDialog")

        storyView.debug("# StoryView.showAutoComplete");
    }

}



