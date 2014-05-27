function StoryView(storyController) {

    this.autoCompleteEntries = AutoCompleteEntryModel[0];

    this.init = function () {

        console.log("> StoryView.init");

        // update button menu links
        AJS.$("#add-jbehave-story-link").click(
            function (event) {
                event.preventDefault();
                console.log("> add-jbehave-story-link clicked");
                storyController.addStory();
                console.log("# add-jbehave-story-link clicked");
            }
        );
        AJS.$("#edit-jbehave-story-link").click(
            function (event) {
                event.preventDefault();
                console.log("> edit-jbehave-story-link clicked");
                storyController.editStoryHandler();
                console.log("# edit-jbehave-story-link clicked");
            }
        )
        AJS.$("#delete-jbehave-story-link").click(
            function (event) {
                event.preventDefault();
                console.log("> delete-jbehave-story-link clicked");
                storyController.deleteStory();
                console.log("# delete-jbehave-story-link clicked");
            }
        );
        AJS.$("#clear-jbehave-story-tests-link").click(
            function (event) {
                event.preventDefault();
                console.log("> clear-jbehave-story-tests-link clicked");
                storyController.clearStoryTests();
                console.log("# clear-jbehave-story-tests-link clicked");
            }
        );

        console.log("rendering story panel");
        AJS.$("#story-panel").html(execspec.viewissuepage.showstory.renderStoryPanel());

        console.log("rendering story edit area");

        this.editor = null;

//        AJS.$("#testMouseOver").mouseover(function() {
//            console.log("Mouse over event fired!");
//        });

        YUI().use('editor-base', function (Y) {

            var content = execspec.viewissuepage.showstory.renderEditStoryArea2();
            var extraCss = execspec.viewissuepage.editstory.renderExtraCss();

            var editor = new Y.EditorBase({
                content: content,
                extracss: extraCss
            });
            storyView.editor = editor;

            //Add the BiDi plugin
            editor.plug(Y.Plugin.EditorBidi);

            Y.mix(Y.Plugin.ExecCommand.COMMANDS, {
                foo: function (cmd, val) {
                    console.log('You clicked on Foo');
                    var inst = this.getInstance();
                    inst.one('body').setStyle('backgroundColor', 'yellow');
                },
                setContent: function (cmd, val) {
                    console.log('You clicked on setContent');
                    console.log('cmd - ' + cmd);
                    console.log('val - ' + val);
                    var inst = this.getInstance();

                    var editorNode = inst.one('.story-panel');
                    editorNode.setContent(val);
//                    inst.one('#storyRichTextEditArea').html('HAHA');
//                    inst.one('body').setStyle('backgroundColor', 'yellow');

//                    AJS.$(".narrative").mouseover(function() {
//                        console.log('mouseOver event from JQuery fired');
//                    });

                    inst.one('.inOrderTo').on('mouseover', function () {
                        console.log("mouse over fired on .inOrderTo");
                        inst.one('.scenario-plus').show();
                    });
                    inst.one('.inOrderTo').on('mouseleave', function () {
                        console.log("mouse leave fired on .inOrderTo");
                        inst.one('.scenario-plus').hide();
                    });

                }
            });

            Y.each(Y.Frame.DOM_EVENTS, function (v, k) {
                editor.on('dom:' + k, function (e) {
                    var tag = e.frameTarget.get('tagName').toLowerCase();
                    console.log('Event: ' + e.type + ' on element (' + tag + ')');
                });
            });


            //Focusing the Editor when the frame is ready..
//            editor.on('frame:ready', function () {
//                this.focus();
//                var height = AJS.$("iframe").contents().height() + 40;
//                console.log("setting editor height to - " + height);
//                AJS.$("#storyEditContainer").height(height);
//            });

            //Rendering the Editor.
            editor.render('#storyEditContainer');

        });

//        var storyEditArea = execspec.viewissuepage.showstory.renderEditStoryArea2();
//        AJS.$("#storyEditContainer").html(storyEditArea);

//        {
//            // keyboard events handling
//            storyView.isCtrDown = false;
//
//            AJS.$("#story-edit-text-area").keydown(function (event) {
//                console.log("keydown, event.keyCode - " + event.keyCode);
//                if (event.keyCode == 17) {
//                    storyView.isCtrDown = true;
//                }
//            });
//
//            AJS.$("#story-edit-text-area").keyup(function (event) {
//                console.log("keyup, event.keyCode - " + event.keyCode);
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
//                console.log("> StoryView.autoCompleteDialog.contentProvider");
//                var autoCompleteHtml = storyView.fetchAutoCompleteContent();
//                content
////                    .css({"padding-left": "10px", "padding-top": "2px", "padding-right": "10px", "padding-bottom": "2px"})
//                    .html(autoCompleteHtml);
//                showPopup();
//                console.log("# StoryView.autoCompleteDialog.contentProvider");
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

        console.log("# StoryView.init");
    }

    this.fetchAutoCompleteContent = function () {

        console.log("> StoryView.fetchAutoCompleteContent");

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

        console.log("autoCompleteHtml - " + autoCompleteHtml);
        console.log("# StoryView.fetchAutoCompleteContent");

        return autoCompleteHtml;
    }

    this.getAutoCompleteYPos = function () {

        console.log("> StoryView.getAutoCompleteYPos");

        var storyInputAsString = storyView.getStoryInputAsString();
        var caretPosition = storyView.getStoryInputCaretPosition();
        console.log("caretPosition - " + caretPosition);
        var substring = storyInputAsString.substr(0, caretPosition);
        console.log("substring - " + substring);
        var lines = substring.split("\n");
        var lineNumber = lines.length;
        console.log("lineNumber - " + lineNumber);

        var scrollTop = AJS.$("#story-edit-text-area").prop("scrollTop");
        console.log("scrollTop - " + scrollTop);

//        var position = 8; // with the arrow present
        var position = 2; // without the arrow present
        position += (lineNumber * 6) + lineNumber * (parseInt(AJS.$("#story-edit-text-area").css('font-size')));
        if (scrollTop != undefined && scrollTop > 0) {
            position = position - scrollTop;
        }

        console.log("position - " + position);
        console.log("# StoryView.getAutoCompleteYPos");

        return  position;
    }

    this.getStoryInputAsString = function () {

        console.log("> StoryView.getStoryInputAsString");

        var asString = AJS.$("#story-edit-text-area").val();
        console.log("asString - " + asString);
        return asString;

        console.log("# StoryView.getStoryInputAsString");
    }

    this.getStoryInputCaretPosition = function () {
        var caretPos = AJS.$("#story-edit-text-area").caret();
        return caretPos;
    }

    this.updateSelectedButton = function (clickedElementId) {
        console.log("> StoryView.updateSelectedButton");
        AJS.$(".story-container-button").removeClass("selected-story-container-button");
        AJS.$("#" + clickedElementId).addClass("selected-story-container-button");
        console.log("# StoryView.updateSelectedButton");
    }

    this.removeStory = function () {
        AJS.$("#storyButtons").html("");
        AJS.$("#storyReportButtons").html("");
        AJS.$("#storyViewContainer").html("");
    }

    this.showStoryButton = function (story) {

        console.log("> StoryView.showStoryButton");

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

        console.log("# StoryView.showStoryButton");
    }

    this.showStory = function (story, editMode) {

        console.log("> StoryView.showStory");
        console.log("story.asString - " + story.asString);
        console.log("editMode - " + editMode);

        if (editMode != undefined && editMode == true) {

            var lines = story.asString.split("\n");
            var lineCount = lines.length;
            var storyAsString = story.asString;
            var storyAsHTML = story.asHTML;

            // hide story and story reports view
            AJS.$("#storyContainer").hide();
            AJS.$("#storyReportContainer").hide();

            // show edit area
//            AJS.$("#story-edit-text-area").val(storyAsString);
//            AJS.$("#story-edit-text-area").attr("rows", lineCount);


//            AJS.$("#storyRichTextEditArea").html(storyAsString);
//            var editorContainer = this.editor.one('storyRichTextEditArea');
//            editorContainer.html(storyAsString);
            this.editor.execCommand("setContent", storyAsHTML);

            var height = AJS.$("iframe").contents().height() + 40;
            console.log("setting editor height to - " + height);
            AJS.$("#storyEditContainer").height(height);

            AJS.$("#storyEditContainer").show();

            var height = AJS.$("iframe").contents().height() + 40;
            console.log("setting editor height to - " + height);
            AJS.$("#storyEditContainer").height(height);

            AJS.$("#testMouseOver").mouseover(function () {
                console.log("Mouse over event fired on test container!");
            });

//            AJS.$("#inOrderTo").mouseover(function() {
//                console.log("Mouse over event fired on inOrderTo!");
//            });

//            AJS.$("#story-edit-text-area").keyup(function (event) {
//
//                console.log("keyup, event.keyCode - " + event.keyCode);
//                var caretPos = AJS.$("#story-edit-text-area").caret();
//                console.log("caretPos - " + caretPos);
//
//
//                if (event.keyCode == 17) {
//                    console.log("control key pressed");
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
////                console.log("posString - " + posString);
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

        } else {

            AJS.$("#storyReportContainer").hide();
            AJS.$("#storyEditContainer").hide();

            AJS.$("#storyContainer").html(story.asHTML);
            AJS.$("#storyContainer").show();

        }

        this.updateSelectedButton("show-story-button");

        console.log("# StoryView.showStory");
    }

    this.showStoryReportButtons = function (story) {

        console.log("> StoryView.showStoryReportButtons");

        var storyVersion = story.version;
        console.log("storyVersion - " + storyVersion);

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

                    console.log("> show story report button clicked");
                    event.preventDefault();

                    var attributes = event.target.attributes;
                    var environmentNode = attributes["environment"];
                    var environment = environmentNode.nodeValue;
                    storyController.showStoryReport(environment);

                    console.log("# show story report button clicked");
                }
            );
        }

        console.log("# StoryView.showStoryReportButtons");
    }

    this.showStoryReport = function (storyReport, storyVersion) {

        console.log("> StoryView.showStoryReport");
        console.log("storyReport.environment - " + storyReport.environment);

        var reportToShowTemplateModel = new Object();
        reportToShowTemplateModel.storyReport = storyReport;
        var storyReportHTML = execspec.viewissuepage.showstory.renderStoryReport(reportToShowTemplateModel);
        AJS.$("#storyReportContainer").html(storyReportHTML);

        if (storyVersion > storyReport.storyVersion) {
            AJS.messages.generic("#reportMessageContainer", {
                title: "Story has been modified since last run",
                closeable: false
            });
        }

        AJS.$("#storyContainer").hide();
        AJS.$("#storyEditContainer").hide();
        AJS.$("#storyReportContainer").show();

        this.updateSelectedButton("show-story-report-" + storyReport.environment);
        console.log("# StoryView.showStoryReport");
    }

    this.showAutoComplete = function (entries) {

        console.log("> StoryView.showAutoComplete");
        console.log("entries - " + entries);

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
        console.log("autoCompleteHtml - " + autoCompleteHtml);

//        AJS.$("#autoCompleteHtml").html(autoCompleteHtml);
//        AJS.$("#arrow-autoCompleteDialog")

        console.log("# StoryView.showAutoComplete");
    }

}



