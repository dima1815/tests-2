var editButtonHandler;

function StoryEditHandler() {

    editButtonHandler = this;

    this.debugOn = true;

    this.debug = function (msg) {
        if (this.debugOn) {
            console.log("[DEBUG StoryEditHandler] " + msg);
        }
    }

    this.init = function ( ) {
        this.debug("initialized");
    }


    this.assignRichEditorHandlers = function (story) {

        this.debug("> assignRichEditorHandlers");

        this.addInsertLinks(story);

        this.assignInsertLinkHandlers(story);

        this.assignShowElementOperationsOnHover(story);

        this.assignAutoHeightForTextAreas(story);


        this.debug("# assignRichEditorHandlers");
    }

    this.assignAutoHeightForTextAreas = function (story) {

        // set height dynamically on key press
        AJS.$('.story-editor-field').keydown(function (event) {
            editButtonHandler.debug('keydown, event.target - ' + event.target);
            var key = event.which;
            editButtonHandler.debug('keydown, event.which - ' + key);
            if (key == 13) {
                editButtonHandler.debug('return pressed, resizing text area');
                editButtonHandler.resizeTextArea(event);
            }
        });

        AJS.$('.story-editor-field').keyup(function (event) {
            editButtonHandler.resizeTextArea(event);
        });

        this.resizeTextArea = function (event) {
            var textArea = event.target;

            editButtonHandler.debug('keypressed, event.target - ' + textArea);
            var value = AJS.$(event.target).val();
            editButtonHandler.debug('value - ' + value)
            var lines = value.split(/\r*\n/);
            editButtonHandler.debug('lines - ' + lines)
            var lineCount = lines.length;
            editButtonHandler.debug('lineCount - ' + lineCount);
            var currentRows = AJS.$(event.target).attr("rows");
            editButtonHandler.debug('currentRows - ' + currentRows);
            if (Number(currentRows) != lineCount) {
                AJS.$(event.target).attr("rows", lineCount);
            }
        }

        // add border on focus
        AJS.$('.story-editor-field').focus(function (event) {
            editButtonHandler.debug('focused, event.target - ' + event.target);
            AJS.$(event.target).addClass("focused");

        });
        AJS.$('.story-editor-field').blur(function (event) {
            editButtonHandler.debug('blurred, event.target - ' + event.target);
            AJS.$(event.target).removeClass("focused");
        });

    }

    this.assignInsertLinkHandlers = function (story) {

        AJS.$(".insert-element-link-div").mouseenter(function (event) {
            var div = event.target;
            editButtonHandler.debug("> mouse enter on - insert-element-link-div");
//            editButtonHandler.debug("hiding all insert links");
//            AJS.$(".insert-element-link").hide();
//            editButtonHandler.debug("div - " + div);

            var target = event.target;
            if (AJS.$(target).hasClass("insert-element-link-div")) {
                AJS.$(target).children(".insert-element-link").show();
            } else {
                // this is the case of hover over text area input field
                AJS.$(target)
                    .closest(".insert-element-link-div")
                    .children(".insert-element-link")
                    .show();
            }

            AJS.$(div).children(".insert-element-link").show();

            editButtonHandler.debug("# mouse enter on - insert-element-link-div");
        });

        AJS.$(".insert-element-link-div").mouseleave(function (event) {

            editButtonHandler.debug("> mouse leave on - insert-element-link-div");
            editButtonHandler.debug("event.target - " + event.target);

            var target = event.target;
            var insertLinkElement = null;
            if (AJS.$(target).hasClass("insert-element-link-div")) {
                insertLinkElement = AJS.$(target).children(".insert-element-link");
            } else {
                // this is the case of hover over text area input field
                insertLinkElement = AJS.$(target)
                    .closest(".insert-element-link-div")
                    .children(".insert-element-link");
            }

            var isPressed = insertLinkElement.attr("pressed");
            if (isPressed == "true") {
                editButtonHandler.debug("Not hiding the insert button as is currently pressed");
            } else {
                editButtonHandler.debug("Hiding the insert button as pressed is - " + isPressed);
                insertLinkElement.hide();
            }

            editButtonHandler.debug("# mouse leave on - insert-element-link-div");
        });

        AJS.$(".insert-dropdown-content").on({

            "aui-dropdown2-show": function (event) {
                var target = event.target;
                editButtonHandler.debug("showing dropdown, event.target.id - " + AJS.$(target).attr("id"));
                var triggerDivId = AJS.$(target).attr("trigger-div-id");
                editButtonHandler.debug("event.target.attr(\"trigger-div-id\") - " + triggerDivId);

                var triggerDiv = AJS.$("#" + triggerDivId);
                var insertLink = triggerDiv.children(".insert-element-link");
                insertLink.attr("pressed", "true");
            },

            "aui-dropdown2-hide": function (event) {

                var target = event.target;
                editButtonHandler.debug("hiding dropdown, event.target.id - " + AJS.$(target).attr("id"));

                var triggerDivId = AJS.$(target).attr("trigger-div-id");
                editButtonHandler.debug("event.target.attr(\"trigger-div-id\") - " + triggerDivId);
                var triggerDiv = AJS.$("#" + triggerDivId);
                var insertLink = triggerDiv.children(".insert-element-link");

                insertLink.attr("pressed", "false");

                var isHovered = triggerDiv.is(":hover");
                if (isHovered) {
                    editButtonHandler.debug("NOT hiding the insert link");
                } else {
                    editButtonHandler.debug("hiding the insert link");
                    insertLink.hide();
                }
            }

        });

    }

    this.assignShowElementOperationsOnHover = function (story) {

        // description
        {
            AJS.$(".story-element-container").mouseenter(function (event) {
                editButtonHandler.debug("> mouse enter on - story-element-container");
                editButtonHandler.debug("event.target - " + event.target);

//                editButtonHandler.debug("hiding all insert links");
//                AJS.$(".insert-element-link").hide();

                var target = event.target;
                if (AJS.$(target).hasClass("story-element-container")) {
                    AJS.$(target).children(".element-operations-container").show();
                } else {
                    // this is the case of hover over text area input field
                    AJS.$(target)
                        .closest(".story-element-container")
                        .children(".element-operations-container")
                        .show();
                }

                editButtonHandler.debug("# mouse enter on - story-element-container");
            });

            AJS.$(".story-element-container").mouseleave(function (event) {

                editButtonHandler.debug("> mouse leave on - story-element-container");
                editButtonHandler.debug("event.target - " + event.target);

                var target = event.target;
                if (AJS.$(target).hasClass("story-element-container")) {
                    AJS.$(target).children(".element-operations-container").hide();
                } else {
                    // this is the case of hover over text area input field
                    AJS.$(target)
                        .closest(".story-element-container")
                        .children(".element-operations-container")
                        .hide();
                }

                editButtonHandler.debug("# mouse leave on - story-element-container");
            });
        }

    }

    this.addInsertLinks = function (story) {

        // before Meta
        {
            if (story.description == null && story.meta != null) {
                var templateObj = new Object();
                templateObj.dropdownItems = [];
                templateObj.triggerDivId = "insertTriggerDivBeforeMeta";
                templateObj.dropdownDivId = "insertDropdownDivBeforeMeta";
                var insertDescriptionLinkInfo = new Object();
                insertDescriptionLinkInfo.text = "Description";
                insertDescriptionLinkInfo.onClickFunction = "insertElement";
                insertDescriptionLinkInfo.elementName = "description";
                templateObj.dropdownItems.push(insertDescriptionLinkInfo);
                var insertBeforeNarrativeHtml = execspec.viewissuepage.editstory.rich.renderInsertLinkDiv(templateObj);
                AJS.$('#insertLinkContainerBeforeMeta').html(insertBeforeNarrativeHtml);
            } else {
                AJS.$('#insertLinkContainerBeforeMeta').html("");
            }
        }

        // Meta properties
        {
            if (story.meta != null) {
                var templateObj = new Object();
                templateObj.dropdownItems = [];
                templateObj.triggerDivId = "insertTriggerDivMetaProperty";
                templateObj.dropdownDivId = "insertDropdownDivMetaProperty";
                var insertMetaPropertyLinkInfo = new Object();
                insertMetaPropertyLinkInfo.text = "New meta field";
                insertMetaPropertyLinkInfo.onClickFunction = "insertElement";
                insertMetaPropertyLinkInfo.elementName = "metaField";
                templateObj.dropdownItems.push(insertMetaPropertyLinkInfo);
                var insertMetaPropertyHtml = execspec.viewissuepage.editstory.rich.renderInsertLinkDiv(templateObj);
                AJS.$('#insertLinkContainerMetaProperty').html(insertMetaPropertyHtml);
            }

        }

        // before Narrative
        {
            var templateObj = new Object();
            templateObj.triggerDivId = "insertBeforeNarrativeTriggerDiv";
            templateObj.dropdownDivId = "insertBeforeNarrativeDropdownDiv";

            var insertDescriptionLinkInfo = new Object();
            insertDescriptionLinkInfo.text = "Description";
            insertDescriptionLinkInfo.onClickFunction = "insertElement";
            insertDescriptionLinkInfo.elementName = "description";

            var insertMetaLinkInfo = new Object();
            insertMetaLinkInfo.text = "Meta";
            insertMetaLinkInfo.onClickFunction = "insertElement";
            insertMetaLinkInfo.elementName = "meta";

            templateObj.dropdownItems = [];
            if (story.description == null && story.meta == null) {
                templateObj.dropdownItems.push(insertDescriptionLinkInfo);
                templateObj.dropdownItems.push(insertMetaLinkInfo);
            } else if (story.meta == null) {
                templateObj.dropdownItems.push(insertMetaLinkInfo);
            }

            if (templateObj.dropdownItems.length > 0) {
                var insertBeforeNarrativeHtml = execspec.viewissuepage.editstory.rich.renderInsertLinkDiv(templateObj);
                AJS.$('#insertLinkContainerBeforeNarrative').html(insertBeforeNarrativeHtml);
            } else {
                AJS.$('#insertLinkContainerBeforeNarrative').html("");
            }
        }


    }

    this.richTextEditorClicked = function (event) {

        this.debug("> richTextEditorClicked");

        var richEditorButtons = execspec.viewissuepage.editstory.rich.renderRichEditorOperationButtons();
        AJS.$("#editorOperationsButtons").html(richEditorButtons);

        AJS.$("#richTextEditorButton").attr("aria-pressed", "true");
        AJS.$("#rawTextEditorButton").attr("aria-pressed", "false");

        AJS.$("#rawEditStoryContainer").hide();
        AJS.$("#richEditStoryContainer").show();

        event.preventDefault();
        this.debug("# richTextEditorClicked");
    }

    this.rawTextEditorClicked = function (event) {

        this.debug("> rawTextEditorClicked");

        var rawEditorButtons = execspec.viewissuepage.editstory.renderRawEditorOperationButtons();
        AJS.$("#editorOperationsButtons").html(rawEditorButtons);

        AJS.$("#richTextEditorButton").attr("aria-pressed", "false");
        AJS.$("#rawTextEditorButton").attr("aria-pressed", "true");

        AJS.$("#richEditStoryContainer").hide();
        AJS.$("#rawEditStoryContainer").show();

        event.preventDefault();
        this.debug("# rawTextEditorClicked");
    }

    this.getArrayIndexFromPath = function (str) {
        this.debug("> getArrayIndexFromPath");
        var regExp = new RegExp('\\[(\\d+)\\]$');
        var match = regExp.exec(str);
        if (match != null) {
            return match[1];
        } else {
            return null;
        }
        this.debug("# getArrayIndexFromPath");
    }

    this.bindInputElementsToModel = function () {

        this.debug("> bindInputElementsToModel");

        this.debug("story before binding:\n" + JSON.stringify(storyController.currentStory, null, "\t"));

        AJS.$("#richStoryEditContent").find(".story-editor-field").each(
            function () {
//                editButtonHandler.debug("> bindInputElementsToModel.each(story-editor-field)");
                var fieldName = AJS.$(this).attr("name");
                var fieldValue = AJS.$(this).attr("value");
                editButtonHandler.debug("fieldName = " + fieldName + ", value = " + fieldValue);

                var path = fieldName.split('.');
                var obj = storyController.currentStory;
                for (var i = 0; i < path.length - 1; i++) {
                    var pathPart = path[i];
//                    if (obj[ pathPart] == null) {
//                        obj[pathPart] = {};
//                    }
                    editButtonHandler.debug("### checking if fieldName part ends in array index - " + pathPart);
                    var arrayIndexFromPath = editButtonHandler.getArrayIndexFromPath(pathPart);
                    if (arrayIndexFromPath != null) {
                        editButtonHandler.debug("### fieldName part ends in array index - " + pathPart);
                        var partWithoutIndex = pathPart.substr(0, pathPart.length - (arrayIndexFromPath.length + 2));
                        editButtonHandler.debug("### partWithoutIndex " + partWithoutIndex);
                        obj = obj[partWithoutIndex][arrayIndexFromPath];
                    } else {
                        obj = obj[pathPart];
                    }

                }
                obj[path[path.length - 1]] = fieldValue;

                //console.log("s before - '" + s + "'");
//                fieldName = fieldName.replace(/\[(\w+)\]/g, '.$1');  // convert indexes to properties
//                //console.log("s after - '" + s + "'");
//                fieldName = fieldName.replace(/^\./, ''); // strip leading dot
//                var token = fieldName.split('.');
//                var o = storyController.currentStory;
//                while (token.length) {
//                    var n = token.shift();
//                    editButtonHandler.debug("processing token - " + n);
//                    o = o[n];
//                    o = fieldValue;
//                }

//                editButtonHandler.debug("# bindInputElementsToModel.each(story-editor-field)");
            }
        );

        this.debug("story after binding:\n" + JSON.stringify(storyController.currentStory, null, "\t"));

        this.debug("# bindInputElementsToModel");
    }

    this.insertElement = function (event, elementName) {

        this.debug("> insertElement");
        this.debug("elementName = " + elementName);

        this.bindInputElementsToModel();

        if (elementName == "description") {
            this.insertDescription();
        } else if (elementName == "meta") {
            this.insertMeta();
        } else if (elementName == "metaField") {
            this.insertMetaField();
        }

        this.assignRichEditorHandlers(storyController.currentStory);
        this.assignShowElementOperationsOnHover(storyController.currentStory);

        event.preventDefault();
        this.debug("# insertElement");
    }

    this.removeElement = function (event, elementName, index) {

        this.debug("> removeElement");
        this.debug("elementName - " + elementName);

        this.bindInputElementsToModel();

        if (elementName == "description") {
            this.removeDescription();
        } else if (elementName == "meta") {
            this.removeMeta();
        } else if (elementName == "metaField") {
            this.removeMetaField(index);
        }

        this.assignRichEditorHandlers(storyController.currentStory);
        this.assignShowElementOperationsOnHover(storyController.currentStory);

        event.preventDefault();
        this.debug("# removeElement");
    }

    this.insertDescription = function () {

        this.debug("> insertDescription");

        storyController.currentStory.description = "";

        var descriptionHtml = execspec.viewissuepage.editstory.rich.renderStoryDescriptionField(storyController.currentStory);
        AJS.$("#storyDescriptionContainer").html(descriptionHtml);

        this.debug("# insertDescription");
    }

    this.removeMeta = function () {

        this.debug("> removeMeta");
        storyController.currentStory.meta = null;

        AJS.$("#storyMetaContainer").html("");
        this.debug("# removeMeta");
    }

    this.removeMetaField = function (index) {

        this.debug("> removeMetaField");

        this.debug("story before removing property at index - " + index
            + ":\n" + JSON.stringify(storyController.currentStory, null, "\t"));

        storyController.currentStory.meta.properties.splice(index, 1);

        this.debug("story after removing property at index - " + index
            + ":\n" + JSON.stringify(storyController.currentStory, null, "\t"));

        var metaHtml = execspec.viewissuepage.editstory.rich.renderStoryMeta(storyController.currentStory);
        AJS.$("#storyMetaContainer").html(metaHtml);
        this.debug("# removeMetaField");
    }


    this.removeDescription = function () {

        this.debug("> removeDescription");
        storyController.currentStory.description = null;

        AJS.$("#storyDescriptionContainer").html("");
        this.debug("# removeDescription");
    }

    this.insertMeta = function () {

        this.debug("> insertMeta");

        var meta = new Object();
        meta.keyword = "Meta:";
        meta.properties = [];
        storyController.currentStory.meta = meta;

        var metaHtml = execspec.viewissuepage.editstory.rich.renderStoryMeta(storyController.currentStory);
        AJS.$("#storyMetaContainer").html(metaHtml);

        this.debug("# insertMeta");
    }

    this.insertMetaField = function (fieldName, fieldValue) {

        this.debug("> insertMetaField");

        var meta = storyController.currentStory.meta;
        var newEntry = new Object();
        if (fieldName == null) {
            newEntry.name = null;
        } else {
            newEntry.name = fieldName;
        }
        if (fieldValue == null) {
            newEntry.value = null;
        } else {
            newEntry.value = fieldValue;
        }
        meta.properties.push(newEntry);

        var metaHtml = execspec.viewissuepage.editstory.rich.renderStoryMeta(storyController.currentStory);
        AJS.$("#storyMetaContainer").html(metaHtml);

        this.debug("# insertMetaField");
    }

    this.insertGivenStories = function (event) {

        this.debug("> insertGivenStories");

        var givenStories = new Object();
        givenStories.keyword = "GivenStories:";
        storyController.currentStory.givenStories = givenStories;

        storyView.editStory(storyController.currentStory);

        event.preventDefault();
        this.debug("# insertGivenStories");
    }

    this.insertLifecycle = function (event) {

        this.debug("> insertLifecycle");

        var lifecycle = new Object();
        lifecycle.keyword = "Lifecycle:";
        storyController.currentStory.lifecycle = lifecycle;

        storyView.editStory(storyController.currentStory);

        event.preventDefault();
        this.debug("# insertLifecycle");
    }

    this.insertScenario = function (event) {

        this.debug("> insertScenario");

        var scenario = new Object();
        scenario.keyword = "Scenario:";
        if (storyController.currentStory.scenarios == null) {
            storyController.currentStory.scenarios = [];
        }
        storyController.currentStory.scenarios.push(scenario);

        storyView.editStory(storyController.currentStory);

        event.preventDefault();
        this.debug("# insertScenario");
    }

    this.saveStory = function (event) {

        this.debug("> saveStory");

        this.bindInputElementsToModel();

        // remove the asString field as we are saving from the rich editor
        storyController.currentStory.asString = null;
        storyController.saveStoryAsModel();

        event.preventDefault();
        this.debug("# saveStory");
    }

}