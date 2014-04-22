function EditStoryView(controller) {

    var $this = this;
    var $c = controller;

    this.dialog = undefined;
    this.totalScenarios = 0;
    this.scenarioIndex = 0;

    this.init = function () {

        console.log("initializing EditStoryView");

        // Add dialog trigger to toolbar button
        AJS.$("a.add-new-story-button").click(function () {
            $this.showDialog();
            return false;
        });
    }

    this.hideDialog = function () {
        this.dialog.hide();
    }

    this.showDialog = function () {

        if (this.dialog !== undefined) {
            console.log("dialog !== undefined");
            AJS.$('#editStoryDialog').remove();
        }

        this.dialog = new AJS.Dialog({
            width: 960,
            height: 500,
            id: "editStoryDialog",
            closeOnOutsideClick: true
        });


        // PAGE 0 (first page)
        // adds header for first page
        this.dialog.addHeader("Add New Story");

        var narrativePanelClass = "narrativePanel";
        this.dialog.addPanel("Narrative: ", "Default content for panel.", narrativePanelClass);
        AJS.$("." + narrativePanelClass).html(execspec.viewissuepage.editstory.narrative());


        /*********************************************************************************
         ** Buttons
         **********************************************************************************/
        this.dialog.addButton("Add", function () {
            $this.addScenarioButtonHandler();
            return false;
        });
        // add first scenario panel - as one scenario should always be present at minimum
        this.addScenarioButtonHandler();

        this.dialog.addButton("Remove", function () {
            $this.removeScenarioButtonHandler();
            return false;
        });

        this.dialog.addButton("Up", function () {
            console("Moving up");
        });

        this.dialog.addButton("Down", function () {
            console("Moving down");
        });

        this.dialog.addButton("Edit", function () {
            console("Editing");
        });

        this.dialog.addButton("Save", function () {
            $this.saveButtonHandler();
            return false;
        });

        this.dialog.addLink("Cancel", function () {
            $this.dialog.remove();
        }, "#");

        // PREPARE FOR DISPLAY
        // start first page, first panel
        this.dialog.gotoPage(0);
        this.dialog.gotoPanel(0);
        this.dialog.show();

        console.log("dialog was shown successfully");
    }

    this.addScenarioButtonHandler = function () {

        console.log("Adding");

        this.scenarioIndex += 1;

        var panelClass = "scenarioPanel" + this.scenarioIndex;
        this.dialog.addPanel("Scenario " + this.scenarioIndex + ":", "Default content for panel", panelClass);
        AJS.$("." + panelClass).html(execspec.viewissuepage.editstory.scenarioInput());

        this.totalScenarios += 1;
    };

    this.removeScenarioButtonHandler = function () {

        console.log("Removing");

        // check if the selected item is the narrative section
        var $liToRemove = AJS.$("li.selected");
        var $parentUl = $liToRemove.parent()
        var $firstLi = $parentUl.children("li").first();

        if ($liToRemove.is($firstLi)) {
            alert("Story must have a narrative!");
        } else {
            if (this.totalScenarios <= 1) {
                alert("Story must have minimum of one scenario!");
            } else {
                // select the previous entry
                var $prevElement = $liToRemove.prev();
                // set selected

                // simulate button click
                $prevElement.find("button").click();

                $liToRemove.remove();
                this.totalScenarios -= 1;
            }
        }
    };

    this.saveButtonHandler = function () {
        console.log("Saving");
        $c.saveStoryInput();
    }

    this.getNarrative = function () {
        var e = AJS.$("#inputNarrative");
        var narrativeVal = e.val();
        // escape line breaks
        console.log("escaping line breaks in:\n" + narrativeVal);
//        narrativeVal = narrativeVal.replace(/\r?\n/g, '<br />');
        console.log("after escaping:\n" + narrativeVal);
        return narrativeVal;
    }

    this.getScenarios = function () {
        var scenariosArray = new Array();
        AJS.$(".scenario-input").each(function () {
            var scenarioText = this.value;
            console.log("escaping line breaks in:\n" + scenarioText);
            scenarioText = scenarioText.replace(/\r?\n/g, '\n');
            console.log("after escaping:\n" + scenarioText);

            var scenario = scenarioText;
            scenariosArray.push(scenario);
        });
        return scenariosArray;
    }
}



