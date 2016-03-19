CKEDITOR.plugins.add( 'SimpleLink', {
    icons: 'simplelink',
    init: function( editor ) {
        editor.addCommand( 'simplelink', new CKEDITOR.dialogCommand( 'simplelinkDialog' ) );
        editor.ui.addButton( 'SimpleLink', {
            label: 'Wstaw link',
            icons: 'simplelink',
            command: 'simplelink'
        });

        CKEDITOR.dialog.add( 'simplelinkDialog', this.path + 'dialogs/simplelink.js' );
    }
});