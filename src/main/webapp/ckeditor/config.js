/**
 * @license Copyright (c) 2003-2016, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function (config) {
    // Define changes to default configuration here.
    // For complete reference see:
    // http://docs.ckeditor.com/#!/api/CKEDITOR.config

    config.toolbar = [{ 
        name: 'all', items: [
            'Bold', 'Italic', 'Underline', '-', 
            'Strike', 'Subscript', 'Superscript', '-',
            'RemoveFormat', '-', 
            'SimpleLink', 'Unlink', '-',
            'SimpleImage', 'Table', 'HorizontalRule', '-',
            'Undo', 'Redo', '-',
            'NumberedList', 'BulletedList', '-',
            'Outdent', 'Indent'
        ] 
    }];

    config.skin = 'minimalist';
    config.disableNativeSpellChecker = false;
    config.extraPlugins = 'SimpleLink,SimpleImage';
};