var editButtonHandler;

function StoryEditHandler() {

    editButtonHandler = this;

    this.debugOn = true;

    this.debug = function (msg) {
        if (this.debugOn) {
            console.log("[DEBUG StoryEditHandler] " + msg);
        }
    }

    this.init = function () {
        this.debug("initialized");
    }


    this.assignRichEditorHandlers = function (story) {

        this.debug("> assignRichEditorHandlers");

        this.addInsertLinks(story);

        this.assignInsertLinkHandlers(story);

        this.assignAutoHeightForTextAreas(story);


        this.debug("# assignRichEditorHandlers");
    }

    this.assignAutoHeightForTextAreas = function (story) {

        // set height dynamically on key press
        AJS.$('.rich-story-editor-field').keydown(function (event) {
            editButtonHandler.debug('keydown, event.target - ' + event.target);
            var key = event.which;
            editButtonHandler.debug('keydown, event.which - ' + key);
            if (key == 13) {
                editButtonHandler.debug('return pressed, resizing text area');
                editButtonHandler.resizeTextArea(event);
            }
        });

        AJS.$('.rich-story-editor-field').keyup(function (event) {
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
        AJS.$('.rich-story-editor-field').focus(function (event) {
            editButtonHandler.debug('focused, event.target - ' + event.target);
            AJS.$(event.target).addClass("focused");

        });
        AJS.$('.rich-story-editor-field').blur(function (event) {
            editButtonHandler.debug('blurred, event.target - ' + event.target);
            AJS.$(event.target).removeClass("focused");
        });

    }

    this.assignInsertLinkHandlers = function (story) {

        AJS.$(".insert-element-trigger-div").mouseenter(function (event) {
            var div = event.target;
            editButtonHandler.debug("mouse enter on - divBeforeNarrative");
            editButtonHandler.debug("hiding all insert links");
            AJS.$(".insert-element-link").hide();
            editButtonHandler.debug("div - " + div);
            AJS.$(div).children(".insert-element-link").show();
        });

        AJS.$(".insert-element-trigger-div").mouseleave(function (event) {

            var div = event.target;
            editButtonHandler.debug("> mouse leave on - divBeforeNarrative");
            editButtonHandler.debug("div - " + div);

//            var isPressed = AJS.$(".insert-element-link").attr("pressed");
            var insertLinkElement = AJS.$(div).children(".insert-element-link");
            var isPressed = insertLinkElement.attr("pressed");
            if (isPressed == "true") {
                editButtonHandler.debug("Not hiding the insert button as is currently pressed");
            } else {
                editButtonHandler.debug("Hiding the insert button as pressed is - " + isPressed);
                insertLinkElement.hide();
            }

            editButtonHandler.debug("# mouse leave on - divBeforeNarrative");
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

    this.addInsertLinks = function (story) {

        // before Narrative
        {
            var templateObj = new Object();
            templateObj.triggerDivId = "insertBeforeNarrativeTriggerDiv";
            templateObj.dropdownDivId = "insertBeforeNarrativeDropdownDiv";

            var insertDescriptionLinkInfo = new Object();
            insertDescriptionLinkInfo.text = "Description";
            insertDescriptionLinkInfo.onClickFunction = "insertDescription";
            var insertMetaLinkInfo = new Object();
            insertMetaLinkInfo.text = "Meta";
            insertMetaLinkInfo.onClickFunction = "insertMeta";

            templateObj.dropdownItems = [];
            if (story.description == null && story.meta == null) {
                templateObj.dropdownItems.push(insertDescriptionLinkInfo);
                templateObj.dropdownItems.push(insertMetaLinkInfo);
            } else if (story.meta == null) {
                templateObj.dropdownItems.push(insertMetaLinkInfo);
            }

            if (templateObj.dropdownItems.length > 0) {
                var insertBeforeNarrativeHtml = execspec.viewissuepage.editstory.rich.renderInsertLinkDiv(templateObj);
                AJS.$('#insertLinkBeforeNarrativeContainer').html(insertBeforeNarrativeHtml);
            } else {
                AJS.$('#insertLinkBeforeNarrativeContainer').html("");
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

    this.insertDescription = function (event) {

        this.debug("> insertDescription");

        storyController.currentStory.description = "";

        var descriptionHtml = execspec.viewissuepage.editstory.rich.renderStoryDescriptionField(storyController.currentStory);
        AJS.$("#storyDescriptionContainer").html(descriptionHtml);

        this.assignRichEditorHandlers(storyController.currentStory);

        event.preventDefault();
        this.debug("# insertDescription");
    }

    this.insertMeta = function (event) {

        this.debug("> insertMeta");

        var meta = new Object();
        meta.keyword = "Meta:";
        storyController.currentStory.meta = meta;

        var metaHtml = execspec.viewissuepage.editstory.rich.renderStoryMetaField(storyController.currentStory);
        AJS.$("#storyMetaContainer").html(metaHtml);

        this.assignRichEditorHandlers(storyController.currentStory);

        event.preventDefault();
        this.debug("# insertMeta");
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

}