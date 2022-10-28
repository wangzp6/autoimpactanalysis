<template>
    <div>
        <textarea id="mytextarea" style="height: 300px"></textarea>
    </div>
</template>

<script>
    export default {
        name: "Tinymce",
        mounted(){
            tinymce.init({
                selector: '#mytextarea',
                branding: false,
                toolbar: 'undo redo | styleselect | bold italic | table | code | searchreplace',
                table_toolbar: "tableprops tabledelete | tableinsertrowbefore tableinsertrowafter tabledeleterow | tableinsertcolbefore tableinsertcolafter tabledeletecol searchreplace",
                menu: {
                    edit: {title: 'Edit', items: 'undo redo | cut copy paste pastetext | selectall'},
                    insert: {title: 'Insert', items: 'link media | template hr'},
                    format: {title: 'Format', items: 'bold italic underline strikethrough superscript subscript | formats | removeformat'},
                    table: {title: 'Table', items: 'inserttable tableprops deletetable | cell row column'},
                    tools: {title: 'Tools', items: 'spellchecker code'}
                },
                plugins: "table image code searchreplace",
                language: 'zh_CN',
                menubar: false,
                //富文本创建之前
                setup: function(editor) {
                    console.log('setup:',editor);
                    //监听click事件
                    editor.on('click', function(e) {
                        //获取富文本框的值
                        console.log('setup:click:',editor.getContent());
                    });
                },
                //富文本渲染之后
                init_instance_callback : function(editor) {
                    console.log("Editor: " + editor.id + " is now initialized.");
                    console.log('init_instance_callback:',editor);
                    //设置富文本的值
                    editor.setContent('<p>hello world</p>')
                    // editor.setContent(this.table)
                }
            });
        },
        data(){
            return{
                image: '',
                table: '',
            }
        }
    }
</script>
