<template>
    <div id="vue-tinymce">
        <vue-tinymce
                v-model="editorVlaue"
                :setting="setting"/>
    </div>
</template>

<script>
    import baseURL from "../../utils/baseURL";
    export default {
        name: 'vueTinymceEditor',
        data() {
            return {
                baseUploadUrl:baseURL,
                editorVlaue: this.content != null ? this.content : "",
                setting: {
                    menubar: true,  // 菜单栏
                    //inline: true,   //开启内联模式
                    readonly: false, // 只读
                    toolbar:
                        "undo redo | fullscreen | searchreplace |formatselect alignleft aligncenter alignright alignjustify | link unlink | numlist bullist | image media table emoticons | fontselect fontsizeselect forecolor backcolor | bold italic underline strikethrough | indent outdent | superscript subscript | removeformat | wordcount code",
                    toolbar_drawer: "sliding",
                    quickbars_selection_toolbar: "removeformat | bold italic underline strikethrough | fontsizeselect forecolor backcolor",
                    plugins: "link image media table lists fullscreen quickbars wordcount paste emoticons code searchreplace searchreplace",
                    language: 'zh_CN',
                    branding: false,
                    height: 500,
                    font_family_formats: "宋体='宋体';仿宋='仿宋';微软雅黑='微软雅黑';楷体='楷体';;隶书='隶书';幼圆='幼圆';Andale Mono=andale mono,times;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;Comic Sans MS=comic sans ms,sans-serif;Courier New=courier new,courier;Georgia=georgia,palatino;Helvetica=helvetica;Impact=impact,chicago;Symbol=symbol;Tahoma=tahoma,arial,helvetica,sans-serif;Terminal=terminal,monaco;Times New Roman=times new roman,times;Trebuchet MS=trebuchet ms,geneva;Verdana=verdana,geneva;Webdings=webdings;Wingdings=wingdings",
                    font_size_formats: '12px 14px 16px 18px 20px 24px 26px 28px 30px 32px 34px 36px 38px',
                    line_height_formats: '1 1.2 1.5 2 2.5',
                    indent_use_margin: true,
                    content_style: 'body { font-family:微软雅黑,Arial,sans-serif; font-size:16px;line-height:2 }',
                    // 图片上传地址(自动上传)
                    // images_upload_url: baseUploadUrl + 'image/vueTinymce/',
                    // 允许粘贴图片上传,及格式
                    paste_data_images: true,
                    images_file_types: 'jpeg,jpg,png,gif,bmp,webp',
                    paste_convert_word_fake_lists:false,
                    paste_retain_style_properties: "all",
                    // paste_word_valid_elements:"table[width],tr,td[colspan|rowspan|width],th[colspan|rowspan|width],thead,tfoot,tbody,h1,h2,h3,h4,h5,img,p",
                    paste_word_valid_elements: "*[*]", //word需要它
                    paste_webkit_styles: "all",
                    paste_merge_formats: true,
                    nonbreaking_force_tab: false,
                    paste_auto_cleanup_on_paste: false,
                    // 此处为图片上传处理函数 (手动上传)
                    /*images_upload_handler: (blobInfo, success, failure, progress) => {
                        console.log('上传处理器：');
                        // 方法1：用base64的图片形式上传图片
                        // const img = 'data:image/jpeg;base64,' + blobInfo.base64()
                        // success(img)

                        // 方法2：上传oos
                        const xhr = new XMLHttpRequest();
                        xhr.withCredentials = false;
                        xhr.open('POST', baseUploadUrl + 'image/vueTinymce/');
                        // xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded;charset=utf-8')
                        // xhr.setRequestHeader('x-token', this.$store.getters.token)
                        xhr.upload.onprogress = function (e) {
                            progress(e.loaded / e.total * 100)
                        };
                        // 成功结果
                        xhr.onload = function () {
                            if (xhr.status != 200) {
                                failure('HTTP Error: ' + xhr.status, {remove: true});
                                return
                            }
                            const json = JSON.parse(xhr.responseText);
                            success(json.data.url);
                        };
                        // 失败结果
                        xhr.onerror = function () {
                            failure('Image upload failed due to a XHR Transport error. Code: ' + xhr.status)
                        };
                        // 请求数据
                        const formData = new FormData();
                        formData.append('file', blobInfo.blob(), blobInfo.filename());
                        xhr.send(formData)
                    },*/
                }
            }
        },
        props: {
            // 接收值父组件传递值
            content: String
        }
        ,
        watch: {
            editorVlaue: function (newNum, oldNum) {
                // 修改调用者传入的值
                this.$emit('update:content', newNum)
            }
        }
    }
</script>

<style>
    /* 富文本按钮功能优先级,最好 > 2500  弹出默认2000+ */
    .tox-tinymce-aux {
        z-index: 9999 !important;
    }
</style>
