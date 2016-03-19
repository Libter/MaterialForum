CKEDITOR.dialog.add("simplelinkDialog", function (editor) {
    return {
        allowedContent: "a[href,target]",
        title: "Wstaw link",
        minWidth: 550,
        minHeight: 100,
        resizable: CKEDITOR.DIALOG_RESIZE_NONE,
        contents: [{
                id: "SimpleLink",
                label: "SimpleLink",
                elements: [{
                        type: "text",
                        label: "Link:",
                        id: "edp-URL",
                        validate: CKEDITOR.dialog.validate.notEmpty("Adres URL nie może być pusty!"),
                        setup: function (element) {
                            var href = element.getAttribute("href");
                            var isExternalURL = /^(http|https):\/\//;
                            if (href) {
                                if (!isExternalURL.test(href)) {
                                    href = "http://" + href;
                                }
                                this.setValue(href);
                            }
                        },
                        commit: function (element) {
                            var href = this.getValue();
                            var isExternalURL = /^(http|https):\/\//;
                            if (href) {
                                if (!isExternalURL.test(href)) {
                                    href = "http://" + href;
                                }
                                element.setAttribute("href", href);
                                if (!element.getText()) {
                                    element.setText(this.getValue());
                                }
                            }
                        }
                    }]
            }],
        onShow: function () {
            var selection = editor.getSelection();
            var selector = selection.getStartElement()
            var element;

            if (selector) {
                element = selector.getAscendant('a', true);
            }

            if (!element || element.getName() != 'a') {
                element = editor.document.createElement('a');
                if (selection) {
                    element.setText(selection.getSelectedText());
                }
                this.insertMode = true;
            } else {
                this.insertMode = false;
            }

            this.element = element;


            this.setupContent(this.element);
        },
        onOk: function () {
            var dialog = this;
            var anchorElement = this.element;

            this.commitContent(this.element);

            if (this.insertMode) {
                editor.insertElement(this.element);
            }
        }
    };
});
