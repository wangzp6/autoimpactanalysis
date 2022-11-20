<template>
    <div>
        <div class="comeOn">
            <p style="font-size:20pt;font-weight:bold;">我就是你想看到的文档哟！！！！！</p>
        </div>
        <el-button @click="download">导它导它</el-button>
        <div class="about">
            <button @click="this.exportClick">aaaaaaaaaaaa</button>
        </div>
    </div>
</template>

<script>
    import $ from 'jquery';
    import htmlDocx from 'html-docx-js/dist/html-docx';
    import saveAs from 'file-saver';
    export default {
        name: "FileDownload",
        methods:{
            download(){
                $(".comeOn").wordExport('生成word文档'); //此处可以写上自己想取的名字哟！！！
            },

            /*handleSaveToPdf() {
                this.$refs.html2Pdf.generatePdf()
            },
        onProgress() {
    },
    hasStartedGeneration() {
    },
    hasGenerated() {
    },
            handleSaveToWord() {
                var converted = htmlDocx.asBlob(this.content);
                saveAs(converted, 'test.docx');
            },*/
            exportClick() {
                console.log("aaaaaaaaaaa");
                var content = ` <h1>This is an about page</h1>
                            <h2>This is an about page</h2>`
                var page = '<!DOCTYPE html><html><head><meta charset="UTF-8"></head><body>' + content + '</body></html>'
                var converted = htmlDocx.asBlob(page);
                // 用 FielSaver.js里的保存方法 进行输出
                saveAs(converted, 'test.docx');
            }
        }
    }
</script>

<style scoped>

</style>