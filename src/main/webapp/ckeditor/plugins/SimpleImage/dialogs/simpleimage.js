CKEDITOR.dialog.add("simpleimageDialog", function (editor) {
    var proportionalScale, isSizePreload, isBase64Allowed, validationMessage;
    isSizePreload = false;
    isBase64Allowed = CKEDITOR.config.simpleImageBase64allowed || false;
    validationMessage = isBase64Allowed ? "Source cannot be empty." : "Source can neither be empty nor have Base64 format. Type an external url."


    function isBase64URL(url) {
        return /data\:image/.test(url);
    }


    return {
        allowedContent: "img[src,alt,width,height]",
        title: "Wstaw obrazek",
        minWidth: 400,
        minHeight: 80,
        resizable: CKEDITOR.DIALOG_RESIZE_NONE,
        contents: [{
                id: "SimpleImage",
                label: "Details",
                elements: [{
                        type: "text",
                        label: "Link do obrazka:",
                        id: "edp-src",
                        validate: CKEDITOR.dialog.validate.notEmpty(validationMessage),
                        setup: function (element) {
                            if (element.getAttribute("src")) {
                                if (element.getAttribute("width") && element.getAttribute("height")) {
                                    isSizePreload = true;
                                }
                                this.setValue(element.getAttribute("src"));
                            }
                        },
                        commit: function (element) {
                            element.setAttribute("src", this.getValue());
                        },
                        onChange: function () {
                            if (isBase64URL(this.getValue())) {
                                if (!isBase64Allowed) {
                                    this.setValue("");
                                    return;
                                }
                                if (!isSizePreload) {
                                    var img = new Image();
                                    var dialog = this.getDialog();
                                    img.onload = function (f) {
                                        if (f) {
                                            proportionalScale = this.width / this.height;
                                            dialog.setValueOf("Dimensions", "edp-width", this.width);
                                            dialog.setValueOf("Dimensions", "edp-height", this.height);
                                        }
                                    };
                                    img.src = this.getValue();
                                } else {
                                    isSizePreload = false;
                                }
                            }
                        }
                    }]
            }
        ],
        onShow: function () {
            var selection = editor.getSelection();
            var selector = selection.getStartElement()
            var element;

            if (selector) {
                element = selector.getAscendant('img', true);
            }

            if (!element || element.getName() != 'img') {
                element = editor.document.createElement('img');
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